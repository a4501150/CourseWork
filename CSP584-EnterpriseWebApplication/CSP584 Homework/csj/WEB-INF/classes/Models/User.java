package Models;

import java.util.ArrayList;

public class User {

	

	
	int UID;
	String username;
	String password;
	String email;
	ArrayList<Order> orders;
	ArrayList<Product> charts;
	
	
	public User(int uID, String username, String password, String email) {
		UID = uID;
		this.username = username;
		this.password = password;
		this.email = email;
		this.orders = new ArrayList<Order>();
		this.charts = new ArrayList<Product>();
	}
	
	public int getUID() {
		return UID;
	}
	public void setUID(int uID) {
		UID = uID;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public ArrayList<Order> getOrders() {
		return orders;
	}
	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}
	public ArrayList<Product> getCharts() {
		return charts;
	}
	public void setCharts(ArrayList<Product> charts) {
		this.charts = charts;
	}
	
	
}
