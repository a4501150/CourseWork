package org.pop.lf2u.model;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;
import org.pop.lf2u.model.farmer.Farm;
import org.pop.lf2u.model.farmer.Personal;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Farmer {

	private String fid;
	
	@Valid
	private Farm farm_info;
	
	@Valid
	private Personal personal_info;

	@NotEmpty
	private List<String> delivers_to;
	

	public Farmer() {
	}
	
	/**
	 * @param fid
	 * @param farm_info
	 * @param personal_info
	 * @param delivers_to
	 */
	public Farmer(String fid, Farm farm_info, Personal personal_info, List<String> delivers_to) {
		super();
		this.fid = fid;
		this.farm_info = farm_info;
		this.personal_info = personal_info;
		this.delivers_to = delivers_to;
	}


	public Farmer(String fid) {
		this.fid = fid;
	}

	public Farmer(String fid2, String name) {
		this.fid = fid2;
		this.farm_info = new Farm(name, null,null, null);
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
	 * @return the farm_info
	 */
	public Farm getFarm_info() {
		return farm_info;
	}


	/**
	 * @param farm_info the farm_info to set
	 */
	public void setFarm_info(Farm farm_info) {
		this.farm_info = farm_info;
	}


	/**
	 * @return the personal_info
	 */
	public Personal getPersonal_info() {
		return personal_info;
	}


	/**
	 * @param personal_info the personal_info to set
	 */
	public void setPersonal_info(Personal personal_info) {
		this.personal_info = personal_info;
	}


	/**
	 * @return the delivers_to
	 */
	public List<String> getDelivers_to() {
		return delivers_to;
	}


	/**
	 * @param delivers_to the delivers_to to set
	 */
	public void setDelivers_to(List<String> delivers_to) {
		this.delivers_to = delivers_to;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Farmer [fid=%s, farm_info=%s, personal_info=%s, delivers_to=%s]", fid, farm_info,
				personal_info, delivers_to);
	}

	
}
