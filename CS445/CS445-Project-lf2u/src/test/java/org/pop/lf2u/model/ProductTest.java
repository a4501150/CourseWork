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
public class ProductTest {
	
	private Product m;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		m = new Product();
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Product#Product(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testProductStringString() {
		m = new Product("1","1");
		assertEquals(" 1 expected","1", m.getFid());
	}
	/**
	 * Test method for {@link org.pop.lf2u.model.Product#Product(org.pop.lf2u.model.Product)}.
	 */
	@Test
	public void testProductProduct() {
		m = new Product(new Product("1","1"));
		assertEquals(" 1 expected","1", m.getFid());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Product#setFid(java.lang.String)}.
	 */
	@Test
	public void testSetFid() {
		m.setFid("1");
		assertEquals(" 1 expected","1", m.getFid());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Product#getGcpid()}.
	 */
	@Test
	public void testGetGcpid() {
		m.setGcpid("1");
		assertEquals(" 1 expected","1", m.getGcpid());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Product#setGcpid(java.lang.String)}.
	 */
	@Test
	public void testSetGcpid() {
		m.setGcpid("1");
		assertEquals(" 1 expected","1", m.getGcpid());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Product#getName()}.
	 */
	@Test
	public void testGetName() {
		m.setName("1");
		assertEquals(" 1 expected","1", m.getName());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Product#setName(java.lang.String)}.
	 */
	@Test
	public void testSetName() {
		m.setName("1");
		assertEquals(" 1 expected","1", m.getName());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Product#setEnd_date(java.lang.String)}.
	 */
	@Test
	public void testSetEnd_date() {
		m.setEnd_date("1");
		assertEquals(" 1 expected","1", m.getEnd_date());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Product#toString()}.
	 */
	@Test
	public void testToString() {
		assertEquals(" 1 expected","Product [fid=null, fspid=null, gcpid=null, name=null, note=null, start_date=null, end_date=null, price=null, product_unit=null, image=null]", m.toString());
	}

}
