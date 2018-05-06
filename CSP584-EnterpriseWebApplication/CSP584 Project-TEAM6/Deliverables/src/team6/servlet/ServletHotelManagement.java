package team6.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.google.gson.Gson;

import team6.entity.Hotel;
import team6.entity.Location;
import team6.entity.Role;
import team6.entity.User;
import team6.helper.FileUploadHelper;
import team6.model.HotelManager;

/**
 * Servlet implementation class ServletHotelManagement
 */
@WebServlet("/hotel/*")
public class ServletHotelManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HotelManager hotel = new HotelManager();
	private Gson gson = new Gson();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		User currentUser = (User) request.getSession().getAttribute("current-user");
		if(currentUser == null || currentUser.getRole() != Role.MANAGER) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		String[] uriSplit = request.getRequestURI().split("/");
		int hotelIndex = -1;
		for(int i = 0; i < uriSplit.length; i++) {
			if(uriSplit[i].equals("hotel")) {
				hotelIndex = i;
				break;
			}
		}
		// /hotel/
		if(uriSplit.length == hotelIndex + 1) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		switch(uriSplit[hotelIndex + 1]) {
			case "add":
			{
				processGetAddHotel(request, response);
				break;
			}
			case "update":
			{
				processGetUpdateHotel(request, response);
				break;
			}
			case "delete":
			{
				processGetDeleteHotel(request, response);
				break;
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		User currentUser = (User) request.getSession().getAttribute("current-user");
		if(currentUser == null || currentUser.getRole() != Role.MANAGER) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		String[] uriSplit = request.getRequestURI().split("/");
		int hotelIndex = -1;
		for(int i = 0; i < uriSplit.length; i++) {
			if(uriSplit[i].equals("hotel")) {
				hotelIndex = i;
				break;
			}
		}
		// /hotel/
		if(uriSplit.length == hotelIndex + 1) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		switch(uriSplit[hotelIndex + 1]) {
			case "add":
			{
				processPostAddHotel(request, response);
				break;
			}
			case "update":
			{
				processPostUpdateHotel(request, response);
				break;
			}
			case "delete":
			{
				processPostDeleteHotel(request, response);
				break;
			}
			
		}
		
	}

	private void processGetAddHotel(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/hotel/hotel_add.jsp").forward(request, response);
	}
	
	private void processGetUpdateHotel(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		String queryString = request.getQueryString();
		
		// go to page if no query string
		if(queryString == null) {
			List<Location> listLocation = hotel.getAvailableLocation();
			request.setAttribute("list-location", listLocation);
			request.getRequestDispatcher("/WEB-INF/jsp/hotel/hotel_update.jsp").forward(request, response);
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
					String[] firstParam = queryStringSplit[1].split("=");
					if(firstParam[0].equals("location")) {
						processGetHotelByLocation
							(request, response, Integer.parseInt(firstParam[1]));
					}
					else if(firstParam[0].equals("city")) {
						String[] secondParam = queryStringSplit[2].split("=");
						String[] thirdParam = queryStringSplit[3].split("=");
						if(secondParam[0].equals("state") && thirdParam[0].equals("zip")) {
							Location location = hotel.getLocation(firstParam[1], secondParam[1], thirdParam[1]);
							processGetHotelByLocation(request, response, location.getSeqNo());
						}
					}
					break;
				}
			}
		}
	}
	
	private void processGetDeleteHotel(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		String queryString = request.getQueryString();
		
		// go to page if no query string
		if(queryString == null) {
			List<Location> listLocation = hotel.getAvailableLocation();
			request.setAttribute("list-location", listLocation);
			request.getRequestDispatcher("/WEB-INF/jsp/hotel/hotel_delete.jsp").forward(request, response);
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
					String[] firstParam = queryStringSplit[1].split("=");
					if(firstParam[0].equals("location")) {
						processGetHotelByLocation
							(request, response, Integer.parseInt(firstParam[1]));
					}
					break;
				}
			}
		}
	}

	private void processPostAddHotel(HttpServletRequest request, HttpServletResponse response)
		throws IOException {
		if(!ServletFileUpload.isMultipartContent(request)) {return;}	// ensure it's multi part request
		FileUploadHelper fuh = new FileUploadHelper();
		
		List<FileItem> listItem = fuh.parseMultipartRequest(request);
		
		String name = listItem.get(0).getString();
		String address = listItem.get(1).getString();
		String city = listItem.get(2).getString();
		String state = listItem.get(3).getString();
		String zip = listItem.get(4).getString();
		String description = listItem.get(5).getString();
		List<String> listImage = null;
		
		List<FileItem> toBeUploaded = new ArrayList<>();
		for(int i = 6; i < listItem.size(); i++) {
			toBeUploaded.add(listItem.get(i));
		}
		
		List<String> listError = hotel.validateInput(name, address, city, state, zip);
		if(listError == null) {
			Hotel newHotel = hotel.addHotel(name, address, city, state, zip, description);
			listImage = fuh.uploadHotelImage(request.getServletContext(), toBeUploaded, newHotel.getSeqNo().intValue());
			newHotel.setListImage(listImage);
			
			hotel.addNewListImageHotel(newHotel.getSeqNo().intValue(), listImage);
			request.getSession().setAttribute("action", "add-hotel");
			response.sendRedirect(request.getContextPath() + "/success");
		}
		
	}
	
	private void processPostUpdateHotel(HttpServletRequest request, HttpServletResponse response)
		throws IOException {
		int hotelId = Integer.valueOf(request.getParameter("hotel-id"));
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String city = request.getParameter("city");
		String state = request.getParameter("state");
		String zip = request.getParameter("zip");
		String description = request.getParameter("description");
		
		hotel.updateHotel(hotelId, name, address, city, state, zip, description);
		request.getSession().setAttribute("action", "update-hotel");
		response.sendRedirect(request.getContextPath() + "/success");
			
	}
	
	private void processPostDeleteHotel(HttpServletRequest request, HttpServletResponse response)
		throws IOException {
		int hotelId = Integer.valueOf(request.getParameter("hotel-id"));
		
		hotel.deleteHotel(hotelId);
		
		request.getSession().setAttribute("action", "delete-hotel");
		response.sendRedirect(request.getContextPath() + "/success");
			
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
}
