package team6.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import team6.entity.User;

/**
 * Servlet implementation class ServletAccount
 */
@WebServlet("/account/*")
public class ServletAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User currentUser = (User) request.getSession().getAttribute("current-user");
		if(currentUser == null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		switch(currentUser.getRole()) {
			case MANAGER:
			{
				processManagerAccount(request, response);
				break;
			}
			case STAFF:
			{
				processStaffAccount(request, response);
				break;
			}
			case CUSTOMER:
			{
				processCustomerAccount(request, response);
				break;
			}
		}
	}

	private void processManagerAccount(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String[] uriSplit = request.getRequestURI().split("/");
		int accountIndex = -1;
		for(int i = 0; i < uriSplit.length; i++) {
			if(uriSplit[i].equals("account")) {
				accountIndex = i;
				break;
			}
		}
		
		if(uriSplit.length == accountIndex + 1) {
			request.getRequestDispatcher("/WEB-INF/jsp/account/manager_menu.jsp").forward(request, response);
		}
		else {
			switch(uriSplit[accountIndex + 1]) {
				case "createAccount":
				{
					request.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(request, response);
				}
			}
		}
		
	}
	
	private void processStaffAccount(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String[] uriSplit = request.getRequestURI().split("/");
		int accountIndex = -1;
		for(int i = 0; i < uriSplit.length; i++) {
			if(uriSplit[i].equals("account")) {
				accountIndex = i;
				break;
			}
		}
		
		if(uriSplit.length == accountIndex + 1) {
			request.getRequestDispatcher("/WEB-INF/jsp/account/staff_menu.jsp").forward(request, response);
		}
		else {
			switch(uriSplit[accountIndex + 1]) {
				case "createAccount":
				{
					request.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(request, response);
				}
			}
		}
	}
	
	private void processCustomerAccount(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/account/customer_menu.jsp").forward(request, response);
	}

}
