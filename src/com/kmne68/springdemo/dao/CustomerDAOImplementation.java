package com.kmne68.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kmne68.springdemo.entity.Customer;
import com.kmne68.springdemo.util.SortUtils;


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


	@Override
	public void deleteCustomer(int id) {

		Session currentSession = sessionFactory.getCurrentSession();
		
		Query query = currentSession.createQuery("delete from Customer where id=:customerId");
		query.setParameter("customerId", id);
		
		query.executeUpdate();
		
	}


	@Override
	public List<Customer> searchCustomers(String searchName) {

		Session currentSession = sessionFactory.getCurrentSession();
		
		Query query = null;
		
		if(searchName != null && searchName.trim().length() > 0) {
			// search for first or last name, case insensitive
			query = currentSession.createQuery("FROM Customer WHERE LOWER(firstName) LIKE :theName OR LOWER(lastName) LIKE :theName", Customer.class);
			query.setParameter("theName", "%" + searchName.toLowerCase() + "%");
		}
		else {
			// searchName is empty so get all customers
			query = currentSession.createQuery("from Customer", Customer.class);
		}
		
		// execute query an get result list
		List<Customer> customers = query.getResultList();
		
		return customers;
	}


	@Override
	public List<Customer> getCustomers(int sortField) {

		Session currentSession = sessionFactory.getCurrentSession();
		
		String fieldName = null;
		
		switch(sortField) {
			case SortUtils.FIRST_NAME:
				fieldName = "firstName";
				break;
			case SortUtils.LAST_NAME:
				fieldName = "lastName";
				break;
			case SortUtils.EMAIL:
				fieldName = "email";
				break;
			default:
				fieldName = "lastName";
		}
		
		String queryString = "FROM Customer ORDER BY " + fieldName;
		Query<Customer> query = currentSession.createQuery(queryString, Customer.class);
		
		List<Customer> customers = query.getResultList();
		
		return customers;
	}	


}
