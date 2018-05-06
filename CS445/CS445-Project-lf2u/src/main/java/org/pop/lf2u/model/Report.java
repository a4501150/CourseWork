package org.pop.lf2u.model;

import java.util.Date;
import java.util.List;

import org.pop.lf2u.model.report.FarmerReportInfo;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Report {
	
	private String frid;
	private String mrid;
	private String name;
	@DateTimeFormat(pattern="yyyyMMdd")
	@JsonFormat(pattern="yyyyMMdd")
	private Date start_date;
	@DateTimeFormat(pattern="yyyyMMdd")
	@JsonFormat(pattern="yyyyMMdd")
	private Date end_date;
	private Integer orders_placed;
	private Integer orders_delivered;
	private Integer orders_open;
	private Integer orders_cancelled;
	
	private Float products_revenue;
	private Float delivery_revenue;
	private List<Order> orders;
	private List<FarmerReportInfo> by_farmer;
	
	public Report() {

	}

	public Report(String frid, String name) {
		this.frid = frid;
		this.name = name;
	}

	/**
	 * @return the frid
	 */
	public String getFrid() {
		return frid;
	}

	/**
	 * @param frid the frid to set
	 */
	public void setFrid(String frid) {
		this.frid = frid;
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
	 * @return the start_date
	 */
	public Date getStart_date() {
		return start_date;
	}

	/**
	 * @param start_date the start_date to set
	 */
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	/**
	 * @return the end_date
	 */
	public Date getEnd_date() {
		return end_date;
	}

	/**
	 * @param end_date the end_date to set
	 */
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
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
	 * @return the orders
	 */
	public List<Order> getOrders() {
		return orders;
	}

	/**
	 * @param orders the orders to set
	 */
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	/**
	 * @return the by_farmer
	 */
	public List<FarmerReportInfo> getBy_farmer() {
		return by_farmer;
	}

	/**
	 * @param by_farmer the by_farmer to set
	 */
	public void setBy_farmer(List<FarmerReportInfo> by_farmer) {
		this.by_farmer = by_farmer;
	}

	/**
	 * @return the mrid
	 */
	public String getMrid() {
		return mrid;
	}

	/**
	 * @param mrid the mrid to set
	 */
	public void setMrid(String mrid) {
		this.mrid = mrid;
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
	
	
	
}
