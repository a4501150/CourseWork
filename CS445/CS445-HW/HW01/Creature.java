/**
 * 
 */

/**
 * @author Jinyang Li
 *
 */
public abstract class Creature extends Thing {

	private Thing eatenThing;
	
	/**

	 *Create a Creature with a name.

	 *@param the given name of this Creature.

	 **/
	public Creature(String name) {
		super(name);
	}
	
	
	/**

	 *Creatures may be asked to eat various Things.

	 *@param the given name of this Thing.

	 **/
	public void eat (Thing aThing) {
		this.eatenThing = aThing;
		
		System.out.println( this.toString() + " has just eaten a " + aThing.toString());
	}
	
	/**

	 *Tell the Creature to move.

	 **/
	public abstract void move();
	
	
	/**

	 *Make the Creature tell what is in its stomach.

	 **/
	public void whatDidYouEat() {
		
		if (this.eatenThing==null)
			System.out.println( this.toString() + " has had nothing to eat");
		else
			System.out.println( this.toString() + " has eaten a " + this.eatenThing.toString());
		
	}

}
