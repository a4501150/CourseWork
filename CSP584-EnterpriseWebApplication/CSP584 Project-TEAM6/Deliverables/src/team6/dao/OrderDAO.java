package team6.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import team6.entity.CustomerProfile;
import team6.entity.Hotel;
import team6.entity.Order;
import team6.entity.OrderStatus;
import team6.entity.RoomType;
import team6.entity.User;

public class OrderDAO {

	private Connection conn;
	private HotelDAO hotelDao = new HotelDAO();
	private UserDAO userDao = new UserDAO();
	public OrderDAO() {
		conn = MySQLDatabaseOperator.INSTANCE.getConnection();
	}

	public Integer selectCustomerProfile(String firstName, String lastName, String phone, String email, String address,
			String city, String state, String zip, String creditCardNum, String expirationDate) {
		String sql = "SELECT seq_no from csp584_project.customer_profile"
					+ " WHERE first_name = ? AND last_name = ? AND email = ?"
					+ " AND phone = ? AND address = ? AND city = ?"
					+ " AND state = ? AND zip = ? AND cc_num = ?"
					+ " AND cc_exp = ?"
		;
		Integer result = null;
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, firstName);
			ps.setString(2, lastName);
			ps.setString(3, email);
			ps.setString(4, phone);
			ps.setString(5, address);
			ps.setString(6, city);
			ps.setString(7, state);
			ps.setString(8, zip);
			ps.setString(9, creditCardNum);
			ps.setString(10, expirationDate);
			ResultSet rs = ps.executeQuery();
			
			if(rs.isBeforeFirst()) {
				rs.next();
				result = Integer.valueOf(rs.getInt("seq_no"));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return result;
	}

	/**
	 * Insert into customer_profile
	 * return seq_no of the new entry 
	 */
	public Integer insertCustomerProfile(CustomerProfile customer) {
		String sql = "INSERT INTO csp584_project.customer_profile"
					+ "(first_name, last_name, email"
					+ ", phone, address, city"
					+ ", state, zip, cc_num, cc_exp)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);"
		;
		Integer result = null;
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, customer.getFirstName());
			ps.setString(2, customer.getLastName());
			ps.setString(3, customer.getEmail());
			ps.setString(4, customer.getPhone());
			ps.setString(5, customer.getAddress());
			ps.setString(6, customer.getCity());
			ps.setString(7, customer.getState());
			ps.setString(8, customer.getZip());
			ps.setString(9, customer.getCreditCardNum());
			ps.setString(10, customer.getExpirationDate());
			ps.execute();
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		sql = "SELECT LAST_INSERT_ID()";
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ResultSet rs = ps.executeQuery();
			rs.next();
			result = Integer.valueOf(rs.getInt(1));
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}		
		return result;
	}

	public void insertOrder(Order order) {
		String sql = "INSERT INTO csp584_project.order"
				+ "(user, customer, hotel"
				+ ", room, order_date, check_in"
				+ ", check_out, price, status)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);"
		;
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, order.getUser().getuId());
			ps.setInt(2, order.getCustomer().getSeqNo());
			ps.setInt(3, order.getHotel().getSeqNo());
			ps.setInt(4, order.getRoomType().getSeqNo());
			ps.setDate(5, Date.valueOf(order.getOrderDate()));
			ps.setTimestamp(6, Timestamp.valueOf(order.getCheckInDateTime()));
			ps.setTimestamp(7, Timestamp.valueOf(order.getCheckOutDateTime()));
			ps.setDouble(8, order.getPrice().doubleValue());
			ps.setString(9, order.getStatus().toString());
			ps.execute();
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public void updateOrder(Order order) {
		String sql = "UPDATE `csp584_project`.`order` SET customer = ?, room = ?, check_in = ?, check_out = ?, price = ?, status = ? WHERE seq_no= ?;";
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, order.getCustomer().getSeqNo());
			ps.setInt(2, order.getRoomType().getSeqNo());
			ps.setTimestamp(3, Timestamp.valueOf(order.getCheckInDateTime()));
			ps.setTimestamp(4, Timestamp.valueOf(order.getCheckOutDateTime()));
			ps.setDouble(5, order.getPrice().doubleValue());
			ps.setString(6, order.getStatus().toString());
			ps.setInt(7, order.getSeqNo());
			ps.execute();
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public void deleteOrder(int seqNo) {
		String sql = "UPDATE `csp584_project`.`order` SET del_flag = 1 WHERE seq_no = ?;";
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, seqNo);
			ps.execute();
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public List<Order> selectAllOrder() {
		String sql = "SELECT * from csp584_project.order WHERE del_flag = 0";
		List<Order> listOrder = null;
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ResultSet rs = ps.executeQuery();
			if(rs.isBeforeFirst()) {
				listOrder = new ArrayList<>();
			}
			while(rs.next()) {
				Order order = buildOrderObject(rs);
				listOrder.add(order);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		if(listOrder != null) {
			for(Order o: listOrder) {
				o.setUser(userDao.selectUser(o.getUser().getuId().intValue()));
				populateCustomerProfile(o.getCustomer());
				hotelDao.populateRoomType(o.getRoomType());
				o.setHotel(o.getRoomType().getHotelBelong());
			}
		}
		
		return listOrder;
	}

	public List<Order> selectOrder(User user) {
		String sql = "SELECT * from csp584_project.order WHERE user = ? AND del_flag = 0";
		List<Order> listOrder = null;
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, user.getuId().intValue());
			ResultSet rs = ps.executeQuery();
			if(rs.isBeforeFirst()) {
				listOrder = new ArrayList<>();
			}
			while(rs.next()) {
				Order order = buildOrderObject(rs);
				listOrder.add(order);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		if(listOrder != null) {
			for(Order o: listOrder) {
				o.setUser(user);
				populateCustomerProfile(o.getCustomer());
				hotelDao.populateRoomType(o.getRoomType());
				o.setHotel(o.getRoomType().getHotelBelong());
			}
		}
		
		return listOrder;
	}

	public Order selectOrder(int seqNo) {
		String sql = "SELECT * from csp584_project.order WHERE seq_no = ?";
		Order order = null;
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, seqNo);
			ResultSet rs = ps.executeQuery();
			if(rs.isBeforeFirst()) {
				order = new Order();
			}
			while(rs.next()) {
				order = buildOrderObject(rs);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		if(order != null) {
			
			order.setUser(userDao.selectUser(order.getUser().getuId().intValue()));
			populateCustomerProfile(order.getCustomer());
			hotelDao.populateRoomType(order.getRoomType());
			order.setHotel(order.getRoomType().getHotelBelong());
		}
		
		return order;
	}

	public List<Order> selectOrderByHotel(int hotel) {
		String sql = "SELECT * from csp584_project.order WHERE hotel = ? AND del_flag = 0";
		List<Order> listOrder = null;
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, hotel);
			ResultSet rs = ps.executeQuery();
			if(rs.isBeforeFirst()) {
				listOrder = new ArrayList<>();
			}
			while(rs.next()) {
				Order order = buildOrderObject(rs);
				listOrder.add(order);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		if(listOrder != null) {
			for(Order o: listOrder) {
				o.setUser(userDao.selectUser(o.getUser().getuId().intValue()));
				populateCustomerProfile(o.getCustomer());
				hotelDao.populateRoomType(o.getRoomType());
				o.setHotel(o.getRoomType().getHotelBelong());
			}
		}
		
		return listOrder;
	}

	public List<Order> selectOrderByStatus(int hotel, OrderStatus status) {
		String sql = "SELECT * from csp584_project.order WHERE hotel = ? AND status = ? AND del_flag = 0";
		List<Order> listOrder = null;
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, hotel);
			ps.setString(2, status.toString());
			ResultSet rs = ps.executeQuery();
			if(rs.isBeforeFirst()) {
				listOrder = new ArrayList<>();
			}
			while(rs.next()) {
				Order order = buildOrderObject(rs);
				listOrder.add(order);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		if(listOrder != null) {
			for(Order o: listOrder) {
				o.setUser(userDao.selectUser(o.getUser().getuId().intValue()));
				populateCustomerProfile(o.getCustomer());
				hotelDao.populateRoomType(o.getRoomType());
				o.setHotel(o.getRoomType().getHotelBelong());
			}
		}
		
		return listOrder;
	}

	public List<Order> selectOrderByCheckInDateTime(int hotelId, LocalDateTime checkInDateTime) {
		String sql = "SELECT * from csp584_project.order WHERE hotel = ? AND check_in = ? AND status = 'PLACED' AND del_flag = 0";
		List<Order> listOrder = null;
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, hotelId);
			ps.setTimestamp(2, Timestamp.valueOf(checkInDateTime));
			ResultSet rs = ps.executeQuery();
			if(rs.isBeforeFirst()) {
				listOrder = new ArrayList<>();
			}
			while(rs.next()) {
				Order order = buildOrderObject(rs);
				listOrder.add(order);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		if(listOrder != null) {
			for(Order o: listOrder) {
				o.setUser(userDao.selectUser(o.getUser().getuId().intValue()));
				populateCustomerProfile(o.getCustomer());
				hotelDao.populateRoomType(o.getRoomType());
				o.setHotel(o.getRoomType().getHotelBelong());
			}
		}
		
		return listOrder;
	}

	/**
	 * Populate customer profile given seq_no
	 */
	private void populateCustomerProfile(CustomerProfile customer) {
		String sql = "SELECT * from csp584_project.customer_profile WHERE seq_no = ?";
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, customer.getSeqNo().intValue());
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			customer.setFirstName(rs.getString("first_name"));
			customer.setLastName(rs.getString("last_name"));
			customer.setEmail(rs.getString("email"));
			customer.setPhone(rs.getString("phone"));
			customer.setAddress(rs.getString("address"));
			customer.setCity(rs.getString("city"));
			customer.setState(rs.getString("state"));
			customer.setZip(rs.getString("zip"));
			customer.setCreditCardNum(rs.getString("cc_num"));
			customer.setExpirationDate(rs.getString("cc_exp"));
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
	}

	/**
	 * Build order object from SQL ResultSet 
	 */
	private Order buildOrderObject(ResultSet rs) {
		Order order = new Order();
		try {
			order.setSeqNo(rs.getInt("seq_no"));
			
			User user = new User();
			user.setuId(Integer.valueOf(rs.getInt("user")));
			order.setUser(user);
			
			CustomerProfile cp = new CustomerProfile();
			cp.setSeqNo(Integer.valueOf(rs.getInt("customer")));
			order.setCustomer(cp);
			
			Hotel hotel = new Hotel();
			hotel.setSeqNo(Integer.valueOf(rs.getInt("hotel")));
			order.setHotel(hotel);
			
			RoomType rt = new RoomType();
			rt.setSeqNo(Integer.valueOf(rs.getInt("room")));
			order.setRoomType(rt);
			
			order.setOrderDate(rs.getDate("order_date").toLocalDate());
			order.setCheckInDateTime(rs.getTimestamp("check_in").toLocalDateTime());
			order.setCheckOutDateTime(rs.getTimestamp("check_out").toLocalDateTime());
			order.setPrice(rs.getDouble("price"));
			order.setStatus(OrderStatus.valueOf(rs.getString("status")));
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		
		return order;
	}
	
}
