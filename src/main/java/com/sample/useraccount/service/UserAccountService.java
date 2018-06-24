package com.sample.useraccount.service;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sample.useraccount.commands.UserCommand;
import com.sample.useraccount.model.AccountModel;
import com.sample.useraccount.model.UserAccountModel;
import com.sample.useraccount.model.UserModel;
import com.sample.useraccount.repository.UserAccountRepository;
import com.sample.useraccount.repository.UseraccountsPK;
//import org.apache.camel.ProducerTemplate;

/**
 * Service class for calling dependent services.
 */
@Service
public class UserAccountService {

  @Autowired
  private UserAccountRepository userAccountRepository;

  @Autowired
  private RestTemplate restTemplate;

  //@Autowired
  //private ProducerTemplate producerTemplate;

  public UserAccountModel findUserAccounts(String userId) {

    UserAccountModel userAccountModel = new UserAccountModel();

    UseraccountsPK useraccountsPK = new UseraccountsPK();
    useraccountsPK.setUserid(userId);
    List<String> accountIds = userAccountRepository.findUserAccountIds(useraccountsPK);

    findUserDetails(useraccountsPK)
        //.subscribeOn(Schedulers.io())
        .subscribe((userModel) -> {
              if(userModel != null) {
                userAccountModel.setUserId(userModel.getUserId());
                userAccountModel.setFirstName(userModel.getFirstName());
                userAccountModel.setLastName(userModel.getLastName());
                userAccountModel.setDob(userModel.getDob());
              }
            }
            , (error) -> error.printStackTrace()
            , () -> System.out.println("Finished getting User details"));


    List<AccountModel> accountModelList = new ArrayList<>();
    accountIds.forEach((accountId) ->  {
      findAccountDetails(accountId)
          //.subscribeOn(Schedulers.io())
          .subscribe((accountModel) -> accountModelList.add(accountModel)
              , (error) -> error.printStackTrace()
              , () -> System.out.println("Finished getting Account details"));
    });

    userAccountModel.setAccounts(accountModelList);

    return userAccountModel;
  }


  //Call Using Spring cloud
  @HystrixCommand(commandKey = "USER_DETAILS", fallbackMethod = "defaultUserResponse")
  public Observable<UserModel> findUserDetails(UseraccountsPK useraccountsPK) {

    return Observable.create(emitter -> {
      UserModel userModel = restTemplate
          .getForObject("http://localhost:8081/user/{userId}",
              UserModel.class, useraccountsPK.getUserid());
          emitter.onNext(userModel);
          emitter.onCompleted();
          emitter.onError(new RuntimeException("Error while getting user details"));
        });

    // Call Using Hystrix
    //UserAccountModel userAccountModel = new UserCommand(userId).execute();

    // Call Using hystrix-apache camel
    //producerTemplate.sendBody("direct:user", userId);
    //producerTemplate.sendBody("direct:account", userId);
  }

  //Call Using Spring cloud
  @HystrixCommand(commandKey = "ACCOUNT_DETAILS", fallbackMethod = "defaultAccountResponse")
  public Observable<AccountModel> findAccountDetails(String accountId) {

    return Observable.create(emitter -> {
      AccountModel accountModel = restTemplate
          .getForObject("http://localhost:8082/account/{accountId}",
              AccountModel.class, accountId);
        emitter.onNext(accountModel);
        emitter.onCompleted();
        emitter.onError(new RuntimeException("Error while getting account details"));
    });
  }

  private UserModel defaultUserResponse(UseraccountsPK useraccountsPK) {
    UserModel userModel = new UserModel();
    userModel.setErrorCode("USER_NOT_FOUND");

    return userModel;
  }

  private AccountModel defaultAccountResponse(String accountId) {
    AccountModel accountModel = new AccountModel();
    accountModel.setErrorCode("ACCOUNT_NOT_FOUND");

    return accountModel;
  }

}
