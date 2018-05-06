package org.pop.lf2u.controller;

import org.junit.*;
import static org.junit.Assert.*;
import org.pop.lf2u.model.Customer;
import org.pop.lf2u.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

/**
 * The class <code>CustomerControllerTest</code> contains tests for the class <code>{@link CustomerController}</code>.
 *
 * @generatedBy CodePro at 11/17/16 3:59 PM
 * @author jinyang
 * @version $Revision: 1.0 $
 */
public class CustomerControllerTest {
	/**
	 * Run the CustomerController() constructor test.
	 *
	 * @generatedBy CodePro at 11/17/16 3:59 PM
	 */
	@Test
	public void testCustomerController_1()
		throws Exception {
		CustomerController result = new CustomerController();
		assertNotNull(result);
		// add additional test code here
	}

	/**
	 * Run the ResponseEntity<Customer> create(Customer,BindingResult) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 11/17/16 3:59 PM
	 */
	@Test
	public void testCreate_1()
		throws Exception {
		CustomerController fixture = new CustomerController();
		fixture.customerService = new CustomerService();
		Customer customer = new Customer();
		BindingResult result = new BeanPropertyBindingResult(new Object(), "");

		ResponseEntity<Customer> result2 = fixture.create(customer, result);

		// add additional test code here
		assertNotNull(result2);
		assertEquals("<201 Created,Customer [cid=1, name=null, street=null, zip=null, phone=null, email=null],{}>", result2.toString());
		assertEquals(true, result2.hasBody());
	}

	/**
	 * Run the ResponseEntity<Customer> create(Customer,BindingResult) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 11/17/16 3:59 PM
	 */
	@Test
	public void testCreate_2()
		throws Exception {
		CustomerController fixture = new CustomerController();
		fixture.customerService = new CustomerService();
		Customer customer = new Customer();
		BindingResult result = new BeanPropertyBindingResult(new Object(), "");

		ResponseEntity<Customer> result2 = fixture.create(customer, result);

		// add additional test code here
		assertNotNull(result2);
		assertEquals("<201 Created,Customer [cid=1, name=null, street=null, zip=null, phone=null, email=null],{}>", result2.toString());
		assertEquals(true, result2.hasBody());
	}

	/**
	 * Run the ResponseEntity<Customer> findById(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 11/17/16 3:59 PM
	 */
	@Test
	public void testFindById_1()
		throws Exception {
		CustomerController fixture = new CustomerController();
		fixture.customerService = new CustomerService();
		String cid = "";

		ResponseEntity<Customer> result = fixture.findById(cid);

		// add additional test code here
		assertNotNull(result);
		assertEquals("<404 Not Found,{}>", result.toString());
		assertEquals(null, result.getBody());
		assertEquals(false, result.hasBody());
	}

	/**
	 * Run the ResponseEntity<Customer> findById(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 11/17/16 3:59 PM
	 */
	@Test
	public void testFindById_2()
		throws Exception {
		CustomerController fixture = new CustomerController();
		fixture.customerService = new CustomerService();
		String cid = "";

		ResponseEntity<Customer> result = fixture.findById(cid);

		// add additional test code here
		assertNotNull(result);
		assertEquals("<404 Not Found,{}>", result.toString());
		assertEquals(null, result.getBody());
		assertEquals(false, result.hasBody());
	}

	/**
	 * Run the ResponseEntity<Void> update(String,Customer,BindingResult) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 11/17/16 3:59 PM
	 */
	@Test
	public void testUpdate_1()
		throws Exception {
		CustomerController fixture = new CustomerController();
		fixture.customerService = new CustomerService();
		String cid = "";
		Customer customer = new Customer();
		BindingResult result = new BeanPropertyBindingResult(new Object(), "");

		ResponseEntity<Void> result2 = fixture.update(cid, customer, result);

		// add additional test code here
		assertNotNull(result2);
		assertEquals("<404 Not Found,{}>", result2.toString());
		assertEquals(null, result2.getBody());
		assertEquals(false, result2.hasBody());
	}

	/**
	 * Run the ResponseEntity<Void> update(String,Customer,BindingResult) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 11/17/16 3:59 PM
	 */
	@Test
	public void testUpdate_2()
		throws Exception {
		CustomerController fixture = new CustomerController();
		fixture.customerService = new CustomerService();
		String cid = "";
		Customer customer = new Customer();
		BindingResult result = new BeanPropertyBindingResult(new Object(), "");

		ResponseEntity<Void> result2 = fixture.update(cid, customer, result);

		// add additional test code here
		assertNotNull(result2);
		assertEquals("<404 Not Found,{}>", result2.toString());
		assertEquals(null, result2.getBody());
		assertEquals(false, result2.hasBody());
	}

	/**
	 * Run the ResponseEntity<Void> update(String,Customer,BindingResult) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 11/17/16 3:59 PM
	 */
	@Test
	public void testUpdate_3()
		throws Exception {
		CustomerController fixture = new CustomerController();
		fixture.customerService = new CustomerService();
		String cid = "";
		Customer customer = new Customer();
		BindingResult result = new BeanPropertyBindingResult(new Object(), "");

		ResponseEntity<Void> result2 = fixture.update(cid, customer, result);

		// add additional test code here
		assertNotNull(result2);
		assertEquals("<404 Not Found,{}>", result2.toString());
		assertEquals(null, result2.getBody());
		assertEquals(false, result2.hasBody());
	}

	/**
	 * Run the ResponseEntity<Void> update(String,Customer,BindingResult) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 11/17/16 3:59 PM
	 */
	@Test
	public void testUpdate_4()
		throws Exception {
		CustomerController fixture = new CustomerController();
		fixture.customerService = new CustomerService();
		String cid = "";
		Customer customer = new Customer();
		BindingResult result = new BeanPropertyBindingResult(new Object(), "");

		ResponseEntity<Void> result2 = fixture.update(cid, customer, result);

		// add additional test code here
		assertNotNull(result2);
		assertEquals("<404 Not Found,{}>", result2.toString());
		assertEquals(null, result2.getBody());
		assertEquals(false, result2.hasBody());
	}

	/**
	 * Perform pre-test initialization.
	 *
	 * @throws Exception
	 *         if the initialization fails for some reason
	 *
	 * @generatedBy CodePro at 11/17/16 3:59 PM
	 */
	@Before
	public void setUp()
		throws Exception {
		// add additional set up code here
	}

	/**
	 * Perform post-test clean-up.
	 *
	 * @throws Exception
	 *         if the clean-up fails for some reason
	 *
	 * @generatedBy CodePro at 11/17/16 3:59 PM
	 */
	@After
	public void tearDown()
		throws Exception {
		// Add additional tear down code here
	}

	/**
	 * Launch the test.
	 *
	 * @param args the command line arguments
	 *
	 * @generatedBy CodePro at 11/17/16 3:59 PM
	 */
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(CustomerControllerTest.class);
	}
}