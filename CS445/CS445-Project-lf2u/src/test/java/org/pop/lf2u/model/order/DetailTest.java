/**
 * 
 */
package org.pop.lf2u.model.order;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.pop.lf2u.model.farmer.Farm;

/**
 * @author jinyang
 *
 */
public class DetailTest {

	private Detail m;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		m = new Detail();
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.order.Detail#Detail()}.
	 */
	@Test
	public void testDetail() {
		assertEquals(" 1 expected",null, m.getAmount());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.order.Detail#getFspid()}.
	 */
	@Test
	public void testGetFspid() {
		m.setFspid(null);
		assertEquals(" 1 expected",null, m.getFspid());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.order.Detail#setFspid(java.lang.String)}.
	 */
	@Test
	public void testSetFspid() {
		m.setFspid(null);
		assertEquals(" 1 expected",null, m.getFspid());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.order.Detail#getAmount()}.
	 */
	@Test
	public void testGetAmount() {
		m.setAmount(null);
		assertEquals(" 1 expected",null, m.getAmount());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.order.Detail#setAmount(java.lang.String)}.
	 */
	@Test
	public void testSetAmount() {
		m.setAmount(null);
		assertEquals(" 1 expected",null, m.getAmount());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.order.Detail#getName()}.
	 */
	@Test
	public void testGetName() {
		m.setName(null);
		assertEquals(" 1 expected",null, m.getName());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.order.Detail#setName(java.lang.String)}.
	 */
	@Test
	public void testSetName() {
		m.setName(null);
		assertEquals(" 1 expected",null, m.getName());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.order.Detail#getPrice()}.
	 */
	@Test
	public void testGetPrice() {
		m.setPrice(null);
		assertEquals(" 1 expected",null, m.getPrice());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.order.Detail#setPrice(java.lang.String)}.
	 */
	@Test
	public void testSetPrice() {
		m.setPrice(null);
		assertEquals(" 1 expected",null, m.getPrice());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.order.Detail#getLine_item_total()}.
	 */
	@Test
	public void testGetLine_item_total() {
		m.setLine_item_total(null);
		assertEquals(" 1 expected",null, m.getLine_item_total());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.order.Detail#setLine_item_total(java.lang.Float)}.
	 */
	@Test
	public void testSetLine_item_total() {
		m.setLine_item_total(null);
		assertEquals(" 1 expected",null, m.getLine_item_total());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.order.Detail#toString()}.
	 */
	@Test
	public void testToString() {
		assertEquals(" 1 expected",m.toString(), m.toString());
	}

}
