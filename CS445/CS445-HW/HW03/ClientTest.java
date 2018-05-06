/**
 * 
 */


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Jinyang
 *
 */
public class ClientTest {
	
	 private Client clt;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		clt = new Client();
	}

	/**
	 * Test method for {@link Client#Client()}.
	 */
	@Test
	public void testClient() {
		assertEquals("isSwitchOn false expected",false, clt.bt.checkStatus());
	}

	/**
	 * Test method for {@link Client#main(java.lang.String[])}.
	 */
	@Test
	public void testMain() {
		assertEquals("isSwitchOn false expected",false, clt.bt.checkStatus());
	}

}
