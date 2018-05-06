package team6.servlet;

import java.io.IOException;
import java.time.LocalTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import team6.business.BusinessLogic;

/**
 * Servlet implementation class ServletSuccess
 */
@WebServlet("/success")
public class ServletSuccess extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String action = (String) session.getAttribute("action");
		if(action == null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		switch(action) {
			case "create-account":
			{
				session.removeAttribute("action");
				request.getRequestDispatcher("/WEB-INF/jsp/success/create-account.jsp").forward(request, response);
				break;
			}
			case "add-hotel":
			{
				session.removeAttribute("action");
				request.getRequestDispatcher("/WEB-INF/jsp/success/add-hotel.jsp").forward(request, response);
				break;
			}
			case "update-hotel":
			{
				session.removeAttribute("action");
				request.getRequestDispatcher("/WEB-INF/jsp/success/update-hotel.jsp").forward(request, response);
				break;
			}
			case "delete-hotel":
			{
				session.removeAttribute("action");
				request.getRequestDispatcher("/WEB-INF/jsp/success/delete-hotel.jsp").forward(request, response);
				break;
			}
			case "add-room":
			{
				session.removeAttribute("action");
				request.getRequestDispatcher("/WEB-INF/jsp/success/add-room.jsp").forward(request, response);
				break;
			}
			case "update-room":
			{
				session.removeAttribute("action");
				request.getRequestDispatcher("/WEB-INF/jsp/success/update-room.jsp").forward(request, response);
				break;
			}
			case "delete-room":
			{
				session.removeAttribute("action");
				request.getRequestDispatcher("/WEB-INF/jsp/success/delete-room.jsp").forward(request, response);
				break;
			}
			case "add-order":
			{
				session.removeAttribute("action");
				request.getRequestDispatcher("/WEB-INF/jsp/success/add-order.jsp").forward(request, response);
				break;
			}
			case "update-order":
			{
				session.removeAttribute("action");
				request.getRequestDispatcher("/WEB-INF/jsp/success/update-order.jsp").forward(request, response);
				break;
			}
			case "delete-order":
			{
				session.removeAttribute("action");
				request.getRequestDispatcher("/WEB-INF/jsp/success/delete-order.jsp").forward(request, response);
				break;
			}
			case "reservation":
			{
				session.removeAttribute("action");
				LocalTime checkInTime = BusinessLogic.INSTANCE.getCheckInTime();
				LocalTime checkOutTime = BusinessLogic.INSTANCE.getCheckOutTime();
				request.setAttribute("check-in-time", checkInTime);
				request.setAttribute("check-out-time", checkOutTime);
				request.getRequestDispatcher("/WEB-INF/jsp/reservation-completed.jsp").forward(request, response);
				break;
			}
			case "cancel-order":
			{
				session.removeAttribute("action");
				request.getRequestDispatcher("/WEB-INF/jsp/success/cancel-order.jsp").forward(request, response);
				break;
			}
			case "submit-review":
			{
				session.removeAttribute("action");
				request.getRequestDispatcher("/WEB-INF/jsp/success/submit-review.jsp").forward(request, response);
				break;
			}
			case "assign-room":
			{
				session.removeAttribute("action");
				request.getRequestDispatcher("/WEB-INF/jsp/success/assign-room.jsp").forward(request, response);
				break;
			}
			case "check-out-room":
			{
				session.removeAttribute("action");
				request.getRequestDispatcher("/WEB-INF/jsp/success/check-out-room.jsp").forward(request, response);
				break;
			}
		}
	}

}
