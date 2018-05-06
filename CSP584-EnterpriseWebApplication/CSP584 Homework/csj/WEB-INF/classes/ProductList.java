

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Models.Product;


public class ProductList extends HttpServlet {
	


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
		HttpSession session=request.getSession();
		
		Utilities utility = new Utilities(request,pw);
		
		String choice = request.getParameter("c");
		
		
		//take out products
		HashMap<Integer, Product> tmp = getProductsFromDB();
		
		utility.printHtml("Header_Start.html");
		utility.printHtml("Header_Nav.html");
		utility.printHtml("Header_End.html");
		utility.printHtml("LeftNavigationBar_Start.html");
		utility.printHtml("LeftTrending.html");
		utility.printHtml("LeftCatagory.html");
		utility.printHtml("LeftAjaxCart.html");
		utility.printHtml("LeftNavigationBar_End.html");

		pw.print("<section id=\"content\">\r\n");
		
		//SmartWatches
		if(choice.equalsIgnoreCase("all")||choice.equalsIgnoreCase("SmartWatches")) {
			pw.println("<br>");
		pw.println("<article class=\"expanded\">");
		pw.println("<h2>Smart Watches</h2>");
		pw.println("<ul>");
		for (Product value  : tmp.values()) {
		    
			if(value.getCatagory().equalsIgnoreCase("SmartWatch")||value.getCatagory().equalsIgnoreCase("ClassicWatch")) {
				pw.println("<li>");
				pw.println(value.toString());
				pw.println("</li>");
			}

		}
		pw.println("</ul>");
		pw.println("</article>");
		}
		
		
		if(choice.equalsIgnoreCase("all")||choice.equalsIgnoreCase("Speakers")) {pw.println("<br>");
		//Speakers
		pw.println("<article class=\"expanded\">");
		pw.println("<h2>Speakers</h2>");
		pw.println("<ul>");
		for (Product value  : tmp.values()) {
		    
			if(value.getCatagory().equalsIgnoreCase("SmallSpeaker")||value.getCatagory().equalsIgnoreCase("BigSpeaker")) {
				pw.println("<li>");
				pw.println(value.toString());
				pw.println("</li>");
			}

		}
		pw.println("</ul>");
		pw.println("</article>");
		}
		
		//HeadPhones
		if(choice.equalsIgnoreCase("all")||choice.equalsIgnoreCase("HeadPhones")) {pw.println("<br>");
		pw.println("<article class=\"expanded\">");
		pw.println("<h2>HeadPhones</h2>");
		pw.println("<ul>");
		for (Product value  : tmp.values()) {
		    
			if(value.getCatagory().equalsIgnoreCase("HeadPhone")||value.getCatagory().equalsIgnoreCase("EarPhone")) {
				pw.println("<li>");
				pw.println(value.toString());
				pw.println("</li>");
			}

		}
		pw.println("</ul>");
		pw.println("</article>");
		}
		
		//Phones
		if(choice.equalsIgnoreCase("all")||choice.equalsIgnoreCase("Phones")) {pw.println("<br>");
		pw.println("<article class=\"expanded\">");
		pw.println("<h2>Phones</h2>");
		pw.println("<ul>");
		for (Product value  : tmp.values()) {
		    
			if(value.getCatagory().equalsIgnoreCase("SmartPhone")) {
				pw.println("<li>");
				pw.println(value.toString());
				pw.println("</li>");
			}

		}
		pw.println("</ul>");
		pw.println("</article>");
		}
		
		//Laptops
		if(choice.equalsIgnoreCase("all")||choice.equalsIgnoreCase("Laptops")) {pw.println("<br>");
		pw.println("<article class=\"expanded\">");
		pw.println("<h2>Laptops</h2>");
		pw.println("<ul>");
		for (Product value  : tmp.values()) {
		    
			if(value.getCatagory().equalsIgnoreCase("Laptop")) {
				pw.println("<li>");
				pw.println(value.toString());
				pw.println("</li>");
			}

		}
		pw.println("</ul>");
		pw.println("</article>");
		}
		
		//External Storage
		if(choice.equalsIgnoreCase("all")||choice.equalsIgnoreCase("ExternalStorage")) {pw.println("<br>");
		pw.println("<article class=\"expanded\">");
		pw.println("<h2>External Storage</h2>");
		pw.println("<ul>");
		for (Product value  : tmp.values()) {
		    
			if(value.getCatagory().equalsIgnoreCase("MemoryCard")||value.getCatagory().equalsIgnoreCase("HardDrive")||value.getCatagory().equalsIgnoreCase("FlashDrive")) {
				pw.println("<li>");
				pw.println(value.toString());
				pw.println("</li>");
			}

		}
		pw.println("</ul>");
		pw.println("</article>");
		}
		
		
		
		
		//Top five most liked products 
		if(choice.equalsIgnoreCase("allT")||choice.equalsIgnoreCase("T1")) {
			pw.println("<br>");
			pw.println("<article class=\"expanded\">");
			pw.println("<h2>Top five most liked products</h2>");
			pw.println("<ul>");
			
		for (Product value  : MySQLDataStoreUtilities.GetMostLikedProduct()) {
		    
			{
				pw.println("<li>");
				pw.println(value.toString());
				pw.println("</li>");
			}

		}
			pw.println("</ul>");
			pw.println("</article>");
		}
		
		//Top five zip-codes where maximum number of products sold 
		if(choice.equalsIgnoreCase("allT")||choice.equalsIgnoreCase("T2")) {
			pw.println("<br>");
			pw.println("<article class=\"expanded\">");
			pw.println("<h2>Top five zip-codes where maximum number of products sold </h2>");
			pw.println("<ul>");
			
		for (Integer value  : MySQLDataStoreUtilities.GetTopFiveZipCode()) {
		    
		   {
				pw.println("<li>");
				pw.println(value.toString());
				pw.println("</li>");
			}

		}
			pw.println("</ul>");
			pw.println("</article>");
		}
		
		
		//Top five most sold products regardless of the rating 
		if(choice.equalsIgnoreCase("allT")||choice.equalsIgnoreCase("T3")) {
			pw.println("<br>");
			pw.println("<article class=\"expanded\">");
			pw.println("<h2>Top five most sold products</h2>");
			pw.println("<ul>");
			
		for (Product value  : MySQLDataStoreUtilities.GetTopFiveProdcutSold()) {
		    
		   {
				pw.println("<li>");
				pw.println(value.toString());
				pw.println("</li>");
			}

		}
			pw.println("</ul>");
			pw.println("</article>");
		}
		
		
		
		
		
		pw.println("</section>\r\n" + 
				"	<div class=\"clear\"></div>");
		utility.printHtml("Footer.html");
		
		
		
	}
	
	
	
	
	
	HashMap<Integer, Product> getProductsFromDB(){
		
		HashMap<Integer, Product> tmp = new HashMap<Integer, Product>();
		
		
		String query = "select * from product";
		ResultSet rs = MySQLDataStoreUtilities.fetchQuery(query);
		Product p;
		
		try {
		while(rs.next()) {
			
			
			p = new Product();
			p.setId(     rs.getInt(1)    );
			p.setCatagory(rs.getString(2));
			p.setName(rs.getString("name"));
			p.setPrice(rs.getDouble("price"));
			p.setImage(rs.getString("image"));
			p.setManufacturer(rs.getString("manufacturer"));
			p.setCondition(rs.getString("pcondition"));
			p.setDiscount(rs.getDouble("discount"));
			String t = rs.getString("Accessory");
			p.setAccessory(t.split(","));
			
			tmp.put(p.getId(), p);
			
		}
		
		}catch(Exception e) {
			e.getMessage();
		}
		
		return tmp;

		
	}
	

	
	

}
