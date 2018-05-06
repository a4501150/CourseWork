

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Models.Order;
import Models.Product;

public class Cart extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String sender = request.getParameter("sender");

		switch(sender) {
			case "ajax":
				AjaxDoGet(request, response);
				break;
				
			default:
				NormalDoGet(request, response);
				break;
		}
		
		
	}
	
	
	private void NormalDoGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
			
		PrintWriter pw = response.getWriter();
		HttpSession session=request.getSession();
		
		
		//take out products
		HashMap<Integer, Product> tmp = MySQLDataStoreUtilities.getProductsFromDB();
		//String pid = request.getParameter("pid");
		String action = request.getParameter("action");
		
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
		pw.println("<h2>Your cart</h2>");
		pw.println("<ul id=\"ajaxCart\">");
		
		for(Product p : p_incart) {
			pw.println("<li>"+   p.getName() + "&nbsp  " + p.getPrice()  + "&nbsp &nbsp <a class=\"delbutton\" href="+ " \"Cart?sender=ajax&action=del&pid="+ p.getId() +"\""  +">delete</a>"  +"</li>");
		}
		
		pw.println("<a href=\"#\" class=\"button\">CehckOut</a>\r\n" + 
				"      <a id=\"cart_clean\" href=\"#\" class=\"button\">ClearCart</a>");
		
		pw.println("</ul>");
		pw.println("</article>");
		
		pw.println("</section>\r\n" + 
				"	<div class=\"clear\"></div>");
		
		utility.printHtml("Footer.html");
		
	}


	private void AjaxDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter pw = response.getWriter();
		HttpSession session=request.getSession();
		
		
		//take out products
		HashMap<Integer, Product> tmp = MySQLDataStoreUtilities.getProductsFromDB();
		//String pid = request.getParameter("pid");
		String action = request.getParameter("action");
		
		if(action.equalsIgnoreCase("view")) {
			
			ArrayList<Product> p_incart = (ArrayList<Product>)session.getAttribute("cart");
			
			for(Product p : p_incart) {
				
				pw.println("<li>"+   p.getName() + "&nbsp  " + p.getPrice()  + "&nbsp &nbsp <a class=\"delbutton\" id=\"deletItem\"href="+ " \"Cart?sender=ajax&action=del&pid="+ p.getId() +"\""  +">delete</a>"  +"</li>");
				
			}
			
			
		}
		
		if(action.equalsIgnoreCase("del")) {
			
			ArrayList<Product> p_incart = (ArrayList<Product>)session.getAttribute("cart");
			
			Iterator<Product> iter = p_incart.iterator();
			
			while(iter.hasNext()) {
				Product p = iter.next();
				if(p.getId()==Integer.parseInt(request.getParameter("pid"))) {
					iter.remove(); //this would remove first found same product, little dirty
					break;
				}
			}
			
			pw.print("del_done");
			
			
		}
		
		if(action.equalsIgnoreCase("add")) {
			
			Product p  = tmp.get(Integer.parseInt(request.getParameter("pid")));
			ArrayList<Product> p_incart = (ArrayList<Product>)session.getAttribute("cart");
			p_incart.add(p);
			pw.print("add_done");
			
		}
		
		if(action.equalsIgnoreCase("clear")) {
			
			
			ArrayList<Product> p_incart = (ArrayList<Product>)session.getAttribute("cart");
			p_incart.clear();
			pw.print("clear_done");
			
		}
		//dirty... I have no time to make this cleaner since I begin to work at 9.27 morning
		//used for resonse order related operation
		if(action.equalsIgnoreCase("delOrder")) {
			
			//String username = request.getParameter("username");
			int ordernumber = Integer.parseInt(request.getParameter("ordernumber"));
			
			
//			request.getParameter("");
//			
//		    //get orders store map
//		    HashMap<String, ArrayList<Order>> os = (HashMap<String, ArrayList<Order>>) session.getAttribute("order");
//		    ArrayList<Order> orders =  os.get(username); //get arraylist store this user's shit
//			
//			Iterator<Order> iter = orders.iterator();
//			while(iter.hasNext()) {
//				Order p = iter.next();
//				if(p.getConfirNumber()==ordernumber) {
//					iter.remove(); //this would remove first found same product, little dirty
//					break; //suppose the order is unique but i know if data is too big this would become shit
//				}
//			}
			
			MySQLDataStoreUtilities.delOrder(ordernumber);
		
			pw.print("del_done");
			
		    
		}
		
		if(action.equalsIgnoreCase("viewOrder")) {
			
			String username = request.getParameter("username");
		    //get orders store map
		    //get orders store map
		    HashMap<String, ArrayList<Order>> os = MySQLDataStoreUtilities.getOrdersFromDB();
		    ArrayList<Order> orders =  os.get(username); //get arraylist store this user's shit
		    
		    LocalDate ld = LocalDate.now();
		    for (Order o : orders) {
		    	
				pw.println("<hr>");
				pw.println("<h3>Your Order: " + o.getProduct().getName() + "</h3>");
				pw.println("<h3>Confirmation#: " + o.getConfirNumber() + "</h3>");
				pw.println("<p>Order Place Date: " + o.getOrderPlaceDate().toString() + "</p>");
				pw.println("<p>Expected Deliver Date: " + o.getExpectedDeliverDate().toString() + "</p>");
				pw.println("<p>Has warranty?: " + o.getWarranty() + "</p>");
				
				Period diff = Period.between(ld, o.getExpectedDeliverDate());

				if(diff.getDays()<=5 &&  ld.isBefore(o.getExpectedDeliverDate()) ) {
					pw.println("<h3> You cannot cancle this order since it would be deliverd in 5 day.</h3>");
				}
				else
					pw.println("<a id=\"cancel_order_btn\" class=\"button\" href=\"Cart?sender=ajax&action=delOrder&ordernumber="+ o.getConfirNumber() +"&username=" + username +"\" >" + "Cancel Order" + "</a>");
				
				pw.println("<br>");
				
				pw.println("<hr>");
		    	
		    	
		    }
			
		}
		
		
		if(action.equalsIgnoreCase("delProduct")) {
			
			String productid = request.getParameter("productid");
			MySQLDataStoreUtilities.delProduct(Integer.parseInt(productid));
			pw.print("del_done");
			
			
			
		}
		

	}

}
