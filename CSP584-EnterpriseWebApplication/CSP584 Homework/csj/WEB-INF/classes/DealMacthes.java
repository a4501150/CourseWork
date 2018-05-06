import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Models.Product;


public class DealMacthes extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		String [] obtainedMessages = GetRecentTwoLinesInFile();
		PrintWriter pw = response.getWriter();
		Product p1, p2;
		
		switch (action) {
		
		case "twitter":
			pw.println("<p>"+"</p>");
			pw.println("<p>" + GetTwitterFromLine(obtainedMessages[0]) +"</p>");
			pw.println("<p>" + GetTwitterFromLine(obtainedMessages[1]) +"</p>");
			break;
		case "ownproduct":
			p1 = MySQLDataStoreUtilities.getProductFromDB(GetProductIDFromLine(obtainedMessages[0]));
			p2 = MySQLDataStoreUtilities.getProductFromDB(GetProductIDFromLine(obtainedMessages[1]));
			pw.println("<p>"+"</p>");
			pw.println("<p>" +p1.toString()+"</p>");
			pw.println("<p>" +p2.toString()+"</p>");
			break;
	
		}
		
		
		
		
	}
	
	private String [] GetRecentTwoLinesInFile() {
		
		String matches[] = {"NotFound","NotFound"};
		ArrayList<String> al = new ArrayList<String>();
		
		
		try {
			
			FileReader fr=new FileReader("C:\\apache-tomcat-7.0.34\\webapps\\csj\\DealMatches.txt");
			
			BufferedReader br = new BufferedReader(fr);
			
			String line;
			
		    while ((line = br.readLine()) != null) 
		    {
		    	if(!line.contains("MYSPLIT") || !line.contains("#Deal"))
		    		continue;
		    	
		    	al.add(line);
		    }
		    
		    int rnd = new Random().nextInt(al.size()-1);
		    rnd = new Random(rnd).nextInt(al.size()-1);
		    rnd = new Random(rnd).nextInt(al.size()-1);
		    matches[0] = al.get(rnd);
		    
		    rnd = new Random(rnd).nextInt(al.size()-1);
		    rnd = new Random(rnd).nextInt(al.size()-1);
		    rnd = new Random(rnd).nextInt(al.size()-1);
		    matches[1] = al.get(rnd);
			
		    System.out.println(matches[0]);
		    System.out.println(matches[1]);
		    
		    
		} catch (IOException e) {
			System.out.println("Read Match File Error!");
		}
		
		
		
		return matches;
	}
	
	private int GetProductIDFromLine(String line) {
		return Integer.parseInt(line.split("<MYSPLIT>")[1]);
	}
	
	private String GetTwitterFromLine(String line) {
		return line.split("<MYSPLIT>")[0];
	}

}
