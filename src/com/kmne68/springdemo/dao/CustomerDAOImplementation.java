package com.kmne68.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kmne68.springdemo.entity.Customer;


@Repository
public class CustomerDAOImplementation implements CustomerDAO {
	
	// inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public List<Customer> getCustomers() {
		
		// get current hibernate session
		Session session = sessionFactory.getCurrentSession();
		
		// create query
		Query<Customer> query = session.createQuery("from Customer order by lastName", Customer.class);
		
		// execute query to get result list
		List<Customer> customers = query.getResultList();
		
		return customers;
		
	}


	@Override
	public void saveCustomer(Customer customer) {

		// get the hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// save the customer
		currentSession.saveOrUpdate(customer);
		
	}


	@Override
	public Customer getCustomer(int id) {

		Session currentSession = sessionFactory.getCurrentSession();
		
		Customer customer = currentSession.get(Customer.class, id);
		
		return customer;
	}	


}
