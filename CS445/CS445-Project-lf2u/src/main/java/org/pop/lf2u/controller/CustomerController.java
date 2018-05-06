package org.pop.lf2u.controller;

import javax.validation.Valid;

import org.pop.lf2u.model.Customer;
import org.pop.lf2u.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	CustomerService customerService;
	
	@RequestMapping(value = {"/customers","/customers/"}, method = RequestMethod.POST)
	public ResponseEntity<Customer> create(@Valid @RequestBody Customer customer, BindingResult result) {
		
		logger.debug("customer create",customer.toString());
		
		if(result.hasErrors()) return new ResponseEntity<Customer>(HttpStatus.BAD_REQUEST);
		
		customer = customerService.add(customer);
		Customer newCustomer = new Customer(customer.getCid());
		
		return new ResponseEntity<Customer>(newCustomer,HttpStatus.CREATED) ;
	}

	@RequestMapping(value = {"/customers/{cid}"}, method = RequestMethod.PUT )
	public ResponseEntity<Void> update(@PathVariable("cid") String cid, @Valid @RequestBody Customer customer, BindingResult result) {
		
		logger.debug("customer update",cid,customer.toString());
		
		if(customerService.findById(cid) == null) return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		if(result.hasErrors()) return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		if(customerService.update(cid, customer)) return new ResponseEntity<Void>(HttpStatus.OK);
		
		return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR) ;
	}
	
	@RequestMapping(value = {"/customers/{cid}"}, method = RequestMethod.GET )
	public ResponseEntity<Customer> findById(@PathVariable("cid") String cid) {
		
		logger.debug("customer find by id",cid);

		Customer customer = customerService.findById(cid);
		if(customer == null) return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<Customer>(customer,HttpStatus.OK) ;
	}
	
	
	
	
	
}
