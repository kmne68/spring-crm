package com.kmne68.springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kmne68.springdemo.dao.CustomerDAO;
import com.kmne68.springdemo.entity.Customer;
import com.kmne68.springdemo.service.CustomerService;
import com.kmne68.springdemo.util.SortUtils;


@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	
	// inject CustomerService
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/list")
	public String listCustomers(Model model, @RequestParam(required=false) String sort) {
		
		// get customers from the DAO via CustomerService
		List<Customer> customers = null;
		
		// check for a sort field
		if(sort != null) {
			int sortField = Integer.parseInt(sort);
			customers = customerService.getCustomers(sortField);
		}
		else {
			// if no sort field is provided default to sorting by last name
			customers = customerService.getCustomers(SortUtils.LAST_NAME);
		}
		
		// add customers to the model
		model.addAttribute("customers", customers);
		
		return "list-customers";
		
	}

	
	// get mapping to show the form to add a customer
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model model) {
		
		// create a new model attribute to bind form data
		Customer customer = new Customer();
		
		model.addAttribute("customer", customer);
		
		return "customer-form";
		
	}
	
	
	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer customer) {
		
		// save the customer
		customerService.saveCustomer(customer);
		
		return "redirect:/customer/list";
	}
	
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int id, Model model) {
		
		// retrieve customer from the service
		Customer customer = customerService.getCustomer(id);
		
		// set the customer as a model attribute to pre-populate the form
		model.addAttribute("customer", customer);
		
		// return data to the form
		return "customer-form";
	}
	
	
	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int id) {
		
		customerService.deleteCustomer(id);
		
		return "redirect:/customer/list";
	}
	
	
	@GetMapping("/search")
	public String searchCustomers(@RequestParam("searchName") String searchName, Model model) {
		
		// search customers from the service
		List<Customer> customers = customerService.searchCustomers(searchName);
		
		// add the customers to the model
		model.addAttribute("customers", customers);
		
		return "list-customers";
	}
	
}
