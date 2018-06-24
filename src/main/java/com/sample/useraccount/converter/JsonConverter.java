package com.sample.useraccount.converter;

import com.fasterxml.jackson.databind.JavaType;

/**
 * Simple interface that defines methods for converting between Java types
 * and JSON.
 *
 * @author Tony Piazza
 * @version 1.0
 */
public interface JsonConverter {
  /** Deserializes a JSON string into a Java object.
   * @param source JSON string.
   * @param type Class type.
   * @param <T> type of class to return.
   * @return A Java object representing the same information as found in the JSON string.
   */
  <T> T fromJson(String source, Class<T> type);

  /** Deserializes a JSON string into a Java object.
   * @param source JSON string.
   * @param type JavaType class type.
   * @param <T> type of class to return.
   * @return A Java object representing the same information as found in the JSON string.
   */
  <T> T fromJson(String source, JavaType type);

  /** Serializes a Java object into a JSON string.
   * @param source A Java object.
   * @param <T> type of class of the source.
   * @return A JSON string representing the data represented in the Java object.
   */
  <T> String toJson(T source);
}
