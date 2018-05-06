package org.pop.lf2u.model;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Catalog {

	private String gcpid;
	@NotEmpty
	private String name;
	
	public Catalog() {

	}

	/**
	 * @param gcpid
	 * @param name
	 */
	public Catalog(String gcpid, String name) {
		super();
		this.gcpid = gcpid;
		this.name = name;
	}

	public Catalog(String gcpid2) {
		this.gcpid = gcpid2;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Catalog [gcpid=%s, name=%s]", gcpid, name);
	}

}
