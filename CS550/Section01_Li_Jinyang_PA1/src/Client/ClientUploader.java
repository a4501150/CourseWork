package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;



public class ClientUploader {
	
	private static int peerPort;
	private ServerSocket sc;

	public ClientUploader() {
		
		//specific for 0 to allocat a random usable port, thus we can run on same machine
		try {
			sc = new ServerSocket(0);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Cannot Initialize uploader socket, client exit.");
			System.exit(0);
			
		}
	
		//get our port, used for other peers to download file.
		peerPort = sc.getLocalPort();
		
		
	}
	
	public int getPeerInfo() {
		return peerPort;
	}
	
	/**
	 * 
	 * 
	 * Open a port to wait connections.
	 * 
	 * */
	
	public void Listen() {
		
		System.out.println("uploder is listening at " +  peerPort);

		//default server accept loop.
		while(true) {
			
			try {
			Socket inCome = sc.accept();
			Thread t = new Thread(new ClientUploaderHandler(inCome));
			//Start Our Job
			t.start();	
			}
			
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("There's some error with peer downloading connection, error socket is closed.");
				break;
			}
			
			
		}
		
		
	}

}



/**
 * 
 * 
 * handle incoming request for download.
 * 
 * */
class ClientUploaderHandler implements Runnable{

	private Socket socket;
	
	public ClientUploaderHandler(Socket sc) {
		// TODO Auto-generated constructor stub
		this.socket = sc;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		boolean isAlive = true;
		while(isAlive) {
			
			try {
			//Establishing streams over Clients 
			DataInputStream dIn = new DataInputStream(socket.getInputStream());
			DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
			
			String reason = dIn.readUTF();
			
			switch(reason) {
			
			case "check":
				dOut.writeUTF("1");
				dOut.flush();
				break;
			case "download":
				
				dOut.writeUTF("1");
				dOut.flush();
				
				String dowanloaFilepath = dIn.readUTF();
				
				//get binary data of file
				byte[] file = getFile(dowanloaFilepath);
				
				if(file!=null) {
					
					//send size first
					dOut.writeInt(file.length);
					dOut.flush();
					
					//send binary data of file
					dOut.write(file);
					dOut.flush();
					
				}
				
				break;
				
			case "exit":
				socket.close();
				isAlive = false;
				break;
		
			default:
				break;
			
			}
			
			
			
			} catch(IOException e) {
				
				System.out.print("Error occured in uploader, close this connection.");
				isAlive = false;
				Client.outputPerformance();
				Thread.currentThread().interrupt();
				break;
				
			}
			
		}
		
	}
	
	/**
	 * 
	 * 
	 * return bytes represents of file, used for transform over socket.
	 * 
	 * */
	public byte[] getFile(String path) {
		
		  FileChannel fc = null;  
	        try {  
	            fc = new RandomAccessFile(path, "r").getChannel();  
	            MappedByteBuffer byteBuffer = fc.map(MapMode.READ_ONLY, 0,  
	                    fc.size()).load();  
	            //System.out.println(byteBuffer.isLoaded());  
	            byte[] result = new byte[(int) fc.size()];  
	            if (byteBuffer.remaining() > 0) {  
	                // System.out.println("remain");  
	                byteBuffer.get(result, 0, byteBuffer.remaining());  
	            }  
	            return result;  
	        } catch (IOException e) {  
	           return null;
	        }
	        
	}
	
}

