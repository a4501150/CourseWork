import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;


public class ReviewManager extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
		Utilities utility = new Utilities(request,pw);
	   
		String pid = request.getParameter("pid");
		HttpSession session=request.getSession();
		String username =  (String )session.getAttribute("username");
		
		
		String rate = request.getParameter("rate");
		String date = LocalDate.now().toString();
		String text = request.getParameter("text");
		
		MongoDBDataStoreUtilities.insertReview(pid, username, text, rate, date);
	
		
		pw.print("add_done");
		
		
	}

}
