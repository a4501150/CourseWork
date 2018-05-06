package org.pop.lf2u.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import org.junit.*;
import static org.junit.Assert.*;
import org.pop.lf2u.model.Farmer;
import org.pop.lf2u.model.farmer.FarmInfo;
import org.pop.lf2u.service.FarmerService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

/**
 * The class <code>FarmerControllerTest</code> contains tests for the class <code>{@link FarmerController}</code>.
 *
 * @generatedBy CodePro at 11/17/16 4:08 PM
 * @author jinyang
 * @version $Revision: 1.0 $
 */
public class FarmerControllerTest {
	/**
	 * Run the FarmerController() constructor test.
	 *
	 * @generatedBy CodePro at 11/17/16 4:08 PM
	 */
	@Test
	public void testFarmerController_1()
		throws Exception {
		FarmerController result = new FarmerController();
		assertNotNull(result);
		// add additional test code here
	}

	/**
	 * Run the ResponseEntity<Farmer> create(Farmer,BindingResult) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 11/17/16 4:08 PM
	 */
	@Test
	public void testCreate_1()
		throws Exception {
		FarmerController fixture = new FarmerController();
		fixture.farmerService = new FarmerService();
		Farmer farmer = new Farmer();
		BindingResult result = new BeanPropertyBindingResult(new Object(), "");

		ResponseEntity<Farmer> result2 = fixture.create(farmer, result);

		// add additional test code here
		assertNotNull(result2);
		assertEquals("<201 Created,Farmer [fid=1, farm_info=null, personal_info=null, delivers_to=null],{}>", result2.toString());
		assertEquals(true, result2.hasBody());
	}

	/**
	 * Run the ResponseEntity<Farmer> create(Farmer,BindingResult) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 11/17/16 4:08 PM
	 */
	@Test
	public void testCreate_2()
		throws Exception {
		FarmerController fixture = new FarmerController();
		fixture.farmerService = new FarmerService();
		Farmer farmer = new Farmer();
		BindingResult result = new BeanPropertyBindingResult(new Object(), "");

		ResponseEntity<Farmer> result2 = fixture.create(farmer, result);

		// add additional test code here
		assertNotNull(result2);
		assertEquals("<201 Created,Farmer [fid=1, farm_info=null, personal_info=null, delivers_to=null],{}>", result2.toString());
		assertEquals(true, result2.hasBody());
	}

	/**
	 * Run the ResponseEntity<Map<String, Float>> getDeliveryCharge(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 11/17/16 4:08 PM
	 */
	@Test
	public void testGetDeliveryCharge_1()
		throws Exception {
		FarmerController fixture = new FarmerController();
		fixture.farmerService = new FarmerService();
		String fid = "";

		ResponseEntity<Map<String, Float>> result = fixture.getDeliveryCharge(fid);

		// add additional test code here
		assertNotNull(result);
		assertEquals("<404 Not Found,{}>", result.toString());
		assertEquals(null, result.getBody());
		assertEquals(false, result.hasBody());
	}

	/**
	 * Run the ResponseEntity<Map<String, Float>> getDeliveryCharge(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 11/17/16 4:08 PM
	 */
	@Test
	public void testGetDeliveryCharge_2()
		throws Exception {
		FarmerController fixture = new FarmerController();
		fixture.farmerService = new FarmerService();
		String fid = "";

		ResponseEntity<Map<String, Float>> result = fixture.getDeliveryCharge(fid);

		// add additional test code here
		assertNotNull(result);
		assertEquals("<404 Not Found,{}>", result.toString());
		assertEquals(null, result.getBody());
		assertEquals(false, result.hasBody());
	}

	/**
	 * Run the ResponseEntity<List<FarmInfo>> searchByZip(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 11/17/16 4:08 PM
	 */
	@Test
	public void testSearchByZip_1()
		throws Exception {
		FarmerController fixture = new FarmerController();
		fixture.farmerService = new FarmerService();
		String zip = "";

		ResponseEntity<List<FarmInfo>> result = fixture.searchByZip(zip);

		// add additional test code here
		assertNotNull(result);
		assertEquals("<200 OK,[],{}>", result.toString());
		assertEquals(true, result.hasBody());
	}

	/**
	 * Run the ResponseEntity<List<FarmInfo>> searchByZip(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 11/17/16 4:08 PM
	 */
	@Test
	public void testSearchByZip_2()
		throws Exception {
		FarmerController fixture = new FarmerController();
		fixture.farmerService = new FarmerService();
		String zip = "";

		ResponseEntity<List<FarmInfo>> result = fixture.searchByZip(zip);

		// add additional test code here
		assertNotNull(result);
		assertEquals("<200 OK,[],{}>", result.toString());
		assertEquals(true, result.hasBody());
	}


	/**
	 * Run the ResponseEntity<Void> update(String,Farmer,BindingResult) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 11/17/16 4:08 PM
	 */
	@Test
	public void testUpdate_1()
		throws Exception {
		FarmerController fixture = new FarmerController();
		fixture.farmerService = new FarmerService();
		String fid = "";
		Farmer farmer = new Farmer();
		BindingResult result = new BeanPropertyBindingResult(new Object(), "");

		ResponseEntity<Void> result2 = fixture.update(fid, farmer, result);

		// add additional test code here
		assertNotNull(result2);
		assertEquals("<404 Not Found,{}>", result2.toString());
		assertEquals(null, result2.getBody());
		assertEquals(false, result2.hasBody());
	}

	/**
	 * Run the ResponseEntity<Void> update(String,Farmer,BindingResult) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 11/17/16 4:08 PM
	 */
	@Test
	public void testUpdate_2()
		throws Exception {
		FarmerController fixture = new FarmerController();
		fixture.farmerService = new FarmerService();
		String fid = "";
		Farmer farmer = new Farmer();
		BindingResult result = new BeanPropertyBindingResult(new Object(), "");

		ResponseEntity<Void> result2 = fixture.update(fid, farmer, result);

		// add additional test code here
		assertNotNull(result2);
		assertEquals("<404 Not Found,{}>", result2.toString());
		assertEquals(null, result2.getBody());
		assertEquals(false, result2.hasBody());
	}

	/**
	 * Run the ResponseEntity<Void> update(String,Farmer,BindingResult) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 11/17/16 4:08 PM
	 */
	@Test
	public void testUpdate_3()
		throws Exception {
		FarmerController fixture = new FarmerController();
		fixture.farmerService = new FarmerService();
		String fid = "";
		Farmer farmer = new Farmer();
		BindingResult result = new BeanPropertyBindingResult(new Object(), "");

		ResponseEntity<Void> result2 = fixture.update(fid, farmer, result);

		// add additional test code here
		assertNotNull(result2);
		assertEquals("<404 Not Found,{}>", result2.toString());
		assertEquals(null, result2.getBody());
		assertEquals(false, result2.hasBody());
	}

	/**
	 * Run the ResponseEntity<Void> update(String,Farmer,BindingResult) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 11/17/16 4:08 PM
	 */
	@Test
	public void testUpdate_4()
		throws Exception {
		FarmerController fixture = new FarmerController();
		fixture.farmerService = new FarmerService();
		String fid = "";
		Farmer farmer = new Farmer();
		BindingResult result = new BeanPropertyBindingResult(new Object(), "");

		ResponseEntity<Void> result2 = fixture.update(fid, farmer, result);

		// add additional test code here
		assertNotNull(result2);
		assertEquals("<404 Not Found,{}>", result2.toString());
		assertEquals(null, result2.getBody());
		assertEquals(false, result2.hasBody());
	}

	/**
	 * Run the ResponseEntity<Farmer> view(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 11/17/16 4:08 PM
	 */
	@Test
	public void testView_1()
		throws Exception {
		FarmerController fixture = new FarmerController();
		fixture.farmerService = new FarmerService();
		String fid = "";

		ResponseEntity<Farmer> result = fixture.view(fid);

		// add additional test code here
		assertNotNull(result);
		assertEquals("<404 Not Found,{}>", result.toString());
		assertEquals(null, result.getBody());
		assertEquals(false, result.hasBody());
	}

	/**
	 * Run the ResponseEntity<Farmer> view(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 11/17/16 4:08 PM
	 */
	@Test
	public void testView_2()
		throws Exception {
		FarmerController fixture = new FarmerController();
		fixture.farmerService = new FarmerService();
		String fid = "";

		ResponseEntity<Farmer> result = fixture.view(fid);

		// add additional test code here
		assertNotNull(result);
		assertEquals("<404 Not Found,{}>", result.toString());
		assertEquals(null, result.getBody());
		assertEquals(false, result.hasBody());
	}

	/**
	 * Perform pre-test initialization.
	 *
	 * @throws Exception
	 *         if the initialization fails for some reason
	 *
	 * @generatedBy CodePro at 11/17/16 4:08 PM
	 */
	@Before
	public void setUp()
		throws Exception {
		// add additional set up code here
	}

	/**
	 * Perform post-test clean-up.
	 *
	 * @throws Exception
	 *         if the clean-up fails for some reason
	 *
	 * @generatedBy CodePro at 11/17/16 4:08 PM
	 */
	@After
	public void tearDown()
		throws Exception {
		// Add additional tear down code here
	}

	/**
	 * Launch the test.
	 *
	 * @param args the command line arguments
	 *
	 * @generatedBy CodePro at 11/17/16 4:08 PM
	 */
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(FarmerControllerTest.class);
	}
}