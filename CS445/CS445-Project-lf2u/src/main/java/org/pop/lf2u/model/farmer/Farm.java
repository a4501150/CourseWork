package org.pop.lf2u.model.farmer;

import org.hibernate.validator.constraints.NotEmpty;
import org.pop.lf2u.common.Utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Farm {
	
	@NotEmpty
	private String name;
	@NotEmpty
	private String address;
	@NotEmpty
	private String phone;
	private String web;
	
	@JsonIgnore
	private Float delivery_charge = 0.00f;
	
	public Farm() {
	}
	
	/**
	 * @param name
	 * @param phone
	 * @param web
	 */
	public Farm(String name, String phone, String address,String web) {
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.web = web;
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
	 * @return the web
	 */
	public String getWeb() {
		return web;
	}

	/**
	 * @param web the web to set
	 */
	public void setWeb(String web) {
		this.web = web;
	}
	
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	

	/**
	 * @return the delivery_charge
	 */
	public Float getDelivery_charge() {
		return Utils.formatFloat(this.delivery_charge);
	}

	/**
	 * @param delivery_charge the delivery_charge to set
	 */
	public void setDelivery_charge(Float delivery_charge) {
		this.delivery_charge = delivery_charge;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Farm [name=%s, phone=%s, address=%s, web=%s]", name, phone, address, web);
	}
	
}
