package com.sample.useraccount.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Error controller for the application.
 */
@RestController
public class AppErrorController implements ErrorController {

  @RequestMapping(value = "/error", method = RequestMethod.GET, produces = "application/json")
  public String error() {
    return "{ \"error\" : \"No mappings found for this URL.\" }";
  }

  @Override
  public String getErrorPath() {
    return "/error";
  }
}
