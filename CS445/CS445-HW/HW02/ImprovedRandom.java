import java.util.Random;

/**
 * @author Jinyang
 *
 */
public class ImprovedRandom extends Random {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1;

	/**
	 *  default constructor
	 */
	public ImprovedRandom() {
		
	}

	/**
	 * @param seed
	 */
	public ImprovedRandom(long seed) {
		super(seed);
	}
	
	
	/**
	 * @param  low lower bound.
	 * @param  high upper bound.
	 * @return random number range in [low, high]
	 */
	public int generateIntegerInRange (int low, int high) {
		
		int out = nextInt( high + 1 - low ) + low;
		
		return out;
		
	}
	

}
