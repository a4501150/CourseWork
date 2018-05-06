/**
 * 
 */
package Buttons;

import Lightbulbs.Lightbulbs;

/**
 * @author Jinyang
 *
 */
public class Button implements Buttons {
	
	
	private boolean isSwitchOn;
	private Lightbulbs lb;
	/**
	 * 
	 */
	public Button(Lightbulbs lb) {
		// TODO Auto-generated constructor stub
		
		this.isSwitchOn = false;
		this.lb = lb;
		
	}

	/* (non-Javadoc)
	 * @see Buttons.Buttons#switchOn()
	 */
	@Override
	public void switchOn() {

		this.isSwitchOn = true;
		System.out.println("Button switched to ON");
		lb.on();

	}

	/* (non-Javadoc)
	 * @see Buttons.Buttons#switchoff()
	 */
	@Override
	public void switchOff() {
		// TODO Auto-generated method stub
		
		this.isSwitchOn = false;
		System.out.println("Button switched to OFF");
		lb.off();

	}

	/* (non-Javadoc)
	 * @see Buttons.Buttons#checkStatus()
	 */
	@Override
	public boolean checkStatus() {
		// TODO Auto-generated method stub
		return this.isSwitchOn;
	}

}
