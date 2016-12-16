package com.jin.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jin.security.domain.UserAccount;
import com.jin.security.service.UserAccountService;

@RestController
public class UserAccountController {

	@Autowired
	UserAccountService userAccountService;

	@PreAuthorize("hasAuthority('Admin')")
	@RequestMapping("/user/save")
	public void saveUser() {
		UserAccount userAccount = userAccountService.prepareNewUserAccount();
		userAccountService.save(userAccount);
	}
	
}
