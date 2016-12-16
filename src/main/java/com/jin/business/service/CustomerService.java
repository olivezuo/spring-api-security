package com.jin.business.service;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jin.business.entity.Customer;
import com.jin.business.repository.CustomerRepository;

@Service
public class CustomerService {

	private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
			
	@Autowired
	CustomerRepository customerRepository;
	
	@PostConstruct
	public void init() {
		Customer customer = prepareNewCustomer();
		save(customer);	
	}
	
	public Customer find(String name) {
		Customer customer = customerRepository.findByName(name);
		logger.info("We find the customer: " + customer.toString());
		//throw new BusinessException("Something wrong in the service");
		return customer;
	}
	
	public void save(Customer customer) {
		customerRepository.save(customer);
	}
	
	private Customer prepareNewCustomer() {
		Customer customer = customerRepository.findByName("Andrew");
		if (customer == null) {
			customer = new Customer();
			customer.setName("Andrew");
		}
		return customer;
	}
}
