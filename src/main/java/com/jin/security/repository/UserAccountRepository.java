package com.jin.security.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Repository;

import com.jin.security.domain.UserAccount;

@Repository
public interface UserAccountRepository extends MongoRepository<UserAccount,String>{

	public UserAccount findByUsernameAndPassword(String username, String password);
	
	public UserAccount findByUsername(String username);
}
