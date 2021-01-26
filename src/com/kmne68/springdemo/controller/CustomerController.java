package com.kmne68.springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kmne68.springdemo.dao.CustomerDAO;
import com.kmne68.springdemo.entity.Customer;


@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	// inject DAO into the controller
	@Autowired
	private CustomerDAO customerDAO;
	
	@RequestMapping("/list")
	public String listCustomers(Model model) {
		
		// get customers from the DAO
		List<Customer> customers = customerDAO.getCustomers();
		
		// add customers to the model
		model.addAttribute("customers", customers);
		
		return "list-customers";
		
	}

}
