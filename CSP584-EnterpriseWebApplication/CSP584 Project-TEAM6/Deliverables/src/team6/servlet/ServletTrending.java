package team6.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import team6.entity.Role;
import team6.entity.User;
import team6.model.ReviewManager;

@WebServlet("/trending")
public class ServletTrending extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ReviewManager rm = new ReviewManager();
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("current-user");
		
		if(user == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		else if(user.getRole() != Role.MANAGER) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		Map<String, Double> topFiveHotelByRating = rm.getTopFiveHotelByRating();
        Map<String, Integer> topZipByReview = rm.getTopZipByReview();
        Map<String, Integer> topFiveHotelByOrder = rm.getTopFiveHotelByOrder();
        
        request.setAttribute("top-five-hotel-rating", topFiveHotelByRating);
        request.setAttribute("top-five-zip", topZipByReview);
        request.setAttribute("top-five-hotel-amount", topFiveHotelByOrder);

        request.getRequestDispatcher("/WEB-INF/jsp/trending.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
