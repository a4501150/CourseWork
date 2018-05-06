package team6.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Order {
	private Integer seqNo;
	private User user;
	private CustomerProfile customer;
	private Hotel hotel;
	private RoomType roomType;
	private LocalDate orderDate;
	private LocalDateTime checkInDateTime;
	private LocalDateTime checkOutDateTime;
	private Double price;
	private OrderStatus status;
	
	public Integer getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public CustomerProfile getCustomer() {
		return customer;
	}
	public void setCustomer(CustomerProfile customer) {
		this.customer = customer;
	}
	public Hotel getHotel() {
		return hotel;
	}
	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
	public RoomType getRoomType() {
		return roomType;
	}
	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}
	public LocalDate getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}
	public LocalDateTime getCheckInDateTime() {
		return checkInDateTime;
	}
	public void setCheckInDateTime(LocalDateTime checkInDateTime) {
		this.checkInDateTime = checkInDateTime;
	}
	public LocalDateTime getCheckOutDateTime() {
		return checkOutDateTime;
	}
	public void setCheckOutDateTime(LocalDateTime checkOutDateTime) {
		this.checkOutDateTime = checkOutDateTime;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	
}
