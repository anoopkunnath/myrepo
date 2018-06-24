package com.sample.useraccount.converter;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.sample.useraccount.exception.ConversionException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * JsonConverter implementation based on the Jackson Databind library.
 *
 * @author Tony Piazza, Sean Alphonse
 * @version 1.0
 */
@Component("JacksonConverter")
@Primary() @Jackson()
public class JacksonConverter implements JsonConverter {
  public static final ObjectMapper mapper = new ObjectMapper().configure(
      DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
      .setSerializationInclusion(JsonInclude.Include.NON_NULL)
      .enable(SerializationFeature.INDENT_OUTPUT)
      .setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ"));

  /** Deserializes a JSON string into a Java object.
   * @param source JSON string.
   * @param valueType Class type.
   * @param <T> type of class to return.
   * @return A Java object representing the same information as found in the JSON string.
   */
  @Override()
  public <T> T fromJson(final String source, final Class<T> valueType) {
    try {
      return mapper.readValue(source, valueType);
    } catch (IOException e) {
      throw new ConversionException(e.getMessage(), e.getMessage());
    }
  }

  /** Deserializes a JSON string into a Java object.
   * @param source JSON string.
   * @param valueType JavaType class type.
   * @param <T> type of class to return.
   * @return A Java object representing the same information as found in the JSON string.
   */
  @Override()
  public <T> T fromJson(final String source, final JavaType valueType) {
    try {
      return mapper.readValue(source, valueType);
    } catch (IOException e) {
      throw new ConversionException(e.getMessage(), e.getMessage());
    }
  }

  /** Serializes a Java object into a JSON string.
   * @param source A Java object.
   * @param <T> type of class of the source.
   * @return A JSON string representing the data represented in the Java object.
   */
  @Override()
  public <T> String toJson(final T source) {
    try {
      return mapper.writeValueAsString(source);
    } catch (JsonProcessingException e) {
      throw new ConversionException(e.getMessage(), e.getMessage());
    }
  }
}
