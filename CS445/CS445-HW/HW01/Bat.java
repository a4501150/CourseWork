/**
 * 
 */

/**
 * @author Jinyang Li
 *
 */
public class Bat extends Creature implements Flyer {

	/**
	 * @param name
	 */
	public Bat(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see Flyer#fly()
	 */
	@Override
	public void fly() {

		System.out.println(this.toString() + " is swooping through the dark.");

	}

	/* (non-Javadoc)
	 * @see Creature#move()
	 */
	@Override
	public void move() {

		fly();

	}
	
	@Override
	public void eat(Thing aThing) {
		
		if(aThing.getClass().getSimpleName().equals("Thing"))
			System.out.println( this.toString() + " won't eat a " + aThing.toString());
		else
			super.eat(aThing);
		
	}

}
