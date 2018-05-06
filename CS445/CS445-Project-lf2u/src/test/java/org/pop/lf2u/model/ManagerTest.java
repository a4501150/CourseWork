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
public class ManagerTest {
	
	private Manager m;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		m = new Manager();
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Manager#getName()}.
	 */
	@Test
	public void testGetName() {
		assertEquals(" 1 expected",null, m.getName());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Manager#setName(java.lang.String)}.
	 */
	@Test
	public void testSetName() {
		m.setName("1");
		assertEquals(" 1 expected","1", m.getName());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Manager#getCreated_by()}.
	 */
	@Test
	public void testGetCreated_by() {
		m.setCreated_by("1");
		assertEquals(" 1 expected","1", m.getCreated_by());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Manager#setCreated_by(java.lang.String)}.
	 */
	@Test
	public void testSetCreated_by() {
		m.setCreated_by("1");
		assertEquals(" 1 expected","1", m.getCreated_by());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Manager#getCreate_date()}.
	 */
	@Test
	public void testGetCreate_date() {
		m.setCreate_date(null);
		assertEquals(" 1 expected",null, m.getCreate_date());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Manager#setCreate_date(java.util.Date)}.
	 */
	@Test
	public void testSetCreate_date() {
		m.setCreate_date(null);
		assertEquals(" 1 expected",null, m.getCreate_date());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Manager#getPhone()}.
	 */
	@Test
	public void testGetPhone() {
		m.setPhone(null);
		assertEquals(" 1 expected",null, m.getPhone());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Manager#setPhone(java.lang.String)}.
	 */
	@Test
	public void testSetPhone() {
		m.setPhone(null);
		assertEquals(" 1 expected",null, m.getPhone());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Manager#getEmail()}.
	 */
	@Test
	public void testGetEmail() {
		m.setEmail(null);
		assertEquals(" 1 expected",null, m.getEmail());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Manager#setEmail(java.lang.String)}.
	 */
	@Test
	public void testSetEmail() {
		m.setEmail(null);
		assertEquals(" 1 expected",null, m.getEmail());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Manager#toString()}.
	 */
	@Test
	public void testToString() {
		
		m = new Manager("1");
		assertEquals(" 1 expected","Manager [mid=1, name=null, created_by=null, create_date=null, phone=null, email=null]", m.toString());
	
	}

}
