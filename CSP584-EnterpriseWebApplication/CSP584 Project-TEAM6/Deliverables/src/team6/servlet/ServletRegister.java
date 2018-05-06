package team6.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import team6.entity.Role;
import team6.entity.User;
import team6.model.Authenticator;

@WebServlet("/register")
public class ServletRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Authenticator auth;
       
    public ServletRegister() {
        auth = new Authenticator();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User currentUser = (User) request.getSession().getAttribute("current-user");
		if(currentUser != null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		request.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User currentUser = (User) request.getSession().getAttribute("current-user");
		
		String email = request.getParameter("email");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String cfp = request.getParameter("cfp");
		Role role = null;
		if(currentUser != null && currentUser.getRole().equals(Role.MANAGER)) {
			role = getRole(request.getParameter("role"));
		}
		
		List<String> listError = new ArrayList<>();
		User user = null;
		
		if(!password.equals(cfp)) {
			listError.add("Password entered not match.");
		}
		else {
			user = auth.doRegister(username, password, email, (role == null ? Role.CUSTOMER : role));
			if(user == null) {
				listError.add("Username already exists.");
			}
		}
		if(listError.size() > 0) {
			request.setAttribute("register-failed", Boolean.TRUE);
			request.setAttribute("list-error", listError);
			request.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(request, response);
			return;
		}
		else {
			// if normal register: login and send to homepage
			if(currentUser == null) {
				request.getSession().setAttribute("current-user", user);
				response.sendRedirect(request.getContextPath());
			}
			// if manager do register: redirect to success page
			else {
				request.getSession().setAttribute("action", "create-account");
				response.sendRedirect(request.getContextPath() + "/success");
			}
		}
		
	}

	/**
	 * Return a Role object by given parameter
	 */
	private Role getRole(String str) {
		for(Role role: Role.values()) {
			if(str.toUpperCase().equals(role.toString())) {
				return role;
			}
		}
		return null;
	}


}
