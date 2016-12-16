package com.jin.business.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jin.business.entity.Customer;
import com.jin.business.exception.ResourceAccessException;
import com.jin.business.service.CustomerService;

@RestController
public class CustomerController extends APIController{
	
	@Autowired
	CustomerService customerService;
	@PreAuthorize("hasAuthority('Regular')")	
	@RequestMapping("/simple/test")
	public String simpleTest(){
		
		return "You can do the simple regular Test!";
	}
	
	@PreAuthorize("hasAuthority('Admin')")
	@RequestMapping("/simple/admin")
	public String simpleAdmin() throws Exception{
		
		String userSource = this.getUserSource();
		if (!userSource.equals("external")) {
			//throw new Exception("Service error");
			throw new ResourceAccessException("Wrong source");
		}
		return "You can do the simple Admin Test!";
		
	}

	@PreAuthorize("hasAuthority('Admin')")	
	@RequestMapping("/customer/{name}")
	public Customer find(@PathVariable String name) {
		String userSource = this.getUserSource();
		if (!userSource.equals("internal")) {
			throw new ResourceAccessException("Wrong source");
		}
		return customerService.find(name);
		
	}	

}
