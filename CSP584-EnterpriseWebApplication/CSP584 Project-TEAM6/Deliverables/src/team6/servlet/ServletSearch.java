package team6.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import team6.entity.Hotel;
import team6.entity.Location;
import team6.model.HotelManager;

@WebServlet("/search")
public class ServletSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private HotelManager hotel = new HotelManager();
    private Gson gson = new Gson();
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String queryString = request.getQueryString();
		if(queryString == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		String[] queryStringSplit = queryString.split("&");
		String[] queryAction = queryStringSplit[0].split("=");
		if(queryAction[0].equals("action")) {
			switch(queryAction[1]) {
				case "searchHotel":
				{
					processSearchHotel(request, response);
					break;
				}
				case "searchLocation":
				{
					String[] inputParam = queryStringSplit[1].split("=");
					if(inputParam[0].equals("input") && inputParam.length == 2) {
						processSearchLocation(request, response, inputParam[1]);
					}
					
					break;
				}
			}
		}
	}

	private void processSearchLocation(HttpServletRequest request, HttpServletResponse response, String input)
		throws IOException {
		List<Location> listLocation = hotel.getAvailableLocation();
		Set<String> result = new HashSet<>();
		final int MAX_SEARCH = 6;
		input = input.toLowerCase();
		String[] inputSplit = input.replaceAll(" ", "").split(",");
		
		if(inputSplit.length > 0) {
			for(Location l: listLocation) {
				if(result.size() == MAX_SEARCH) {
					break;
				}
				if(inputSplit.length == 2) {
					if(l.getCity().toLowerCase().contains(inputSplit[0]) && l.getState().toLowerCase().contains(inputSplit[1])) {
						result.add(l.getCity() + ", " + l.getState());
					}
				}
				else if(inputSplit.length == 1) {
					if(l.getCity().toLowerCase().contains(inputSplit[0])) {
						result.add(l.getCity() + ", " + l.getState());
					}
				}
			}
		}
		String json = gson.toJson(result);
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);
	}

	private void processSearchHotel(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate checkIn = LocalDate.parse(request.getParameter("check-in-date"), dtf);
		LocalDate checkOut = LocalDate.parse(request.getParameter("check-out-date"), dtf);
		String location = request.getParameter("location");
		String[] locationSplit = location.split(", ");
		
		Map<Hotel, Boolean> listAvailableHotel = hotel.doSearch(locationSplit[0], locationSplit[1], checkIn, checkOut);
		if(listAvailableHotel != null) {
			request.setAttribute("search-result", listAvailableHotel);
		}
		request.setAttribute("query-location", location);
		request.setAttribute("query-check-in", checkIn);
		request.setAttribute("query-check-out", checkOut);
		request.getRequestDispatcher("/WEB-INF/jsp/search_result_hotel.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
