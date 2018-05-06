import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Buttons.Button;
import Buttons.ChainButton;
import Lightbulbs.Lightbulb;

/**
 * 
 */

/**
 * @author Jinyang
 *
 */
public class ButtonTest {

	private Button bt;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		bt = new Button(new Lightbulb());
	}

	/**
	 * Test method for {@link Buttons.Button#Button(Lightbulbs.Lightbulbs)}.
	 */
	@Test
	public void testButton() {
		assertEquals("isSwitchOn false expected",false, bt.checkStatus());
	}

	/**
	 * Test method for {@link Buttons.Button#switchOn()}.
	 */
	@Test
	public void testSwitchOn() {
		bt.switchOn();
		assertEquals("isSwitchOn true expected",true, bt.checkStatus());
	}

	/**
	 * Test method for {@link Buttons.Button#switchOff()}.
	 */
	@Test
	public void testSwitchOff() {
		bt.switchOff();
		assertEquals("isSwitchOn false expected",false, bt.checkStatus());
	}

	/**
	 * Test method for {@link Buttons.Button#checkStatus()}.
	 */
	@Test
	public void testCheckStatus() {
		assertEquals("isSwitchOn false expected",false, bt.checkStatus());
		bt.switchOn();
		assertEquals("isSwitchOn true expected",true, bt.checkStatus());
	}

}
