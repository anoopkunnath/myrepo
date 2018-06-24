package com.sample.useraccount.repository;

import java.io.Serializable;

public class UseraccountsPK implements Serializable {


  private String userid;

  private String accountid;

  public String getUserid() {
    return userid;
  }

  public void setUserid(String userid) {
    this.userid = userid;
  }

  public String getAccountid() {
    return accountid;
  }

  public void setAccountid(String accountid) {
    this.accountid = accountid;
  }

}
