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
public class CatalogTest {
	
	private Catalog ct;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		ct = new Catalog();
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Catalog#Catalog(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testCatalogStringString() {
		ct = new Catalog("1","1");
		assertEquals(" 1 expected","1", ct.getGcpid());
		assertEquals(" 1 expected","1", ct.getName());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Catalog#Catalog(java.lang.String)}.
	 */
	@Test
	public void testCatalogString() {
		ct = new Catalog("1");
		assertEquals(" 1 expected","1", ct.getGcpid());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Catalog#getGcpid()}.
	 */
	@Test
	public void testGetGcpid() {
		ct = new Catalog("1");
		assertEquals(" 1 expected","1", ct.getGcpid());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Catalog#setGcpid(java.lang.String)}.
	 */
	@Test
	public void testSetGcpid() {
		
		ct.setGcpid("2");
		assertEquals(" 2 expected","2", ct.getGcpid());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Catalog#getName()}.
	 */
	@Test
	public void testGetName() {
		ct.setName("2");
		assertEquals(" 2 expected","2", ct.getName());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Catalog#setName(java.lang.String)}.
	 */
	@Test
	public void testSetName() {
		ct.setName("2");
		assertEquals(" 2 expected","2", ct.getName());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Catalog#toString()}.
	 */
	@Test
	public void testToString() {
		ct = new Catalog("1","1");
		assertEquals(" 2 expected","Catalog [gcpid=1, name=1]", ct.toString());
	}

}
