/**
 * 
 */
package org.pop.lf2u.configuration;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author jinyang
 *
 */
public class AppInitializerTest {
	
	private AppInitializer a;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		a = new AppInitializer();
	}

	/**
	 * Test method for {@link org.pop.lf2u.configuration.AppInitializer#getRootConfigClasses()}.
	 */
	@Test
	public void testGetRootConfigClasses() {
		assertArrayEquals(" 1 expected",new Class[] { AppConfiguration.class }, a.getRootConfigClasses());

	}

	/**
	 * Test method for {@link org.pop.lf2u.configuration.AppInitializer#getServletConfigClasses()}.
	 */
	@Test
	public void testGetServletConfigClasses() {
		assertArrayEquals(" 1 expected",null, a.getServletConfigClasses());
	}

	/**
	 * Test method for {@link org.pop.lf2u.configuration.AppInitializer#getServletMappings()}.
	 */
	@Test
	public void testGetServletMappings() {
		assertArrayEquals(" 1 expected",new String[] { "/" }, a.getServletMappings());
	}

}
