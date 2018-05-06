import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ImprovedStringTokenizerTest {
	
	
	private static ImprovedStringTokenizer a, b, c, d;
	@Before
	public void setUp() throws Exception {
		a = new ImprovedStringTokenizer("test string a");
		b = new ImprovedStringTokenizer("test|string|b", "|");
		c = new ImprovedStringTokenizer("test|string|c", "|", true);
		d = new ImprovedStringTokenizer("test|string|d", "|", false);
	}

	@Test
	public void testImprovedStringTokenizerString() {
		
		assertEquals("3 counts expected", 3, a.countTokens());
	}

	@Test
	public void testImprovedStringTokenizerStringString() {

		assertEquals("3 counts expected", 3, b.countTokens());
		
	}

	@Test
	public void testImprovedStringTokenizerStringStringBoolean() {
		
		assertEquals("3 counts expected", 5, c.countTokens());
		assertEquals("3 counts expected", 3, d.countTokens());
		
	}

	@Test
	public void testReturnWordsInSingleArray() {
		
		String [] aArr = a.returnWordsInSingleArray();
		String [] bArr = b.returnWordsInSingleArray();
		String [] cArr = c.returnWordsInSingleArray();
		String [] dArr = d.returnWordsInSingleArray();
		
		assertArrayEquals("array equal expected", new String[]{"test","string","a"}, aArr);
		assertArrayEquals("array equal expected", new String[]{"test","string","b"}, bArr);
		

		assertArrayEquals("array equal expected", new String[]{"test","|","string","|","c"}, cArr);
		assertArrayEquals("array equal expected", new String[]{"test","string","d"}, dArr);
		
		
	}

}
