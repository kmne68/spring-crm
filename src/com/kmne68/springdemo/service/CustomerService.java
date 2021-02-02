package com.kmne68.springdemo.service;

import java.util.List;

import com.kmne68.springdemo.entity.Customer;

public interface CustomerService {

	public List<Customer> getCustomers();

	public void saveCustomer(Customer customer);
	
}
