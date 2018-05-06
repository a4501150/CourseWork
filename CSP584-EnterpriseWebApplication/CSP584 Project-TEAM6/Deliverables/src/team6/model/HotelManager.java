package team6.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import team6.dao.HotelDAO;
import team6.entity.BedType;
import team6.entity.Hotel;
import team6.entity.Location;
import team6.entity.Order;
import team6.entity.OrderStatus;
import team6.entity.RoomAssign;
import team6.entity.RoomType;

public class HotelManager {

	private HotelDAO hotelDao = new HotelDAO();
	private OrderManager om = new OrderManager();

	/**
	 * Input validation. Return a list of error
	 */
	public List<String> validateInput(String name, String address, String city, String state, String zip) {
		List<String> listError = new ArrayList<>();
		
		// check duplicate address
		Location location = hotelDao.selectLocation(city, state, zip);
		if(location != null) {	// location already registered
			Hotel hotel = hotelDao.selectHotel(address, location);
			if(hotel != null) {
				listError.add("Address already has a hotel.");
			}
		}
		
		return (listError.size() == 0 ? null : listError);
	}

	public Hotel addHotel(String name, String address, String city, String state, String zip, String description) {
		Hotel hotel = new Hotel(name, address, city, state, zip, description);
		hotelDao.insertHotel(hotel);
		return hotel;
	}

	public void updateHotel(int hotelId, String name, String address, String city, String state, String zip, String description) {
		Hotel hotel = new Hotel(name, address, city, state, zip, description);
		hotel.setSeqNo(Integer.valueOf(hotelId));
		hotelDao.updateHotel(hotel);
	}

	public void deleteHotel(int hotelId) {
		hotelDao.deleteHotel(hotelId);
	}

	public List<Location> getAvailableLocation() {
		return hotelDao.selectAllLocation();
	}

	public List<Hotel> getAvailableHotel() {
		return hotelDao.selectAllHotel();
	}

	/**
	 * Get all available hotel in an Location 
	 */
	public List<Hotel> getAvailableHotel(int locationId) {
		return hotelDao.selectHotelByLocation(locationId);
	}

	public List<Integer> getAvailableRoomNumber(RoomType roomType, LocalDateTime from) {
		return hotelDao.selectAvailableRoomNumber(roomType.getSeqNo(), from);
	}

	public List<RoomType> getAvailableRoomType() {
		return hotelDao.selectAllRoomType();
	}

	public List<RoomType> getListRoomType(int hotelId) {
		return hotelDao.selectRoomTypeByHotel(hotelId);
	}

	public Integer addRoomType(int hotelId, RoomType paramObject) {
		return hotelDao.insertRoomType(hotelId, paramObject);
	}

	public void addRoom(int hotelId, int roomNum, int roomTypeId, double price, double discount) {
		hotelDao.insertRoom(hotelId, roomNum, roomTypeId, price, discount);
	}

	public boolean isRoomExist(int hotelId, String roomName) {
		RoomType roomType = hotelDao.selectRoomType(hotelId, roomName);
		return (roomType != null ? true : false);
	}

	public void updateRoomType(RoomType rt) {
		hotelDao.updateRoomType(rt);
	}

	public void deleteRoomType(int roomId) {
		hotelDao.deleteRoomType(roomId);
	}

	public Location getLocation(String city, String state, String zip) {
		return hotelDao.selectLocation(city, state, zip);
	}

	/**
	 * Search hotels by <checkin, checkout, city, state>
	 */
	public Map<Hotel, Boolean> doSearch(String city, String state, LocalDate checkIn, LocalDate checkOut) {
		// TODO: filter by current order
		List<Hotel> listHotel = hotelDao.selectHotelByLocation(city, state);
		Map<Hotel, Boolean> mapHotelRoomAvail = null;
		if(listHotel != null) {
			mapHotelRoomAvail = new LinkedHashMap<>();
			for(Hotel h: listHotel) {
				mapHotelRoomAvail.put(h, Boolean.TRUE);
			}
		}
		return mapHotelRoomAvail;
	}

	/**
	 * Do assign room:
	 * - Set status of order to CHECK_IN
	 * - Log transaction into room_assign 
	 */
	public void processAssignRoom(int orderId, int roomNum) {
		Order order = om.getOrder(orderId);
		order.setStatus(OrderStatus.CHECKED_IN);
		om.updateOrder(order);
		
		hotelDao.insertRoomAssign(order.getUser(), order.getRoomType(), roomNum, order.getCheckInDateTime(), order.getCheckOutDateTime());
	}

	/**
	 * Do check out room:
	 * - Set status of order to CHECK_OUT
	 * - Set check-out time to current date on room_assign entry 
	 */
	public void processCheckOutRoom(int orderId) {
		Order order = om.getOrder(orderId);
		order.setStatus(OrderStatus.CHECKED_OUT);
		om.updateOrder(order);
		
		RoomAssign ra = hotelDao.selectRoomAssign(order.getUser(), order.getRoomType(), order.getCheckInDateTime());
		ra.setCheckOut(LocalDateTime.now());
		hotelDao.updateRoomAssign(ra);
			
	}

	public void addNewListImageHotel(int hotelId, List<String> listImage) {
		hotelDao.updateHotelListImage(hotelId, listImage);
	}

	public void addNewRoomImage(int roomId, String image) {
		hotelDao.updateRoomImage(roomId, image);
	}

	public List<String> getListBedType() {
		List<String> listBedType = new ArrayList<>();
		for(BedType bt: BedType.values()) {
			String value = bt.toString().toLowerCase();
			String[] valueSplit = value.split("_");
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < valueSplit.length; i++) {
				if(valueSplit[i].length() > 1) {
					valueSplit[i] = valueSplit[i].substring(0, 1).toUpperCase() + valueSplit[i].substring(1);
				}
				else if(valueSplit[i].length() == 1) {
					valueSplit[i] = valueSplit[i].toUpperCase();
				}
				sb.append(valueSplit[i])
				.append(i == valueSplit.length - 1 ? "" : " ");
			}
			listBedType.add(sb.toString());
		}
		return listBedType;
	}

	/**
	 * Compare list of room number to existing DB.
	 * Return a list of room number that belongs to another room type.
	 */
	public List<Integer> getListRoomExist(int hotelId, List<Integer> toCheck) {
		List<Integer> listRoomInHotel = hotelDao.selectRoomListByHotel(hotelId);
		toCheck.retainAll(listRoomInHotel == null ? new ArrayList<Integer>() : listRoomInHotel);	// intersection of 2 set
		return (toCheck.size() == 0 ? null : toCheck);
	}

	public RoomType getRoomType(int roomTypeId) {
		return hotelDao.selectRoomType(roomTypeId);
	}

	public int calcDayBetween(LocalDate checkInDate, LocalDate checkOutDate) {
		int count = 0;
		LocalDate tmp = LocalDate.of(checkInDate.getYear(), checkInDate.getMonthValue(), checkInDate.getDayOfMonth());
		while(!tmp.isAfter(checkOutDate)) {
			count++;
			tmp = tmp.plusDays(1);
		}
		return count;
	}

	public Hotel getHotel(int hotelId) {
		return hotelDao.selectHotel(hotelId);
	}

}
