package org.pop.lf2u.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.pop.lf2u.model.Customer;
import org.pop.lf2u.service.CustomerService;

public class CustomerServiceTest {

	@Test
	public void testCustomerService() {
		CustomerService customerService = new CustomerService();
		assert (customerService != null);
	}
	
	@Test
	public void testGetCustomers() {
		CustomerService customerService = new CustomerService();
		assert (customerService.getCustomers() != null && customerService.getCustomers().size() == 0);
	}
	

	@Test
	public void testAdd() {
		CustomerService customerService = new CustomerService();
		List<Customer> customers = customerService.getCustomers();

		Customer customer = customerService
				.add(new Customer(null, "customer_name_1", "street_1", "600001", "123-4567-8901", "123456@uu.com"));
		assert (customers.size() == 1);
		assertEquals(customers.get(customers.size() - 1).getCid(), "1");
		assertEquals(customer, customers.get(customers.size() - 1));
	}

	@Test
	public void testUpdate() {
		CustomerService customerService = new CustomerService();

		Customer customer = new Customer(null, "customer_name_1", "street_1", "600001", "123-4567-8901",
				"123456@uu.com");
		customer = customerService.add(customer);

		Customer customer2 = new Customer(null, "customer_name_2", "street_2", "600002", "123-4567-8902",
				"123456@uu.com1");

		assert (customerService.update(customer.getCid(), customer2));

		assertEquals(customer.getCid(), "1");
		assertEquals(customer.getName(), "customer_name_2");
		assertEquals(customer.getStreet(), "street_2");
		assertEquals(customer.getZip(), "600002");
		assertEquals(customer.getPhone(), "123-4567-8902");
		assertEquals(customer.getEmail(), "123456@uu.com1");

	}

	@Test
	public void testFindById() {
		CustomerService customerService = new CustomerService();

		customerService.add(new Customer(null, "customer_name_1", "street_1", "600001", "123-4567-8901", "123456@uu.com"));
		customerService.add(new Customer(null, "customer_name_2", "street_2", "600002", "123-4567-8902", "1234567@uu.com"));
		customerService.add(new Customer(null, "customer_name_3", "street_3", "600003", "123-4567-8903", "12345678@uu.com"));
		
		assert(customerService.findById("100") == null);
		
		Customer customer = customerService.findById("1");
		assertEquals(customer.getCid(), "1");
		
	}

}
