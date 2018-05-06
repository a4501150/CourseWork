import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Models.Product;


public class AjaxUtility extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String action = request.getParameter("action");
		
		switch(action) {
		
		case "ajax":
			doAjaxGet(response.getWriter(),request);
			break;
		
		
		}
		
		
	}
	
	private void doAjaxGet(PrintWriter pw, HttpServletRequest request) {
		
		String pattern = request.getParameter("pattern");
		if(pattern.length()<1 || pattern.equalsIgnoreCase(" ")||pattern.equalsIgnoreCase(""))
			return;
		String res = this.readdata(pattern).toString();
		pw.print(res);
		
	}
	
	//since I already do these in previous hw, so here i jsut use a wrapper for easier grading
	private HashMap<Integer, Product> getData() {
		return MySQLDataStoreUtilities.getProductsFromDB();
	}
	
	//since I already do these in previous hw, so here i jsut use a wrapper for easier grading
	public StringBuffer readdata(String searchPattern)
	{
		HashMap<Integer,Product> data;
		data=getData();
		Iterator<Entry<Integer,Product>> it = data.entrySet().iterator();
		StringBuffer sb = new StringBuffer();
		while (it.hasNext())
		{
			Map.Entry pi = (Map.Entry)it.next();
			Product p=(Product)pi.getValue();
			if (p.getName().toLowerCase().startsWith(searchPattern))
			{
				//sb.append("<tr><td>");
				sb.append("<p><a href=ProductDetail?id=" + p.getId() +">");
				sb.append(""+ p.getName() +"");
				sb.append("</a></p>");
				//sb.append("</td></tr>");
			}
		}
		return sb;
	}
	
	

	
	
	

}
