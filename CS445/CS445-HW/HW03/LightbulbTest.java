import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Lightbulbs.*;

/**
 * 
 */

/**
 * @author Jinyang
 *
 */
public class LightbulbTest {
	
	private static Lightbulb lb;
	@Before
	public void setUp() throws Exception {
		lb = new Lightbulb();
	}

	/**
	 * Test method for {@link Lightbulbs.Lightbulb#Lightbulb()}.
	 */
	@Test
	public void testLightbulb() {
		
		assertEquals("isOn false expected",false, lb.checkStatus());
		
	}

	/**
	 * Test method for {@link Lightbulbs.Lightbulb#on()}.
	 */
	@Test
	public void testOn() {
		
		lb.on();
		assertEquals("isOn true expected",true, lb.checkStatus());
		
	}

	/**
	 * Test method for {@link Lightbulbs.Lightbulb#off()}.
	 */
	@Test
	public void testOff() {

		lb.off();
		assertEquals("isOn false expected",false, lb.checkStatus());
		
	}

	/**
	 * Test method for {@link Lightbulbs.Lightbulb#checkStatus()}.
	 */
	@Test
	public void testCheckStatus() {
		
		assertEquals("false expected",false, lb.checkStatus());
		lb.on();
		assertEquals("true expected",true, lb.checkStatus());
		
	}

}
