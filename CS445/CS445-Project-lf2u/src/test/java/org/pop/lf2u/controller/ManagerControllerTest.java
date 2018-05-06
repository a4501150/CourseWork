package org.pop.lf2u.controller;

import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;
import org.pop.lf2u.model.Manager;
import org.pop.lf2u.service.ManagerService;
import org.springframework.http.ResponseEntity;

/**
 * The class <code>ManagerControllerTest</code> contains tests for the class <code>{@link ManagerController}</code>.
 *
 * @generatedBy CodePro at 11/17/16 4:08 PM
 * @author jinyang
 * @version $Revision: 1.0 $
 */
public class ManagerControllerTest {
	/**
	 * Run the ManagerController() constructor test.
	 *
	 * @generatedBy CodePro at 11/17/16 4:08 PM
	 */
	@Test
	public void testManagerController_1()
		throws Exception {
		ManagerController result = new ManagerController();
		assertNotNull(result);
		// add additional test code here
	}

	/**
	 * Run the ResponseEntity<Manager> findByMid(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 11/17/16 4:08 PM
	 */
	@Test
	public void testFindByMid_1()
		throws Exception {
		ManagerController fixture = new ManagerController();
		fixture.managerService = new ManagerService();
		String mid = "";

		ResponseEntity<Manager> result = fixture.findByMid(mid);

		// add additional test code here
		assertNotNull(result);
		assertEquals("<404 Not Found,{}>", result.toString());
		assertEquals(null, result.getBody());
		assertEquals(false, result.hasBody());
	}

	/**
	 * Run the ResponseEntity<Manager> findByMid(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 11/17/16 4:08 PM
	 */
	@Test
	public void testFindByMid_2()
		throws Exception {
		ManagerController fixture = new ManagerController();
		fixture.managerService = new ManagerService();
		String mid = "";

		ResponseEntity<Manager> result = fixture.findByMid(mid);

		// add additional test code here
		assertNotNull(result);
		assertEquals("<404 Not Found,{}>", result.toString());
		assertEquals(null, result.getBody());
		assertEquals(false, result.hasBody());
	}

	/**
	 * Run the ResponseEntity<List<Manager>> listAll() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 11/17/16 4:08 PM
	 */
	@Test
	public void testListAll_1()
		throws Exception {
		ManagerController fixture = new ManagerController();
		ManagerService managerService = new ManagerService();
		managerService.add(new Manager());
		fixture.managerService = managerService;

		ResponseEntity<List<Manager>> result = fixture.listAll();

		// add additional test code here
		assertNotNull(result);

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
		new org.junit.runner.JUnitCore().run(ManagerControllerTest.class);
	}
}