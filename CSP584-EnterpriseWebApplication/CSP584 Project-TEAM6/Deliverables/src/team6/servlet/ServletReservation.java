package team6.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import team6.business.BusinessLogic;
import team6.entity.CustomerProfile;
import team6.entity.Hotel;
import team6.entity.Order;
import team6.entity.RoomType;
import team6.entity.User;
import team6.model.HotelManager;
import team6.model.OrderManager;

/**
 * Servlet implementation class ServletReservation
 */
@WebServlet("/reserve/*")
public class ServletReservation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HotelManager hm = new HotelManager();
	private OrderManager om = new OrderManager();
	private BusinessLogic logic = BusinessLogic.INSTANCE;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] queryStringSplit = request.getQueryString().split("&");
		String[] actionSplit = queryStringSplit[0].split("=");
		if(actionSplit[0].equals("action")) {
			switch(actionSplit[1]) {
				case "chooseRoom":
				{
					String[] hotelParam = queryStringSplit[1].split("=");
					String[] checkInParam = queryStringSplit[2].split("=");
					String[] checkOutParam = queryStringSplit[3].split("=");
					if(hotelParam[0].equals("hotel") && checkInParam[0].equals("checkIn") && checkOutParam[0].equals("checkOut")) {
						List<RoomType> listRoomType = hm.getListRoomType(Integer.parseInt(hotelParam[1]));
						
						DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMddyyyy");
						LocalDate checkInDate = LocalDate.parse(checkInParam[1], dtf);
						LocalTime checkInTime = BusinessLogic.INSTANCE.getCheckInTime();
						LocalDateTime checkInDateTime = LocalDateTime.of(checkInDate, checkInTime);
								
						Map<RoomType, Boolean> mapRoomTypeAvailable = new HashMap<>();
						if(listRoomType != null) {
							for(RoomType rt: listRoomType) {
								List<Integer> listAvailableRoomNum = hm.getAvailableRoomNumber(rt, checkInDateTime);
								mapRoomTypeAvailable.put(rt, listAvailableRoomNum == null ? Boolean.FALSE : Boolean.TRUE);
							}
						}
						request.setAttribute("map-room-type", mapRoomTypeAvailable);
						request.setAttribute("hotel-id", hotelParam[1]);
						request.setAttribute("check-in", checkInParam[1]);
						request.setAttribute("check-out", checkOutParam[1]);
						request.getRequestDispatcher("/WEB-INF/jsp/choose-room.jsp").forward(request, response);
					}
					break;
				}
				case "reservation":
				{
					String[] hotelParam = queryStringSplit[1].split("=");
					String[] roomParam = queryStringSplit[2].split("=");
					String[] checkInParam = queryStringSplit[3].split("=");
					String[] checkOutParam = queryStringSplit[4].split("=");
					if(	hotelParam[0].equals("hotel")
						&& roomParam[0].equals("room")	
						&& checkInParam[0].equals("checkIn")
						&& checkOutParam[0].equals("checkOut")
					) {
						HttpSession session = request.getSession();
						String url = request.getRequestURL().toString() + "?" + request.getQueryString();
						
						User currentUser = (User) request.getSession().getAttribute("current-user");
						if(currentUser == null) {
							session.setAttribute("request-url", url);
							response.sendRedirect(request.getContextPath() +"/login");
						}
						else {
							DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMddyyyy");
							LocalDate checkInDate = LocalDate.parse(checkInParam[1], dtf);
							LocalDate checkOutDate = LocalDate.parse(checkOutParam[1], dtf);
							RoomType rt = hm.getRoomType(Integer.parseInt(roomParam[1]));
							Hotel hotel = rt.getHotelBelong();
							
							Integer numDay = Integer.valueOf(hm.calcDayBetween(checkInDate, checkOutDate));
							
							request.setAttribute("hotel", hotel);
							request.setAttribute("room-type", rt);
							request.setAttribute("check-in-date", checkInDate);
							request.setAttribute("check-out-date", checkOutDate);
							request.setAttribute("num-day", numDay);
							request.getRequestDispatcher("/WEB-INF/jsp/reservation.jsp").forward(request, response);
						}
						break;
					}
				}
			}
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CustomerProfile cp = buildCustomerProfile(request);
		Order order = buildOrder(request, cp);

		om.processOrderPlaced(order);
		
		HttpSession session = request.getSession();
		session.removeAttribute("reservation-hotel");
		session.removeAttribute("reservation-room");
		session.removeAttribute("reservation-check-in");
		session.removeAttribute("reservation-check-out");
		
		session.setAttribute("action", "reservation");
		response.sendRedirect(request.getContextPath() + "/success");
	}

	private Order buildOrder(HttpServletRequest request, CustomerProfile cp) {
		Order order = new Order();
		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute("current-user");
		
		order.setUser(currentUser);
		order.setHotel(
			hm.getHotel(Integer.parseInt(request.getParameter("hotel-id")))
		);
		order.setRoomType(
			hm.getRoomType(Integer.parseInt(request.getParameter("room-type-id")))
		);
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate checkInDate = LocalDate.parse(request.getParameter("check-in"), dtf);
		LocalDate checkOutDate = LocalDate.parse(request.getParameter("check-out"), dtf);
		LocalTime checkInTime = logic.getCheckInTime();
		LocalTime checkOutTime = logic.getCheckOutTime();
		order.setOrderDate(LocalDate.now());
		order.setCheckInDateTime(LocalDateTime.of(checkInDate, checkInTime));
		order.setCheckOutDateTime(LocalDateTime.of(checkOutDate, checkOutTime));
		order.setPrice(Double.valueOf(request.getParameter("total-price")));
		order.setCustomer(cp);
		return order;
	}

	private CustomerProfile buildCustomerProfile(HttpServletRequest request) {
		CustomerProfile cp = new CustomerProfile();
		cp.setFirstName(request.getParameter("first-name"));
		cp.setLastName(request.getParameter("last-name"));
		cp.setEmail(request.getParameter("email"));
		cp.setPhone(request.getParameter("phone"));
		cp.setAddress(request.getParameter("address"));
		cp.setCity(request.getParameter("city"));
		cp.setState(request.getParameter("state"));
		cp.setZip(request.getParameter("zip"));
		cp.setCreditCardNum(request.getParameter("cc-num"));
		cp.setExpirationDate(request.getParameter("cc-exp"));
		return cp;
	}
}
