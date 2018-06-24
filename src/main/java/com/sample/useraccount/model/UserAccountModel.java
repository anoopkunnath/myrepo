package com.sample.useraccount.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class UserAccountModel extends ErrorModel {

  @JsonProperty(value = "userId")
  private String userId;

  @JsonProperty(value = "firstName")
  private String firstName;

  @JsonProperty(value = "lastName")
  private String lastName;

  @JsonProperty(value = "dateOfBirth")
  private String dob;

  @JsonProperty(value = "address")
  private String address;

  @JsonProperty(value = "accounts")
  private List<AccountModel> accounts;


  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getDob() {
    return dob;
  }

  public void setDob(String dob) {
    this.dob = dob;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public List<AccountModel> getAccounts() {
    return accounts;
  }

  public void setAccounts(List<AccountModel> accounts) {
    this.accounts = accounts;
  }

}
