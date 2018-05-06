
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

public class TestCreatureTest {

	@Before
	public void setUp() throws Exception {
		new TestCreature();
	}

	@Test
	public void testTestCreature() {
		assertEquals("5 expedted",5,TestCreature.CREATURE_COUNT);
		assertEquals("5 expedted",5,TestCreature.THING_COUNT);
	}

	@Test
	public void testMain() {
		//Prepare to redirect output
		OutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		System.setOut(ps);
		
		System.getProperty("line.separator");
		
		TestCreature.main(null);
		String actuallString = os.toString().replaceAll("\\s+","");
		String expectedString = "Things: Random Thing #0Random Thing #1Random Thing #2Random Thing #3Random Thing #4Creatures: White TigerBig AntYellow FlyBlack BatRed Fly";
		expectedString = expectedString.replaceAll("\\s+","");
		
		assertEquals(expectedString,actuallString);
	    
		//Restore normal output
		PrintStream originalOut = System.out;
		System.setOut(originalOut);
	}

	@Test
	public void testConcat() {
		Thing[] thingsArrayOne = new Thing[]{new Thing("1"), new Thing("2")};
		Thing[] thingsArrayTwo = new Thing[]{new Thing("3"), new Thing("4")};
		
		Thing[] thingsArrayThree = TestCreature.concat(thingsArrayOne,thingsArrayTwo);
		
		Thing[] thingsArrayFour = new Thing[]{new Thing("1"), new Thing("2"),new Thing("3"), new Thing("4")};
		
		for(int i=0;i<thingsArrayThree.length;i++)
			assertEquals("Two array equals expedted",thingsArrayThree[i].toString(),thingsArrayFour[i].toString());
		
	}

}
