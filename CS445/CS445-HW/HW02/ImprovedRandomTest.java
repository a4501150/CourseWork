import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ImprovedRandomTest {

	
	private static ImprovedRandom a, b;
	
	@Rule
	  public final ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setUp() throws Exception {
		a = new ImprovedRandom();
		b = new ImprovedRandom(1);
	}

	@Test
	public void testImprovedRandomLong() {
		assertEquals((new Random(1)).nextInt(),b.nextInt());
	}

	@Test
	public void testGenerateIntegerInRange() {
		
		int aout, bout;
		for(int i=0; i<1000; i++ ) {
			
		 aout = a.generateIntegerInRange(1, 5);
		 bout = b.generateIntegerInRange(1, 5);
		
			 if(1>aout||aout>5|1>bout||bout>5)
				 exception.expect(IndexOutOfBoundsException.class);


		}
		
	}

}
