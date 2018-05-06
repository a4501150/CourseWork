package org.pop.lf2u.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class OrderTest {
	
	private Order m;

	@Before
	public void setUp() throws Exception {
		m = new Order();
	}

	@Test
	public void testOrderString() {
		m = new Order("1");
		assertEquals(" 1 expected","1", m.getOid());
	}

	@Test
	public void testOrderStringDateDateDateStringString() {
		m = new Order("1",null,null,null,null,null);
		assertEquals(" 1 expected","1", m.getOid());
	}

	@Test
	public void testGetFid() {
		m.setFid("1");
		assertEquals(" 1 expected","1", m.getFid());
	}

	@Test
	public void testGetOrder_date() {
		m.setOrder_date(null);
		assertEquals(" 1 expected",null, m.getOrder_date());
	}

	@Test
	public void testSetOrder_date() {
		m.setOrder_date(null);
		assertEquals(" 1 expected",null, m.getOrder_date());
	}

	@Test
	public void testGetPlanned_delivery_date() {
		m.setPlanned_delivery_date(null);
		assertEquals(" 1 expected",null, m.getPlanned_delivery_date());
	}

	@Test
	public void testSetPlanned_delivery_date() {
		m.setPlanned_delivery_date(null);
		assertEquals(" 1 expected",null, m.getPlanned_delivery_date());
	}

	@Test
	public void testGetDelivery_note() {
		m.setDelivery_note(null);
		assertEquals(" 1 expected",null, m.getDelivery_note());
	}

	@Test
	public void testSetDelivery_note() {
		m.setDelivery_note(null);
		assertEquals(" 1 expected",null, m.getDelivery_note());
	}

	@Test
	public void testGetProducts_total() {
		m.setProducts_total(null);
		assertEquals(" 1 expected",null, m.getProducts_total());
	}

	@Test
	public void testSetProducts_total() {
		m.setProducts_total(null);
		assertEquals(" 1 expected",null, m.getProducts_total());
	}

	@Test
	public void testGetDelivery_charge() {
		m.setDelivery_charge(null);
		assertEquals(" 1 expected",null, m.getDelivery_charge());
	}

	@Test
	public void testSetDelivery_charge() {
		m.setDelivery_charge(null);
		assertEquals(" 1 expected",null, m.getDelivery_charge());
	}

	@Test
	public void testGetOrder_total() {
		m.setOrder_total(null);
		assertEquals(" 1 expected",null, m.getOrder_total());
	}
	
	@Test
	public void testGetOrder_detail() {
		m.setOrder_detail(null);
		assertEquals(" 1 expected",null, m.getOrder_detail());
	}

	@Test
	public void testSetOrder_total() {
		m.setOrder_total(null);
		assertEquals(" 1 expected",null, m.getOrder_total());
	}

	@Test
	public void testGetFarm_info() {
		m.setFarm_info(null);
		assertEquals(" 1 expected",null, m.getFarm_info());
	}

	@Test
	public void testSetFarm_info() {
		m.setFarm_info(null);
		assertEquals(" 1 expected",null, m.getFarm_info());
	}

	@Test
	public void testGetActual_delivery_date() {
		m.setActual_delivery_date(null);
		assertEquals(" 1 expected",null, m.getActual_delivery_date());
	}

	@Test
	public void testGetCancel_date() {
		m.setCancel_date(null);
		assertEquals(" 1 expected",null, m.getCancel_date());
	}

	@Test
	public void testToString() {
		assertEquals(" 1 expected","Order [oid=null, fid=null, cid=null, order_date=null, planned_delivery_date=null, actual_delivery_date=null, status=null, order_detail=null, delivery_note=null, products_total=null, delivery_charge=null, order_total=null, farm_info=null]", m.toString());
	}

}
