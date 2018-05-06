package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;



public class ClientUploader {
	
	private int peerUploadPort;
	private ServerSocket sc;

	public ClientUploader() {
		
		//specific for 0 to allocat a random usable port, thus we can run on same machine
		try {
			
			sc = new ServerSocket(0);
			
			//lambda for thread creating
			Runnable task = () -> {
				Listen();
			};
			
			Thread t = new Thread(task);
			//Start Our Job of Client Uploader
			t.start();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Cannot Initialize uploader socket, client exit.");
			System.exit(0);
			
		}
	
		//get our port, used for other peers to download file.
		peerUploadPort = sc.getLocalPort();
		
		
	}
	
	public int getUploaderPort() {
		return peerUploadPort;
	}
	
	/**
	 * 
	 * 
	 * Open a port to wait connections.
	 * 
	 * */
	
	public void Listen() {
		
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
				
				System.out.println("Error occured in uploader connection " + this.socket.getInetAddress() +", close this connection.");
				isAlive = false;
				//Client.outputPerformance();
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
	@SuppressWarnings("resource")
	public byte[] getFile(String path) {
		
		  FileChannel fc = null;  
	        try {  
	            fc = new RandomAccessFile(path, "r").getChannel();  
	            MappedByteBuffer byteBuffer = fc.map(MapMode.READ_ONLY, 0,  
	                    fc.size()).load();  
	            byte[] result = new byte[(int) fc.size()];  
	            if (byteBuffer.remaining() > 0) {  
	                byteBuffer.get(result, 0, byteBuffer.remaining());  
	            }  
	            return result;  
	        } catch (IOException e) {  
	           return null;
	        }
	        
	}
	
}

