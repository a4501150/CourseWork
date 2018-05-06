import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import Models.Order;
import Models.Product;

public class MySQLDataStoreUtilities {
	
	
	// JDBC driver name and database URL
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	private static final String DB_URL="jdbc:mysql://localhost/CS584";

    //  Database credentials
	private static final String USER = "root";
	private static final String PASS = "root";
    
	private static Connection conn;
	
	public static boolean init = false;
    
    public static final MySQLDataStoreUtilities mysql = new MySQLDataStoreUtilities();

	private MySQLDataStoreUtilities() {
		
		
	      try
	      {
	          Class.forName(JDBC_DRIVER);
	          conn = DriverManager.getConnection(DB_URL,USER,PASS);
	      } 
	      catch (Exception e) 
	      {
	           e.printStackTrace();
	      }
	      
	      try {
	    	  
	    	  ResultSet rs = fetchQuery("select * from product");
		      if(rs.next()) {
		    	  if(!rs.wasNull())
		    		  return;
	      }
	    	  
	     
	      }catch(Exception e) {
	    	  System.out.println("initialize product failed");
	      }
	      
	      HashMap<Integer, Product> tmp = SaxParserDataStore.products;      
	      
	      for(Product value  : tmp.values()) {
	    	  
	    	  String acc= "";
	    	  for (String a : value.getAccessory())
	    		  acc += a+","; 
	    	  
	    	  acc = acc.substring(0, acc.length() - 1);
	    	  
	    	  nonFetchQuery("insert into product (pid, catagory, name, price, image, manufacturer, pcondition, discount, Accessory) values ("
	    			+ ""+ value.getId()  + "," 
	    			+ "'"+ value.getCatagory()  + "'," 
	    	  		+ "'"+ value.getName() + "'," 
	    	  		+ ""+ value.getPrice()  + "," 
	    	  		+ "'"+ value.getImage()  + "'," 
	    	  		+ "'"+ value.getManufacturer()  + "'," 
	    	  		+ "'"+ value.getCondition()  + "'," 
	    	  		+ ""+ value.getDiscount()  + "," 	 
	    	    	+ "'"+ acc  + "'" 	  
	    			  +")");
	    	  
	    	  
	      }
	      
	      System.out.println("initialize product done");
		
	}
	
	
	public static ResultSet fetchQuery(String query) {
		
		ResultSet rs = null;
		
		try
		{
			
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			return rs;
			
		}
		catch (Exception e)
		{
			System.out.println("Fetch Query Failed For ");
			System.out.println(query);
		}
		
		return rs;
	
		
	}
	
	
	public static void nonFetchQuery(String query) {
		
	      try
	      {
	          Statement stmt = conn.createStatement();
	          stmt.executeUpdate(query);
	             
	      } 
	      catch (Exception e) 
	      {
	           //e.printStackTrace();
				System.out.println("nonFetchQuery Query Failed For ");
				System.out.println(query);
	    	  
	      }
		
	}
	
	public static HashMap<Integer, Product> getProductsFromDB(){
		
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

	
	
	
	
	public static void ModifyProduct(int id,Product p) {
		
		delProduct(id);
		p.setId(id);
		AddProduct(p);
		
	}
	
	public static void delProduct(int id) {
		
		String query = "delete from product where pid = " + id;
		nonFetchQuery(query);
		
	}
	
	public static void AddProduct(Product value) {
		
		
	  	  String acc= "";
	  	  for (String a : value.getAccessory())
	  		  acc += a+","; 
	  	  
	  	  acc = acc.substring(0, acc.length() - 1);
	  	  
	  	  nonFetchQuery("insert into product (pid, catagory, name, price, image, manufacturer, pcondition, discount, Accessory) values ("
	  			+ ""+ value.getId()  + "," 
	  			+ "'"+ value.getCatagory()  + "'," 
	  	  		+ "'"+ value.getName() + "'," 
	  	  		+ ""+ value.getPrice()  + "," 
	  	  		+ "'"+ value.getImage()  + "'," 
	  	  		+ "'"+ value.getManufacturer()  + "'," 
	  	  		+ "'"+ value.getCondition()  + "'," 
	  	  		+ ""+ value.getDiscount()  + "," 	 
	  	    	+ "'"+ acc  + "'" 	  
	  			  +")");
		
		
	}
	
	public static int getMaxProductID() {
		
		
		  String query1 = "select MAX(PID) from product";
			
		  ResultSet rss = fetchQuery(query1);

		  try 
		  
		  {
			if(!rss.wasNull()&&rss.next())
				return rss.getInt("pid");
		  } catch (SQLException e) {
			  e.printStackTrace();
		  }
		  
		  return 1;
		
		
	}
	
	public static void addOrder(int uid,Order value){
		
		
	  	  nonFetchQuery("insert into orders (uid, pid ,confirmationNumber, status, warranty, expectedDeliverDate, orderPlaceDate, zipcode) values ("
	  			+ ""+ uid + "," 
	  			+ ""+ value.getProduct().getId() + "," 
	  			+ ""+ value.getConfirNumber()  + "," 
	  	  		+ "'"+ value.getStatus() + "'," 
	  	  		+ "'"+ value.getWarranty()  + "'," 
	  	  		+ "'"+ value.getExpectedDeliverDate().toString()  + "'," 
	  	  		+ "'"+ value.getOrderPlaceDate().toString() + "'," 
	  	  		+ ""+ value.getZipcode()  + "" 

	  			  +")");
		 
		
	}
	
	
	public static void delOrder(int confirmationNumber) {
		
		String query = "delete from orders where confirmationNumber = " + confirmationNumber;
		nonFetchQuery(query);
		
	}
	
	public static int getUserIDFromName(String username) {
		
		String query = "select uid from account where username ='"+username+"'" ;
		  ResultSet rss = fetchQuery(query);

		  try 
		  
		  {
			if(!rss.wasNull()&&rss.next())
				return rss.getInt("uid");
		  } catch (SQLException e) {
			  e.printStackTrace();
		  }
		  
		  return -1;
		
	}
	
	public static HashMap<String, ArrayList<Order>> getOrdersFromDB(){

		
		String query = "select username,uid from account where uid != 1 and uid != 2";
		ResultSet rss = fetchQuery(query);
		HashMap<String, ArrayList<Order>> tmp = new HashMap<String, ArrayList<Order>>();
		
		
		try {
			while(rss.next()) 
			{
				
				tmp.put(rss.getString("username"), getOrdersForUser(rss.getInt("uid")));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return tmp;
		
	}
	
	public static ArrayList<Order> getOrdersForUser(int uid){
		
		
			String query = "select * from orders where uid ="+uid+"";
			ResultSet rss = fetchQuery(query);
		
			ArrayList<Order> tmp = new ArrayList<Order>();
			
			try {
				while(rss.next()) {
					
					Order o = new Order();
					
					o.setConfirNumber(rss.getInt("confirmationNumber"));
					o.setExpectedDeliverDate(LocalDate.parse(rss.getString("expectedDeliverDate")));
					o.setOrderPlaceDate(LocalDate.parse(rss.getString("orderPlaceDate")));
					o.setStatus(rss.getString("status"));
					o.setWarranty(rss.getString("warranty"));
					o.setZipcode(rss.getInt("zipCode"));
					
					Product p = getProductFromDB(rss.getInt("pid"));
					o.setProduct(p);
					
					tmp.add(o);
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return tmp;
		
		
	}
	
	public static Product getProductFromDB(int pid) {
		
		return getProductsFromDB().get(pid);
		
	}
	
	
	public static ArrayList<Product> GetTopFiveProdcutSold() {
		
		ArrayList<Product> tmp = new ArrayList<Product>();
		
		String query = "SELECT pid, COUNT(pid) AS pidCount " + 
				" FROM orders " + 
				" GROUP BY pid " + 
				" ORDER BY pidCount DESC ";
		ResultSet rs = fetchQuery(query);
		
		Product p;
		int i=1;
		
		try {
		while(rs.next()) {
			
			
			p = getProductsFromDB().get(rs.getInt("pid"));
			tmp.add(p);
			
			i++;
			
			if(i>=5)
				break;
			
		}
		
		}catch(Exception e) {
			e.getMessage();
		}
		
		
		return tmp;
	}
	
	
	
	public static ArrayList<Integer> GetTopFiveZipCode() {
		
		ArrayList<Integer> tmp = new ArrayList<Integer>();
		
		String query = "SELECT zipCode, COUNT(pid) AS pidCount " + 
				" FROM orders " + 
				" GROUP BY zipCode " + 
				" ORDER BY pidCount DESC ";
		ResultSet rs = fetchQuery(query);
		
		Product p;
		int i=1;
		
		try {
		while(rs.next()) {
			

			tmp.add(rs.getInt("zipCode"));
			
			i++;
			
			if(i>=5)
				break;
			
		}
		
		}catch(Exception e) {
			e.getMessage();
		}
		
		
		return tmp;
	}
	
	
	public static ArrayList<Product> GetMostLikedProduct() {
		
		ArrayList<Product> tmp = new ArrayList<Product>();
		
		String query = "SELECT pid, avg(reviewRate) AS rate " + 
				"FROM review " + 
				"GROUP BY pid " + 
				"ORDER BY rate DESC;";
		
		ResultSet rs = fetchQuery(query);
		
		Product p;
		int i=1;
		
		try {
		while(rs.next()) {
			
			
			p = getProductsFromDB().get(rs.getInt("pid"));
			tmp.add(p);
			
			i++;
			
			if(i>=5)
				break;
			
		}
		
		}catch(Exception e) {
			e.getMessage();
		}
		
		
		return tmp;
	}
	
	public static void addRate(double rate, int pid) {
		
		String query = "insert into review (reviewRate, pid) values ( "+ rate +" , "+  pid +")";
		nonFetchQuery(query);
		
	}
	
	
	public static void PrintSalesReport(PrintWriter pw) {
		
		String query = "SELECT name,price,discount as ds, product.pid as pd from product,orders where product.pid in (select orders.pid from orders group by orders.pid) group by name";
		ResultSet rs = fetchQuery(query);
		
		try {
		
			pw.println("<br><tr>");
			pw.println("<h2> all products sold and how many </h2>");
			
			pw.println("<table cellspacing=\"0\">");
			pw.println("<tr>");
			pw.println("<th>Product Name</th>");
			pw.println("<th>Product Price</th>");
			pw.println("<th>Product Sold Count</th>");
			pw.println("<th>Total Sales</th>");
			pw.println("</tr>");
			
			HashMap<String, Double> t = new HashMap<String, Double>();
			
			while(rs.next()) {
				
				String name = rs.getString("name");
				double price = rs.getDouble("price");
				double discount = rs.getDouble("ds");
				int pid = rs.getInt("pd");
				
				pw.println("<tr>");
				pw.println("<th>"+ name +"</th>");
				pw.println("<th>"+ price +"</th>");
				
				
				
				String q1 = "SELECT COUNT(pid) AS pc " + 
						"FROM orders " + 
						"where pid = " + pid +"";
				
				ResultSet rs1 = fetchQuery(q1);
				
				if(rs1.next()) {
					
					
					int c = rs1.getInt("pc");
					pw.println("<th>"+ c +"</th>");
					
					double value = (100-discount)/100 * price * c;
					value = Double.parseDouble(new DecimalFormat("#0.00").format(value));
					
					t.put(name, value);
					pw.println("<th>"+ value +"</th>");
					
				}
					
				
				
				pw.println("</tr>");
				
				

	
			}
			
			pw.println("</table>");
			
			
			pw.println("<br><tr>");
			pw.println("<h2> BarCart </h2>");
			
			pw.println("<link rel=\"stylesheet\" href=\"static/simple-chart.css\">");
			
			
			pw.println("    <div class=\"sc-wrapper\">\r\n" + 
					"        <section class=\"sc-section\">\r\n" + 
					"            <div class=\"column-chart\"></div>\r\n" + 
					"        </section> </div>");
			
			
			pw.println("<script src=\"static/simple-chart.js\"></script>");
			
			pw.println("<script> var labels = [");
			
			
			for (String pname : t.keySet()) {
				pw.print("\""+pname+"\"" + ",") ;
			}
			pw.println(" ];");
			
			pw.println("var values = [");
			
			for (double a : t.values() ) {
				pw.print(""+  a  +"" + ",") ;
			}
			pw.println(" ];");
			
			pw.println("var outputValues = values;");
			
			pw.println("</script>");
			
			pw.println("<script>        $('.column-chart').simpleChart({\r\n" + 
					"            title: {\r\n" + 
					"                text: ' product names and the " + 
					"total sales for every product'," + 
					"                align: 'center'\r\n" + 
					"            },\r\n" + 
					"            type: 'column',\r\n" + 
					"            layout: {\r\n" + 
					"                width: '100%',\r\n" + 
					"                height: '250px'\r\n" + 
					"            },\r\n" + 
					"            item: {\r\n" + 
					"                label: labels,\r\n" + 
					"                value: values,\r\n" + 
					"                outputValue: outputValues,\r\n" + 
					"                color: ['#00aeef'],\r\n" + 
					"                prefix: '$ ',\r\n" + 
					"                suffix: '',\r\n" + 
					"                render: {\r\n" + 
					"                    margin: 0.2,\r\n" + 
					"                    size: 'relative'\r\n" + 
					"                }\r\n" + 
					"            }\r\n" + 
					"        });</script>");
			
			
			pw.println("<br><tr>");
			pw.println("<h2>  total daily sales transactions </h2>");
			

			pw.println("<table cellspacing=\"0\">");
			pw.println("<tr>");
			pw.println("<th>dates</th>");
			pw.println("<th>Total Sales</th>");
			pw.println("</tr>");
			
			query = "select orderPlaceDate, pid, count(pid) as pc from orders group by pid";
			rs = fetchQuery(query);
			
			HashMap<String, Double> tt = new HashMap<String,Double>();
			
			while(rs.next()) {
				
				
				String opd = rs.getString("orderPlaceDate");
				int pid = rs.getInt("pid");
				int pc = rs.getInt("pc");
				
				tt.put(opd, 0.0);
				
				String q1 = "select price, discount as dc from product where pid = " + pid;
				ResultSet rs1 = fetchQuery(q1);
				
				if(rs1.next()) {
					
					double value = rs1.getDouble("price")*pc*(100-rs1.getDouble("dc"))/100;
					value = Double.parseDouble(new DecimalFormat("#0.00").format(value));
					
					tt.put(opd, tt.get(opd) + value);
				}
				
				
			}
			
			
			Iterator<Entry<String, Double>> it = tt.entrySet().iterator();
			while(it.hasNext()) {
				
				Map.Entry<String, Double> pair = (Map.Entry<String, Double>)it.next();
				pw.println("<tr>");
				pw.println("<th>" +  pair.getKey() + "</th>");
			    pw.println("<th>"+   pair.getValue() +"</th>");
				pw.println("</tr>");
				
			}

			
			pw.println("</table>");
			
			
			
		}catch(Exception e) {
			String a = e.getMessage();
			
			System.out.println(a);
		}
		
	}
	
	public static void ParttenSearchProductName() {
		
		
		
	}
	
	
	public static void PrintInventoryReport(PrintWriter pw) {
		
		
		String query = "SELECT name,price,count(name) as ct from product group by name";
		ResultSet rs = fetchQuery(query);
		
		
		try {
			
			pw.println("<table cellspacing=\"0\">");
			pw.println("<tr>");
			pw.println("<th>Product Name</th>");
			pw.println("<th>Product Price</th>");
			pw.println("<th>Product Count</th>");
			pw.println("</tr>");
			
			
			while(rs.next()) {
				
				String name = rs.getString("name");
				double price = rs.getDouble("price");
				int count = rs.getInt("ct");
				
				pw.println("<tr>");
				pw.println("<th>"+ name +"</th>");
				pw.println("<th>"+ price +"</th>");
				pw.println("<th>"+ count +"</th>");
				pw.println("</tr>");

	
			}
			
			pw.println("</table>");
			
			
			query = "SELECT name,price,count(name) as ct from product group by name";
			rs = fetchQuery(query);
			
			pw.println("<br><tr>");
			pw.println("<h2> BarCart </h2>");
			
			pw.println("<link rel=\"stylesheet\" href=\"static/simple-chart.css\">");
			
			
			pw.println("    <div class=\"sc-wrapper\">\r\n" + 
					"        <section class=\"sc-section\">\r\n" + 
					"            <div class=\"column-chart\"></div>\r\n" + 
					"        </section> </div>");
			
			
			pw.println("<script src=\"static/simple-chart.js\"></script>");
			
			pw.println("<script> var labels = [");
			
			
			
			while(rs.next()) {
				
				String name = rs.getString("name");
						
				if(rs.next()) {
					pw.println(" \"" + name  + "\"" + ",");
					rs.previous();
				}else
					pw.println(" \"" + name  + "\"];");
				
			}
			
			
			query = "SELECT name,price,count(name) as ct from product group by name";
			rs = fetchQuery(query);
			
			pw.println("var values = [");
			
			while(rs.next()) {
				
				int ct = rs.getInt("ct");
						
				if(rs.next()) {
					pw.println("" + ct  + "" + ",");
					rs.previous();
				}else
					pw.println("" + ct  + "];");
				
			}
			
			pw.println("var outputValues = values;");
			
			pw.println("</script>");
			
			
			pw.println("<script>        $('.column-chart').simpleChart({\r\n" + 
					"            title: {\r\n" + 
					"                text: 'product names and the number of items available',\r\n" + 
					"                align: 'center'\r\n" + 
					"            },\r\n" + 
					"            type: 'column',\r\n" + 
					"            layout: {\r\n" + 
					"                width: '100%',\r\n" + 
					"                height: '250px'\r\n" + 
					"            },\r\n" + 
					"            item: {\r\n" + 
					"                label: labels,\r\n" + 
					"                value: values,\r\n" + 
					"                outputValue: outputValues,\r\n" + 
					"                color: ['#00aeef'],\r\n" + 
					"                prefix: 'Count: ',\r\n" + 
					"                suffix: '',\r\n" + 
					"                render: {\r\n" + 
					"                    margin: 0.2,\r\n" + 
					"                    size: 'relative'\r\n" + 
					"                }\r\n" + 
					"            }\r\n" + 
					"        });</script>");
			
			

			
			query = "SELECT name, discount from product where discount != 0.0";
			rs = fetchQuery(query);
			
			
			pw.println("<br><tr>");
			pw.println("<h2> Product OnSale </h2>");
			
			pw.println("<table cellspacing=\"0\">");
			pw.println("<tr>");
			pw.println("<th>Product Name</th>");
			pw.println("<th>Product Discount</th>");
			pw.println("</tr>");
			
			
			while(rs.next()) {
				
				String name = rs.getString("name");
				double dis = rs.getDouble("discount");
				
				pw.println("<tr>");
				pw.println("<th>"+ name +"</th>");
				pw.println("<th>"+ dis +"</th>");
				pw.println("</tr>");


	
			}
			
			pw.println("</table>");
			
			query = "SELECT name from product where discount = 0.0";
			rs = fetchQuery(query);
			
			pw.println("<br><tr>");
			pw.println("<h2> Product HAVE manufacturer rebates  </h2>");
			
			pw.println("<table cellspacing=\"0\">");
			pw.println("<tr>");
			pw.println("<th>Product Name</th>");
			pw.println("</tr>");
			
			
			while(rs.next()) {
				
				String name = rs.getString("name");
				
				pw.println("<tr>");
				pw.println("<th>"+ name +"</th>");
				pw.println("</tr>");

	
			}
			
			pw.println("</table>");
			
			
			
			
		
		}catch(Exception e) {
			e.getMessage();
		}
		
		

	}
	
	
	
	
}
