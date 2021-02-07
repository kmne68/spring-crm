package com.kmne68.springdemo.dao;

import java.util.List;

import com.kmne68.springdemo.entity.Customer;


public interface CustomerDAO {
	
	public List<Customer> getCustomers();

	public void saveCustomer(Customer customer);

	public Customer getCustomer(int id);

	public void deleteCustomer(int id);

	public List<Customer> searchCustomers(String searchName);

	public List<Customer> getCustomers(int sortField);

}
