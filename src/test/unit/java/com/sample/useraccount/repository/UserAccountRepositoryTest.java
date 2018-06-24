package com.sample.useraccount.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserAccountRepositoryTest {

  @Autowired
  private TestEntityManager testEntityManager;

  @Autowired
  private UserAccountRepository userAccountRepository;

  private UseraccountsPK useraccountsPK = new UseraccountsPK();

  @Before
  public void setup() {

    useraccountsPK.setUserid("U12345");
    useraccountsPK.setAccountid("A1001");

    Useraccounts useraccounts1 = new Useraccounts();
    useraccounts1.setUserid("U12345");
    useraccounts1.setAccountid("A1001");

    Useraccounts useraccounts2 = new Useraccounts();
    useraccounts2.setUserid("U12345");
    useraccounts2.setAccountid("A1002");

    testEntityManager.persist(useraccounts1);
    testEntityManager.persist(useraccounts2);
  }

  @Test
  public void testUserAccountRepository_findOne() {

    Assert.assertNotNull(userAccountRepository.findOne(useraccountsPK));
  }

  @Test
  public void testUserAccountRepository_findUserAccountIds() {

    List<String> accountIdList = userAccountRepository.findUserAccountIds(useraccountsPK);
    Assert.assertEquals(2, accountIdList.size());
    Assert.assertEquals("A1001", accountIdList.get(0));
  }

}
