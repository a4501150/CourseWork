/**
 * 
 */
package Client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import Model.*;

/**
 * @author jinyang
 *
 */
public class Client {

	ServerList sl;
	SocketManager sm;
	ArrayList<SocketManager> sms = new ArrayList<SocketManager>();
	ClientGateway cg;
	
	//count our performance
	private static final HashMap<String, Long> performance = new HashMap<>();
	private static int regCount = 0, searchCount = 0, downLoadCount =0, listCount=0;
	private static long t = 0,startTime=0,endTime=0;
	
	
	/**
	 * Main Routine
	 */
	public static void main(String[] args) {
		
		try {
			Client c = new Client();
			System.out.println("Client Uploader is listning at " + c.cg.getCu().getUploaderPort());
			c.menu();
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public Client() throws NumberFormatException {
		
		//get static server list
		sl = new ServerList();
		
		//store list for futher use
		for(String server : ServerList.ServerList)
		{
			try	{
				
				Socket sc = new Socket(sl.getIP(server),Integer.parseInt(sl.getPort(server)));
				sm = new SocketManager(sc);
				sms.add(sm);
				
				
			} catch (IOException e) {
				
				if(e instanceof SocketException) {
					System.out.println("Server is down at " + server);
				}
				
			}
		}
		
		if(sms.size()==0)
		{
			System.out.println("No servers are alive, client exit");
			System.exit(0);
		}
		
		cg = new ClientGateway(sms);
		
		
			
	}
	
	public void menu() throws IOException{
		
		//intialize Our shit.
		performance.put("Register", t);
		performance.put("Search", t);
		performance.put("Download", t);
		performance.put("List", t);
		
		while(true) {
			
			showOption();
			BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in)); 
			String choice = buffer.readLine();
			//only accept numbers through 1-5
			boolean check =	choice.equalsIgnoreCase("1") 
					|| choice.equalsIgnoreCase("2") 
					|| choice.equalsIgnoreCase("3")
					|| choice.equalsIgnoreCase("4")
					|| choice.equalsIgnoreCase("5") ;
			
			if(!check) {
				System.out.println("Invalid Input, please only enter numbers");
				continue;
			}
			
			switch(choice) {
			
			case "1":	//Register files
				
				System.out.println("Enter the filename(in current path) or fullpath to be Register: ");
				String file = buffer.readLine();
				
				startTime = System.currentTimeMillis();
				String ret = cg.register(file);
				if(ret!="")
					System.out.println(ret);
				else
					System.out.println("Invalid File Name / Path");
				
				endTime = System.currentTimeMillis();
				t = endTime - startTime;
				t += performance.get("Register");
				performance.put("Register", t);
				regCount++;
				
				break;
	
			case "2":	//Search for file
				
				System.out.println("Enter the filename to be searched: ");
				String selectedFile = buffer.readLine();
				
				startTime = System.currentTimeMillis();
				String searchRet = cg.searchAllServer(selectedFile);
				System.out.println(searchRet);
				
				endTime = System.currentTimeMillis();
				t = endTime - startTime;
				t += performance.get("Search");
				performance.put("Search", t);
				searchCount++;
				
				break;
	
			case "3":	//Obtain the file
	
				System.out.println("Enter the filename to be downloaded: ");
				String downloadedFile = buffer.readLine();
				
				//process to gateway to get file download info
				String downloadRet = cg.obtain(downloadedFile);						
				if(downloadRet.contains("no specifc file"))
					break;
				
				startTime = System.currentTimeMillis();
				//cast to object
				SharedFile sf = SharedFile.toObject(downloadRet);
				
				//download to disk
				boolean dRet = cg.downloadFileFromPeer(sf.getFilePath(), sf.GetPeerHost(), sf.GetPeerPort());
						
				if(dRet)
				{
					System.out.println("File Download Complete In Folder: /DownloadFromPeer/" + sf.getPeerInfo());
					//register file automatically when download succuss.
					String downloadAndRegisterRet = cg.register(System.getProperty("user.dir")+"/DownloadFromPeer/"+sf.getPeerInfo() + "/"+sf.getFileName());
					System.out.println(downloadAndRegisterRet);
					
				}
				else
					System.out.println("File Download failed.");
				
				endTime = System.currentTimeMillis();
				t = endTime - startTime;
				
				t += performance.get("Download");
				performance.put("Download", t);
				downLoadCount++;
				
				break;
				
			case "4":  //List files in server
				
				startTime = System.currentTimeMillis();
				String listRet = cg.listAll();
				System.out.println(listRet);
				
				endTime = System.currentTimeMillis();
				t = endTime - startTime;
				
				t += performance.get("List");
				performance.put("List", t);
				listCount++;
				
				break;
			case "5":	//Exit
	
				System.out.println("Client Connection Closing .. Bye !!!");
				cg.exit();
				outputPerformance();
				System.exit(0);
				break;
	
			default:
				break;
			
			
			}
			
		}

	}
	
	public void showOption(){
		
		System.out.println("\n****MENU****");
		System.out.println("1. Share File");
		System.out.println("2. Search for a File");
		System.out.println("3. Download a File");
		System.out.println("4. List All Files");
		System.out.println("5. Exit");
		System.out.println("****End*****\n");
		System.out.println("Please pick an option, enter the number, then press enter");
		
	}

	public ClientGateway getCg() {
		return cg;
	}
	
	public static void outputPerformance() {
		
		try {
		
		System.out.println("\nDuring its life circle: " +
				
				"Register " + regCount + " Times, using total " + performance.get("Register") + "ms, Average: "+ performance.get("Register")/regCount + " \n" +
				"Search "    + searchCount + " Times, using total " + performance.get("Search") +  "ms, Average: "+ performance.get("Search")/searchCount + " \n" +
				"Download "    + downLoadCount + " Times, using total " + performance.get("Download") + "ms, Average: "+ performance.get("Download")/downLoadCount + " \n" +
				"ListFiles "    + listCount + " Times, using total " + performance.get("List") +  "ms, Average: "+ performance.get("List")/listCount + " \n"
				
				
				);
		
		} catch (ArithmeticException ae) {
			
			System.out.println("Some Tasks are not performed. Thus evalution can not complete.");
			
		}
		
	}

}
