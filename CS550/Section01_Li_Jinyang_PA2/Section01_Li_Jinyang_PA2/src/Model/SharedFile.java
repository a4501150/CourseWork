/**
 * 
 */
package Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author jinyang
 *
 */
public class SharedFile implements ISharedFile, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7928381921919807144L;

	/**
	 * Default constructor
	 */
	public SharedFile() { }
	
	/**
	 * The constructor of this class.
	 * 
	 * @param filePath	-the file absolute path in peer's machine of this file
	 * @param peerInfo	-the peer's net addr in ip:port format, peer is whom share the file
	 * @param fileChecksum	-file's md5
	 * @param fileSize	-the size in Byte of the file
	 */
	public SharedFile(String filePath, String peerInfo, String fileChecksum, long fileSize) {
		FilePath = filePath;
		PeerInfo = peerInfo;
		FileSize = fileSize;
		FileChecksum = fileChecksum;
		this.setFileName();
	}

	/**
	 * The file name of the file.
	 */
	private String FileName;
	
	/**
	 * The file full path of the file.
	 */
	private String FilePath;

	/**
	 * The peer name who share the file.
	 */
	private String PeerInfo;
	/**
	 * The size in Byte of the file.
	 */
	private long FileSize;
	/**
	 * The checksum of the file.
	 */
	private String FileChecksum;
	
	/* (non-Javadoc)
	 * @see Model.ISharedFile#GetPeerHost()
	 */
	@Override
	public String GetPeerHost() {
		String ip = this.PeerInfo.split(":")[0];
		return ip;
	}


	/* (non-Javadoc)
	 * @see Model.ISharedFile#GetPeerPort()
	 */
	@Override
	public String GetPeerPort() {
		String port = this.PeerInfo.split(":")[1];
		return port;
	}

	/**
	 * @return
	 */
	public String getFileName() {
		return FileName;
	}

	/**
	 * 
	 */
	public void setFileName() {
		FileName = FilePath.split("/")[FilePath.split("/").length - 1];
	}

	/**
	 * @return
	 */
	public String getFilePath() {
		return FilePath;
	}

	/**
	 * @param filePath
	 */
	public void setFilePath(String filePath) {
		FilePath = filePath;
	}

	/**
	 * @return
	 */
	public String getPeerInfo() {
		return PeerInfo;
	}

	/**
	 * @param peerInfo
	 */
	public void setPeerInfo(String peerInfo) {
		PeerInfo = peerInfo;
	}

	/**
	 * @return
	 */
	public long getFileSize() {
		return FileSize;
	}

	/**
	 * @param fileSize
	 */
	public void setFileSize(long fileSize) {
		FileSize = fileSize;
	}

	/**
	 * @return
	 */
	public String getFileChecksum() {
		return FileChecksum;
	}

	/**
	 * @param fileChecksum
	 */
	public void setFileChecksum(String fileChecksum) {
		FileChecksum = fileChecksum;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return  FilePath +","+ PeerInfo+","+ FileChecksum+","+FileSize;
	}
	
	//transfer string to object
	public static SharedFile toObject(String sharedFileString) {
		
		String []sharedFileFileds = sharedFileString.split(",");
		SharedFile sf = new SharedFile();
		sf.setFilePath(sharedFileFileds[0]);
		sf.setPeerInfo(sharedFileFileds[1]);
		sf.setFileChecksum(sharedFileFileds[2]);
		sf.setFileSize(Long.parseLong(sharedFileFileds[3]));
		sf.setFileName();
		
		return sf;
		
	}
	
	//get sharedfile object from a file path
	public static SharedFile getSharedFileObjectFromFile(String filepath)  {
		
		SharedFile sf = null;
		
		try {
			
			File file = new File(filepath); 
			
			if(!file.exists()) {
				System.out.println("File Does not exists, please check the name.");
				return null;
			}
			
			sf = new SharedFile();
			sf.setFileChecksum(getFileChecksum(file));
			sf.setFilePath(file.getAbsolutePath());
			sf.setFileSize(file.length());
			sf.setFileName();
			return sf;
			
		} catch (Exception e) {
			
			System.out.println("cannot get file.");
			return null;
		}
		
	}
	
	//get file md5
	public static String getFileChecksum(File file) throws IOException, NoSuchAlgorithmException
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
	
    //Since JDK 7, NIO way write bytes to a file
    public static void writeBytesToFileNio(byte[] bFile, String fileDest) {

        try {
            Path path = Paths.get(fileDest);
            Files.write(path, bFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
