package org.pop.lf2u.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.pop.lf2u.model.Order;
import org.pop.lf2u.service.CustomerService;
import org.pop.lf2u.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	CustomerService customerService;
	
	@RequestMapping(value = "/customers/{cid}/orders", method = RequestMethod.POST)
	public ResponseEntity<Order> create(@PathVariable String cid,@Valid @RequestBody Order order, BindingResult result, HttpServletResponse response) {
		
		logger.debug("order create",order.toString());
		
		if(customerService.findById(cid) == null) return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
		
		if(result.hasErrors()) return new ResponseEntity<Order>(HttpStatus.BAD_REQUEST);
		
		order.setCid(cid);
		order = orderService.add(order);
		Order newOrder = new Order(order.getOid());
		
		String location = String.format("/customers/%s/orders/%s", cid, order.getOid());
		response.setHeader("Location", location);
		
		return new ResponseEntity<Order>(newOrder,HttpStatus.CREATED) ;
	}
	
	@RequestMapping(value = {"/customers/{cid}/orders"}, method = RequestMethod.GET )
	public ResponseEntity<List<Order>> findByCid(@PathVariable("cid") String cid) {
		
		logger.debug("order find by cid",cid);
		
		if(customerService.findById(cid) == null) return new ResponseEntity<List<Order>>(HttpStatus.NOT_FOUND);

		List<Order> findByCId = orderService.findByCId(cid);
		List<Order> result = new ArrayList<Order>();
		for (Order order : findByCId) {
			result.add(new Order(order.getOid(),order.getOrder_date(),order.getPlanned_delivery_date(),order.getActual_delivery_date(),order.getStatus(),order.getFid()));
		}
		
		return new ResponseEntity<List<Order>>(result,HttpStatus.OK);
	}
	
	@RequestMapping(value = {"/customers/{cid}/orders/{oid}"}, method = RequestMethod.GET )
	public ResponseEntity<Order> findByIds(@PathVariable("cid") String cid,@PathVariable("oid") String oid) {
		
		logger.debug("order find by cid and oid",cid,oid);
		
		Order order = orderService.findByIds(oid);
		if(order == null) return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<Order>(order,HttpStatus.OK);
	}
	
	@RequestMapping(value = {"/customers/{cid}/orders/{oid}"}, method = RequestMethod.POST )
	public ResponseEntity<Void> cancel(@PathVariable("cid") String cid,@PathVariable("oid") String oid, @RequestBody Order order,HttpServletResponse response) {
		
		logger.debug("cancel order",cid,oid);
		if(order==null || StringUtils.isEmpty(order.getStatus())) return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		String status = order.getStatus();
		if(!status.equals("cancelled")) return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		
		Order dbOrder = orderService.findByIds(oid);
		if(dbOrder == null) return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		
		orderService.cancel(cid, oid);
		
		String location = String.format("/customers/%s/orders/%s", cid, oid);
		response.setHeader("Location", location);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value = {"/delivery/{oid}"}, method = RequestMethod.POST )
	public ResponseEntity<Void> cancel(@PathVariable("oid") String oid, @RequestBody Order order,HttpServletResponse response) {
		
		if(order==null || StringUtils.isEmpty(order.getStatus())) return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		String status = order.getStatus();
		if(!status.equals("delivered")) return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		
		Order dbOrder = orderService.findByIds(oid);
		if(dbOrder == null) return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		
		orderService.deliver(oid);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	
}
