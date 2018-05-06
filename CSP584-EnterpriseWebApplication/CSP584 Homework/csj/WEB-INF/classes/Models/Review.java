package Models;

public class Review {



	String date;
	String rate;
	String text;
	String productID;
	String username;
	
	String UserAge;
	String UserGender;
	String UserOccupation;
	
	public Review() {
		
	}
	
	public Review(String date, String rate, String text) {
		super();
		this.date = date;
		this.rate = rate;
		this.text = text;
	}
	
	public Review(String date, String rate, String text, String productID, String username, String userAge,
			String userGender, String userOccupation) {
		this.date = date;
		this.rate = rate;
		this.text = text;
		this.productID = productID;
		this.username = username;
		UserAge = userAge;
		UserGender = userGender;
		UserOccupation = userOccupation;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserAge() {
		return UserAge;
	}

	public void setUserAge(String userAge) {
		UserAge = userAge;
	}

	public String getUserGender() {
		return UserGender;
	}

	public void setUserGender(String userGender) {
		UserGender = userGender;
	}

	public String getUserOccupation() {
		return UserOccupation;
	}

	public void setUserOccupation(String userOccupation) {
		UserOccupation = userOccupation;
	}

}
