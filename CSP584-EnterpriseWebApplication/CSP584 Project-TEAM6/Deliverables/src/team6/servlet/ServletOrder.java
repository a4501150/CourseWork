package team6.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import team6.business.BusinessLogic;
import team6.entity.CustomerProfile;
import team6.entity.Hotel;
import team6.entity.Location;
import team6.entity.Order;
import team6.entity.OrderStatus;
import team6.entity.Role;
import team6.entity.RoomType;
import team6.entity.User;
import team6.model.HotelManager;
import team6.model.OrderManager;

/**
 * Servlet implementation class ServletOrder
 */
@WebServlet("/order/*")
public class ServletOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrderManager om = new OrderManager();
	private HotelManager hotel = new HotelManager();
	private BusinessLogic logic = BusinessLogic.INSTANCE;
	private Gson gson = new Gson();
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("current-user");
		if(user == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		
		String[] uriSplit = request.getRequestURI().split("/");
		int orderIndex = -1;
		for(int i = 0; i < uriSplit.length; i++) {
			if(uriSplit[i].equals("order")) {
				orderIndex = i;
				break;
			}
		}
		
		switch(uriSplit[orderIndex + 1]) {
			case "view":
			{
				String queryString = request.getQueryString();
				if(queryString == null) {
					List<Order> listOrder = om.getListOrder(user);
					
					request.setAttribute("list-order", listOrder);
					request.getRequestDispatcher("/WEB-INF/jsp/order/view_order.jsp").forward(request, response);
				}
				else {
					String[] queryStringSplit = queryString.split("&");
					String[] orderIdParam = queryStringSplit[0].split("=");
					if(orderIdParam[0].equals("orderId")) {
						Order order = om.getOrder(Integer.parseInt(orderIdParam[1]));
						request.setAttribute("queried-order", order);
						request.getRequestDispatcher("/WEB-INF/jsp/order/order_details.jsp").forward(request, response);
					}
				}
				break;
			}
			case "add":
			{
				processGetAddOrder(request, response);
				break;
			}
			case "update":
			{
				processGetUpdateOrder(request, response);
				break;
			}
			case "delete":
			{
				processGetDeleteOrder(request, response);
				break;
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] uriSplit = request.getRequestURI().split("/");
		int orderIndex = -1;
		for(int i = 0; i < uriSplit.length; i++) {
			if(uriSplit[i].equals("order")) {
				orderIndex = i;
				break;
			}
		}
		
		switch(uriSplit[orderIndex + 1]) {
			case "add":
			{
				processPostAddOrder(request, response);
				break;
			}
			case "update":
			{
				processPostUpdateOrder(request, response);
				break;
			}
			case "delete":
			{
				processPostDeleteOrder(request, response);
				break;
			}
			case "cancel":
			{
				int orderId = Integer.parseInt(uriSplit[orderIndex + 2]);
				
				User currentUser = (User) request.getSession().getAttribute("current-user");
				Order order = om.getOrder(orderId);
				if(!currentUser.getuId().equals(order.getUser().getuId())) {
					response.sendRedirect(request.getContextPath());
					return;
				}
				
				om.cancelOrder(order);
				request.getSession().setAttribute("action", "cancel-order");
				response.sendRedirect(request.getContextPath() + "/success");
				break;
			}
		}
	}

	private void processGetAddOrder(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		User currentUser = (User) request.getSession().getAttribute("current-user");
		if(currentUser.getRole() != Role.MANAGER && currentUser.getRole() != Role.STAFF) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		

		String queryString = request.getQueryString();
		
		// go to page if no query string
		if(queryString == null) {
			List<Location> listLocation = hotel.getAvailableLocation();
			request.setAttribute("list-location", listLocation);
			request.getRequestDispatcher("/WEB-INF/jsp/order/order_add.jsp").forward(request, response);
		}
		else {
			String[] queryStringSplit = queryString.split("&");
			String[] queryAction = queryStringSplit[0].split("=");
			if(!queryAction[0].equals("action")) {
				return;
			}
			
			String action = queryAction[1];
			switch(action) {
				case "getHotel":
				{
					String[] locationParam = queryStringSplit[1].split("=");
					if(locationParam[0].equals("location")) {
						processGetHotelByLocation
							(request, response, Integer.parseInt(locationParam[1]));
					}
					break;
				}
				case "getRoomType":
				{
					String[] hotelParam = queryStringSplit[1].split("=");
					if(hotelParam[0].equals("hotel")) {
						processGetListRoomType
							(request, response, Integer.parseInt(hotelParam[1]));
					}
					break;
				}
			}
		}
	}
	
	private void processGetUpdateOrder(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		User currentUser = (User) request.getSession().getAttribute("current-user");
		if(currentUser.getRole() != Role.MANAGER && currentUser.getRole() != Role.STAFF) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		

		String queryString = request.getQueryString();
		
		// go to page if no query string
		if(queryString == null) {
			List<Location> listLocation = hotel.getAvailableLocation();
			request.setAttribute("list-location", listLocation);
			request.getRequestDispatcher("/WEB-INF/jsp/order/order_update.jsp").forward(request, response);
		}
		else {
			String[] queryStringSplit = queryString.split("&");
			String[] queryAction = queryStringSplit[0].split("=");
			if(!queryAction[0].equals("action")) {
				return;
			}
			
			String action = queryAction[1];
			switch(action) {
				case "getHotel":
				{
					String[] locationParam = queryStringSplit[1].split("=");
					if(locationParam[0].equals("location")) {
						processGetHotelByLocation
							(request, response, Integer.parseInt(locationParam[1]));
					}
					break;
				}
				case "getOrder":
				{
					String[] hotelParam = queryStringSplit[1].split("=");
					if(hotelParam[0].equals("hotel")) {
						processGetListOrder
							(request, response, Integer.parseInt(hotelParam[1]));
					}
					break;
				}
				case "getRoomType":
				{
					String[] hotelParam = queryStringSplit[1].split("=");
					if(hotelParam[0].equals("hotel")) {
						processGetListRoomType
							(request, response, Integer.parseInt(hotelParam[1]));
					}
					break;
				}
				case "getOrderStatus":
				{
					processGetListOrderStatus(request, response);
					break;
				}
			}
		}
		
	}
	
	private void processGetDeleteOrder(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
			User currentUser = (User) request.getSession().getAttribute("current-user");
			if(currentUser.getRole() != Role.MANAGER && currentUser.getRole() != Role.STAFF) {
				response.sendRedirect(request.getContextPath());
				return;
			}
			

			String queryString = request.getQueryString();
			
			// go to page if no query string
			if(queryString == null) {
				List<Location> listLocation = hotel.getAvailableLocation();
				request.setAttribute("list-location", listLocation);
				request.getRequestDispatcher("/WEB-INF/jsp/order/order_delete.jsp").forward(request, response);
			}
			else {
				String[] queryStringSplit = queryString.split("&");
				String[] queryAction = queryStringSplit[0].split("=");
				if(!queryAction[0].equals("action")) {
					return;
				}
				
				String action = queryAction[1];
				switch(action) {
					case "getHotel":
					{
						String[] locationParam = queryStringSplit[1].split("=");
						if(locationParam[0].equals("location")) {
							processGetHotelByLocation
								(request, response, Integer.parseInt(locationParam[1]));
						}
						break;
					}
					case "getOrder":
					{
						String[] hotelParam = queryStringSplit[1].split("=");
						if(hotelParam[0].equals("hotel")) {
							processGetListOrder
								(request, response, Integer.parseInt(hotelParam[1]));
						}
						break;
					}
					case "getRoomType":
					{
						String[] hotelParam = queryStringSplit[1].split("=");
						if(hotelParam[0].equals("hotel")) {
							processGetListRoomType
								(request, response, Integer.parseInt(hotelParam[1]));
						}
						break;
					}
					case "getOrderStatus":
					{
						processGetListOrderStatus(request, response);
						break;
					}
				}
			}
			
		}

	private void processPostAddOrder(HttpServletRequest request, HttpServletResponse response)
		throws IOException {
		CustomerProfile cp = buildCustomerProfile(request);
		Order order = buildOrder(request, cp);

		om.processOrderPlaced(order);
		
		request.getSession().setAttribute("action", "add-order");
		response.sendRedirect(request.getContextPath() + "/success");
	}
	
	private void processPostUpdateOrder(HttpServletRequest request, HttpServletResponse response)
		throws IOException {
		CustomerProfile cp = buildCustomerProfile(request);
		Order order = buildOrder(request, cp);
		
		om.updateOrder(order);
		
		request.getSession().setAttribute("action", "update-order");
		response.sendRedirect(request.getContextPath() + "/success");
	}
	
	private void processPostDeleteOrder(HttpServletRequest request, HttpServletResponse response)
		throws IOException {
		int orderId = Integer.parseInt(request.getParameter("order-id"));
		om.deleteOrder(orderId);
		
		request.getSession().setAttribute("action", "delete-order");
		response.sendRedirect(request.getContextPath() + "/success");
	}

	private Order buildOrder(HttpServletRequest request, CustomerProfile cp) {
		Order order = new Order();
		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute("current-user");
		
		String orderId = request.getParameter("order-id");
		order.setSeqNo(orderId == null ? null : Integer.valueOf(orderId));
		order.setUser(currentUser);
		order.setHotel(
			hotel.getHotel(Integer.parseInt((String) request.getParameter("hotel-id")))
		);
		order.setRoomType(
			hotel.getRoomType(Integer.parseInt((String) request.getParameter("room-type")))
		);
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate checkInDate = LocalDate.parse((String) request.getParameter("check-in"), dtf);
		LocalDate checkOutDate = LocalDate.parse((String) request.getParameter("check-out"), dtf);
		LocalTime checkInTime = logic.getCheckInTime();
		LocalTime checkOutTime = logic.getCheckOutTime();
		if(request.getParameter("order-date") == null) {	// for add order
			order.setOrderDate(LocalDate.now());
		}
		else {
			order.setOrderDate(LocalDate.parse(request.getParameter("order-date"), dtf));
		}
		order.setCheckInDateTime(LocalDateTime.of(checkInDate, checkInTime));
		order.setCheckOutDateTime(LocalDateTime.of(checkOutDate, checkOutTime));
		order.setPrice(Double.valueOf(request.getParameter("price")));
		order.setCustomer(cp);
		if(request.getParameter("order-status") == null) {
			order.setStatus(OrderStatus.PLACED);
		}
		else {
			order.setStatus(OrderStatus.valueOf(request.getParameter("order-status")));
		}
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

	/**
	 * Process AJAX request get list hotel. Return an JSON object of list hotel 
	 */
	private void processGetHotelByLocation(HttpServletRequest request, HttpServletResponse response, int locationId)
		throws IOException {
		List<Hotel> listHotel = hotel.getAvailableHotel(locationId);
		String json = listHotel == null ? "" : gson.toJson(listHotel);
		
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);
	}

	private void processGetListRoomType(HttpServletRequest request, HttpServletResponse response, int hotelId)
		throws IOException {
		List<RoomType> listRoom = hotel.getListRoomType(hotelId);
		List<RoomType> listRoomOccupied = new ArrayList<>();
		for(RoomType rt: listRoom) {
			List<Integer> listRoomTypeNumber = hotel.getAvailableRoomNumber(rt, LocalDateTime.now());
			if(listRoomTypeNumber == null) {
				listRoomOccupied.add(rt);
			}
		}
		listRoom.removeAll(listRoomOccupied);
		String json = listRoom == null ? "" : gson.toJson(listRoom);
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);
	}

	private void processGetListOrder(HttpServletRequest request, HttpServletResponse response, int hotelId)
		throws IOException {
		List<Order> listOrder = om.getListOrder(hotelId);
		String json = gson.toJson(listOrder == null ? "" : listOrder);
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);
		
	}

	/**
	 * Process AJAX request get list order status. 
	 */
	private void processGetListOrderStatus(HttpServletRequest request, HttpServletResponse response)
		throws IOException {
		List<String> listOrderStatus = new ArrayList<>();
		for(OrderStatus os: OrderStatus.values()) {
			listOrderStatus.add(os.toString());
		}
		
		String json = gson.toJson(listOrderStatus == null ? "" : listOrderStatus);
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);
	}
}
