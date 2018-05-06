/**
 * 
 */
package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import Model.*;

/**
 * @author jinyang
 *
 */
//file server is used for store sharedFiles, and making file related operation
public class FileServer {

	/**
	 * The map of shared files.
	 *
	 * The key is the checksum of a shared file. So, the file would be unique.
	 * The value is an ArrayList which stores the meta data of the share file objects 
	 * 
	 */
	private final HashMap<String, ArrayList<SharedFile>> sharedFiles = new HashMap<>();

	/**
	 * The unique instance of File server.
	 */
	//private static final FileServer INSTANCE = new FileServer();	

	/**
	 * @return Unique Instance of File Server
	 */
	public  FileServer getInstance() {
		return this;
	}
	
	/**
	 *	This method registers the file with the indexing server, synchronized for thread safe;
	 */
	public synchronized String registry(SharedFile sf) {
		
		if(sharedFiles.containsKey(sf.getFileChecksum())) {
			sharedFiles.get(sf.getFileChecksum()).add(sf);		
		} else {
			sharedFiles.put(sf.getFileChecksum(), new ArrayList<SharedFile>());
			sharedFiles.get(sf.getFileChecksum()).add(sf);
		}
			
		return "Registering Successful, currently " + sharedFiles.get(sf.getFileChecksum()).size() + " peers has registered this file on One of Servers, using list All to see all server's files\n (not all of them are online and valid)";
	}
	
	
	/**
	 *	@return Searched files, store them in array as Shared File Object
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public synchronized SharedFile[] searchFile(String name) {
		
		Iterator it = sharedFiles.entrySet().iterator();
		ArrayList<SharedFile> sfs = new ArrayList<SharedFile> ();
				
		while(it.hasNext()) {
			
	        Map.Entry pair = (Map.Entry)it.next();
	        
	        for(SharedFile sf : (ArrayList<SharedFile>)pair.getValue()) {
	        	
				String []temp = sf.getFilePath().split("/");
				String filename = temp[temp.length-1];
				
				//add matched files to temp list which is alive(downloadable)
				if(filename.equalsIgnoreCase(name) && checkAlive(sf))
					sfs.add(sf);
	        }
	        			
		}
		
		SharedFile[] t = new SharedFile[sfs.size()];
		t = sfs.toArray(t);
		return t;
		
	}
	
	/**
	 *	Check if the Peer that holding this Shared File Object is online;
	 */
	public synchronized boolean checkAlive (SharedFile sf) {
		
		try {
			
			String host = sf.GetPeerHost();
			String port = sf.GetPeerPort();
			
			@SuppressWarnings("resource")
			Socket sc = new Socket(host,Integer.parseInt(port));
			
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
			
			System.out.println("Peer at "+ sf.getPeerInfo()+ " is offline, file cannot be download from him anymore.");
			return false;

		} 
		
	}
	
	
	public synchronized String listFileInArray(ArrayList<SharedFile> al) {
		
		String fileinfo ="";
		
		for(SharedFile sf : al) {
			
			String []temp = sf.getFilePath().split("/");
			String filename = temp[temp.length-1];
			
			if(checkAlive(sf))
				fileinfo += "\n" + filename + " shared by "+ sf.getPeerInfo() + " size is " + sf.getFileSize();
			else
				fileinfo += "\n(Peer is offline, thus invalid) \n" + filename + " shared by "+ sf.getPeerInfo() + " size is " + sf.getFileSize();
			
		}
		
		return fileinfo;
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public synchronized String listFile() {
		
	    Iterator it = sharedFiles.entrySet().iterator();
	    String filelist = "";
	    
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        filelist += "\nFile MD5: " + (String)pair.getKey() + " :\n-----Begin-----" + listFileInArray((ArrayList<SharedFile>)pair.getValue()) + "\n-----End-----\n\n" ;
	    }
		
		return filelist;
	}
	

}
