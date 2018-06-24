package com.sample.useraccount.validator;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.sample.useraccount.exception.ValidationException;

/**
 * Validator class for parameters.
 */
@Component
public class ParamValidator {

  private static String customerIdPattern = "^\\d+$";

  public void validate(String customerId) {

    Pattern pattern = Pattern.compile(customerIdPattern);
    if(!pattern.matcher(customerId).matches()) {
      throw new ValidationException(ValidationException.VALIDATION_ERROR, "Invalid Customer Id");
    }
  }

}
