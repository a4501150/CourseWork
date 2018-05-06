	package org.pop.lf2u.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.pop.lf2u.common.Utils;
import org.pop.lf2u.model.Farmer;
import org.pop.lf2u.model.Order;
import org.pop.lf2u.model.Product;
import org.pop.lf2u.model.farmer.FarmInfo;
import org.pop.lf2u.model.order.Detail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

	@Autowired
	ProductService productService;
	@Autowired
	FarmerService farmerService;

	private List<Order> orders = new ArrayList<Order>();

	public Order add(Order order) {
		String lastId = null;
		if (orders.size() > 0) lastId = orders.get(orders.size() - 1).getOid();
		String newId = Utils.generateId(lastId);
		order.setOid(newId);

		// set orderdetail
		Float total = 0f;
		String fid = order.getFid();
		List<Detail> details = order.getOrder_detail();
		for (Detail detail : details) {
			Product product = productService.findById(fid, detail.getFspid());
			if (product == null)
				continue;
			detail.setName(product.getName());
			detail.setPrice(product.getPrice() + " per " + product.getProduct_unit());
			detail.setLine_item_total(Utils.formatFloat(product.getPrice() * Float.parseFloat(detail.getAmount())));
			detail.setAmount(detail.getAmount() + " " + product.getProduct_unit());
			total += detail.getLine_item_total();
		}
		// set farm info
		Farmer farmer = farmerService.findById(fid);
		//TODO: null check
		FarmInfo farminfo = new FarmInfo(fid, farmer.getFarm_info());
		order.setFarm_info(farminfo);
		// set price
		order.setProducts_total(Utils.formatFloat(total));
		order.setDelivery_charge(farmer.getFarm_info().getDelivery_charge());
		order.setOrder_total(order.getProducts_total() + order.getDelivery_charge());
		// set status
		order.setStatus("placed");
		//set date
		order.setOrder_date(new Date());
		order.setPlanned_delivery_date(Utils.getTomorrowDate(new Date()));
		orders.add(order);

		return order;
	}

	public List<Order> findByCId(String cid) {
		List<Order> result = new ArrayList<Order>();
		for (Order order : orders) {
			if (order.getCid().equals(cid))
				result.add(order);
		}
		return result;
	}

	public boolean cancel(String cid, String oid) {
		Order findByOId = findByIds(oid);
		findByOId.setStatus("cancelled");
		findByOId.setCancel_date(new Date());
		return true;
	}

	public boolean deliver(String oid) {
		Order findByOId = findByIds(oid);
		findByOId.setStatus("delivered");
		findByOId.setActual_delivery_date(new Date());
		return true;
	}

	/**
	 * @return the orders
	 */
	public List<Order> getOrders() {
		return orders;
	}

	public Order findByIds(String oid) {
		for (Order order : orders) {
			if (order.getOid().equals(oid))
				return order;
		}
		return null;
	}

}
