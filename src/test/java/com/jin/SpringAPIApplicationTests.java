package com.jin;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.jin.business.entity.Customer;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringAPIApplicationTests {

	@Autowired
    private TestRestTemplate restTemplate;
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void testCustomerFind() {
		 ResponseEntity<Customer> responseEntity =
		            restTemplate.withBasicAuth("testuser","testpass").getForEntity("/customer/Andrew",Customer.class);
		 assertThat(HttpStatus.OK).isEqualTo(responseEntity.getStatusCode());
		 assertThat("Andrew").isEqualTo(responseEntity.getBody().getName());
	}
	
	
	@Test
	public void testAPINoAuth() {
		 ResponseEntity<Customer> responseEntity =
		            restTemplate.getForEntity("/customer/Andrew",Customer.class);
		 assertThat(HttpStatus.UNAUTHORIZED).isEqualTo(responseEntity.getStatusCode());
	}

}
