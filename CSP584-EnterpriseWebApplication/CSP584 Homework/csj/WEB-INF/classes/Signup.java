

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Models.Order;
import Models.User;

public class Signup extends HttpServlet {
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
		Utilities utility = new Utilities(request,pw);
	   
		utility.printHtml("Signup.html");
		
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    response.setContentType("text/html");
		String userid = request.getParameter("userid");
	    String password = request.getParameter("password");
	    String email = request.getParameter("email");
	    
	    
	    HttpSession session=request.getSession();
		  
		//general sign procedure
	    HashMap<String, User> t = (HashMap<String, User>) session.getAttribute("users");
		
	    PrintWriter pw = response.getWriter();
	    
	    ResultSet userinfo = MySQLDataStoreUtilities.fetchQuery("select * from account where username = '" + userid + "' and password = '"+password+"'");
	    boolean usernameInDB = false;
	    
	    try {
	    
		    if(userinfo.next() && userinfo!=null) {
		    	
		    	usernameInDB = true;
		    	
		    } else 
		    	usernameInDB = false;
	    
	    }
	    
	    catch (Exception e) {
	    	
	    	e.printStackTrace();
	    }
	    


	    
	    pw.println("<html>\r\n" + 
	    		"   <head>\r\n" + 
	    		"      <title>Signup Page</title>\r\n" + 
	    		"      <style>\r\n" + 
	    		"         h1{\r\n" + 
	    		"         text-transform: uppercase;\r\n" + 
	    		"         letter-spacing: 3px;\r\n" + 
	    		"         font-family: \"Century Gothic\", Times, serif;\r\n" + 
	    		"         font-weight: bold;\r\n" + 
	    		"         }\r\n" + 
	    		"         div{\r\n" + 
	    		"         border-radius: 30px;\r\n" + 
	    		"         background-color: white;\r\n" + 
	    		"         border: 5px solid black;\r\n" + 
	    		"         padding bottom: 500px;\r\n" + 
	    		"         padding top: 100px;\r\n" + 
	    		"         float:center\r\n" + 
	    		"         width: 300px;\r\n" + 
	    		"         height: 250px;\r\n" + 
	    		"         margin: 200px;\r\n" + 
	    		"         text-align: justify;\r\n" + 
	    		"         }\r\n" + 
	    		"         td{\r\n" + 
	    		"         font-variant: small-caps;\r\n" + 
	    		"         font-weight: bold;\r\n" + 
	    		"         word-spacing: 5px;\r\n" + 
	    		"         font-family: \"Century Gothic\", Times, serif;\r\n" + 
	    		"         }\r\n" + 
	    		"         button{\r\n" + 
	    		"         font-family: \"Century Gothic\", Times, serif;\r\n" + 
	    		"         font-weight: bold;\r\n" + 
	    		"         }\r\n" + 
	    		"      </style>\r\n" + 
	    		"   </head>\r\n" + 
	    		"   <body>\r\n" + 
	    		"      <div>\r\n" + 
	    		"         <center>");
	    
	    
	    if(usernameInDB) {
	    	pw.println("<br><br><h1>Sign up faild, the username is already taken!..would redirect in 3 seconds</h1>");
	    	
		    pw.println("         </center>\r\n" + 
		    		"      </div>\r\n" + 
		    		"   </body>\r\n" + 
		    		"</html>");
	    	
	    	pw.println("<meta http-equiv=\"Refresh\" content=\"3;url=Home\">");
	    }
	    else {
	    	
	    pw.println("<br><br><h1>Sign up success!..would redirect in 3 seconds</h1>");
	    pw.println("         </center>\r\n" + 
	    		"      </div>\r\n" + 
	    		"   </body>\r\n" + 
	    		"</html>");
	    
	    String query = "insert into account (username, password, email) values ('"+userid+"','"+password+"','"+email+"')";
	    MySQLDataStoreUtilities.nonFetchQuery(query);

		
	    pw.println("<meta http-equiv=\"Refresh\" content=\"3;url=Home\">");
	    
	   // pw.flush();
	    
	   // response.sendRedirect("");
	    
	    }
		
	}

}
