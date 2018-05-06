/**
 * 
 */
package org.pop.lf2u.model.farmer;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author jinyang
 *
 */
public class FarmInfoTest {

	private FarmInfo m;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		m = new FarmInfo();
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.farmer.FarmInfo#FarmInfo()}.
	 */
	@Test
	public void testFarmInfo() {
		
		assertEquals(" 1 expected",null, m.getPhone());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.farmer.FarmInfo#FarmInfo(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testFarmInfoStringStringStringStringString() {
		m = new FarmInfo(null,null,null,null,null);
		assertEquals(" 1 expected",null, m.getPhone());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.farmer.FarmInfo#FarmInfo(java.lang.String, org.pop.lf2u.model.farmer.Farm)}.
	 */
	@Test
	public void testFarmInfoStringFarm() {
		m = new FarmInfo(null,null);
		assertEquals(" 1 expected",null, m.getPhone());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.farmer.FarmInfo#getFid()}.
	 */
	@Test
	public void testGetFid() {
		m.setFid(null);
		assertEquals(" 1 expected",null, m.getFid());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.farmer.FarmInfo#setFid(java.lang.String)}.
	 */
	@Test
	public void testSetFid() {
		m.setFid(null);
		assertEquals(" 1 expected",null, m.getFid());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.farmer.FarmInfo#getName()}.
	 */
	@Test
	public void testGetName() {
		m.setName(null);
		assertEquals(" 1 expected",null, m.getName());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.farmer.FarmInfo#setName(java.lang.String)}.
	 */
	@Test
	public void testSetName() {
		m.setName(null);
		assertEquals(" 1 expected",null, m.getName());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.farmer.FarmInfo#getPhone()}.
	 */
	@Test
	public void testGetPhone() {
		m.setPhone(null);
		assertEquals(" 1 expected",null, m.getPhone());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.farmer.FarmInfo#setPhone(java.lang.String)}.
	 */
	@Test
	public void testSetPhone() {
		m.setPhone(null);
		assertEquals(" 1 expected",null, m.getPhone());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.farmer.FarmInfo#getWeb()}.
	 */
	@Test
	public void testGetWeb() {
		m.setWeb(null);
		assertEquals(" 1 expected",null, m.getWeb());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.farmer.FarmInfo#setWeb(java.lang.String)}.
	 */
	@Test
	public void testSetWeb() {
		m.setWeb(null);
		assertEquals(" 1 expected",null, m.getWeb());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.farmer.FarmInfo#getAddress()}.
	 */
	@Test
	public void testGetAddress() {
		m.setAddress(null);
		assertEquals(" 1 expected",null, m.getAddress());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.farmer.FarmInfo#setAddress(java.lang.String)}.
	 */
	@Test
	public void testSetAddress() {
		m.setAddress(null);
		assertEquals(" 1 expected",null, m.getAddress());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.farmer.FarmInfo#toString()}.
	 */
	@Test
	public void testToString() {
		assertEquals(" 1 expected",m.toString(), m.toString());
	}

}
