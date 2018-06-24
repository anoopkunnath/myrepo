package com.sample.useraccount.commands;

import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.sample.useraccount.model.UserAccountModel;

public class UserCommand extends HystrixCommand<UserAccountModel> {

  private String userId;

  public UserCommand(String userId) {
    super(HystrixCommandGroupKey.Factory.asKey("USER_KEY"));
    this.userId = userId;
  }

  @Override
  protected UserAccountModel run() throws Exception {
    return new RestTemplate()
        .getForObject("http://localhost:8081/user/{userId}", UserAccountModel.class, this.userId);
  }

  @Override
  public UserAccountModel getFallback() {
    UserAccountModel userAccountModel = new UserAccountModel();
    userAccountModel.setUserId("UXXXXX");

    return userAccountModel;
  }

}
