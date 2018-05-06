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
public class ReportTest {
	
	private Report m;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		m = new Report();
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Report#setFrid(java.lang.String)}.
	 */
	@Test
	public void testSetFrid() {
		m.setFrid("1");
		assertEquals(" 1 expected","1", m.getFrid());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Report#getName()}.
	 */
	@Test
	public void testGetName() {
		m.setName("1");
		assertEquals(" 1 expected","1", m.getName());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Report#getStart_date()}.
	 */
	@Test
	public void testGetStart_date() {
		m.setStart_date(null);
		assertEquals(" 1 expected",null, m.getStart_date());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Report#setStart_date(java.util.Date)}.
	 */
	@Test
	public void testSetStart_date() {
		m.setStart_date(null);
		assertEquals(" 1 expected",null, m.getStart_date());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Report#getEnd_date()}.
	 */
	@Test
	public void testGetEnd_date() {
		m.setEnd_date(null);
		assertEquals(" 1 expected",null, m.getEnd_date());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Report#setEnd_date(java.util.Date)}.
	 */
	@Test
	public void testSetEnd_date() {
		m.setEnd_date(null);
		assertEquals(" 1 expected",null, m.getEnd_date());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Report#getOrders_placed()}.
	 */
	@Test
	public void testGetOrders_placed() {
		m.setOrders_placed(null);
		assertEquals(" 1 expected",null, m.getOrders_placed());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Report#setOrders_placed(java.lang.Integer)}.
	 */
	@Test
	public void testSetOrders_placed() {
		m.setOrders_placed(null);
		assertEquals(" 1 expected",null, m.getOrders_placed());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Report#getOrders_cancelled()}.
	 */
	@Test
	public void testGetOrders_cancelled() {
		m.setOrders_cancelled(null);
		assertEquals(" 1 expected",null, m.getOrders_cancelled());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Report#setOrders_cancelled(java.lang.Integer)}.
	 */
	@Test
	public void testSetOrders_cancelled() {
		m.setOrders_cancelled(null);
		assertEquals(" 1 expected",null, m.getOrders_cancelled());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Report#getOrders_delivered()}.
	 */
	@Test
	public void testGetOrders_delivered() {
		m.setOrders_delivered(null);
		assertEquals(" 1 expected",null, m.getOrders_delivered());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Report#setOrders_delivered(java.lang.Integer)}.
	 */
	@Test
	public void testSetOrders_delivered() {
		m.setOrders_delivered(null);
		assertEquals(" 1 expected",null, m.getOrders_delivered());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Report#getProducts_revenue()}.
	 */
	@Test
	public void testGetProducts_revenue() {
		m.setProducts_revenue(null);
		assertEquals(" 1 expected",null, m.getProducts_revenue());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Report#setProducts_revenue(java.lang.Float)}.
	 */
	@Test
	public void testSetProducts_revenue() {
		m.setProducts_revenue(null);
		assertEquals(" 1 expected",null, m.getProducts_revenue());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Report#getDelivery_revenue()}.
	 */
	@Test
	public void testGetDelivery_revenue() {
		m.setDelivery_revenue(null);
		assertEquals(" 1 expected",null, m.getDelivery_revenue());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Report#setDelivery_revenue(java.lang.Float)}.
	 */
	@Test
	public void testSetDelivery_revenue() {
		m.setDelivery_revenue(null);
		assertEquals(" 1 expected",null, m.getDelivery_revenue());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Report#getOrders()}.
	 */
	@Test
	public void testGetOrders() {
		m.setOrders(null);
		assertEquals(" 1 expected",null, m.getOrders());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Report#setOrders(java.util.List)}.
	 */
	@Test
	public void testSetOrders() {
		m.setOrders(null);
		assertEquals(" 1 expected",null, m.getOrders());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Report#getBy_farmer()}.
	 */
	@Test
	public void testGetBy_farmer() {
		m.setBy_farmer(null);
		assertEquals(" 1 expected",null, m.getBy_farmer());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Report#setBy_farmer(java.util.List)}.
	 */
	@Test
	public void testSetBy_farmer() {
		m.setBy_farmer(null);
		assertEquals(" 1 expected",null, m.getBy_farmer());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Report#setMrid(java.lang.String)}.
	 */
	@Test
	public void testSetMrid() {
		m.setMrid(null);
		assertEquals(" 1 expected",null, m.getMrid());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Report#getOrders_open()}.
	 */
	@Test
	public void testGetOrders_open() {
		m.setOrders_open(null);
		assertEquals(" 1 expected",null, m.getOrders_open());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.Report#setOrders_open(java.lang.Integer)}.
	 */
	@Test
	public void testSetOrders_open() {
		m.setOrders_open(null);
		assertEquals(" 1 expected",null, m.getOrders_open());
	}

}
