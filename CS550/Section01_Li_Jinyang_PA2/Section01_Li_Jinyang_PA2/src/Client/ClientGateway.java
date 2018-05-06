package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

import Model.SocketManager;
import Model.SharedFile;

public class ClientGateway {
	
	private ArrayList<SocketManager> sms;
	private Random randomGenerator;
	private ClientUploader cu;
	
	public ClientGateway(ArrayList<SocketManager> sms) {
		
		this.sms = sms; //SocketManagers for each server
		this.randomGenerator = new Random(); //random engine
		this.cu = new ClientUploader(); //new Client uploader server
		
	}
	
	//client register file
	public String register(String filepath) {
		
		//randomly choose a index of our servers
        int index = randomGenerator.nextInt(sms.size());
        SocketManager sm = sms.get(index);
        
		//store local file as a shared file object
		SharedFile sharedFile = SharedFile.getSharedFileObjectFromFile(filepath);
        
		if(sharedFile == null) {
			return "";
		}
		
        //send choice
        sm.send("1");
		
        //set shared file object peerinfo
        sharedFile.setPeerInfo(sm.getIp()+":" + cu.getUploaderPort());
        
        //send shared file object, and get response from server
        String res = sm.oneStepCommunicate(sharedFile.toString());
        
        //return result from server
        return res;
		
	}
	
	//search file for single server
	public String search(String file, SocketManager sm) {
		
        //send choice
        sm.send("2");
        
        //send file name to be searched, and handle response
        String res = sm.oneStepCommunicate(file);
        
        //return result from server, if not found return null string
        if(res.contains("Found"))
        	return res;
        else
        	return "";
		
	}
	
	//search file loop through server list
	public String searchAllServer(String file) {
		
		String ret = "";
		
		for(SocketManager sm : sms) {
			
			if(sm.isAlive()==false)
				continue;
			
			//if found then append the file 
			String tmp = search(file, sm);
			if(!tmp.equalsIgnoreCase(""))
				ret +=  tmp + "\n";
			
		}
		
		if(!ret.equalsIgnoreCase(""))
			return ret;
		else
			//if not found
			return "There's no specifc file or valid peer sharing point for this file in this P2P network.";
		
		
	}
	
	
	public String obtain(String file) {
		
		for(SocketManager sm : sms) {
			
			if(sm.isAlive()==false)
				continue;
			
			//send choice
			sm.send("3");
			
			//if found then return the file in first occurence server
			String ret = sm.oneStepCommunicate(file);
			if(ret.contains("ound"))
			{
				System.out.println("file founded, now downloading...");
				String sharedFileObjectString = sm.oneStepCommunicate("d");
				return sharedFileObjectString;
			}
			
		}
		
		return "There's no specifc file or valid peer sharing point for this file in this P2P network.";
		
	}
	
	public String listAll() {
		
		String tmp ="";
		
		for(SocketManager sm : sms) {
			
			if(sm.isAlive()==false)
				continue;
			
			//send choice
			sm.send("4");
			
			//if found then return the file in first occurence server
			String ret = sm.get();
			
			if(!ret.contains("no files"))
			{
				tmp += ret +"\n";
			}
			
		}
		
		if(tmp.equalsIgnoreCase(""))
			tmp = "Currently, no files are shared in this p2p net work";
		
		return tmp;
		
	}
	
	public void exit(){
		
		for(SocketManager sm : sms) {
			
			if(sm.isAlive()==false)
				continue;
			
			//send choice
			sm.send("5");
			
			//close
			sm.close();
			
		}
	
		
	}
	
	public boolean downloadFileFromPeer(String filepath, String ip, String port) {
		
		try {
			
			Socket dsk = new Socket(ip,Integer.parseInt(port));
			
			DataInputStream dInServer = new DataInputStream(dsk.getInputStream());
			DataOutputStream dOutServer = new DataOutputStream(dsk.getOutputStream());
			
			//send reason
			dOutServer.writeUTF("download");
			dOutServer.flush();
			
			//check alive
			if(!dInServer.readUTF().equalsIgnoreCase("1")) {
				
				dOutServer.writeUTF("exit");
				dsk.close();
				return false;
			}
				
			
			//send filepath
			dOutServer.writeUTF(filepath);
			dOutServer.flush();
			
			int buffer_size = dInServer.readInt();
			byte[] fileBuffer = new byte[buffer_size];
			dInServer.readFully(fileBuffer);
			
			String filename = filepath.split("/")[filepath.split("/").length -1];
			
			String peerinfo = ip+ ":" +port+"/";
			
			Files.createDirectories(Paths.get(System.getProperty("user.dir")+"/DownLoadFromPeer/" + peerinfo));
			
			SharedFile.writeBytesToFileNio(fileBuffer, System.getProperty("user.dir")+"/DownLoadFromPeer/"+peerinfo+filename);
			
			dOutServer.writeUTF("exit");
			dOutServer.flush();
			
			dsk.close();
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
			return false;
			
		}
		
		
		
		return true;
	}

	public ClientUploader getCu() {
		return cu;
	}
	

	

}
