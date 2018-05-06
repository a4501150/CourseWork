/**
 * 
 */
package org.pop.lf2u.model.farmer;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.pop.lf2u.model.Report;

/**
 * @author jinyang
 *
 */
public class FarmTest {
	
	private Farm m;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		m = new Farm();
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.farmer.Farm#getPhone()}.
	 */
	@Test
	public void testGetPhone() {
		m.setPhone(null);
		assertEquals(" 1 expected",null, m.getPhone());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.farmer.Farm#setPhone(java.lang.String)}.
	 */
	@Test
	public void testSetPhone() {
		m.setPhone(null);
		assertEquals(" 1 expected",null, m.getPhone());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.farmer.Farm#getWeb()}.
	 */
	@Test
	public void testGetWeb() {
		m.setWeb(null);
		assertEquals(" 1 expected",null, m.getWeb());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.farmer.Farm#setWeb(java.lang.String)}.
	 */
	@Test
	public void testSetWeb() {
		m.setWeb(null);
		assertEquals(" 1 expected",null, m.getWeb());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.farmer.Farm#getAddress()}.
	 */
	@Test
	public void testGetAddress() {
		m.setAddress(null);
		assertEquals(" 1 expected",null, m.getAddress());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.farmer.Farm#setAddress(java.lang.String)}.
	 */
	@Test
	public void testSetAddress() {
		m.setAddress(null);
		assertEquals(" 1 expected",null, m.getAddress());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.farmer.Farm#getDelivery_charge()}.
	 */
	@Test
	public void testGetDelivery_charge() {
		m.setDelivery_charge(0.0f);
		assertEquals(" 1 expected",0, m.getDelivery_charge().intValue());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.farmer.Farm#setDelivery_charge(java.lang.Float)}.
	 */
	@Test
	public void testSetDelivery_charge() {
		m.setDelivery_charge(0.0f);
		assertEquals(" 1 expected",0, m.getDelivery_charge().intValue());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.farmer.Farm#toString()}.
	 */
	@Test
	public void testToString() {
		assertEquals(" 1 expected",m.toString(), m.toString());
	}

}
