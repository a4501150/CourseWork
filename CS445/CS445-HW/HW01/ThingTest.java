
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 */

/**
 * @author Administrator
 *
 */
public class ThingTest {

	private static Thing thing;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		thing = new Thing("testedThing");
	}

	/**
	 * Test method for {@link Thing#Thing(java.lang.String)}.
	 */
	@Test
	public void testThing() {
		
		assertEquals("testedThing expected","testedThing", thing.toString());
		thing = new Thing("");
		assertEquals("expected","", thing.toString());
		thing = new Tiger("a");
		assertEquals("a Tiger expected","a Tiger", thing.toString());
		
	}

	/**
	 * Test method for {@link Thing#toString()}.
	 */
	@Test
	public void testToString() {
		
		thing = new Thing("sss");
		assertEquals("toString error","sss", thing.toString());
		
	}

}
