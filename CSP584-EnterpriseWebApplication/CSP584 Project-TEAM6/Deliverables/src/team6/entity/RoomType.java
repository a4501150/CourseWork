package team6.entity;

import java.util.List;

public class RoomType {
	private Integer seqNo;
	private Hotel hotelBelong;
	private String name;
	private BedType bedType;
	private Integer bedAmount;
	private Integer peopleNo;
	private String view;
	private Boolean isWifi;
	private Boolean isTV;
	private Double price;
	private Double discount;
	private List<Integer> roomList;
	private String image;
	
	public Integer getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}
	public Hotel getHotelBelong() {
		return hotelBelong;
	}
	public void setHotelBelong(Hotel hotelBelong) {
		this.hotelBelong = hotelBelong;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BedType getBedType() {
		return bedType;
	}
	public void setBedType(BedType bedType) {
		this.bedType = bedType;
	}
	public Integer getBedAmount() {
		return bedAmount;
	}
	public void setBedAmount(Integer bedAmount) {
		this.bedAmount = bedAmount;
	}
	public Integer getPeopleNo() {
		return peopleNo;
	}
	public void setPeopleNo(Integer peopleNo) {
		this.peopleNo = peopleNo;
	}
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}
	public Boolean getIsWifi() {
		return isWifi;
	}
	public void setIsWifi(Boolean isWifi) {
		this.isWifi = isWifi;
	}
	public Boolean getIsTV() {
		return isTV;
	}
	public void setIsTV(Boolean isTV) {
		this.isTV = isTV;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public List<Integer> getRoomList() {
		return roomList;
	}
	public void setRoomList(List<Integer> roomList) {
		this.roomList = roomList;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
}
