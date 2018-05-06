package Models;

public class ClassicWatch extends Product {

	public ClassicWatch(int id, String catagory, String subCatagory, String name, double price, String image,
			String manufacturer, String condition, double discont, String[] accessory) {
		super(id, catagory, subCatagory, name, price, image, manufacturer, condition, discont, accessory);
	}

	public ClassicWatch() {
		super();
	}

}
