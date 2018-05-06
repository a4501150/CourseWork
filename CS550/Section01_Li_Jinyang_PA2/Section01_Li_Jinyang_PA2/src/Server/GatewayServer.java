/**
 * 
 */
package Server;

import java.net.Socket;

import Model.SharedFile;
import Model.SocketManager;

/**
 * @author jinyang
 *
 */
public class GatewayServer {

	private Socket Client_Sc;
	private SocketManager Sm;
	private FileServer fs;
	
	/**Gateway Server to distribute our shit
	 * @param fs 
	 * 
	 */
	public GatewayServer(Socket sc, FileServer fs) {
		this.fs = fs;
		Client_Sc = sc;
		setSm(new SocketManager(Client_Sc));
		System.out.println("Peer At " + Sm.getAddress() + " is connected.");
		
	}
	
	public String getRequest() {
		return Sm.get();
	}
	
	public void register(String sharedFileToString) {

		System.out.println(Sm.getAddress() + " register file requested.");
		SharedFile sf = SharedFile.toObject(sharedFileToString);
		
		if(sf.getPeerInfo().contains(":") == false)
			sf.setPeerInfo(Sm.getIp()+":"+sf.getPeerInfo());
		
		String ret = fs.getInstance().registry(sf);
		Sm.send(ret);
		
	}
	
	public void search(String name) {

		System.out.println(Sm.getAddress() + " search file requested.");
		SharedFile[] sfs = fs.getInstance().searchFile(name);
		
		//if we found a valid peer sharing requested file.
		if(sfs.length>0) {
			
			String fileDetail= "";
			for(SharedFile tsf : sfs)
				fileDetail += "File Name: " + tsf.getFileName()
						+ " File size: "
						+ tsf.getFileSize()
						+ "\n";
			
			Sm.send("Found! Currently " + sfs.length + " Peers are shareing this file in Server " + Client_Sc.getLocalAddress().getHostAddress() + ":" + Client_Sc.getLocalPort() +" \n" + fileDetail);
		}
		else
		{
			Sm.send("There's no specifc file or valid peer sharing point for this file in this P2P network.");
		}
		
	}
	
	public void obtain(String searchedFileName){
		
		System.out.println(Sm.getAddress() + " obtain file requested.");
		SharedFile[] osfs = fs.getInstance().searchFile(searchedFileName);
		
		//if we found a valid peer sharing requested file.
		if(osfs.length>0) {
			Sm.send("Found! Currently " + osfs.length + " Peers are holding this file \n");
			
			if(!Sm.get().equalsIgnoreCase("d"))
				return;
			
			//send first valid Shared File object to client. TODO:: let client pick download all or just one.
			Sm.send(osfs[0].toString());
			
		}
		else
		{
			Sm.send("There's no specifc file or valid peer sharing point for this file in this P2P network.");
		}
	
	}
	
	public void listAll(){
		System.out.println(Sm.getAddress() + " list All files requested.");
		String list = fs.getInstance().listFile();
		if(list == "")
			list = "Currently, no files are shared in this p2p net work.";
		Sm.send(list);
	}
	
	public void exit(){
		System.out.println(Sm.getAddress() + " exit requested.");
		getSm().close();
	}

	public SocketManager getSm() {
		return Sm;
	}

	public void setSm(SocketManager sm) {
		Sm = sm;
	}


}
