
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

public class BatTest {

	private static Bat bat;
	@Before
	public void setUp() throws Exception {
		bat = new Bat("tested");
	}

	@Test
	public void testEat() {
		//Prepare to redirect output
		OutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		System.setOut(ps);
		
		String crct = System.getProperty("line.separator");
		
		bat.eat(new Tiger("White"));
	    assertEquals("tested Bat has just eaten a White Tiger"+crct,os.toString());
	    
		//Restore normal output
		PrintStream originalOut = System.out;
		System.setOut(originalOut);
		
		//
	    os = new ByteArrayOutputStream();
		ps = new PrintStream(os);
		System.setOut(ps);
		
		bat.eat(new Thing("Chips"));
		assertEquals("tested Bat won't eat a Chips"+crct,os.toString());
		
		//Restore normal output
	    originalOut = System.out;
	    System.setOut(originalOut);
	}

	@Test
	public void testMove() {
		//Prepare to redirect output
		OutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		System.setOut(ps);
		
		String crct = System.getProperty("line.separator");
		
		bat.move();
		assertEquals("tested Bat is swooping through the dark." + crct, os.toString());
	    
		//Restore normal output
		PrintStream originalOut = System.out;
		System.setOut(originalOut);
	}

	@Test
	public void testBat() {
		assertEquals("tested Bat expected","tested Bat", bat.toString());
	}

	@Test
	public void testFly() {
		//Prepare to redirect output
		OutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		System.setOut(ps);
		
		String crct = System.getProperty("line.separator");
		
		bat.move();
		assertEquals("tested Bat is swooping through the dark." + crct, os.toString());
	    
		//Restore normal output
		PrintStream originalOut = System.out;
		System.setOut(originalOut);
	}

	@Test
	public void testCreature() {
		assertEquals("tested Bat expected","tested Bat", bat.toString());
	}

	@Test
	public void testWhatDidYouEat() {
		bat.eat(new Tiger("White"));
		
		//Prepare to redirect output
		OutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		System.setOut(ps);
		
		String crct = System.getProperty("line.separator");
		
		bat.whatDidYouEat();
		assertEquals("tested Bat has eaten a White Tiger" + crct, os.toString());
	    
		//Restore normal output
		PrintStream originalOut = System.out;
		System.setOut(originalOut);
	}

	@Test
	public void testThing() {
		assertEquals("tested Bat expected","tested Bat", bat.toString());
	}

	@Test
	public void testToString() {
		assertEquals("tested Bat expected","tested Bat", bat.toString());
	}

}
