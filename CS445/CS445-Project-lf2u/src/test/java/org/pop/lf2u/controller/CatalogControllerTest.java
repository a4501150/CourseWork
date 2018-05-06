package org.pop.lf2u.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import org.junit.*;
import static org.junit.Assert.*;
import org.pop.lf2u.model.Catalog;
import org.pop.lf2u.service.CatalogService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

/**
 * The class <code>CatalogControllerTest</code> contains tests for the class <code>{@link CatalogController}</code>.
 *
 * @generatedBy CodePro at 11/17/16 4:08 PM
 * @author jinyang
 * @version $Revision: 1.0 $
 */
public class CatalogControllerTest {
	/**
	 * Run the CatalogController() constructor test.
	 *
	 * @generatedBy CodePro at 11/17/16 4:08 PM
	 */
	@Test
	public void testCatalogController_1()
		throws Exception {
		CatalogController result = new CatalogController();
		assertNotNull(result);
		// add additional test code here
	}

	/**
	 * Run the ResponseEntity<Catalog> create(Catalog,BindingResult) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 11/17/16 4:08 PM
	 */
	@Test
	public void testCreate_1()
		throws Exception {
		CatalogController fixture = new CatalogController();
		fixture.catalogService = new CatalogService();
		Catalog catalog = new Catalog();
		BindingResult result = new BeanPropertyBindingResult(new Object(), "");

		ResponseEntity<Catalog> result2 = fixture.create(catalog, result);

		// add additional test code here
		assertNotNull(result2);
		assertEquals("<201 Created,Catalog [gcpid=1, name=null],{}>", result2.toString());
		assertEquals(true, result2.hasBody());
	}

	/**
	 * Run the ResponseEntity<Catalog> create(Catalog,BindingResult) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 11/17/16 4:08 PM
	 */
	@Test
	public void testCreate_2()
		throws Exception {
		CatalogController fixture = new CatalogController();
		fixture.catalogService = new CatalogService();
		Catalog catalog = new Catalog();
		BindingResult result = new BeanPropertyBindingResult(new Object(), "");

		ResponseEntity<Catalog> result2 = fixture.create(catalog, result);

		// add additional test code here
		assertNotNull(result2);
		assertEquals("<201 Created,Catalog [gcpid=1, name=null],{}>", result2.toString());
		assertEquals(true, result2.hasBody());
	}

	/**
	 * Run the ResponseEntity<List<Catalog>> listAll() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 11/17/16 4:08 PM
	 */
	@Test
	public void testListAll_1()
		throws Exception {
		CatalogController fixture = new CatalogController();
		CatalogService catalogService = new CatalogService();
		catalogService.add(new Catalog());
		fixture.catalogService = catalogService;

		ResponseEntity<List<Catalog>> result = fixture.listAll();

		// add additional test code here
		assertNotNull(result);
		assertEquals("<200 OK,[Catalog [gcpid=1, name=null]],{}>", result.toString());
		assertEquals(true, result.hasBody());
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
		new org.junit.runner.JUnitCore().run(CatalogControllerTest.class);
	}
}