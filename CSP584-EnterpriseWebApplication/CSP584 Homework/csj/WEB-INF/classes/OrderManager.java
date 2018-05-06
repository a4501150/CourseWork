

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
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

public class OrderManager extends HttpServlet {

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
		Utilities utility = new Utilities(request,pw);
	   
		String action = request.getParameter("action");
		
		switch(action) {
		
		case "modify":
			utility.printHtml("SaleModifyOrder.html");
			break;
			
		case "add":
			utility.printHtml("SaleAddOrder.html");
			break;
			
		case "addCustomer":
			utility.printHtml("CreateAccount.html");
			break;
			
		}
		
	}
	
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		HttpSession session=request.getSession();
		
		String username = request.getParameter("username");
		String s_ProductID = request.getParameter("ProductID");
		String s_Confirmation = request.getParameter("Confirmation");
		String Warranty = request.getParameter("Warranty");
		String s_expectedDeliverDate = request.getParameter("expectedDeliverDate");
		String s_orderPlaceDate = request.getParameter("orderPlaceDate");
		
		int zipCode = Integer.parseInt(request.getParameter("zipCode"));
		
		Utilities utility = new Utilities(request,pw);

	    //get orders store map
	    HashMap<String, ArrayList<Order>> os = MySQLDataStoreUtilities.getOrdersFromDB();
	    ArrayList<Order> orders = os.get(username);
	    
	    HashMap<Integer, Product> products = MySQLDataStoreUtilities.getProductsFromDB();
	    
	    int productid = Integer.parseInt(s_ProductID);
	    int confir  = Integer.parseInt(s_Confirmation);
	    
	    LocalDate orderPlaceDate =  LocalDate.parse(s_orderPlaceDate);
	    LocalDate expectedDeliverDate = LocalDate.parse(s_expectedDeliverDate);
	    
		utility.printHtml("Header_Start.html");
		utility.printHtml("Header_Nav.html");
		utility.printHtml("Header_End.html");
		utility.printHtml("LeftNavigationBar_Start.html");
		utility.printHtml("LeftCatagory.html");
		utility.printHtml("LeftNavigationBar_End.html");
		
		pw.print("<section id=\"content\">\r\n");
		pw.println("<br>");
		pw.println("<article class=\"expanded\">");
		
		
		

	    
		switch(action) {
		
		case "Add":
			
			pw.println("<h2>Order Add Successful!</h2>");
			Order to = Checkout.generateOrder(products.get(productid),Warranty,"",zipCode);
			//orders.add(to);
			MySQLDataStoreUtilities.addOrder(MySQLDataStoreUtilities.getUserIDFromName(username), to);
			
			pw.println("<h3>Order# is "+ to.getConfirNumber() +" !</h3>");
			pw.println("<h3>Customer username is "+ username +" !</h3>");
			
			pw.println("<h3>Expected Deliver Date is "+ to.getExpectedDeliverDate().toString() +" !</h3>");
			pw.println("<h3>Order Place Date  is "+ to.getOrderPlaceDate().toString() +" !</h3>");
			pw.println("<h3>Zipcode is "+ to.getZipcode() +" !</h3>");
			
			
			break;
			
			
		case "Modify":
		
//			int index = 0;
//			
//		    for(int x = 0; x < orders.size(); x = x + 1) {
//		         
//		    	if(orders.get(x).getConfirNumber()==confir) {
//		    		index = x;
//		    		break;
//		    	}
//		    }
		    
		    Order tmp = Checkout.generateOrder(products.get(productid),Warranty,"",zipCode);
		    tmp.setExpectedDeliverDate(expectedDeliverDate);
		    tmp.setOrderPlaceDate(orderPlaceDate);
		    //orders.set(index, tmp);
		    tmp.setConfirNumber(confir);
		    MySQLDataStoreUtilities.delOrder(tmp.getConfirNumber());
		    MySQLDataStoreUtilities.addOrder(MySQLDataStoreUtilities.getUserIDFromName(username), tmp);
		    
		    
			pw.println("<h2>Order Modified Successful!</h2>");
			pw.println("<h3>Order# is "+ tmp.getConfirNumber() +" !</h3>");
			pw.println("<h3>Customer username is "+ username +" !</h3>");
			
			
			pw.println("<h3>Expected Deliver Date is "+ tmp.getExpectedDeliverDate().toString() +" !</h3>");
			pw.println("<h3>Order Place Date  is "+ tmp.getOrderPlaceDate().toString() +" !</h3>");
			pw.println("<h3>Zipcode is "+ tmp.getZipcode() +" !</h3>");
		
			break;
			
			

		
		
		}
		
		
		pw.println("</article>");
		
		pw.println("</section>\r\n" + 
				"	<div class=\"clear\"></div>");
		
	
		
		utility.printHtml("Footer.html");
		
		
		
		
	}

}
