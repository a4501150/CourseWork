/**
 * 
 */
package org.pop.lf2u.model.report;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author jinyang
 *
 */
public class FarmerReportInfoTest {

	
	private FarmerReportInfo fri;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		fri = new FarmerReportInfo();
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.report.FarmerReportInfo#getFid()}.
	 */
	@Test
	public void testGetFid() {
		fri.setFid("1");
		assertEquals("fid 1 expected","1", fri.getFid());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.report.FarmerReportInfo#setFid(java.lang.String)}.
	 */
	@Test
	public void testSetFid() {
		fri.setFid("1");
		assertEquals("fid 1 expected","1", fri.getFid());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.report.FarmerReportInfo#getName()}.
	 */
	@Test
	public void testGetName() {
		fri.setName("1");
		assertEquals("name 1 expected","1", fri.getName());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.report.FarmerReportInfo#setName(java.lang.String)}.
	 */
	@Test
	public void testSetName() {
		fri.setName("1");
		assertEquals("name 1 expected","1", fri.getName());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.report.FarmerReportInfo#getOrders_placed()}.
	 */
	@Test
	public void testGetOrders_placed() {
		fri.setOrders_placed(1);
		assertEquals("order 1 expected",1, fri.getOrders_placed().intValue());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.report.FarmerReportInfo#setOrders_placed(java.lang.Integer)}.
	 */
	@Test
	public void testSetOrders_placed() {
		fri.setOrders_placed(1);
		assertEquals("order 1 expected",1, fri.getOrders_placed().intValue());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.report.FarmerReportInfo#getOrders_delivered()}.
	 */
	@Test
	public void testGetOrders_delivered() {
		fri.setOrders_delivered(1);
		assertEquals("order 1 expected",1, fri.getOrders_delivered().intValue());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.report.FarmerReportInfo#setOrders_delivered(java.lang.Integer)}.
	 */
	@Test
	public void testSetOrders_delivered() {
		fri.setOrders_delivered(1);
		assertEquals("order 1 expected",1, fri.getOrders_delivered().intValue());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.report.FarmerReportInfo#getOrders_open()}.
	 */
	@Test
	public void testGetOrders_open() {
		fri.setOrders_open(1);
		assertEquals("order 1 expected",1, fri.getOrders_open().intValue());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.report.FarmerReportInfo#setOrders_open(java.lang.Integer)}.
	 */
	@Test
	public void testSetOrders_open() {
		fri.setOrders_delivered(1);
		assertEquals("order 1 expected",1, fri.getOrders_delivered().intValue());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.report.FarmerReportInfo#getOrders_cancelled()}.
	 */
	@Test
	public void testGetOrders_cancelled() {
		fri.setOrders_cancelled(1);
		assertEquals("order 1 expected",1, fri.getOrders_cancelled().intValue());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.report.FarmerReportInfo#setOrders_cancelled(java.lang.Integer)}.
	 */
	@Test
	public void testSetOrders_cancelled() {
		fri.setOrders_cancelled(1);
		assertEquals("order 1 expected",1, fri.getOrders_cancelled().intValue());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.report.FarmerReportInfo#getProducts_revenue()}.
	 */
	@Test
	public void testGetProducts_revenue() {
		fri.setProducts_revenue((float) 1);
		assertEquals("order 1 expected",1, fri.getProducts_revenue().intValue());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.report.FarmerReportInfo#setProducts_revenue(java.lang.Float)}.
	 */
	@Test
	public void testSetProducts_revenue() {
		fri.setProducts_revenue((float) 1);
		assertEquals("order 1 expected",1, fri.getProducts_revenue().intValue());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.report.FarmerReportInfo#getDelivery_revenue()}.
	 */
	@Test
	public void testGetDelivery_revenue() {
		fri.setDelivery_revenue((float) 1);
		assertEquals("order 1 expected",1, fri.getDelivery_revenue().intValue());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.report.FarmerReportInfo#setDelivery_revenue(java.lang.Float)}.
	 */
	@Test
	public void testSetDelivery_revenue() {
		fri.setDelivery_revenue((float) 1);
		assertEquals("order 1 expected",1, fri.getDelivery_revenue().intValue());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.report.FarmerReportInfo#getLftu_fees()}.
	 */
	@Test
	public void testGetLftu_fees() {
		fri.setLftu_fees((float) 1);
		assertEquals("order 1 expected",1, fri.getLftu_fees().intValue());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.report.FarmerReportInfo#setLftu_fees(java.lang.Float)}.
	 */
	@Test
	public void testSetLftu_fees() {
		fri.setLftu_fees((float) 1);
		assertEquals("order 1 expected",1, fri.getLftu_fees().intValue());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.report.FarmerReportInfo#getPayable_to_farm()}.
	 */
	@Test
	public void testGetPayable_to_farm() {
		fri.setLftu_fees((float) 1);
		assertEquals("order 1 expected",1, fri.getLftu_fees().intValue());
	}

	/**
	 * Test method for {@link org.pop.lf2u.model.report.FarmerReportInfo#setPayable_to_farm(java.lang.Float)}.
	 */
	@Test
	public void testSetPayable_to_farm() {
		fri.setLftu_fees((float) 1);
		assertEquals("order 1 expected",1, fri.getLftu_fees().intValue());
	}

}
