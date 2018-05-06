/**
 * 
 */
package Model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author jinyang
 *
 * @category Wrapper For Data input/output
 * 
 */
public class SocketManager {

	private Socket sc;
	
	private DataInputStream dIn;
	private DataOutputStream dOut;
	
	private InputStream ins;
	private OutputStream ous;
	
	private boolean alive = false;

	public SocketManager(Socket sc) {
		
		this.sc = sc;
		
		try {
			
			ins = this.sc.getInputStream();
			ous = this.sc.getOutputStream();
			
			dIn = new DataInputStream(ins);
			dOut = new DataOutputStream(ous);
			
			setAlive(true);
						
		} catch (IOException e) {
			System.out.println("Unable to initialize streams from socket.");
			System.out.println("Address : " + sc.getInetAddress());
			System.out.println("Close Socket.");
			close();
			

		}
	}
	
	public void send(String msg) {
		
		try {
			
			dOut.writeUTF(msg);
			dOut.flush();
			
		} catch (IOException e) {
			
			System.out.println("Connection Aborted for " + this.getAddress());
			close();
			//e.printStackTrace();
			
		}
		
	}
	
	public String get() {
		
		try {
			
			return dIn.readUTF();
			
			
		} catch (IOException e) {
			
			System.out.println("Connection Aborted for " + this.getAddress());
			close();
			//e.printStackTrace();
			
		}
		
		return "";
		
	}
	
	//Wrapper for single send - recieve communication
	public String oneStepCommunicate(String msg) {
		
		send(msg);
		return get();
		
	}
	
	public void close() {
		
		setAlive(false);
		
		try {
			
			sc.close();
			
		} catch (IOException e) {
			System.out.println("Sever is Down, unable to close.");
		}
		
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public Socket getSc() {
		return sc;
	}

	public void setSc(Socket sc) {
		this.sc = sc;
	}
	
	public String getIp(){
		return sc.getInetAddress().getHostAddress()+"";
	}
	
	public String getPort(){
		return sc.getPort()+"";
	}
	
	public String getAddress(){
		return getIp() + ":" +getPort();
	}
	

}
