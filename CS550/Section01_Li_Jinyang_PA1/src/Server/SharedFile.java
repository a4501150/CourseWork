package Server;
import java.io.Serializable;

/**
 * This class is abstract of shared file.
 */
public class SharedFile implements Serializable {
	/**
	 * The name of the file.
	 */
	private String fileName;

	/**
	 * The peer name who share the file.
	 */
	private String peer;
	/**
	 * The size in Byte of the file.
	 */
	private long size;

	
	/**
	 * The checksum of the file.
	 */
	private String checksum;
	
	/**
	 * The unique ID for serializing.
	 */
	
	private static final long serialVersionUID = 8092593948347160810L;
	/**
	 * Default constructor
	 */
	public SharedFile() { }

	/**
	 * The constructor of this class.
	 * 
	 * @param checksum   file's md5
	 * @param fileName  the file name of the file
	 * @param peer    the peer who share the file
	 * @param size      the size in Byte of the file
	 */
	public SharedFile(String fileName, String peer, long size , String checksum) {
		this.fileName = fileName;
		this.peer = peer;
		this.size = size;
		this.checksum = checksum;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSharer() {
		return peer;
	}

	public void setSharer(String sharer) {
		this.peer = sharer;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
	
	public String getChecksum() {
		return checksum;
	}
	
	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return  fileName +","+ peer+","+ checksum+","+size;
	}

}