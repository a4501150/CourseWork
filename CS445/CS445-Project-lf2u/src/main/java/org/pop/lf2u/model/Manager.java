package org.pop.lf2u.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Manager {

	private String mid;
	private String name;
	private String created_by;
	@DateTimeFormat(pattern="yyyyMMdd")
	@JsonFormat(pattern="yyyyMMdd")
	private Date create_date;
	private String phone;
	private String email;

	public Manager() {
	}
	
	/**
	 * @param mid
	 * @param name
	 * @param created_by
	 * @param create_date
	 * @param phone
	 * @param email
	 */
	public Manager(String mid, String name, String created_by, Date create_date, String phone, String email) {
		super();
		this.mid = mid;
		this.name = name;
		this.created_by = created_by;
		this.create_date = create_date;
		this.phone = phone;
		this.email = email;
	}

	public Manager(String mid) {
		this.mid = mid;
	}

	/**
	 * @return the mid
	 */
	public String getMid() {
		return mid;
	}

	/**
	 * @param mid
	 *            the mid to set
	 */
	public void setMid(String mid) {
		this.mid = mid;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the created_by
	 */
	public String getCreated_by() {
		return created_by;
	}

	/**
	 * @param created_by
	 *            the created_by to set
	 */
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	/**
	 * @return the create_date
	 */
	public Date getCreate_date() {
		return create_date;
	}

	/**
	 * @param create_date
	 *            the create_date to set
	 */
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
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
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Manager [mid=%s, name=%s, created_by=%s, create_date=%s, phone=%s, email=%s]", mid, name,
				created_by, create_date, phone, email);
	}


}
