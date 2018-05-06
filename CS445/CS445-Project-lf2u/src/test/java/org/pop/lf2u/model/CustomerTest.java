/**
 * 
 */
package org.pop.lf2u.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author jinyang
 *
 */
public class CustomerTest {
	
	private Customer ct;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		ct = new Customer();
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Customer#Customer(java.lang.String)}.
	 */
	@Test
	public void testCustomerString() {
		ct = new Customer("1");
		assertEquals(" 1 expected","1", ct.getCid());
		
	}


	/**
	 * Test method for {@link org.pop.lf2u.model.Customer#toString()}.
	 */
	@Test
	public void testToString() {
		ct = new Customer("1","1","1","1","1","1");
		assertEquals(" 1 expected","Customer [cid=1, name=1, street=1, zip=1, phone=1, email=1]", ct.toString());
	}

}
