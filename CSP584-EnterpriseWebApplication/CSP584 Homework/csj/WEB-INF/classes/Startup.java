import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;


public class Startup extends HttpServlet {

       
	public void init(ServletConfig config) throws ServletException {
		
		SaxParserDataStore.addHashmap();

		
		
	}

}
