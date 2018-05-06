package org.pop.lf2u.model;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Customer {
	
	private String cid;
	@NotEmpty
	private String name;
	@NotEmpty
	private String street;
	@NotEmpty
	private String zip;
	@NotEmpty
	private String phone;
	@NotEmpty
	private String email;
	
	public Customer() {
		
	}

	public Customer(String cid2) {
		this.cid = cid2;
	}
	
	/**
	 * @param cid
	 * @param name
	 * @param street
	 * @param zip
	 * @param phone
	 * @param email
	 */
	public Customer(String cid, String name, String street, String zip, String phone, String email) {
		super();
		this.cid = cid;
		this.name = name;
		this.street = street;
		this.zip = zip;
		this.phone = phone;
		this.email = email;
	}

	/**
	 * @return the cid
	 */
	public String getCid() {
		return cid;
	}

	/**
	 * @param cid the cid to set
	 */
	public void setCid(String cid) {
		this.cid = cid;
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
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @return the zip
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * @param zip the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Customer [cid=%s, name=%s, street=%s, zip=%s, phone=%s, email=%s]", cid, name, street,
				zip, phone, email);
	}

}
