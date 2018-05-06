import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Models.Product;


public class Inventory extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
		HttpSession session=request.getSession();

		String username = (String) session.getAttribute("username");
		
		Utilities utility = new Utilities(request,pw);
		
		utility.printHtml("Header_Start.html");
		utility.printHtml("Header_Nav.html");
		utility.printHtml("Header_End.html");
//		utility.printHtml("LeftNavigationBar_Start.html");
//		utility.printHtml("LeftCatagory.html");
//		utility.printHtml("LeftAjaxCart.html");
//		utility.printHtml("LeftNavigationBar_End.html");
		
		
		pw.print("<section id=\"content\" style=\"width:100%;\" >\r\n");
		pw.println("<br>");
		pw.println("<article class=\"expanded\">");
		
		
		
		
		pw.println("<h2>Inventory Report</h2>");
		pw.println("<br>");
		
		MySQLDataStoreUtilities.PrintInventoryReport(pw);

		
		pw.println("</article>");
		pw.println("</section>\r\n" + 
				"	<div class=\"clear\"></div>");

		utility.printHtml("Footer.html");
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		
		
		
	}

}
