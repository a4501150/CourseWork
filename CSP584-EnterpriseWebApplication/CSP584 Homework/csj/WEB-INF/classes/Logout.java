import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Models.Product;

public class Logout extends HttpServlet {

       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		HttpSession session=request.getSession();
		//session.invalidate();
		
		if(request.getParameter("des") != null) {
			session.invalidate();
		
		}
		
		
		session.setAttribute("isLogin", false);
		session.setAttribute("username", null);
		
		//clear cart only
		ArrayList<Product> p_incart = (ArrayList<Product>)session.getAttribute("cart");
		p_incart.clear();
		
		response.sendRedirect("");
		
		
		
	}

}
