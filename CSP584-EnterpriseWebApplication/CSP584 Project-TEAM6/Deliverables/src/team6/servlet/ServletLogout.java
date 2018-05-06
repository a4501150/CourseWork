package team6.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import team6.model.Authenticator;

@WebServlet("/logout")
public class ServletLogout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private Authenticator auth;
	
    public ServletLogout() {
        auth = new Authenticator();
    }

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		HttpSession session = request.getSession();
		auth.doLogout(session);
		
		response.sendRedirect(request.getContextPath());
	}


}
