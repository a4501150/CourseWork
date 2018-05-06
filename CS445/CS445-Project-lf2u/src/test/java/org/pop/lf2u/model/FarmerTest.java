/**
 * 
 */
package org.pop.lf2u.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.pop.lf2u.model.farmer.Farm;

/**
 * @author jinyang
 *
 */
public class FarmerTest {

	private Farmer ct;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		ct = new Farmer();
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Farmer#Farmer(java.lang.String)}.
	 */
	@Test
	public void testFarmerString() {
		ct = new Farmer("1");
		assertEquals(" 1 expected","1", ct.getFid());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Farmer#Farmer(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testFarmerStringString() {
		ct = new Farmer("1","1");
		assertEquals(" 1 expected","1", ct.getFid());
		assertEquals(" 1 expected",(new Farm("1",null,null,null)).getName(), ct.getFarm_info().getName());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Farmer#toString()}.
	 */
	@Test
	public void testToString() {
		ct = new Farmer("1",null,null,null);
		assertEquals(" 1 expected","Farmer [fid=1, farm_info=null, personal_info=null, delivers_to=null]", ct.toString());
	}

}
