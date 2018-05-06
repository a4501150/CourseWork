package org.pop.lf2u.model.report;

public class FarmerReportInfo {
	
	private String fid;
	private String name;
	private Integer orders_placed;
	private Integer orders_delivered;
	private Integer orders_open;
	private Integer orders_cancelled;
	private Float products_revenue;
	private Float delivery_revenue;
	private Float lftu_fees;
	private Float payable_to_farm;
	

	public FarmerReportInfo() {

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
	 * @return the orders_placed
	 */
	public Integer getOrders_placed() {
		return orders_placed;
	}


	/**
	 * @param orders_placed the orders_placed to set
	 */
	public void setOrders_placed(Integer orders_placed) {
		this.orders_placed = orders_placed;
	}


	/**
	 * @return the orders_delivered
	 */
	public Integer getOrders_delivered() {
		return orders_delivered;
	}


	/**
	 * @param orders_delivered the orders_delivered to set
	 */
	public void setOrders_delivered(Integer orders_delivered) {
		this.orders_delivered = orders_delivered;
	}


	/**
	 * @return the orders_open
	 */
	public Integer getOrders_open() {
		return orders_open;
	}


	/**
	 * @param orders_open the orders_open to set
	 */
	public void setOrders_open(Integer orders_open) {
		this.orders_open = orders_open;
	}


	/**
	 * @return the orders_cancelled
	 */
	public Integer getOrders_cancelled() {
		return orders_cancelled;
	}


	/**
	 * @param orders_cancelled the orders_cancelled to set
	 */
	public void setOrders_cancelled(Integer orders_cancelled) {
		this.orders_cancelled = orders_cancelled;
	}


	/**
	 * @return the products_revenue
	 */
	public Float getProducts_revenue() {
		return products_revenue;
	}


	/**
	 * @param products_revenue the products_revenue to set
	 */
	public void setProducts_revenue(Float products_revenue) {
		this.products_revenue = products_revenue;
	}


	/**
	 * @return the delivery_revenue
	 */
	public Float getDelivery_revenue() {
		return delivery_revenue;
	}


	/**
	 * @param delivery_revenue the delivery_revenue to set
	 */
	public void setDelivery_revenue(Float delivery_revenue) {
		this.delivery_revenue = delivery_revenue;
	}


	/**
	 * @return the lftu_fees
	 */
	public Float getLftu_fees() {
		return lftu_fees;
	}


	/**
	 * @param lftu_fees the lftu_fees to set
	 */
	public void setLftu_fees(Float lftu_fees) {
		this.lftu_fees = lftu_fees;
	}


	/**
	 * @return the payable_to_farm
	 */
	public Float getPayable_to_farm() {
		return payable_to_farm;
	}


	/**
	 * @param payable_to_farm the payable_to_farm to set
	 */
	public void setPayable_to_farm(Float payable_to_farm) {
		this.payable_to_farm = payable_to_farm;
	}
	
	

}
