package Models;

import java.util.Arrays;

public class Product implements java.io.Serializable{
	
	int id;
	String Catagory;
	String SubCatagory;
	String Name;
	double Price;
	String Image;
	String Manufacturer;
	String Condition;
	double Discont;
	String[] Accessory;
	
	
	public Product(int id, String catagory, String subCatagory, String name, double price, String image,
			String manufacturer, String condition, double discont, String[] accessory) {
		
		this.id = id;
		Catagory = catagory;
		SubCatagory = subCatagory;
		Name = name;
		Price = price;
		Image = image;
		Manufacturer = manufacturer;
		Condition = condition;
		Discont = discont;
		Accessory = new String[0];
	}
	
	public Product() {
		Accessory = new String[0];
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCatagory() {
		return Catagory;
	}
	public void setCatagory(String catagory) {
		Catagory = catagory;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public double getPrice() {
		return Price;
	}
	public void setPrice(double price) {
		Price = price;
	}
	public String getImage() {
		return Image;
	}
	public void setImage(String image) {
		Image = image;
	}
	public String getManufacturer() {
		return Manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		Manufacturer = manufacturer;
	}
	public String getCondition() {
		return Condition;
	}
	public void setCondition(String condition) {
		Condition = condition;
	}
	public double getDiscount() {
		return Discont;
	}
	public void setDiscount(double discont) {
		Discont = discont;
	}
	public String[] getAccessory() {
		return Accessory;
	}
	public void setAccessory(String[] accessory) {
		Accessory = accessory;
	}
	public String getSubCatagory() {
		return SubCatagory;
	}
	public void setSubCatagory(String subCatagory) {
		SubCatagory = subCatagory;
	}
	public void addAccessory(String a) {
		
		   String[] temp= new String[Accessory.length + 1];

		   for (int i = 0; i < Accessory.length; i++){
		      temp[i] = Accessory[i];
		   }
		   
		   temp[Accessory.length] = a;
		   Accessory = temp;
		
	}

	@Override
	public String toString() {
		return "<a href=\"ProductDetail?id="+ id +"\">Product [" + Name + ", Price: " + Price + ", Manufacturer: " + Manufacturer
				+ ", Condition: " + Condition + ", Discont: " + Discont + "</a>";
	}
	
	
	
	
}
