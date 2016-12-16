package com.jin.business.service;

import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jin.business.exception.BusinessException;

public class BusinessService {

	@ExceptionHandler(BusinessException.class)
	public void handleException(BusinessException ex){
		
	}
	
}
