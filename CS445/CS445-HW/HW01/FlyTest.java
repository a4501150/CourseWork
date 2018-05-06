
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

public class FlyTest {

	
	private static Fly fly;
	@Before
	public void setUp() throws Exception {
		fly = new Fly("tested");
	}

	@Test
	public void testEat() {
		//Prepare to redirect output
		OutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		System.setOut(ps);
		
		String crct = System.getProperty("line.separator");
		
	    fly.eat(new Tiger("White"));
	    assertEquals("tested Fly won't eat White Tiger"+crct,os.toString());
	    
		//Restore normal output
		PrintStream originalOut = System.out;
		System.setOut(originalOut);
		
		//
	    os = new ByteArrayOutputStream();
		ps = new PrintStream(os);
		System.setOut(ps);
		
		fly.eat(new Thing("Chips"));
		assertEquals("tested Fly has just eaten a Chips"+crct,os.toString());
		
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
		
		fly.move();
		assertEquals("tested Fly is buzzing around in flight." + crct, os.toString());
	    
		//Restore normal output
		PrintStream originalOut = System.out;
		System.setOut(originalOut);
	}

	@Test
	public void testFly() {
		assertEquals("tested Fly expected","tested Fly", fly.toString());
	}

	@Test
	public void testFly1() {
		//Prepare to redirect output
		OutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		System.setOut(ps);
		
		String crct = System.getProperty("line.separator");
		
		fly.move();
		assertEquals("tested Fly is buzzing around in flight." + crct, os.toString());
	    
		//Restore normal output
		PrintStream originalOut = System.out;
		System.setOut(originalOut);
	}

	@Test
	public void testCreature() {
		assertEquals("tested Fly expected","tested Fly", fly.toString());
	}

	@Test
	public void testWhatDidYouEat() {
		fly.eat(new Thing("Chips"));
		
		//Prepare to redirect output
		OutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		System.setOut(ps);
		
		String crct = System.getProperty("line.separator");
		
		fly.whatDidYouEat();
		assertEquals("tested Fly has eaten a Chips" + crct, os.toString());
	    
		//Restore normal output
		PrintStream originalOut = System.out;
		System.setOut(originalOut);
	}

	@Test
	public void testToString() {
		assertEquals("tested Fly expected","tested Fly", fly.toString());
	}

}
