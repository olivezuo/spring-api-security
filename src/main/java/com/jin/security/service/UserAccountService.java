package com.jin.security.service;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jin.security.domain.UserAccount;
import com.jin.security.repository.UserAccountRepository;

@Service
public class UserAccountService {
	private static final Logger logger = LoggerFactory.getLogger(UserAccountService.class);
	
	@Autowired
	UserAccountRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@PostConstruct
	public void init(){
		UserAccount userAccount =  prepareNewUserAccount();
		this.save(userAccount);
	}
	
	public UserAccount retrieveUser(String username, String password){		
		return userRepository.findByUsernameAndPassword(username, password);
	}
	
	public UserAccount findByUsername(String username){
		return userRepository.findByUsername(username);
	}
	
	public void save(UserAccount userAccount) {
		userRepository.save(userAccount);
	}
		
	public UserAccount prepareNewUserAccount() {
		UserAccount userAccount = this.findByUsername("testuser");
		if (userAccount == null) {
			userAccount = new UserAccount();			
			userAccount.setUsername("testuser");
			userAccount.setPassword(passwordEncoder.encode("testpass"));
			userAccount.setEnabled(true);
			//userAccount.insertRole("Regular");
			userAccount.insertRole("Admin");
			userAccount.setUserSource("internal");
		}
		
		return userAccount;		
	}

}
