package com.sample.useraccount.exception;

/**
 * Exception class for service calls.
 */
public class ClientException extends ApplicationException {

  public static String SERVICE_NOT_AVAILABLE = "SERVICE_NOT_AVAILABLE";

  public ClientException(String errorCode, String errorMessage) {
    super(errorCode, errorMessage);
  }

}
