package Server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class ServerNode {
	
	//Server Listening Port
	private int listening_port;
	private ServerSocket server_socket;
	private FileServer fs;

	public ServerNode(int port) throws IOException {
		
		listening_port = port;
		
		//initialize ServerSocket
	    server_socket = new ServerSocket(listening_port);
		
		//wait for Connections.
		System.out.println("Server Is Lisening At Port >>> " + listening_port);

		fs = new FileServer();
		
		Runnable task = () -> {
			try {
				loop();
			} catch (IOException e) {
				e.printStackTrace();
			}
		};
		
		Thread t = new Thread(task);
		//Start Our Job
		t.start();
		

	}
	
	public void loop() throws IOException {
		
		//loop for waiting peer connected
		while(true)
		{

			try {
				
			Socket c_socket =  server_socket.accept();//accept client connection
			
			Runnable task = () -> {
				newClient(c_socket);
			};
			
			Thread t = new Thread(task);
			//Start Our Job
			t.start();
			} 
			catch (SocketException se) {
				System.out.println("Connection is either reset or c0rrupted, Closeing  this socket.. ");
				break;
			}
				
		}
		
	}
	
	public void newClient(Socket c_socket)
	{
		GatewayServer gs = new GatewayServer(c_socket , fs);
		
		//Keep Alive while communicating.
		while(gs.getSm().isAlive()) {
			
			//Get the reason of connection
			String reason = gs.getRequest();
						
			//Data recieved
			String data;
						
			//Switch Reason of Connection
			switch(reason) {
			
			//Registration of Files
			case "1":
				data = gs.getRequest();
				if(!data.equalsIgnoreCase("Invalid File"))
					gs.register(data);
				break;
				
				//search file
			case "2":
				data = gs.getRequest();
				gs.search(data);
				break;
				
				//obtain file
			case "3":
				data = gs.getRequest();
				gs.obtain(data);
				break;
				
				//list all files
			case "4":
				gs.listAll();
				break;
				
				//exit connection
			case "5":
				gs.exit();
				break;
				
				default:
					gs.getSm().send("Wrong Input.");
					break;

			}
			
		}
	}
	
	
	public static void main(String []args) throws IOException {

		if(args.length==0)
			new ServerNode(6666);
		else
			new ServerNode(Integer.parseInt(args[0]));
		
	}
	
	
	
}
