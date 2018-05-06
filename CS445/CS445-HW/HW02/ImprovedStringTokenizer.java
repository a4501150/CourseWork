import java.util.StringTokenizer;


/**
 * @author Jinyang
 *
 */
public class ImprovedStringTokenizer extends StringTokenizer {

	/**
	 * @param str
	 */
	public ImprovedStringTokenizer(String str) {
		super(str);
	}

	/**
	 * @param str
	 * @param delim
	 */
	public ImprovedStringTokenizer(String str, String delim) {
		super(str, delim);
	}

	/**
	 * @param str
	 * @param delim
	 * @param returnDelims
	 */
	public ImprovedStringTokenizer(String str, String delim, boolean returnDelims) {
		super(str, delim, returnDelims);
	}
	
	public String[] returnWordsInSingleArray () {
		
		//count available single word
		int arrLength = countTokens();
		
		//declare string array
		String [] arr = new String [arrLength];
		
		//loop for putting in words
		int pos = 0;
		while(this.hasMoreTokens()) {
			
			arr[pos] = this.nextToken();
			pos ++ ;
			
		}
		
		
		return arr;
	}

}
