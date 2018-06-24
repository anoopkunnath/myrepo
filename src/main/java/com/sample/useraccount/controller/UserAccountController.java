package com.sample.useraccount.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.useraccount.exception.ApplicationException;
import com.sample.useraccount.model.UserAccountModel;
import com.sample.useraccount.service.UserAccountService;
import com.sample.useraccount.validator.ParamValidator;

@RestController
@RequestMapping(value = "")
public class UserAccountController {

  @Autowired
  private ParamValidator validator;

  @Autowired
  private UserAccountService userAccountService;


  @GetMapping(value = "/user/{userId}/accounts", produces = "application/json")
  //@ResponseBody
  public UserAccountModel getUserDetails(@PathVariable String userId) {

    try {
      //validator.validate(userId);

      return userAccountService.findUserAccounts(userId);
    } catch (ApplicationException e) {
      final UserAccountModel response = new UserAccountModel();
      response.setErrorCode(e.getErrorCode());
      response.setErrorMessage(e.getErrorMessage());

      return response;
    }

  }

}
