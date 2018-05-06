/**
 * 
 */
package Client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import Server.SharedFile;

/**
 * @author Jinyang
 *
 */
public class Client {

	private String ServerHost;
	private int ServerPort;
	private int uploadPort;
	
	//count our performance
	private static final HashMap<String, Long> performance = new HashMap<>();
	private static int regCount = 0, searchCount = 0, downLoadCount =0, listCount=0;
	private static long t = 0,startTime=0,endTime=0;
	
	
	
	/**
	 * @throws NoSuchAlgorithmException 
	 * 
	 */
	public Client() throws NoSuchAlgorithmException {

		try {
			
			Menu();
			
		} catch (IOException e) {
			
			
			if(e instanceof SocketException) {
				System.out.println("Connection Error, Maybe Server is not running.");
				Client.outputPerformance();
				System.exit(0);
				
			}
			else
				e.printStackTrace();
			
			
		}
		
	}

	/**
	 * @param args
	 * @throws NoSuchAlgorithmException 
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException {
		
		//intialize Our shit.
		performance.put("Register", t);
		performance.put("Search", t);
		performance.put("Download", t);
		performance.put("List", t);
		
		new Client();

	}
	
	public void Menu() throws UnknownHostException, IOException, NoSuchAlgorithmException {
		
			Socket cAsServer = new Socket("localhost",6666); //173.208.201.154
			BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in)); 
			
			ClientUploader clientUploader = new ClientUploader();
			
			
			new Thread() {
				public void run() {
					clientUploader.Listen();
				}
			}.start();
			
			//Get that auto-collect port
			String UploadPort = clientUploader.getPeerInfo()+"";
		
			while(true) {
			
			System.out.println("****MENU****");
			System.out.println("1. Share File");
			System.out.println("2. Search for a File");
			System.out.println("3. Download a File");
			System.out.println("4. List All Files");
			System.out.println("5. Exit");
			System.out.println("****End*****\n");
			System.out.println("Please pick an option, enter the number, then press enter");
			
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
			
				
	
			DataInputStream dInServer = new DataInputStream(cAsServer.getInputStream());
			DataOutputStream dOutServer = new DataOutputStream(cAsServer.getOutputStream());
			
			String selectedFile = null;
			
			switch(choice)
			{
	
			case "1":	//Register files
	
				//send choice
				dOutServer.writeUTF(choice);
				dOutServer.flush();
				
				//enter a local file
				System.out.println("Enter the filename(in current path) or fullpath to be Register: ");
				selectedFile = buffer.readLine();
				
				
				//store local file as a shared file object
				SharedFile sharedFile = getSharedFileObject(selectedFile);
				
				//if local file doesn't exist
				if(sharedFile == null) {
					dOutServer.writeUTF("Invalid File");
					dOutServer.flush();
					break;
				}
				
				startTime = System.currentTimeMillis();
				//set upload port for downloading, ip address would be handled in index server side
				sharedFile.setSharer(""+UploadPort);
				
				//send object to server
				dOutServer.writeUTF(sharedFile.toString());
				dOutServer.flush();
				
				//get response from server
				String res = dInServer.readUTF();
				System.out.println(res);
				
				endTime = System.currentTimeMillis();
				t = endTime - startTime;
				t += performance.get("Register");
				performance.put("Register", t);
				regCount++;
				
				break;
	
			case "2":	//Search for file
				
				//send choice
				dOutServer.writeUTF(choice);
				dOutServer.flush();
				
				System.out.println("Enter the filename to be searched: ");
				selectedFile = buffer.readLine();
				
				startTime = System.currentTimeMillis();
				
				dOutServer.writeUTF(selectedFile);
				dOutServer.flush();
				
				String searchResults = dInServer.readUTF();
				System.out.println(searchResults);
				
				endTime = System.currentTimeMillis();
				t = endTime - startTime;
				t += performance.get("Search");
				performance.put("Search", t);
				searchCount++;
				
				break;
	
			case "3":	//Obtain the file
	

				//send choice
				dOutServer.writeUTF(choice);
				dOutServer.flush();
				
				System.out.println("Enter the fileName to be downloaded");
				String obtainFileName = buffer.readLine();
				
				//send requested file
				dOutServer.writeUTF(obtainFileName);
				dOutServer.flush();
				
				//get respons from server
				String reso = dInServer.readUTF();
				
				System.out.println(reso);
				
				//if found
				if(reso.contains("ound")) {
					
					dOutServer.writeUTF("d");
					dOutServer.flush();
					System.out.println("file founded, now downloading...");
					
				} else
					break;
				
				startTime = System.currentTimeMillis();
				
				//recieve object
				String respon = dInServer.readUTF();
				String [] t_respon = respon.split(",");
				
				//return  fileName +","+ peer+","+ checksum+","+size;
				SharedFile inSF = new SharedFile();
				inSF.setFileName(t_respon[0]);
				inSF.setSharer(t_respon[1]);
				inSF.setSize(Long.parseLong(t_respon[3]));
				
				//get necessary meta data for us to download file.
				
				//get ip and port.
				String ip = inSF.getSharer().split(":")[0];
				String port = inSF.getSharer().split(":")[1];

				downloadFileFromPeer(inSF.getFileName(),ip,port);
				System.out.println("File " + inSF.getFileName().split("/")[inSF.getFileName().split("/").length -1] + " download Comlete!");
				
				endTime = System.currentTimeMillis();
				t = endTime - startTime;
				
				t += performance.get("Download");
				performance.put("Download", t);
				downLoadCount++;
				
				break;
				
			case "4":  //List files in server
				//always send choice before any action
				
				startTime = System.currentTimeMillis();
				
				dOutServer.writeUTF(choice);
				dOutServer.flush();
				
				//get response from server
				String in = dInServer.readUTF();
				System.out.println(in);
				
				endTime = System.currentTimeMillis();
				t = endTime - startTime;
				
				t += performance.get("List");
				performance.put("List", t);
				listCount++;
				
				break;
			case "5":	//Exit
	
				System.out.println("Client Connection Closing .. Bye !!!");
				dOutServer.writeUTF(choice);
				cAsServer.close();
				
				outputPerformance();
				
				System.exit(0);
				break;
	
			default:
				break;
			}
		}
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
	
	public boolean downloadFileFromPeer(String filepath, String ip, String port) {
		
		try {
			
			Socket dsk = new Socket(ip,Integer.parseInt(port));
			
			DataInputStream dInServer = new DataInputStream(dsk.getInputStream());
			DataOutputStream dOutServer = new DataOutputStream(dsk.getOutputStream());
			
			//send reason
			dOutServer.writeUTF("download");
			dOutServer.flush();
			
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
			
			Files.createDirectories(Paths.get(System.getProperty("user.dir")+"/DownLoadFromPeer/"));
			
			
			writeBytesToFileNio(fileBuffer, System.getProperty("user.dir")+"/DownLoadFromPeer/"+filename);
			
			dOutServer.writeUTF("exit");
			dsk.close();

			
		} catch (IOException e) {
			
			e.printStackTrace();
			return false;
			
		}
		

		
		return true;
	}
	
	
    //Since JDK 7, NIO
    private static void writeBytesToFileNio(byte[] bFile, String fileDest) {

        try {
            Path path = Paths.get(fileDest);
            Files.write(path, bFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
	
	public SharedFile getSharedFileObject(String filepath) throws NoSuchAlgorithmException, IOException {
		
		File w = new File(filepath); 
		
		if(!w.exists()) {
			System.out.println("File Does not exists, please check the name.");
			return null;
		}
		
		SharedFile sf = new SharedFile();
		
		sf.setChecksum(getFileChecksum(w));
		sf.setFileName(w.getAbsolutePath());
		sf.setSize(w.length());
		
		return sf;
		
	}
	
	private static String getFileChecksum(File file) throws IOException, NoSuchAlgorithmException
	{
		MessageDigest digest = MessageDigest.getInstance("MD5");
		
	    //Get file input stream for reading the file content
	    FileInputStream fis = new FileInputStream(file);
	     
	    //Create byte array to read data in chunks
	    byte[] byteArray = new byte[1024];
	    int bytesCount = 0;
	      
	    //Read file data and update in message digest
	    while ((bytesCount = fis.read(byteArray)) != -1) {
	        digest.update(byteArray, 0, bytesCount);
	    };
	     
	    //close the stream; We don't need it now.
	    fis.close();
	     
	    //Get the hash's bytes
	    byte[] bytes = digest.digest();
	     
	    //This bytes[] has bytes in decimal format;
	    //Convert it to hexadecimal format
	    StringBuilder sb = new StringBuilder();
	    for(int i=0; i< bytes.length ;i++)
	    {
	        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	    }
	     
	    //return complete hash
	   return sb.toString();
	}
	

}
