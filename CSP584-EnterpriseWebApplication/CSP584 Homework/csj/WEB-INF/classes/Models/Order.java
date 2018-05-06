package Models;

import java.time.LocalDate;

public class Order {
	

	Product product;
	int ConfirNumber;
	String status;
	String Warranty;
	int zipcode;
	
	LocalDate  expectedDeliverDate; 
	LocalDate  orderPlaceDate;
	
	public Order(Product product, int confirNumber, String status, LocalDate expectedDeliverDate, LocalDate orderPlaceDate) {
		
		this.product = product;
		ConfirNumber = confirNumber;
		this.status = status;
		this.expectedDeliverDate = expectedDeliverDate;
		this.orderPlaceDate = orderPlaceDate;
	}
	
	
	public Order() {
		
	}


	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getConfirNumber() {
		return ConfirNumber;
	}
	public void setConfirNumber(int confirNumber) {
		ConfirNumber = confirNumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDate getExpectedDeliverDate() {
		return expectedDeliverDate;
	}
	public void setExpectedDeliverDate(LocalDate expectedDeliverDate) {
		this.expectedDeliverDate = expectedDeliverDate;
	}
	public LocalDate getOrderPlaceDate() {
		return orderPlaceDate;
	}
	public void setOrderPlaceDate(LocalDate orderPlaceDate) {
		this.orderPlaceDate = orderPlaceDate;
	}


	public String getWarranty() {
		return Warranty;
	}


	public void setWarranty(String warranty) {
		Warranty = warranty;
	}


	public int getZipcode() {
		return zipcode;
	}


	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}

	
	
	
}
