/**
 * 
 */
package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * @author Jinyang
 *
 */
public class CentralIndexServer {

	//Server Listening Port
	private static int listening_port;
	private ServerSocket server_socket;
	
	/**
	 * @param listening_port
	 * @throws IOException 
	 * 
	 */
	public CentralIndexServer(int port) throws IOException {


		listening_port = port;
		
		//initialize ServerSocket
	    server_socket = new ServerSocket(listening_port);
		
		//wait for Connections.
		System.out.println("Server Is Lisening At Port >>> " + listening_port);


		//loop for waiting peer connected
		while(true)
		{

			try {
			Socket c_socket =  server_socket.accept();//accept client connection
			Thread t = new Thread(new GateWay(c_socket));
			//Start Our Job
			t.start();
			} 
			catch (SocketException se) {
				
				System.out.println("Connection is either reset or c0rrupted, Closeing  this socket.. ");
				break;
			}
				
		}
		
	}
	


	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		//initialize server
		new CentralIndexServer(6666);

	}

}





class GateWay implements Runnable  {
	
	private Socket Client_Socket;
	private String peerAddress;
	
	//HashMap store Key(Filename) and Value(File Location)
	public static HashMap<String, ArrayList<String>> FileMap = new HashMap<String, ArrayList<String>>();
	
	GateWay(Socket socket){
		
		Client_Socket = socket;
		peerAddress = Client_Socket.getInetAddress().getHostAddress().substring(0);
		System.out.println("Peer at " + peerAddress + " is Connected!");
		
	}
	
	@Override
	public void run()
	{

		System.out.println("Waiting For Peer "+ peerAddress + "'s Request.");
		boolean isAlive = true;

		//Keep Alive while communicating.
		while(isAlive)
		{
			try
			{
				String data = null;
				String searchedFileName = null;

				//Establishing streams over Clients and Server
				DataInputStream dIn = new DataInputStream(Client_Socket.getInputStream());
				DataOutputStream dOut = new DataOutputStream(Client_Socket.getOutputStream());

				//Get the reason of connection
				String Reason = dIn.readUTF();

				//Switch Reason of Connection
				switch(Reason)
				{
				case "1": //Registration of Files
					System.out.println("Peer at "+ peerAddress +" Requested to registered a file.");
					data = dIn.readUTF();

					//if client say local file is invalid 
					if(data.equalsIgnoreCase("Invalid File"))
						break;
					
					//else, the server is expected to recieved a shared file object string 
					SharedFile sf = new SharedFile();
					String []sharedFileFileds = data.split(",");
					
					//sharedFile.tostring : return  fileName +","+ peer+","+ checksum+","+size;
					sf.setFileName(sharedFileFileds[0]);
					sf.setSharer(peerAddress+":"+sharedFileFileds[1]);
					sf.setChecksum(sharedFileFileds[2]);
					sf.setSize(Long.parseLong(sharedFileFileds[3]));
					
					//after above code, a file index is complete. Process to register.
					String res = FileServer.registry(sf);
					
					//response to client.
					dOut.writeUTF(res);
					dOut.flush();

					break;

				case "2":	//Search for a file

					searchedFileName = dIn.readUTF();	//get the filename to be searched
					System.out.println("Peer at "+ peerAddress +" Requested to search file >>> " + searchedFileName);
					SharedFile[] sfs = FileServer.getInstance().searchFile(searchedFileName);
					
					//if we found a valid peer sharing requested file.
					if(sfs.length>0) {
						
						String fileDetail= "";
						for(SharedFile tsf : sfs)
							fileDetail += "File Name: " + tsf.getFileName().split("/")[tsf.getFileName().split("/").length - 1]
									+ " File size: "
									+ tsf.getSize()
									+ "\n";
						
						dOut.writeUTF("Found! Currently " + sfs.length + " Peers are shareing this file. \n" + fileDetail);
						dOut.flush();
					}
					else
					{
						dOut.writeUTF("There's no specifc file or valid peer sharing point for this file in this P2P network.");
						dOut.flush();
					}

					break;
					
				case "3":  //Obtain the file, return a shared file object to client
					
					//get filename from peer
					searchedFileName = dIn.readUTF();	//get the filename to be searched
					System.out.println("Peer at " + peerAddress+ " Request to obtain file >>> " + searchedFileName);
					SharedFile[] osfs = FileServer.getInstance().searchFile(searchedFileName);
					
					//if we found a valid peer sharing requested file.
					if(osfs.length>0) {
						dOut.writeUTF("Found! Currently " + osfs.length + " Peers are holding this file \n");
						dOut.flush();
						
						if(!dIn.readUTF().equalsIgnoreCase("d"))
							break;
						
						//send first valid Shared File object to client. TODO:: let client pick download all or just one.
						dOut.writeUTF(osfs[0].toString());
						dOut.flush();
						
					}
					else
					{
						dOut.writeUTF("There's no specifc file or valid peer sharing point for this file in this P2P network.");
						dOut.flush();
					}
					
					break;
					
				case "4": //list file
				
					System.out.println("Peer at "+ peerAddress + " Request to DisPlayAllFiles.");
					String list = FileServer.getInstance().listFile();
					dOut.writeUTF(list);
					dOut.flush();
					
					break;

				case "5":	//Disconnected

					Client_Socket.close();
					System.out.println("Peer at "+ peerAddress + " Request to Dissconnect...Bye");
					isAlive = false;	//End Loop
					break;
					
				default:
					dOut.writeUTF("Wrong Input.");
					dOut.flush();
					break;

				}
			}
			catch(IOException e)
			{
				System.out.println("There's some error with connection, socket closed.");
				break;
			}
		}
		
	}


}


//file server is used for store sharedFiles, and making file related operation
class FileServer {
	
	/**
	 * The map of shared files.
	 *
	 * The key is the checksum of a shared file. So, the file would be unique.
	 * The value is an ArrayList which stores the meta data of the share file objects 
	 * 
	 */
	private static final HashMap<String, ArrayList<SharedFile>> sharedFiles = new HashMap<>();

	/**
	 * The unique instance of File server.
	 */
	private static final FileServer INSTANCE = new FileServer();	

	
	public static FileServer getInstance() {
		return INSTANCE;
	}
	
	/*
	 *	This method registers the file with the indexing server, synchronized for thread safe;
	 */
	public synchronized static String registry(SharedFile sf) {
		
		if(sharedFiles.containsKey(sf.getChecksum())) {
			sharedFiles.get(sf.getChecksum()).add(sf);		
		} else {
			sharedFiles.put(sf.getChecksum(), new ArrayList<SharedFile>());
			sharedFiles.get(sf.getChecksum()).add(sf);
		}
			
		
		return "Registering Successful, currently " + sharedFiles.get(sf.getChecksum()).size() + " peers has registered this file before (not all of them are online and valid)";
	}
	
	//return searched files, store them in array
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SharedFile[] searchFile(String name) {
		
		Iterator it = sharedFiles.entrySet().iterator();
		ArrayList<SharedFile> sfs = new ArrayList<SharedFile> ();
				
		while(it.hasNext()) {
			
	        Map.Entry pair = (Map.Entry)it.next();
	        
	        for(SharedFile sf : (ArrayList<SharedFile>)pair.getValue()) {
	        	
				String []temp = sf.getFileName().split("/");
				String filename = temp[temp.length-1];
				
				//add matched files to temp list which is alive(downloadable)
				if(filename.equalsIgnoreCase(name) && checkAlive(sf))
					sfs.add(sf);
	        	
	        }
	        
	        //it.remove(); // avoids a ConcurrentModificationException
			
		}
		
		
		SharedFile[] t = new SharedFile[sfs.size()];
		
		t = sfs.toArray(t);
		
		return t;
		
	}
	
	public ArrayList<SharedFile> pickFile(String md5) {
		
		if(sharedFiles.containsKey(md5))
			return sharedFiles.get(md5);
		else
			return null;
		
	}
	
	public boolean checkAlive (SharedFile sf) {
		
		try {
			
			String [] t= sf.getSharer().split(":");
			Socket sc = new Socket(t[0],Integer.parseInt(t[1]));
			
			DataInputStream dIn = new DataInputStream(sc.getInputStream());
			DataOutputStream dOut = new DataOutputStream(sc.getOutputStream());
			
			dOut.writeUTF("check");
			dOut.flush();
			
			String res = dIn.readUTF();
			if(res.equalsIgnoreCase("1"))
				return true;
			else
				return false;
			
		} catch (NumberFormatException | IOException sc) {
			
			System.out.println("Peer at "+ sf.getSharer()+ " is offline, file cannot be download from him anymore.");
			return false;

		}
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String listFile() {
		
	    Iterator it = sharedFiles.entrySet().iterator();
	    String filelist = "";
	    
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        filelist += "\nFile MD5: " + (String)pair.getKey() + "\n-----Begin-----" + listFileInArray((ArrayList<SharedFile>)pair.getValue()) + "\n-----End-----\n\n" ;
	        //it.remove(); // avoids a ConcurrentModificationException
	    }
		
		return filelist;
	}
	
	
	
	public String listFileInArray(ArrayList<SharedFile> al) {
		
		String fileinfo ="";
		
		for(SharedFile sf : al) {
			
			String []temp = sf.getFileName().split("/");
			String filename = temp[temp.length-1];
			
			if(checkAlive(sf))
				fileinfo += "\n" + filename + " shared by "+ sf.getSharer() + " size is " + sf.getSize();
			else
				fileinfo += "\n(Peer is offline, thus invalid) \n" + filename + " shared by "+ sf.getSharer() + " size is " + sf.getSize();
			
		}
		
		return fileinfo;
		
	}


}











