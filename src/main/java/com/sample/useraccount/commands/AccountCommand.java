package com.sample.useraccount.commands;

import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.sample.useraccount.model.AccountModel;

public class AccountCommand extends HystrixCommand<AccountModel> {

  private String accountId;

  public AccountCommand(String AccountId) {
    super(HystrixCommandGroupKey.Factory.asKey("ACCOUNT_KEY"));
    this.accountId = accountId;
  }

  @Override
  protected AccountModel run() throws Exception {
    return new RestTemplate()
        .getForObject("http://localhost:8082/account/{accountId}", AccountModel.class, this.accountId);
  }

  @Override
  protected AccountModel getFallback() {
    AccountModel accountModel = new AccountModel();
    accountModel.setAccountId("AXXXXX");

    return accountModel;
  }

}
