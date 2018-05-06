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


public class ProductManager extends HttpServlet {



	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
		Utilities utility = new Utilities(request,pw);
	   
		String action = request.getParameter("action");
		
		switch(action) {

			
		case "modifyProduct":
			utility.printHtml("ManagerModifyProduct.html");
			break;
			
		case "addProduct":
			utility.printHtml("ManagerAddProduct.html");
			break;

		}
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		HttpSession session=request.getSession();
		
		
		Utilities utility = new Utilities(request,pw);

	    HashMap<Integer, Product> products = MySQLDataStoreUtilities.getProductsFromDB();
	    
	    
		utility.printHtml("Header_Start.html");
		utility.printHtml("Header_Nav.html");
		utility.printHtml("Header_End.html");
		utility.printHtml("LeftNavigationBar_Start.html");
		utility.printHtml("LeftCatagory.html");
		utility.printHtml("LeftNavigationBar_End.html");
		
		pw.print("<section id=\"content\">\r\n");
		pw.println("<br>");
		pw.println("<article class=\"expanded\">");
		
		
		
		String Name = request.getParameter("Name");
		
		String price = request.getParameter("price");
		String Image = request.getParameter("Image");
		String Manufacturer = request.getParameter("Manufacturer");
		String Condition = request.getParameter("Condition");
		String catagory = request.getParameter("catagory");
		String Discont = request.getParameter("Discont");
		String Accessory = request.getParameter("Accessory");
		
		
		
	    
		
		
		switch(action) {
		
		case "ModifyProduct":
			
			String  pid = request.getParameter("pid");
			Product pm = products.get(Integer.parseInt(pid));
			
			pm.setName(Name);
			pm.setCatagory(catagory);
			pm.setCondition(Condition);
			pm.setDiscount(Double.parseDouble(Discont));
			pm.setPrice(Double.parseDouble(price));
			pm.setImage(Image);
			
			System.out.println("addres" +pm.getImage());
			
			pm.setManufacturer(Manufacturer);
			
			String[] tt = Accessory.split(",");
			pm.setAccessory(tt);
			
			pw.println("<h2>Product Modified Successful!</h2>");
			pw.println("<h3>Product# is "+ pm.getId() +" !</h3>");
			pw.println("<h3>Product detail "+ pm.toString() +" !</h3>");
			
			//products.put(pm.getId(), pm);
			MySQLDataStoreUtilities.ModifyProduct(pm.getId(), pm);
		
			
			break;
		
		case "AddProduct" :
			
			
			Product tmpr = new Product();
			
			
			tmpr.setId(MySQLDataStoreUtilities.getMaxProductID() + 1);
			
			tmpr.setName(Name);
			tmpr.setCatagory(catagory);
			tmpr.setCondition(Condition);
			tmpr.setDiscount(Double.parseDouble(Discont));
			tmpr.setPrice(Double.parseDouble(price));
			tmpr.setImage(Image);
			tmpr.setManufacturer(Manufacturer);
			
			System.out.println("addres" +tmpr.getImage());

			String[] ttt = Accessory.split(",");
			tmpr.setAccessory(ttt);
			
			//products.put(tmpr.getId(), tmpr);
			MySQLDataStoreUtilities.AddProduct(tmpr);
			
			pw.println("<h2>Product Added Successful!</h2>");
			pw.println("<h3>Product# is "+ tmpr.getId() +" !</h3>");
			pw.println("<h3>Product detail "+ tmpr.toString() +" !</h3>");
			
			
			break;
		
		
		}
		
		
		pw.println("</article>");
		
		pw.println("</section>\r\n" + 
				"	<div class=\"clear\"></div>");

		utility.printHtml("Footer.html");
		
		
	    
		
		
	}

}
