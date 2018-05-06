import Buttons.*;
import Lightbulbs.*;


/**
 * 
 */

/**
 * @author Jinyang
 *
 */
public class Client {

	public Buttons bt;
	public ChainButton cbt;
	/**
	 * 
	 */
	public Client() {

		//normal button
		System.out.println("-------- "+"normal button"+"-------- \n");
		 bt = new Button(new Lightbulb());
		bt.switchOn();
		bt.switchOff();
		
		//chain button
		System.out.println("\n--------- "+"chain button"+"--------- \n");
		 cbt = new ChainButton(new Lightbulb());
		cbt.pushBotton();
		cbt.pushBotton();
		
		
	}
	
	public static void main(String[]args) {
		
		new Client();

	}

}
