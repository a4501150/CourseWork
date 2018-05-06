package org.pop.lf2u.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {
	
	@JsonIgnore
	private String fid;
	private String fspid;
	@NotEmpty
	private String gcpid;
	private String name;
	private String note;
	private String start_date;
	private String end_date;
	@NotNull
	private Float price;
	@NotEmpty
	private String product_unit;
	private String image;
	
	public Product() {
	}

	
	/**
	 * @param fid
	 * @param fspid
	 * @param gcpid
	 * @param name
	 * @param note
	 * @param start_date
	 * @param end_date
	 * @param price
	 * @param product_unit
	 * @param image
	 */
	public Product(String fid, String fspid, String gcpid, String name, String note, String start_date, String end_date,
			Float price, String product_unit, String image) {
		super();
		this.fid = fid;
		this.fspid = fspid;
		this.gcpid = gcpid;
		this.name = name;
		this.note = note;
		this.start_date = start_date;
		this.end_date = end_date;
		this.price = price;
		this.product_unit = product_unit;
		this.image = image;
	}


	public Product(String fid2, String fspid2) {
		this.fid = fid2;
		this.fspid = fspid2;
	}


	public Product(Product product) {
		this.fid = product.getFid();
		this.fspid = product.getFspid();
		this.gcpid = product.getGcpid();
		this.name = product.getName();
		this.note = product.getNote();
		this.start_date = product.getStart_date();
		this.end_date = product.getEnd_date();
		this.price = product.getPrice();
		this.product_unit = product.getProduct_unit();
		this.image = product.getImage();
	}


	/**
	 * @return the fid
	 */
	public String getFid() {
		return fid;
	}

	/**
	 * @param fid the fid to set
	 */
	public void setFid(String fid) {
		this.fid = fid;
	}

	/**
	 * @return the fspid
	 */
	public String getFspid() {
		return fspid;
	}

	/**
	 * @param fspid the fspid to set
	 */
	public void setFspid(String fspid) {
		this.fspid = fspid;
	}

	/**
	 * @return the gcpid
	 */
	public String getGcpid() {
		return gcpid;
	}

	/**
	 * @param gcpid the gcpid to set
	 */
	public void setGcpid(String gcpid) {
		this.gcpid = gcpid;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @return the start_date
	 */
	public String getStart_date() {
		return start_date;
	}

	/**
	 * @param start_date the start_date to set
	 */
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	/**
	 * @return the end_date
	 */
	public String getEnd_date() {
		return end_date;
	}

	/**
	 * @param end_date the end_date to set
	 */
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	/**
	 * @return the price
	 */
	public Float getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(Float price) {
		this.price = price;
	}

	/**
	 * @return the product_unit
	 */
	public String getProduct_unit() {
		return product_unit;
	}

	/**
	 * @param product_unit the product_unit to set
	 */
	public void setProduct_unit(String product_unit) {
		this.product_unit = product_unit;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return String.format(
				"Product [fid=%s, fspid=%s, gcpid=%s, name=%s, note=%s, start_date=%s, end_date=%s, price=%s, product_unit=%s, image=%s]",
				fid, fspid, gcpid, name, note, start_date, end_date, price, product_unit, image);
	}
	
	

}
