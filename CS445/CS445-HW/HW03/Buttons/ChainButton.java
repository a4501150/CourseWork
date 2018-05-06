package Buttons;

import Buttons.Buttons;

import Lightbulbs.Lightbulbs;

/**
 * 
 */

/**
 * @author Jinyang
 *
 */
public class ChainButton implements Buttons {

	
	private boolean isSwitchOn;
	private Lightbulbs lb;
	
	
	public ChainButton(Lightbulbs lb) {
		
		this.isSwitchOn = false;
		this.lb = lb;
		
	}

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
	
	public void pushBotton() {
		
		if (isSwitchOn) 
			this.switchOff();
		else 
			this.switchOn();
		
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
