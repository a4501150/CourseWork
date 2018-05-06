package org.pop.lf2u.service;

import java.util.ArrayList;
import java.util.List;

import org.pop.lf2u.common.Utils;
import org.pop.lf2u.model.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

	private List<Customer> customers = new ArrayList<Customer>();
	
	public Customer add(Customer customer) {
		String lastId = null;
		if(customers.size() > 0) lastId = customers.get(customers.size()-1).getCid();
		String newId = Utils.generateId(lastId);
		
		customer.setCid(newId);
		customers.add(customer);
		
		return customer;
	}
	
	public boolean update(String cid,Customer newCustomer) {
		for (Customer customer : customers) {
			if(customer.getCid().equals(cid)) {
				customer.setEmail(newCustomer.getEmail());
				customer.setName(newCustomer.getName());
				customer.setPhone(newCustomer.getPhone());
				customer.setStreet(newCustomer.getStreet());
				customer.setZip(newCustomer.getZip());
				return true;
			}
		}
		return false;
	}
	
	public Customer findById(String cid) {
		for (Customer customer : customers) {
			if(customer.getCid().equals(cid)) return customer;
		}
		return null;
	}

	/**
	 * @return the customers
	 */
	public List<Customer> getCustomers() {
		return customers;
	}
	

}
