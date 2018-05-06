
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

public class AntTest {

	private static Ant ant;
	@Before
	public void setUp() throws Exception {
		ant = new Ant("tested");
	}

	@Test
	public void testMove() {
		//Prepare to redirect output
		OutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		System.setOut(ps);
		
		String crct = System.getProperty("line.separator");
		
		ant.move();
		assertEquals("tested Ant is crawling around." + crct, os.toString());
	    
		//Restore normal output
		PrintStream originalOut = System.out;
		System.setOut(originalOut);
	}

	@Test
	public void testAnt() {
		assertEquals("tested Ant expected","tested Ant", ant.toString());
	}

	@Test
	public void testCreature() {
		assertEquals("tested Ant expected","tested Ant", ant.toString());
	}

	@Test
	public void testEat() {
		//Prepare to redirect output
		OutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		System.setOut(ps);
		
		String crct = System.getProperty("line.separator");
		
		ant.eat(new Thing("Chips"));
		assertEquals("tested Ant has just eaten a Chips" + crct, os.toString());
	    
		//Restore normal output
		PrintStream originalOut = System.out;
		System.setOut(originalOut);
	}

	@Test
	public void testWhatDidYouEat() {
		
		ant.eat(new Thing("Chips"));
		
		//Prepare to redirect output
		OutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		System.setOut(ps);
		
		String crct = System.getProperty("line.separator");
		
		ant.whatDidYouEat();
		assertEquals("tested Ant has eaten a Chips" + crct, os.toString());
	    
		//Restore normal output
		PrintStream originalOut = System.out;
		System.setOut(originalOut);
	}

	@Test
	public void testToString() {
		assertEquals("tested Ant expected","tested Ant", ant.toString());
	}

}
