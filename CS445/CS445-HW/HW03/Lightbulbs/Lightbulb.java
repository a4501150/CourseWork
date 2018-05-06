/**
 * 
 */
package Lightbulbs;

/**
 * @author Jinyang
 *
 */
public class Lightbulb implements Lightbulbs {

	private boolean isOn;
	/**
	 * 
	 */
	public Lightbulb() {

		this.isOn = false;
		
	}
	
	public void on() {
		
		this.isOn = true;
		System.out.println("Lightbulb on");
		
	}
	
	public void off() {
		
		this.isOn = false;
		System.out.println("Lightbulb off");
		
	}
	
	public boolean checkStatus() {
		
		return isOn;
	}

}
