package com.sample.useraccount.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import org.springframework.beans.factory.annotation.Autowired;

import com.sample.useraccount.converter.Jackson;
import com.sample.useraccount.converter.JsonConverter;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Created by anoopbalakrishnankunnath on 4/24/17.
 */

@Provider()
@Priority(value = Priorities.ENTITY_CODER + 200)
public class FieldsFilter implements ContainerResponseFilter {

  private static final Logger FIELDS_FILTER_LOGGER = LoggerFactory.getLogger("fieldsFilterLogger");

  @Autowired() @Jackson() //@Named(value = "JacksonConverter")
  private JsonConverter jsonConverter;

  /**
   * Filters the fields in the document.
   *
   * @param requestContext
   * @param responseContext
   * @throws IOException
   */
  @Override()
  public void filter(final ContainerRequestContext requestContext,
      final ContainerResponseContext responseContext) throws IOException {

    JsonNode rootNode = null;
    final ObjectMapper mapper = JacksonConverter.mapper;

    final MultivaluedMap<String, String> queryParams = requestContext
        .getUriInfo().getQueryParameters(true);

    final List<String> filterFields = queryParams.get("fields");
    if (CollectionUtils.isNotEmpty(filterFields) && responseContext.hasEntity()) {
      final Object entity = responseContext.getEntity();
      if(ArrayList.class.equals(responseContext.getEntityClass())
        && ((ArrayList<?>) entity).size() == 0) {
        FIELDS_FILTER_LOGGER.info("No fields present for filtering.");
        return;
      }

      FIELDS_FILTER_LOGGER.info("Filtering fields from the response.");
      rootNode = mapper.readTree(jsonConverter.toJson(entity));

      filterFields("", rootNode, filterFields);
    }

    if (rootNode != null) {

      if (rootNode.isArray()) {
        final Class<?> entityClass = ((ArrayList<?>) responseContext.getEntity()).get(0).getClass();
        final JavaType parametricType = mapper.getTypeFactory()
            .constructParametricType(ArrayList.class, entityClass);
        responseContext.setEntity(jsonConverter
            .fromJson(mapper.writeValueAsString(rootNode), parametricType),
            entityClass.getAnnotations(), responseContext.getMediaType());
      } else {
        responseContext.setEntity(jsonConverter
            .fromJson(mapper.writeValueAsString(rootNode), responseContext.getEntityClass()),
            responseContext.getEntityAnnotations(), responseContext.getMediaType());
      }
    }
  }

  /**
   * Recursively filter the fields.
   *
   * @param parentKey
   * @param rootNode
   * @param filterFields
   */
  private void filterFields(final String parentKey, final JsonNode rootNode,
      final List<String> filterFields) {

    final boolean allNegate = filterFields.stream().allMatch(e -> e.startsWith("!"));

    if (rootNode.isArray()) {
      final Iterator<JsonNode> valueNodeIterator = rootNode.elements();
      while (valueNodeIterator.hasNext()) {
        filterFields(parentKey, valueNodeIterator.next(), filterFields);
      }
    } else {
      final Iterator<Map.Entry<String, JsonNode>> nodeIterator = rootNode.fields();
      while (nodeIterator.hasNext()) {
        final Map.Entry<String, JsonNode> currentNode = nodeIterator.next();

        final String currentNodeKey = currentNode.getKey();
        final JsonNode currentNodeValue = currentNode.getValue();

        String propertyKey;
        if (parentKey == null || parentKey.isEmpty()) {
          propertyKey = currentNodeKey;
        } else {
          propertyKey = parentKey + "." + currentNodeKey;
        }

        if (allNegate && filterFields.stream().anyMatch(filter -> filter
            .equals("!" + propertyKey) || filter.equals("!" + currentNodeKey))) {
          ((ObjectNode) rootNode).putNull(currentNodeKey);
        } else if (!allNegate && !filterFields.stream().anyMatch(filter -> filter
            .equals(propertyKey) || filter.startsWith(propertyKey + ".")
            || propertyKey.startsWith(filter + ".")
            || propertyKey.endsWith("." + filter)
            || propertyKey.contains("." + filter + ".")
            || containsNode(propertyKey, currentNodeValue, filter))) {
          ((ObjectNode) rootNode).putNull(currentNodeKey);
        }

        if (!currentNodeValue.isValueNode()) {
          filterFields(propertyKey, currentNodeValue, filterFields);
        }
      }
    }
  }

  /**
   * Check for the existence of filter node within a particular node.
   *
   * @param parentKey
   * @param valueNode
   * @param filter
   */
  private boolean containsNode(final String parentKey, final JsonNode valueNode,
      final String filter) {

    final List<String> subNodeKeyList = new ArrayList<>();
    containsNode(parentKey, valueNode, filter, subNodeKeyList);

    if (subNodeKeyList.size() > 0) {
      return true;
    }

    return false;
  }

  /**
   * Check for the existence of filter node within a particular node.
   *
   * @param parentKey
   * @param valueNode
   * @param filter
   */
  private void containsNode(final String parentKey, final JsonNode valueNode,
      final String filter, final List<String> subNodeKeyList) {

    if (valueNode.isArray()) {
      final Iterator<JsonNode> valueNodeIterator = valueNode.elements();
      while (valueNodeIterator.hasNext()) {
        containsNode(parentKey, valueNodeIterator.next(), filter, subNodeKeyList);
      }
    } else {
      final Iterator<Map.Entry<String, JsonNode>> fieldsIterator = valueNode.fields();
      while (fieldsIterator.hasNext()) {
        final Map.Entry<String, JsonNode> currentNode = fieldsIterator.next();

        final String currentNodeKey = currentNode.getKey();
        final JsonNode currentNodeValue = currentNode.getValue();

        String propertyKey;
        if (parentKey == null || parentKey.isEmpty()) {
          propertyKey = currentNodeKey;
        } else {
          propertyKey = parentKey + "." + currentNodeKey;
        }

        if (propertyKey.endsWith("." + filter)) {
          subNodeKeyList.add(propertyKey);
        }

        if (!currentNodeValue.isValueNode()) {
          containsNode(propertyKey, currentNodeValue, filter, subNodeKeyList);
        }
      }
    }
  }

}
