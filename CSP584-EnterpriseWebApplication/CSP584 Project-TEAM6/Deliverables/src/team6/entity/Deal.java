package team6.entity;

import team6.entity.DealType;

public class Deal {
	private String tweet;
	private String hotelName;
	private DealType dealType;
	private Double amount;
	private String link;
	
	public String getTweet() {
		return tweet;
	}
	public void setTweet(String tweet) {
		this.tweet = tweet;
	}
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public DealType getDealType() {
		return dealType;
	}
	public void setDealType(DealType dealType) {
		this.dealType = dealType;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
}
