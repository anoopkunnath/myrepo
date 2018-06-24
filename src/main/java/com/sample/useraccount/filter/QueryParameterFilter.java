package com.sample.useraccount.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.annotation.Priority;
import javax.ws.rs.BeanParam;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.Priorities;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

/**
 * Created by anoopbalakrishnankunnath on 5/11/17.
 */
@Provider()
@Priority(value = Priorities.ENTITY_CODER + 1)
public class QueryParameterFilter implements ContainerRequestFilter {

  private static final String COMMA_DELIMITER = ",";

  @Context()
  private ResourceInfo resourceInfo;

  /**
   * Identifies query parameters with csv format values and splits them into array elements.
   *
   * @param requestContext RequestContext object
   * @throws IOException throws IOException
   */
  @Override()
  public void filter(final ContainerRequestContext requestContext) throws IOException {

    if (!HttpMethod.GET.equals(requestContext.getMethod())) {
      return;
    }

    final List<String> arrayTypeMethodParams = getArrayTypeMethodParameters();
    final MultivaluedMap<String, String> queryParams = requestContext
        .getUriInfo().getQueryParameters(true);

    final List<String> filterFields = queryParams.get("fields");
    if(filterFields != null && filterFields.size() > 0) {
      arrayTypeMethodParams.add("fields");
    }

    arrayTypeMethodParams
        .forEach(param -> {
          queryParams
              .entrySet()
              .forEach(entry -> {
                if (param.equals(entry.getKey())) {
                  final List<String> valueList = new ArrayList<>();
                  entry.getValue()
                      .forEach(value -> valueList
                          .addAll(Arrays.asList(value.split(COMMA_DELIMITER))));
                  entry.setValue(valueList);
                }
              });
        });
  }

  /**
   * Checks for csv format for a QueryParam field in a BeanParam.
   * Also, Checks for csv format for a QueryParam.
   *
   * @return array type method parameters
   */
  public List<String> getArrayTypeMethodParameters() {

    final List<String> arrayTypeMethodParams = new ArrayList<>();
    final List<Class<?>> supportedTypes = new ArrayList<>();
    supportedTypes.add(List.class);
    supportedTypes.add(Set.class);

    Arrays
        .stream(resourceInfo.getResourceMethod().getParameters())
        .forEach(param -> {
          if (param.getDeclaredAnnotation(BeanParam.class) != null) {
            Arrays
                .stream(param.getType().getDeclaredFields())
                .forEach(field -> {
                  if (field.getDeclaredAnnotation(QueryParam.class) != null
                      && supportedTypes.contains(field.getType())) {
                    arrayTypeMethodParams.add(field.getName());
                  }
                });
          } else if (param.getDeclaredAnnotation(QueryParam.class) != null
              && supportedTypes.contains(param.getType())) {
            arrayTypeMethodParams.add(param.getDeclaredAnnotation(QueryParam.class).value());
          }
        });

    return arrayTypeMethodParams;
  }

}
