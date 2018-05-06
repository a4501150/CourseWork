
/**
 * @author Jinyang Li
 *
 */
public class Tiger extends Creature {

	/**
	 * @param the given name of this Tiger.
	 */
	public Tiger(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see Creature#move()
	 */
	@Override
	public void move() {

		System.out.println(this.toString() + " has jsut pounced.");

	}

}
