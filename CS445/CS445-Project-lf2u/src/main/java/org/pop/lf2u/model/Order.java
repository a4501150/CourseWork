package org.pop.lf2u.model;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;
import org.pop.lf2u.model.farmer.FarmInfo;
import org.pop.lf2u.model.order.Detail;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Order {
	
	private String oid;
	@NotEmpty
	private String fid;
	private String cid;
	@DateTimeFormat(pattern="yyyyMMdd")
	@JsonFormat(pattern="yyyyMMdd")
	private Date order_date;
	@DateTimeFormat(pattern="yyyyMMdd")
	@JsonFormat(pattern="yyyyMMdd")
	private Date planned_delivery_date;
	@DateTimeFormat(pattern="yyyyMMdd")
	@JsonFormat(pattern="yyyyMMdd")
	private Date actual_delivery_date;
	@JsonFormat(pattern="yyyyMMdd")
	private Date cancel_date;
	private String status;
	@NotEmpty
	private List<Detail> order_detail;
	private String delivery_note;
	private Float products_total;
	private Float delivery_charge;
	private Float order_total;
	private FarmInfo farm_info;
	
	public Order() {
		
	}

	public Order(String oid2) {
		this.oid = oid2;
	}

	public Order(String oid2, Date order_date2, Date planned_delivery_date2, Date actual_delivery_date2, String status2,
			String fid2) {
		this.oid = oid2;
		this.order_date = order_date2;
		this.planned_delivery_date = planned_delivery_date2;
		this.actual_delivery_date = actual_delivery_date2;
		this.status = status2;
		this.fid = fid2;
	}

	/**
	 * @return the oid
	 */
	public String getOid() {
		return oid;
	}

	/**
	 * @param oid the oid to set
	 */
	public void setOid(String oid) {
		this.oid = oid;
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
	 * @return the order_date
	 */
	public Date getOrder_date() {
		return order_date;
	}

	/**
	 * @param order_date the order_date to set
	 */
	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}

	/**
	 * @return the planned_delivery_date
	 */
	public Date getPlanned_delivery_date() {
		return planned_delivery_date;
	}

	/**
	 * @param planned_delivery_date the planned_delivery_date to set
	 */
	public void setPlanned_delivery_date(Date planned_delivery_date) {
		this.planned_delivery_date = planned_delivery_date;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the order_detail
	 */
	public List<Detail> getOrder_detail() {
		return order_detail;
	}

	/**
	 * @param order_detail the order_detail to set
	 */
	public void setOrder_detail(List<Detail> order_detail) {
		this.order_detail = order_detail;
	}

	/**
	 * @return the delivery_note
	 */
	public String getDelivery_note() {
		return delivery_note;
	}

	/**
	 * @param delivery_note the delivery_note to set
	 */
	public void setDelivery_note(String delivery_note) {
		this.delivery_note = delivery_note;
	}

	/**
	 * @return the products_total
	 */
	public Float getProducts_total() {
		return products_total;
	}

	/**
	 * @param products_total the products_total to set
	 */
	public void setProducts_total(Float products_total) {
		this.products_total = products_total;
	}

	/**
	 * @return the delivery_charge
	 */
	public Float getDelivery_charge() {
		return delivery_charge;
	}

	/**
	 * @param delivery_charge the delivery_charge to set
	 */
	public void setDelivery_charge(Float delivery_charge) {
		this.delivery_charge = delivery_charge;
	}

	/**
	 * @return the order_total
	 */
	public Float getOrder_total() {
		return order_total;
	}

	/**
	 * @param order_total the order_total to set
	 */
	public void setOrder_total(Float order_total) {
		this.order_total = order_total;
	}

	/**
	 * @return the farm_info
	 */
	public FarmInfo getFarm_info() {
		return farm_info;
	}

	/**
	 * @param farm_info the farm_info to set
	 */
	public void setFarm_info(FarmInfo farm_info) {
		this.farm_info = farm_info;
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
	 * @return the actual_delivery_date
	 */
	public Date getActual_delivery_date() {
		return actual_delivery_date;
	}

	/**
	 * @param actual_delivery_date the actual_delivery_date to set
	 */
	public void setActual_delivery_date(Date actual_delivery_date) {
		this.actual_delivery_date = actual_delivery_date;
	}

	/**
	 * @return the cancel_date
	 */
	public Date getCancel_date() {
		return cancel_date;
	}

	/**
	 * @param cancel_date the cancel_date to set
	 */
	public void setCancel_date(Date cancel_date) {
		this.cancel_date = cancel_date;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format(
				"Order [oid=%s, fid=%s, cid=%s, order_date=%s, planned_delivery_date=%s, actual_delivery_date=%s, status=%s, order_detail=%s, delivery_note=%s, products_total=%s, delivery_charge=%s, order_total=%s, farm_info=%s]",
				oid, fid, cid, order_date, planned_delivery_date, actual_delivery_date, status, order_detail,
				delivery_note, products_total, delivery_charge, order_total, farm_info);
	}

}
