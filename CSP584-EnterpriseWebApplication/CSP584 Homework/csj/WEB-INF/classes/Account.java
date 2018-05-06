


import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Models.Order;
import Models.Product;
import Models.User;


public class Account extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
		HttpSession session=request.getSession();

		String username = (String) session.getAttribute("username");
		
		Utilities utility = new Utilities(request,pw);
		
		utility.printHtml("Header_Start.html");
		utility.printHtml("Header_Nav.html");
		utility.printHtml("Header_End.html");
		utility.printHtml("LeftNavigationBar_Start.html");
		utility.printHtml("LeftCatagory.html");
		utility.printHtml("LeftAjaxCart.html");
		utility.printHtml("LeftNavigationBar_End.html");
		
		
		pw.print("<section id=\"content\">\r\n");
		pw.println("<br>");
		pw.println("<article class=\"expanded\">");
		pw.println("<h2>Manage Your Account</h2>");
		pw.println("<br>");
		
		if(username.equalsIgnoreCase("manager")) {
			
			
			
			pw.println("<hr>");
			pw.println("<h2>Add A new product</h2>");
			pw.println("<br>");
			
			pw.println("<br>");
			pw.println("<a class=\"button\" href=\"Report?r=inventory\"> Inventory Report </a>");
			pw.println("<br>");
			
			pw.println("<br>");
			pw.println("<a class=\"button\" href=\"Report?r=sale\"> Sales Report </a>");
			pw.println("<br>");
			pw.println("<br>");
			
			pw.println("<a class=\"button\" href=\"ProductManager?action=addProduct\">  add a new product </a>");
			pw.println("<br>");
			pw.println("<br>");
			pw.println("<hr>");
			
			
			HashMap<Integer, Product> products = MySQLDataStoreUtilities.getProductsFromDB();
			
			for(int pid : products.keySet()) {
				
				Product p  = products.get(pid);
				
				pw.println("<h2>Product ID:"+ p.getId() +"'s Detail</h2>");
				pw.println("<br>");
				pw.println("<h3>Product name: " + p.getName() + "</h3>");
				pw.println("<h3>Catagory: " + p.getCatagory() + "</h3>");
				pw.println("<h3>Condition: " + p.getCondition() + "</h3>");
				pw.println("<h5>Discount: " + p.getDiscount()+ "</h5>");
				pw.println("<h5>Image Address: " + p.getImage()+ "</h5>");
				pw.println("<h5>Price : " + p.getPrice()+ "</h5>");
				pw.println("<h5>Manufacturer : " + p.getManufacturer()+ "</h5>");
				
				for(String aname: p.getAccessory()) {
					pw.println("<h5>	Accesorry : " + aname + "</h5>");
				}
				
				pw.println("<a class=\"button\" id=\"managerDel\" href=\"Cart?sender=ajax&action=delProduct&productid="+ p.getId() + "\"> Delete this product </a>");
				pw.println("<a class=\"button\" href=\"ProductManager?action=modifyProduct\"> Modify </a>");
				
			}
			
			

			
		}
		
		//sales man, he can create account for user Add/Delete/Update customers¡¯ orders
		if(username.equalsIgnoreCase("sale")) {
			
			
			pw.println("<hr>");
			pw.println("<h2>Add A new account</h2>");
			pw.println("<br>");
			pw.println("<a class=\"button\" href=\"OrderManager?action=addCustomer\">  create Customer accounts </a>");
			pw.println("<br>");
			pw.println("<br>");
			pw.println("<hr>");
			
			//take out our users
			//HashMap<String, User> t = (HashMap<String, User>) session.getAttribute("users");
		    //get orders store map
		    HashMap<String, ArrayList<Order>> os = MySQLDataStoreUtilities.getOrdersFromDB();
			
			for (String urnm : os.keySet()) {
			    
				if(urnm.equalsIgnoreCase("sale")||urnm.equalsIgnoreCase("manager")) //skip two officer
					continue;
				
				pw.println("<h2>User:"+ urnm +"'s Orders</h2>");
				
			    ArrayList<Order> orders =  os.get(urnm); //get arraylist store this user's shit
				
				//print his orders to operating
				for(Order o : orders) {
					
					pw.println("<br>");
					pw.println("<h3>His Order: " + o.getProduct().getName() + "</h3>");
					pw.println("<h3>Confirmation#: " + o.getConfirNumber() + "</h3>");
					pw.println("<h5>OrderPlaceDate#: " + o.getOrderPlaceDate()+ "</h5>");
					pw.println("<h5>OrderDeliverDate#: " + o.getExpectedDeliverDate()+ "</h5>");
					pw.println("<a class=\"button\" id=\"saleDel\" href=\"Cart?sender=ajax&action=delOrder&ordernumber="+ o.getConfirNumber() + "&username="+ urnm +"\"> Delete this Order </a>");
					
					//complite way
					//pw.println("<a href=\"OrderManager?action=modify&ordernumber="+ o.getConfirNumber()  +"\"> Modify this Order </a>");
					pw.println("<br>");
					pw.println("<a class=\"button\" href=\"OrderManager?action=modify\"> Modify </a>");
					pw.println("<br>");
					
				}
				
				pw.println("<hr>");
				pw.println("<br>");
				pw.println("<a href=\"OrderManager?action=add\">  Add a Order for him</a>");
				pw.println("<br>");
				pw.println("<hr>");
				
			}
			
			
			
		}
		
		//Customers
		if(!username.equalsIgnoreCase("sale") && !username.equalsIgnoreCase("manager")) {
			
			
//		    //get orders store map
//		    HashMap<String, ArrayList<Order>> os = (HashMap<String, ArrayList<Order>>) session.getAttribute("order");
//		    ArrayList<Order> orders =  os.get(username); //get arraylist store this user's shit
//		    
//		    
//		    for (Order o : orders) {
//		    	
//				pw.println("<hr>");
//				pw.println("<h3>Your Order: " + o.getProduct().getName() + "</h3>");
//				pw.println("<h3>Confirmation#: " + o.getConfirNumber() + "</h3>");
//				
//				pw.println("<p>Expected Deliver Date: " + o.getExpectedDeliverDate().toString() + "</p>");
//				pw.println("<p>Has warranty?: " + o.getWarranty() + "</p>");
//				pw.println("<a id=\"cancel_order_btn\" class=\"button\" href=\"Cart?sender=ajax&action=delOrder&ordernumber="+ o.getConfirNumber() +"&username=" + username +"\" >" + "Cancel Order" + "</a>");
//				pw.println("<hr>");
//		    	
//		    	
//		    }
		    pw.println("<div id=\"cust_view_order\"> </div>");
		    
		    pw.println("<script>");
		    
		    pw.println("			$(window).on('load', function(){\r\n" + 
		    		"				viewCustOrders();\r\n" + 
		    		"			});");
		    
		    pw.println("			$(document).on('click','#cancel_order_btn',function(e){\r\n" + 
		    		"			\r\n" + 
		    		"			var a_href = $(this).attr('href');\r\n" + 
		    		"			\r\n" + 
		    		"			console.log(a_href);\r\n" + 
		    		"			\r\n" + 
		    		"			e.preventDefault();\r\n" + 
		    		"			\r\n" + 
		    		"            $.get(a_href, function(responseText) {    \r\n" + 
		    		"\r\n" + 
		    		"            if(responseText==\"del_done\") {   \r\n" + 
		    		"					viewCustOrders();\r\n" + 
		    		"			} else {\r\n" + 
		    		"				alert(\"del fault\")\r\n" + 
		    		"			}\r\n" + 
		    		"					\r\n" + 
		    		"                });\r\n" + 
		    		"			});\r\n" + 
		    		"			\r\n" + 
		    		"			function viewCustOrders () {\r\n" + 
		    		"			\r\n" + 
		    		"				$.get(\"Cart?sender=ajax&action=viewOrder&username="+ username +"\", function(responseText) {    \r\n" + 
		    		"					$(\"#cust_view_order\").html(responseText);  \r\n" + 
		    		"                });\r\n" + 
		    		"			\r\n" + 
		    		"			\r\n" + 
		    		"			}");
		    pw.println("</script>");
			
		}
		
		pw.println("</article>");
		
		pw.println("</section>\r\n" + 
				"	<div class=\"clear\"></div>");
		
	
		
		utility.printHtml("Footer.html");
		

		
		
	}

}
