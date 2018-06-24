package com.sample.useraccount.repository;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@IdClass(UseraccountsPK.class)
@Entity
public class Useraccounts {

  @Id
  private String userid;

  @Id
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
