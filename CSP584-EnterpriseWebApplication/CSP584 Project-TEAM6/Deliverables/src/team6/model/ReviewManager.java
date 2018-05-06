package team6.model;

import java.time.LocalDate;
import java.util.Map;

import team6.dao.ReviewDAO;
import team6.entity.CustomerProfile;
import team6.entity.Hotel;
import team6.entity.Order;
import team6.entity.Review;

public class ReviewManager {

	private OrderManager om = new OrderManager();
	private ReviewDAO reviewDao = new ReviewDAO();
	
	public void doSubmitReview(int orderId, double rating, String comment) {
		Order order = om.getOrder(orderId);
		Hotel hotel = order.getHotel();
		CustomerProfile cp = order.getCustomer();
		
		Review review = new Review(hotel.getName(), hotel.getAddress(), hotel.getLocation().getCity(), hotel.getLocation().getState()
									, hotel.getLocation().getZip(), order.getRoomType().getName(), order.getCheckInDateTime().toLocalDate()
									, order.getCheckOutDateTime().toLocalDate(), cp.getFirstName(), cp.getLastName()
									, Double.valueOf(rating), LocalDate.now(), comment);
		
		reviewDao.insertReview(review);
	}

	public Map<String, Double> getTopFiveHotelByRating() {
		return reviewDao.selectTopHotelByRating(5);
	}

	public Map<String, Integer> getTopZipByReview() {
		return reviewDao.selectTopZipByReviewCount(5);
	}

	public Map<String, Integer> getTopFiveHotelByOrder() {
		return reviewDao.selectTopHotelByOrder(5);
	}

}
