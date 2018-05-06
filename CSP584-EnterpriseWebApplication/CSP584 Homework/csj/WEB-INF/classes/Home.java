import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Models.Order;
import Models.Product;
import Models.User;


public class Home extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request,pw);
		
		HttpSession session=request.getSession();
		
		utility.printHtml("Header_Start.html");
		utility.printHtml("Header_Nav.html");
		utility.printHtml("Header_End.html");
		utility.printHtml("LeftNavigationBar_Start.html");
		utility.printHtml("LeftCatagory.html");
		utility.printHtml("LeftTrending.html");
		utility.printHtml("LeftAjaxCart.html");
		utility.printHtml("LeftNavigationBar_End.html");
		utility.printHtml("Content.html");
		utility.printHtml("Footer.html");
		
		
		if(session.getAttribute("init")==null) {

			session.setMaxInactiveInterval(7*24*60*60); //last for one week
			
//			//general add product procedure
//			HashMap<Integer, Product> tmp = SaxParserDataStore.products;
//			session.setAttribute("products", tmp);
//			session.setAttribute("lastPID", 60);
			session.setAttribute("init", true);
			//cart
			session.setAttribute("cart", new ArrayList<Product>());
			
			//set login to false
			session.setAttribute("isLogin",false);
			
			MySQLDataStoreUtilities.nonFetchQuery("insert into account (username, password, email) values('manager','manager','manager@host')");
			MySQLDataStoreUtilities.nonFetchQuery("insert into account (username, password, email) values('sale','sale','sale@host')");
			
			//order
			//session.setAttribute("order", new HashMap<String, ArrayList<Order>>()); //key - username value - array for orders
			
			//pw.println("Last UID"+ session.getAttribute("lastUID"));
			pw.println("DEBUG# USER MAP AND MANAGER/SALESMAN CREATED");
		}
		
	}
}
