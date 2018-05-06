package org.pop.lf2u.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;
import org.pop.lf2u.model.Farmer;
import org.pop.lf2u.model.farmer.Farm;

public class UtilsTest {

	@Test
	public void testGenerateId() {
		assertEquals(Utils.generateId(null), "1"); 
		assertEquals(Utils.generateId("101"), "102");
	}

	@Test
	public void testFormatFloatFloat() {
		assert(Utils.formatFloat(1.2333f).equals(1.23f));
	}

	@Test
	public void testFormatFloatString() {
		assert(Utils.formatFloat("1.2333").equals(1.23f));
	}

	@Test
	public void testIsSameDay() {
		assertTrue(Utils.isSameDay(new Date(), new Date()));
		Date tomorrow = new Date();
		tomorrow.setTime(new Date().getTime()+24*60*60*1000);
		assertTrue(!Utils.isSameDay(new Date(), tomorrow));
		
		assertTrue(!Utils.isSameDay(null,null));
	}

	@Test
	public void testIsBetween() {
		Date date1 = new Date();
		Date tomorrow = new Date();
		tomorrow.setTime(date1.getTime()+24*60*60*1000);
		Date yesterday = new Date();
		yesterday.setTime(date1.getTime()-24*60*60*1000);
		
		assertTrue(Utils.isBetween(yesterday, tomorrow, date1));
		assertTrue(!Utils.isBetween(yesterday, yesterday, date1));
		assertTrue(!Utils.isBetween(tomorrow, yesterday, null));
	}

	@Test
	public void testGetTomorrowDate() {
		Date today = new Date();
		Date tomorrowDate = Utils.getTomorrowDate(today);
		assert(tomorrowDate.after(today));
	}

	@Test
	public void testContainKeyword() {
		Farmer farmer = new Farmer();
		farmer.setFarm_info(new Farm("111", null, null, null));
		farmer.setFid("keyasda");
		assert(Utils.containKeyword(farmer, "kEy"));
		assert(Utils.containKeyword(farmer, "111"));
		assert(!Utils.containKeyword(farmer, "1231"));
	}

}
