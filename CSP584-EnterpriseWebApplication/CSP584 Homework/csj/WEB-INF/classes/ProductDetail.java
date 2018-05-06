

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Models.Product;
import Models.Review;


public class ProductDetail extends HttpServlet {


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
		HttpSession session=request.getSession();

		String username = (String) session.getAttribute("username");
		
		Utilities utility = new Utilities(request,pw);
		
		String productId = request.getParameter("id");
		
		
		//take out products
		HashMap<Integer, Product> tmp = MySQLDataStoreUtilities.getProductsFromDB();
		
		//get that product
		Product product = tmp.get(Integer.parseInt(productId));
		
		//start our rendering
		utility.printHtml("Header_Start.html");
		utility.printHtml("Header_Nav.html");
		utility.printHtml("Header_End.html");
		utility.printHtml("LeftNavigationBar_Start.html");
		utility.printHtml("LeftCatagory.html");
		utility.printHtml("LeftTrending.html");

		utility.printHtml("LeftAjaxCart.html");
		utility.printHtml("LeftNavigationBar_End.html");
		
		
		pw.print("<section id=\"content\">\r\n");
		
		pw.println("<center>");
		pw.println("<img src=");
		pw.print(product.getImage());
		pw.print(" width = \"500\" height = \"500\"  alt=\"img\" />");
		pw.println("</center>");
		
		
		//Detail
		pw.println("<article class=\"expanded\">");
		pw.println("<h2>Product Detail:</h2>");
		pw.println("<br>");
		pw.println("<h5>Product Name: "+ product.getName() +"</h5>"); 
		pw.println("<h5>Product Price: "+ product.getPrice() +"</h5>"); 
		pw.println("<h5>Product manufacturer: "+ product.getManufacturer() +"</h5>"); 
		pw.println("<h5>Product condition: "+ product.getCondition() +"</h5>"); 
		pw.println("<h5>Product discount: "+ utility.toPercentage(product.getDiscount()/100.0) +"</h5>"); 
		
		
		
		pw.println("<a id=\"addToCartBtn\" href=\"\" class=\"button\">Add To Cart</a>\r\n");
		
		pw.println("</article>");

		
		
		//accessory
		pw.println("<h2>Accessory recommend:</h2>");
		pw.println("<marquee scrollamount = \"20\" direction = \"left\" behavior = \"alternate\" loop = \"10000\">");
		String [] accs = product.getAccessory();
		
		for(String cat : accs) {
			
			
			for (Product p : tmp.values()) {
			    
				if(p.getCatagory().equalsIgnoreCase(cat)) {
					
					pw.println();
					pw.print("<img src=\"");
					pw.print(p.getImage()+"\"");
					pw.print("width = \"300\" height = \"300\"  alt=\"accessory\" />");
					pw.println();
					
					break;
					
				}
				
			}
			
			
		}
		
		pw.println("</marquee>");
		
		pw.println("<br><br>");
		
		HashMap<String, ArrayList<Review>> rt = MongoDBDataStoreUtilities.selectReview();
		
		ArrayList<Review> rw = new ArrayList<Review>();
		
		if(rt.get(productId)!=null)
			rw = rt.get(productId);
		
		pw.println("<article class=\"expanded\">");
		pw.println("<h2>Reviews:</h2>");
		
		if(rw.size()!=0) {
		
			for(Review r : rw) {
				
				pw.println("<br><hr>");
				pw.println("<p>ProductModelName: "+ product.getName() +" </p>");
				pw.println("<p>ProductCategory: "+ product.getCatagory() +" </p>");
				pw.println("<p>ProductPrice: $"+ product.getPrice() +" </p>");
				pw.println("<p>RetailerName: "+ "SmartPortables" +" </p>");
				pw.println("<p>RetailerZip: "+ "60616" +" </p>");
				pw.println("<p>RetailerCity: "+ "Chicago" +" </p>");
				pw.println("<p>RetailerState: "+ "IL" +" </p>");
				pw.println("<p>ProductOnSale: "+ (product.getDiscount()!=0.0) +" </p>");
				pw.println("<p>ManufacturerName: "+ product.getManufacturer()+" </p>");
				pw.println("<p>ManufacturerRebate: "+"yes"+" </p>");
				pw.println("<p>UserID: "+r.getUsername()+" </p>");
				pw.println("<p>UserAge: "+r.getUserAge()+" </p>");
				pw.println("<p>UserGender: "+r.getUserGender()+" </p>");
				pw.println("<p>UserOccupation: "+r.getUserOccupation()+" </p>");
				pw.println("<p>ReviewRating: "+r.getRate()+" </p>");
				pw.println("<p>ReviewDate: "+r.getRate()+" </p>");
				pw.println("<p>ReviewText: "+r.getText()+" </p>");
				
				
				
			}
		}
		
		
		pw.println("<br><hr>");
		pw.println("<h3>Write A Review For this Product</h3>");
		pw.println("<br>");
		
		if(session.getAttribute("username")!=null) {
		
		pw.println("            <form id=\"reviewForm\" method = \"Get\" action=\"ReviewManager\">\r\n" + 
				"               <table cellpadding='2' cellspacing='8'>\r\n" + 
				"			      <tr>\r\n" + 
				"                     <td>Review Rate</td>\r\n" + 
				"                     <td><input type=\"TEXT\" size=\"20\" name=\"rate\"></input></td>\r\n" + 
				"					 <input type=\"hidden\" name=\"action\" value=\"Add\">\r\n" + 
				"                  </tr>\r\n" + 
				"				  \r\n" + 
				"				  <tr>\r\n" + 
				"                     <td>Review Text</td>\r\n" + 
				"                     <td><input type=\"TEXT\" size=\"20\" name=\"text\"></input></td>\r\n" + 
				"					 <input type=\"hidden\" name=\"action\" value=\"Add\">\r\n" + 
				"                  </tr>\r\n" + 
				"               </table>\r\n" + 
				"            </form>");
		
		
		pw.println("<a id=\"writeReviewBtn\" href=\"ReviewManager?pid="+ productId +"&\" class=\"button\">Write A Review</a>\r\n");
		
		} else {
			
			
			pw.println("<h3>Please Login First To Write A review</h3>");
			
			
			
		}
		
		
		pw.println("</article>");
		
		pw.println("</section>\r\n" + 
				"	<div class=\"clear\"></div>");
		utility.printHtml("Footer.html");
		
		
		
		
		
	}

}
