import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;

import Models.Product;

public class SaxParserDataStore extends HttpServlet {

	
	public static HashMap<Integer,Product> products; 
	
	
	public static boolean initilazied;
	
	public SaxParserDataStore(String string) throws ServletException {
		
		if(initilazied)
			throw new ServletException();

		MySaxParserEx A = new MySaxParserEx(string);
		SaxParserDataStore.products = MySaxParserEx.products;
		
		
		initilazied = true;
		
	}

	public static void addHashmap() throws ServletException {
		
		String TOMCAT_HOME = System.getProperty("catalina.home");
		new SaxParserDataStore(TOMCAT_HOME+"\\webapps\\csj\\ProductCatalog.xml");
		
	}
	
	

}
