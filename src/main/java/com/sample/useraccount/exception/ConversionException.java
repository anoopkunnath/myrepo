package com.sample.useraccount.exception;

/**
 * Exception class for conversion errors.
 */
public class ConversionException extends ApplicationException {

  public static String CONVERSION_ERROR = "CONVERSION_ERROR";

  public ConversionException(String errorCode, String errorMessage) {
    super(errorCode, errorMessage);
  }

}
