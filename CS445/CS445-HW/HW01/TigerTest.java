
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

public class TigerTest {
	
	private static Tiger tiger;
	@Before
	public void setUp() throws Exception {
		tiger = new Tiger("tested");
	}

	@Test
	public void testMove() {
		//Prepare to redirect output
		OutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		System.setOut(ps);
		
		String crct = System.getProperty("line.separator");
		
		tiger.move();
		assertEquals("tested Tiger has jsut pounced." + crct, os.toString());
	    
		//Restore normal output
		PrintStream originalOut = System.out;
		System.setOut(originalOut);
	}

	@Test
	public void testTiger() {
		assertEquals("tested Tiger expected","tested Tiger", tiger.toString());
	}

	@Test
	public void testCreature() {
		assertEquals("tested Tiger expected","tested Tiger", tiger.toString());
	}

	@Test
	public void testEat() {
		
		
		
		//Prepare to redirect output
		OutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		System.setOut(ps);
		
		String crct = System.getProperty("line.separator");
		
		tiger.eat(new Thing("Chips"));
		assertEquals("tested Tiger has just eaten a Chips" + crct, os.toString());
	    
		//Restore normal output
		PrintStream originalOut = System.out;
		System.setOut(originalOut);
		
	}

	@Test
	public void testWhatDidYouEat() {
		
		tiger.eat(new Thing("Chips"));
		
		//Prepare to redirect output
		OutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		System.setOut(ps);
		
		String crct = System.getProperty("line.separator");
		
		tiger.whatDidYouEat();
		assertEquals("tested Tiger has eaten a Chips" + crct, os.toString());
	    
		//Restore normal output
		PrintStream originalOut = System.out;
		System.setOut(originalOut);
		
	}

	@Test
	public void testToString() {
		assertEquals("tested Tiger expected","tested Tiger", tiger.toString());
	}

}
