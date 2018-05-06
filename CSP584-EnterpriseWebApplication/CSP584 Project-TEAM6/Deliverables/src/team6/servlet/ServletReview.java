package team6.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import team6.entity.Order;
import team6.entity.User;
import team6.model.OrderManager;
import team6.model.ReviewManager;

@WebServlet("/review")
public class ServletReview extends HttpServlet {
	private static final long serialVersionUID = 1L;
	OrderManager om = new OrderManager();
	ReviewManager rm = new ReviewManager();
	Gson gson = new Gson();
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("current-user");
		if(user == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		
		String queryString = request.getQueryString();
		if(queryString == null) {
			List<Order> listOrder = om.getListOrder(user);
			request.setAttribute("list-order", listOrder);
			request.getRequestDispatcher("/WEB-INF/jsp/review.jsp").forward(request, response);
		}
		else {
			String[] queryStringSplit = queryString.split("&");
			String[] actionParam = queryStringSplit[0].split("=");
			
			if(actionParam[0].equals("action")) {
				switch(actionParam[1]) {
					case "getOrder":
					{
						String[] idParam = queryStringSplit[1].split("=");
						if(idParam[0].equals("id")) {
							Order order = om.getOrder(Integer.parseInt(idParam[1]));
							String json = order == null ? "" : gson.toJson(order);
							
							response.setContentType("application/json");
						    response.setCharacterEncoding("UTF-8");
						    response.getWriter().write(json);
						}
						break;
					}
				}
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int orderId = Integer.parseInt(request.getParameter("order-id"));
		double rating = Double.parseDouble(request.getParameter("rating"));
		String comment = request.getParameter("comment");
		
		rm.doSubmitReview(orderId, rating, comment);
		
		request.getSession().setAttribute("action", "submit-review");
		response.sendRedirect(request.getContextPath() + "/success");
	}

}
