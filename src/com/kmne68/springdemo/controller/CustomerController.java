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


@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	
	// inject CustomerService
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/list")
	public String listCustomers(Model model) {
		
		// get customers from the DAO via CustomerService
		List<Customer> customers = customerService.getCustomers();
		
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
	
}
