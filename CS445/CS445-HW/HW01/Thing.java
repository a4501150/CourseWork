/**
 * 
 * @author Jinyang Li
 *
 **/
public class Thing {

	private final String name;
	
	/**

	 *Constructor of Thing class

	 *<p>Thing() detailed describe:</p>

	 *@param the given name of this Thing.

	 **/
	public Thing(String name) {

		this.name = name;
		
	}
	
	/**

	 *Produce a String description of this instance. If the class of the instance is Thing, then return only the name of the Thing. Otherwise add the name of the class after the name of the thing, separated by a space. this makes the class name of the thing serve as the thing's last name (surname), and the given name of the thing is its first name (given name).
	
	*@return the description of this instance.
	
	 **/
	@Override
	public String toString() {
		
		if (this.getClass().isInstance(new Thing("")))
			return this.name;
		else
			return this.name + " " +  this.getClass().getSimpleName();
		
	}
}
