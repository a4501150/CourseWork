package team6.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.google.gson.Gson;

import team6.entity.BedType;
import team6.entity.Hotel;
import team6.entity.Location;
import team6.entity.Order;
import team6.entity.Role;
import team6.entity.RoomType;
import team6.entity.User;
import team6.helper.FileUploadHelper;
import team6.model.HotelManager;
import team6.model.OrderManager;

@WebServlet("/room/*")
public class ServletRoomManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HotelManager hotel = new HotelManager();
	private OrderManager om = new OrderManager();
    private Gson gson = new Gson();
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User currentUser = (User) request.getSession().getAttribute("current-user");
		if(currentUser == null || currentUser.getRole() == Role.CUSTOMER) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		String[] uriSplit = request.getRequestURI().split("/");
		int roomIndex = -1;
		for(int i = 0; i < uriSplit.length; i++) {
			if(uriSplit[i].equals("room")) {
				roomIndex = i;
				break;
			}
		}
		// /room/
		if(uriSplit.length == roomIndex + 1) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		switch(uriSplit[roomIndex + 1]) {
			case "add":
			{
				processGetAddRoomType(request, response);
				break;
			}
			case "update":
			{
				processGetUpdateRoomType(request, response);
				break;
			}
			case "delete":
			{
				processGetDeleteRoomType(request, response);
				break;
			}
			case "checkin":
			{
				processGetCheckIn(request, response);
				break;
			}
			case "checkout":
			{
				processGetCheckOut(request, response);
				break;
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User currentUser = (User) request.getSession().getAttribute("current-user");
		if(currentUser == null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		String[] uriSplit = request.getRequestURI().split("/");
		int roomIndex = -1;
		for(int i = 0; i < uriSplit.length; i++) {
			if(uriSplit[i].equals("room")) {
				roomIndex = i;
				break;
			}
		}
		// /room/
		if(uriSplit.length == roomIndex + 1) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		switch(uriSplit[roomIndex + 1]) {
			case "add":
			{
				processPostAddRoom(request, response);
				break;
			}
			case "update":
			{
				processPostUpdateRoom(request, response);
				break;
			}
			case "delete":
			{
				processPostDeleteRoom(request, response);
				break;
			}
			case "checkin":
			{
				processPostCheckInRoom(request, response);
				break;
			}
			case "checkout":
			{
				processPostCheckOutRoom(request, response);
				break;
			}
		}
	}

	private void processGetAddRoomType(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		User currentUser = (User) request.getSession().getAttribute("current-user");
		if(currentUser.getRole() != Role.MANAGER) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		String queryString = request.getQueryString();
		
		// go to page if no query string
		if(queryString == null) {
			List<Location> listLocation = hotel.getAvailableLocation();
			request.setAttribute("list-location", listLocation);
			request.getRequestDispatcher("/WEB-INF/jsp/room/room_add.jsp").forward(request, response);
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
				case "getBedType":
				{
					processGetBedType(request, response);
					break;
				}
				case "isRoomTypeExist":
				{
					String[] hotelParam = queryStringSplit[1].split("=");
					String[] roomNameParam = queryStringSplit[2].split("=");
					if(hotelParam[0].equals("hotel") && roomNameParam[0].equals("roomName")) {
						processCheckRoomTypeExist
							(request, response
							, Integer.parseInt(hotelParam[1]), roomNameParam[1].replaceAll("\\+", " "));
					}
					break;
				}
				case "isRoomExist":
				{
					String[] hotelParam = queryStringSplit[1].split("=");
					String[] roomListParam = queryStringSplit[2].split("=");
					if(hotelParam[0].equals("hotel") && roomListParam[0].equals("roomList")) {
						List<Integer> roomList = Arrays.asList(roomListParam[1].replaceAll("\\+", ",")
													.split(","))
													.stream()
													.map(Integer::parseInt)
													.collect(Collectors.toList())
						;
						processCheckRoomExist
							(request, response
							, Integer.parseInt(hotelParam[1]), roomList);
					}
					break;
				}
			}
		}
	}

	private void processGetUpdateRoomType(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		User currentUser = (User) request.getSession().getAttribute("current-user");
		if(currentUser.getRole() != Role.MANAGER) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		String queryString = request.getQueryString();
		
		// go to page if no query string
		if(queryString == null) {
			List<Location> listLocation = hotel.getAvailableLocation();
			request.setAttribute("list-location", listLocation);
			request.getRequestDispatcher("/WEB-INF/jsp/room/room_update.jsp").forward(request, response);
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
						processGetListRoomType(request, response, Integer.parseInt(hotelParam[1]));
					}
					break;
				}
				case "getBedType":
				{
					processGetBedType(request, response);
					break;
				}
				case "isRoomTypeExist":
				{
					String[] hotelParam = queryStringSplit[1].split("=");
					String[] roomNameParam = queryStringSplit[2].split("=");
					if(hotelParam[0].equals("hotel") && roomNameParam[0].equals("roomName")) {
						processCheckRoomTypeExist
							(request, response
							, Integer.parseInt(hotelParam[1]), roomNameParam[1].replaceAll("\\+", " "));
					}
					break;
				}
				case "isRoomExist":
				{
					String[] hotelParam = queryStringSplit[1].split("=");
					String[] roomListParam = queryStringSplit[2].split("=");
					if(hotelParam[0].equals("hotel") && roomListParam[0].equals("roomList")) {
						List<Integer> roomList = Arrays.asList(roomListParam[1].replaceAll("\\+", ",")
													.split(","))
													.stream()
													.map(Integer::parseInt)
													.collect(Collectors.toList())
						;
						processCheckRoomExist
							(request, response
							, Integer.parseInt(hotelParam[1]), roomList);
					}
					break;
				}
			}
		}
		
	}

	private void processGetDeleteRoomType(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		User currentUser = (User) request.getSession().getAttribute("current-user");
		if(currentUser.getRole() != Role.MANAGER) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		String queryString = request.getQueryString();
		
		// go to page if no query string
		if(queryString == null) {
			List<Location> listLocation = hotel.getAvailableLocation();
			request.setAttribute("list-location", listLocation);
			request.getRequestDispatcher("/WEB-INF/jsp/room/room_delete.jsp").forward(request, response);
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
						processGetListRoomType(request, response, Integer.parseInt(hotelParam[1]));
					}
					break;
				}
				case "getBedType":
				{
					processGetBedType(request, response);
					break;
				}
			}
		}
		
	}

	private void processGetCheckIn(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		String queryString = request.getQueryString();
		// go to page if no query string
		if(queryString == null) {
			List<Location> listLocation = hotel.getAvailableLocation();
			request.setAttribute("list-location", listLocation);
			request.getRequestDispatcher("/WEB-INF/jsp/room/check_in.jsp").forward(request, response);
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
				case "getCheckInOrder":
				{
					String[] hotelParam = queryStringSplit[1].split("=");
					if(hotelParam[0].equals("hotel")) {
						processGetCheckInOrder(request, response, Integer.parseInt(hotelParam[1]));
					}
					break;
				}
				case "getAvailableRoomNumber":
				{
					String[] orderParam = queryStringSplit[1].split("=");
					if(orderParam[0].equals("order")) {
						processGetAvailableRoomNumber(request, response, Integer.parseInt(orderParam[1]));
					}
					break;
				}
			}
		}
	}

	private void processGetCheckOut(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		String queryString = request.getQueryString();
		// go to page if no query string
		if(queryString == null) {
			List<Location> listLocation = hotel.getAvailableLocation();
			request.setAttribute("list-location", listLocation);
			request.getRequestDispatcher("/WEB-INF/jsp/room/check_out.jsp").forward(request, response);
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
				case "getCheckOutOrder":
				{
					String[] hotelParam = queryStringSplit[1].split("=");
					if(hotelParam[0].equals("hotel")) {
						processGetCheckOutOrder(request, response, Integer.parseInt(hotelParam[1]));
					}
					break;
				}
				case "getAvailableRoomNumber":
				{
					String[] orderParam = queryStringSplit[1].split("=");
					if(orderParam[0].equals("order")) {
						processGetAvailableRoomNumber(request, response, Integer.parseInt(orderParam[1]));
					}
					break;
				}
			}
		}
	}
	
	/**
	 *	Process AJAX request get available reservation for current date 
	 */
	private void processGetCheckInOrder(HttpServletRequest request, HttpServletResponse response, int hotelId)
		throws IOException {
		List<Order> listOrder = om.getCheckInOrder(hotelId);
		String json = gson.toJson(listOrder == null ? "" : listOrder);
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);
	}
	
	/**
	 *	Process AJAX request get available reservation for current date 
	 */
	private void processGetCheckOutOrder(HttpServletRequest request, HttpServletResponse response, int hotelId)
		throws IOException {
		List<Order> listOrder = om.getCheckOutOrder(hotelId);
		String json = gson.toJson(listOrder == null ? "" : listOrder);
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);
	}

	/**
	 * Process AJAX request get available room number given room type 
	 * @param orderId 
	 * @throws IOException 
	 */
	private void processGetAvailableRoomNumber(HttpServletRequest request, HttpServletResponse response, int orderId)
		throws IOException {
		Order order = om.getOrder(orderId);
		List<Integer> listRoomNumber = hotel.getAvailableRoomNumber
			(order.getRoomType(), order.getCheckInDateTime());
		String json = listRoomNumber == null ? "" : gson.toJson(listRoomNumber);
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);
		
	}

	private void processGetListRoomType(HttpServletRequest request, HttpServletResponse response, int hotelId)
		throws IOException {
		List<RoomType> listRoom = hotel.getListRoomType(hotelId);
		String json = listRoom == null ? "" : gson.toJson(listRoom);
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);
	}

	/**
	 * Process AJAX request get list bed type. Return an JSON object of list room type 
	 * @throws IOException 
	 */
	private void processGetBedType(HttpServletRequest request, HttpServletResponse response)
		throws IOException {
		List<String> listBedType = hotel.getListBedType();
		String json = listBedType == null ? "" : gson.toJson(listBedType);
		
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);
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

	private void processPostAddRoom(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		if(!ServletFileUpload.isMultipartContent(request)) {return;}	// ensure it's multi part request
		FileUploadHelper fuh = new FileUploadHelper();
		
		List<FileItem> listItem = fuh.parseMultipartRequest(request);
		Map<String, String> paramMap = new HashMap<>();
		List<FileItem> toBeUploaded = new ArrayList<>();
		for(FileItem fi: listItem) {
			if(fi.isFormField()) {
				paramMap.put(fi.getFieldName(), fi.getString());
			}
			else {
				toBeUploaded.add(fi);
			}
		}
		
		int hotelId = Integer.parseInt(paramMap.get("hotel-id"));
		String roomName = paramMap.get("name");
		BedType bedType = BedType.valueOf(paramMap.get("bed-type"));
		int bedAmount = Integer.parseInt(paramMap.get("bed-amount"));
		int peopleNo = Integer.parseInt(paramMap.get("people-no"));
		String view = paramMap.get("view");
		boolean isWifi = (paramMap.get("is-wifi") == null ? false : true);
		boolean isTv = (paramMap.get("is-tv") == null ? false : true);
		double price = Double.parseDouble(paramMap.get("price"));
		double discount = Double.parseDouble(paramMap.get("discount"));
		
		List<Integer> roomList = Arrays.asList(paramMap.get("room-list").split(","))
									.stream()
									.map(Integer::parseInt)
									.collect(Collectors.toList())
		;
		
		RoomType roomType = new RoomType();
		roomType.setName(roomName);
		roomType.setBedType(bedType);
		roomType.setBedAmount(Integer.valueOf(bedAmount));
		roomType.setPeopleNo(Integer.valueOf(peopleNo));
		roomType.setView(view);
		roomType.setIsWifi(Boolean.valueOf(isWifi));
		roomType.setIsTV(Boolean.valueOf(isTv));
		roomType.setPrice(Double.valueOf(price));
		roomType.setDiscount(Double.valueOf(discount));
		roomType.setRoomList(roomList);
		
		Integer roomId = hotel.addRoomType(hotelId, roomType);
		String image = fuh.uploadRoomImage(request.getServletContext(), toBeUploaded, roomId);
		roomType.setImage(image);
		
		hotel.addNewRoomImage(roomId.intValue(), image);
		
		request.getSession().setAttribute("action", "add-room");
		response.sendRedirect(request.getContextPath() + "/success");
	}

	private void processPostUpdateRoom(HttpServletRequest request, HttpServletResponse response)
		throws IOException {
		int roomId = Integer.parseInt(request.getParameter("room-id"));
		
		String roomName = request.getParameter("name");
		BedType bedType = BedType.valueOf(request.getParameter("bed-type"));
		int bedAmount = Integer.parseInt(request.getParameter("bed-amount"));
		int peopleNo = Integer.parseInt(request.getParameter("people-no"));
		String view = request.getParameter("view");
		boolean isWifi = (request.getParameter("is-wifi") == null ? false : true);
		boolean isTv = (request.getParameter("is-tv") == null ? false : true);
		double price = Double.parseDouble(request.getParameter("price"));
		double discount = Double.parseDouble(request.getParameter("discount"));
		
		List<Integer> roomList = Arrays.asList(request.getParameter("room-list").split(","))
									.stream()
									.map(Integer::parseInt)
									.collect(Collectors.toList())
		;
		
		RoomType roomType = new RoomType();
		roomType.setSeqNo(Integer.valueOf(roomId));
		roomType.setName(roomName);
		roomType.setBedType(bedType);
		roomType.setBedAmount(Integer.valueOf(bedAmount));
		roomType.setPeopleNo(Integer.valueOf(peopleNo));
		roomType.setView(view);
		roomType.setIsWifi(Boolean.valueOf(isWifi));
		roomType.setIsTV(Boolean.valueOf(isTv));
		roomType.setPrice(Double.valueOf(price));
		roomType.setDiscount(Double.valueOf(discount));
		roomType.setRoomList(roomList);
		
		hotel.updateRoomType(roomType);
		
		request.getSession().setAttribute("action", "update-room");
		response.sendRedirect(request.getContextPath() + "/success");
	}
	
	private void processPostDeleteRoom(HttpServletRequest request, HttpServletResponse response)
		throws IOException {
		int roomTypeId = Integer.parseInt(request.getParameter("room-type-id"));
		
		hotel.deleteRoomType(roomTypeId);
		
		request.getSession().setAttribute("action", "delete-room");
		response.sendRedirect(request.getContextPath() + "/success");
	}

	private void processPostCheckInRoom(HttpServletRequest request, HttpServletResponse response)
		throws IOException {
		int roomNum = Integer.parseInt(request.getParameter("room-num"));
		int orderId = Integer.parseInt(request.getParameter("order-id"));
		
		hotel.processAssignRoom(orderId, roomNum);
		
		request.getSession().setAttribute("action", "assign-room");
		response.sendRedirect(request.getContextPath() + "/success");
	}
	
	private void processPostCheckOutRoom(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
			int orderId = Integer.parseInt(request.getParameter("order-id"));
			
			hotel.processCheckOutRoom(orderId);
			
			request.getSession().setAttribute("action", "check-out-room");
			response.sendRedirect(request.getContextPath() + "/success");
		}

	private void processCheckRoomTypeExist
		(HttpServletRequest request, HttpServletResponse response, int hotelId, String roomName)
		throws IOException {
		boolean isRoomExist = hotel.isRoomExist(hotelId, roomName);
		String json = isRoomExist ? gson.toJson("true"): gson.toJson("false");
		
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);
	}

	private void processCheckRoomExist(HttpServletRequest request, HttpServletResponse response, int hotelId,
			List<Integer> roomList) throws IOException {
		List<Integer> listRoomExist = hotel.getListRoomExist(hotelId, roomList);
		String json = listRoomExist == null ? "" : gson.toJson(listRoomExist);
		
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);		
		
	}

}
