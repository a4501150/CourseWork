

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Models.Order;
import Models.Product;
import Models.User;

public class Checkout extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter pw = response.getWriter();
		HttpSession session=request.getSession();
		
		
		//take out products
		HashMap<Integer, Product> tmp = (HashMap<Integer, Product>) session.getAttribute("products");
		//String pid = request.getParameter("pid");
		String action = request.getParameter("action");
		
		ArrayList<Product> p_incart = (ArrayList<Product>)session.getAttribute("cart");
		
		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header_Start.html");
		utility.printHtml("Header_Nav.html");
		utility.printHtml("Header_End.html");
		utility.printHtml("LeftNavigationBar_Start.html");
		utility.printHtml("LeftTrending.html");
		utility.printHtml("LeftCatagory.html");
		utility.printHtml("LeftNavigationBar_End.html");
		
		pw.print("<section id=\"content\">\r\n");
		pw.println("<br>");
		pw.println("<article class=\"expanded\">");
		pw.println("<h2>Comfirm Your Cart</h2>");
		pw.println("<ul id=\"ajaxCart\">");
		pw.println("<br>");
		
		double total = 0.0;
		if(!p_incart.isEmpty()) {
		for(Product p : p_incart) {
			pw.println("<li>"+   p.getName() + "&nbsp  " + p.getPrice()  + "&nbsp &nbsp <a class=\"delbutton\" href="+ " \"Cart?sender=ajax&action=del&pid="+ p.getId() +"\""  +">delete</a>"  +"</li>");
			total+= p.getPrice();
		}
		
		
		pw.println("</ul>");
		
		//buggy here
		pw.println("<p> Your total is "+ total +"</p>");
		pw.println("<a id=\"place_order\" href=\"static\\CreditCard.html\" class=\"button\">Place Order</a>\r\n" + 
				"      <a id=\"cart_clean\" href=\"#\" class=\"button\">ClearCart</a>");
		
		
		
		} else {
			
			pw.println("</ul>");
			pw.println("<p>Your shopping cart is empty</p>");
		}
		
		pw.println("</article>");
		
		pw.println("</section>\r\n" + 
				"	<div class=\"clear\"></div>");
		
		utility.printHtml("Footer.html");
		
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	    response.setContentType("text/html");
	    
	    HttpSession session=request.getSession();
		  	
	    PrintWriter pw = response.getWriter();
	    
	    boolean check = (boolean) session.getAttribute("isLogin");
	    
		ArrayList<Product> p_incart = (ArrayList<Product>)session.getAttribute("cart");
		
		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header_Start.html");
		utility.printHtml("Header_Nav.html");
		utility.printHtml("Header_End.html");
		utility.printHtml("LeftNavigationBar_Start.html");
		utility.printHtml("LeftCatagory.html");
		utility.printHtml("LeftNavigationBar_End.html");
		
		pw.print("<section id=\"content\">\r\n");
		pw.println("<br>");
		pw.println("<article class=\"expanded\">");
		pw.println("<h2>Order Confirmation</h2>");
		
	    //if not login
	    if(!check) {
	    	
	    	pw.println("<h3> Please Login in first to check out!</h3>");
	    	//login in
	    } else {
	    	
	    	String username = (String) session.getAttribute("username");
	    	int uid = (int) session.getAttribute("uid");
	    	
			String name = request.getParameter("name");
		    String address = request.getParameter("address");
		    String creditcard = request.getParameter("creditcard");
		    String warranty = request.getParameter("warranty");
		    int zipCode = Integer.parseInt(request.getParameter("zipCode"));
		    
		    System.out.println(warranty);
		    
			//loop thrpugh charts
			Iterator<Product> iter = p_incart.iterator();
			
			while(iter.hasNext()) {
				Product p = iter.next();
				
				Order o = generateOrder(p,warranty,"s",zipCode);
				MySQLDataStoreUtilities.addOrder(uid, o);
				
				//print confirmation number for user
				pw.println("<hr>");
				pw.println("<h3>Your Order: " + o.getProduct().getName() + "</h3>");
				pw.println("<h3>Confirmation#: " + o.getConfirNumber() + "</h3>");
				pw.println("<hr>");
				
				
				iter.remove(); // remove placed products in charts
			}
			

		    pw.println("Go to your account page to manage these orders!");
		    
	    	
	    }
	    
		
	    	
		pw.println("</article>");
		
		pw.println("</section>\r\n" + 
				"	<div class=\"clear\"></div>");
		
		utility.printHtml("Footer.html");
	    	
	    	
		
	}
	
	public static Order generateOrder( Product p, String warrant, String status, int zipcode) {
		
		LocalDate l = LocalDate.now();
		LocalDate ex_p = LocalDate.now().plusDays(14);
		String Status = "On the way";
		
		Random randomGenerator = new Random();
		int Confirmation =  randomGenerator.nextInt(1000000);
		
		if (warrant==null) {
			warrant = "No";
		}
		
		if(!warrant.equalsIgnoreCase("yes"))
			warrant = "No";
		Order t = new Order(p,Confirmation,status,ex_p,l);
		t.setWarranty(warrant);
		t.setZipcode(zipcode);
		t.setStatus(Status);
		return t;
	}
	

}
