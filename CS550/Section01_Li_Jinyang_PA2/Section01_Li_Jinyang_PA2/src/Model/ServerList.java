package Model;


import java.util.HashSet;
import java.util.Set;

public class ServerList {

	public static final Set<String> ServerList = new HashSet<String>();
	
	//leave for future uses
	public ServerList(int num) {
		
	}
	
	//add server to list
	public ServerList() {
		
		for(int i = 0; i<8; i++ ) {
			String port = (int)(6666 + i) + "";
			ServerList.add("127.0.0.1:"+ port);
		}
		
	}
	
	public String getIP(String serverInfo) {
		
		return serverInfo.split(":")[0];
		
	}
	
	public String getPort(String serverInfo) {
		
		return serverInfo.split(":")[1];
		
	}

}
