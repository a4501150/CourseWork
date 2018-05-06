/**
 * @author Jinyang Li
 *
 */
public class Ant extends Creature {

	/**
	 * @param the given name of this Ant
	 */
	public Ant(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see Creature#move()
	 */
	@Override
	public void move() {
		
		System.out.println(this.toString() + " is crawling around.");

	}

}
