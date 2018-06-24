package com.sample.useraccount.repository;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends CrudRepository<Useraccounts, UseraccountsPK> {

  @Query("select ua.accountid from Useraccounts ua where ua.userid = :#{#useraccountsPK.userid}")
  List<String> findUserAccountIds(@Param("useraccountsPK") UseraccountsPK useraccountsPK);

}
