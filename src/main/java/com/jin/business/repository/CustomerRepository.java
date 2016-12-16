package com.jin.business.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.jin.business.entity.Customer;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {

	public Customer findByName(String name);


}