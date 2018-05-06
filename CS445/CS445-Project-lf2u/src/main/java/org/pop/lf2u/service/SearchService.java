package org.pop.lf2u.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.pop.lf2u.common.Utils;
import org.pop.lf2u.model.Customer;
import org.pop.lf2u.model.Farmer;
import org.pop.lf2u.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SearchService {
	
	@Autowired
	private FarmerService farmerService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private OrderService orderService;
	
	private List<String> topics = new ArrayList<String>(Arrays.asList("farm","customer","order"));
	
	public List<?> search(String topic,String keyword) {
		List<Object> result = new ArrayList<Object>();
		if("farm".equals(topic)){
			if(StringUtils.isEmpty(keyword)) return farmerService.getFarmers();
			List<Farmer> farmers = farmerService.getFarmers();
			for (Farmer farmer : farmers) {
				if(Utils.containKeyword(farmer, keyword)) result.add(farmer);
			}
		} else if("customer".equals(topic)){
			if(StringUtils.isEmpty(keyword)) return customerService.getCustomers();
			List<Customer> customers = customerService.getCustomers();
			for (Customer customer : customers) {
				if(Utils.containKeyword(customer, keyword)) result.add(customer);
			}
		} else if("order".equals(topic)){
			if(StringUtils.isEmpty(keyword)) return orderService.getOrders();
			List<Order> orders = orderService.getOrders();
			for (Order order : orders) {
				if(Utils.containKeyword(order, keyword)) result.add(order);
			}
		} 
		return result;
	}
	
	public boolean isContainsTopic(String topic){
		return topics.contains(topic.toLowerCase());
	}
}
