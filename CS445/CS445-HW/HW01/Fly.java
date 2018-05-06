
/**
 * @author Jinyang Li
 *
 */
public class Fly extends Creature implements Flyer {

	/**
	 * @param the given name of this Fly
	 */
	public Fly(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see Flyer#fly()
	 */
	@Override
	public void fly() {

		System.out.println(this.toString() + " is buzzing around in flight.");

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
		
		if(aThing instanceof Creature)
			System.out.println( this.toString() + " won't eat " + aThing.toString());
		else
			super.eat(aThing);
		
	}

}
