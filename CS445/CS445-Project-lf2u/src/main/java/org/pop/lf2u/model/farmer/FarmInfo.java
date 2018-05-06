package org.pop.lf2u.model.farmer;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FarmInfo {

	private String fid;
	private String name;
	private String phone;
	private String address;
	private String web;

	public FarmInfo() {
	}

	/**
	 * @param fid
	 * @param name
	 * @param phone
	 * @param web
	 */
	public FarmInfo(String fid, String name, String phone, String address,String web) {
		super();
		this.fid = fid;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.web = web;
	}

	public FarmInfo(String fid2, Farm farm_info) {
		this.fid = fid2;
		if(farm_info != null) {
			this.name = farm_info.getName();
			this.phone = farm_info.getPhone();
			this.address = farm_info.getAddress();
			this.web = farm_info.getPhone();
		}
	}

	/**
	 * @return the fid
	 */
	public String getFid() {
		return fid;
	}

	/**
	 * @param fid
	 *            the fid to set
	 */
	public void setFid(String fid) {
		this.fid = fid;
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
	 * @return the web
	 */
	public String getWeb() {
		return web;
	}

	/**
	 * @param web
	 *            the web to set
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("FarmInfo [fid=%s, name=%s, phone=%s, address=%s, web=%s]", fid, name, phone, address,
				web);
	}

}
