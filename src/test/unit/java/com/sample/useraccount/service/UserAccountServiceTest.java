package com.sample.useraccount.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.sample.useraccount.model.AccountModel;
import com.sample.useraccount.model.UserModel;
import com.sample.useraccount.repository.UserAccountRepository;
import com.sample.useraccount.repository.UseraccountsPK;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;

@RunWith(SpringRunner.class)
public class UserAccountServiceTest {

  @TestConfiguration
  static class UserAccountConfigTest {

    @Bean
    public UserAccountService userAccountService() {
      return new UserAccountService();
    }
  }

  @Autowired
  private UserAccountService userAccountService;

  @MockBean
  private UserAccountRepository userAccountRepository;

  @MockBean
  private RestTemplate restTemplate;

  @Before
  public void setup() {

    UseraccountsPK useraccountsPK = new UseraccountsPK();
    useraccountsPK.setUserid("U67890");
    List<String> accountIdList = new ArrayList<>();
    accountIdList.add("A2001");
    accountIdList.add("A2002");


    Mockito.when(userAccountRepository.findUserAccountIds(useraccountsPK)).thenReturn(accountIdList);

    UserModel userModel = new UserModel();
    userModel.setUserId("U67890");
    userModel.setFirstName("FName");
    userModel.setLastName("LName");
    Mockito.when(restTemplate.getForObject(Matchers.eq("http://localhost:8081/user/{userId}"),
        Matchers.eq(UserModel.class), Matchers.eq("U67890"))).thenReturn(userModel);

    AccountModel accountModel1 = new AccountModel();
    accountModel1.setAccountId("A2001");
    accountModel1.setAccountType("Checking");
    accountModel1.setBalance(500.00);

    AccountModel accountModel2 = new AccountModel();
    accountModel2.setAccountId("A2002");
    accountModel2.setAccountType("Savings");
    accountModel2.setBalance(100.00);
    Mockito.when(restTemplate.getForObject(Matchers.eq("http://localhost:8082/account/{accountId}"),
        Matchers.eq(AccountModel.class), Matchers.eq("A2001"))).thenReturn(accountModel1);
    Mockito.when(restTemplate.getForObject(Matchers.eq("http://localhost:8082/account/{accountId}"),
        Matchers.eq(AccountModel.class), Matchers.eq("A2002"))).thenReturn(accountModel2);
  }

  @Test
  public void testAccountService_findUserAccounts() {
    Assert.assertEquals(2, userAccountService.findUserAccounts("U67890").getAccounts().size());
    Assert.assertEquals("LName" , userAccountService.findUserAccounts("U67890").getLastName());
    Assert.assertEquals("Checking" , userAccountService.findUserAccounts("U67890").getAccounts().get(0).getAccountType());
    Assert.assertEquals(new Double(100.00) , userAccountService.findUserAccounts("U67890").getAccounts().get(1).getBalance());
  }

}
