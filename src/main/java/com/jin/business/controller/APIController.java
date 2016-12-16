package com.jin.business.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import com.jin.security.core.CustomUser;
import com.jin.security.core.IAuthenticationFacade;

public class APIController {
	
	@Autowired
    protected IAuthenticationFacade authenticationFacade;
	
	protected String getUserSource() {
		Authentication authentication = authenticationFacade.getAuthentication();
		CustomUser customeUser = (CustomUser) authentication.getPrincipal();	
		return customeUser.getUserSource();
	}
	
}
