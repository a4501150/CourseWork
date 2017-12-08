/*  Group Member
 *  Jinyang Li, Bingyu Song, Xin liu  */

import java.util.Scanner;
import java.sql.* ;  
import oracle.jdbc.driver.OracleDriver;

public class Client {
	
	public static final String DBURL = "jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
    public static final String DBUSER = "bsong11";
    public static final String DBPASS = "930107";


    
	public static void main(String[] args) throws ClassNotFoundException, SQLException  {
			new Client();
	}
	
	Client() throws ClassNotFoundException, SQLException  {
		
		Connection con = null;// create connection
	 
        Class.forName("oracle.jdbc.driver.OracleDriver");// load Oracle driver
        
        con = DriverManager.getConnection(DBURL, DBUSER, DBPASS);// get it online
	    
        System.out.println("success to connect to db");
        
        
        Scanner in = new Scanner(System.in);
          
        while (true) {
        	
    		System.out.println("\n\n Hi, Choose the Question u wanna test for \n\n"
    				
				+ "enter a number betwwen 4-6 to choose questions, enter 0 for to exit \n\n ");
        	
        	switch (in.nextInt()) {
		
        	case 4:
        		new q4(con);
			break;
		
        	case 5:
        		new q5(con);
			break;
		
        	case 6:
        		new q6(con);
			break;
			
        	case 0:
        		con.close();
        		System.exit(0);
		
        	default:
        		System.out.println("enter a correct number!");
			break;		
        	}
        }
        
	}
	
	
}