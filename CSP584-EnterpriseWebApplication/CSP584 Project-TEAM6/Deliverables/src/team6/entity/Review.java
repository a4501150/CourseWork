package team6.entity;

import java.time.LocalDate;

public class Review {

	private String hotelName;
	private String hotelAddress;
	private String hotelCity;
	private String hotelState;
	private String hotelZip;
	private String roomName;
	private LocalDate checkInDate;
	private LocalDate checkOutDate;
	private String firstName;
	private String lastName;
	private Double rating;
	private LocalDate reviewDate;
	private String comment;
	
	public Review() {
		
	}
	
	public Review(String hotelName, String hotelAddress, String hotelCity, String hotelState, String hotelZip,
			String roomName, LocalDate checkInDate, LocalDate checkOutDate, String firstName, String lastName,
			Double rating, LocalDate reviewDate, String comment) {
		this.hotelName = hotelName;
		this.hotelAddress = hotelAddress;
		this.hotelCity = hotelCity;
		this.hotelState = hotelState;
		this.hotelZip = hotelZip;
		this.roomName = roomName;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.firstName = firstName;
		this.lastName = lastName;
		this.rating = rating;
		this.reviewDate = reviewDate;
		this.comment = comment;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getHotelAddress() {
		return hotelAddress;
	}

	public void setHotelAddress(String hotelAddress) {
		this.hotelAddress = hotelAddress;
	}

	public String getHotelCity() {
		return hotelCity;
	}

	public void setHotelCity(String hotelCity) {
		this.hotelCity = hotelCity;
	}

	public String getHotelState() {
		return hotelState;
	}

	public void setHotelState(String hotelState) {
		this.hotelState = hotelState;
	}

	public String getHotelZip() {
		return hotelZip;
	}

	public void setHotelZip(String hotelZip) {
		this.hotelZip = hotelZip;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public LocalDate getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(LocalDate checkInDate) {
		this.checkInDate = checkInDate;
	}

	public LocalDate getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(LocalDate checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public LocalDate getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(LocalDate reviewDate) {
		this.reviewDate = reviewDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
