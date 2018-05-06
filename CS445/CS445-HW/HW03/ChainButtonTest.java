import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Buttons.ChainButton;
import Lightbulbs.Lightbulb;

/**
 * 
 */

/**
 * @author Jinyang
 *
 */
public class ChainButtonTest {

	private ChainButton cbt;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		cbt = new ChainButton(new Lightbulb());
	}

	/**
	 * Test method for {@link Buttons.ChainButton#ChainButton(Lightbulbs.Lightbulbs)}.
	 */
	@Test
	public void testChainButton() {
		
		assertEquals("isSwitchOn false expected",false, cbt.checkStatus());
		
	}

	/**
	 * Test method for {@link Buttons.ChainButton#switchOn()}.
	 */
	@Test
	public void testSwitchOn() {
		
		cbt.switchOn();
		assertEquals("isSwitchOn true expected",true, cbt.checkStatus());
		
	}

	/**
	 * Test method for {@link Buttons.ChainButton#switchOff()}.
	 */
	@Test
	public void testSwitchOff() {
		
		cbt.switchOff();
		assertEquals("isSwitchOn false expected",false, cbt.checkStatus());
		
	}

	/**
	 * Test method for {@link Buttons.ChainButton#pushBotton()}.
	 */
	@Test
	public void testPushBotton() {
		
		cbt.pushBotton();
		assertEquals("isSwitchOn true expected",true, cbt.checkStatus());
		cbt.pushBotton();
		assertEquals("isSwitchOn false expected",false, cbt.checkStatus());
		
	}

	/**
	 * Test method for {@link Buttons.ChainButton#checkStatus()}.
	 */
	@Test
	public void testCheckStatus() {

		assertEquals("isSwitchOn false expected",false, cbt.checkStatus());
		cbt.pushBotton();
		assertEquals("isSwitchOn true expected",true, cbt.checkStatus());
		
	}

}
