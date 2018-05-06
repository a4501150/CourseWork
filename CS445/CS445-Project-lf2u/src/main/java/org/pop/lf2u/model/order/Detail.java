package org.pop.lf2u.model.order;

import org.hibernate.validator.constraints.NotEmpty;

public class Detail {
	
	@NotEmpty
	private String fspid;
	@NotEmpty
	private String amount;
	
	private String name;
	
	private String price;
	
	private Float line_item_total;
	
	

	public Detail() {
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
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}



	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
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
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}



	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}



	/**
	 * @return the line_item_total
	 */
	public Float getLine_item_total() {
		return line_item_total;
	}



	/**
	 * @param line_item_total the line_item_total to set
	 */
	public void setLine_item_total(Float line_item_total) {
		this.line_item_total = line_item_total;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Detail [fspid=%s, amount=%s, name=%s, price=%s, line_item_total=%s]", fspid, amount, name,
				price, line_item_total);
	}

	
}
