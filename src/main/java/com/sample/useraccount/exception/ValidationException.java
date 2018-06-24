package com.sample.useraccount.exception;

/**
 * Exception class for validations.
 */
public class ValidationException extends ApplicationException {

  public static String VALIDATION_ERROR = "VALIDATION_ERROR";

  public ValidationException(String errorCode, String errorMessage) {
    super(errorCode, errorMessage);
  }

}
