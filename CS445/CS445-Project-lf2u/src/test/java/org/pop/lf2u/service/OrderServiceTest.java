package org.pop.lf2u.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.pop.lf2u.configuration.AppConfiguration;
import org.pop.lf2u.configuration.AppInitializer;
import org.pop.lf2u.model.Farmer;
import org.pop.lf2u.model.Order;
import org.pop.lf2u.model.Product;
import org.pop.lf2u.model.farmer.Farm;
import org.pop.lf2u.model.farmer.Personal;
import org.pop.lf2u.model.order.Detail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { AppConfiguration.class, AppInitializer.class })
public class OrderServiceTest {

	@Autowired
	OrderService orderService;
	@Autowired
	ProductService productSerivce;
	@Autowired
	FarmerService farmerService;
	
	@Test
	public void testOrderService() {
		OrderService orderService = new OrderService();
		assert(orderService.getOrders() != null);
		assert(orderService.getOrders().size() == 0);
	}

	@Test
	public void testAdd() {
		Product add = productSerivce.add(new Product("1", null, "1", "name", null, null, null, 1.0f, "lb", ""));
		List<Order> orders = orderService.getOrders();
		farmerService.add(new Farmer(null, new Farm("1", "dsa", "asda", "dsad"), new Personal("name", "email", "123"), new ArrayList<String>()));
		Order order = new Order();
		Detail detail = new Detail();
		detail.setAmount("1.5");
		detail.setFspid(add.getFspid());
		List<Detail> details = new ArrayList<Detail>();
		details.add(detail);
		order.setOrder_detail(details);
		order.setFid("1");
		
		orderService.add(order);
		
		assert(orders.size() >0);
	}

	@Test
	public void testFindByIds() {
		OrderService orderService = new OrderService();
		List<Order> orders = orderService.getOrders();
		
		for(int i=101;i<106;i++) {
			Order order = new Order();
			order.setOid(i+"");
			order.setCid((i+100) + "");
			orders.add(order);
		}
		
		assert(orderService.findByIds("000") == null);
		assert(orderService.findByIds("000") == null);
		Order order = orderService.findByIds("101");
		assert(order.getOid().equals("101"));
		assert(order.getCid().equals("201"));
		
	}

	@Test
	public void testFindByCId() {
		OrderService orderService = new OrderService();
		List<Order> orders = orderService.getOrders();
		
		for(int i=101;i<106;i++) {
			Order order = new Order();
			order.setOid(i+"");
			order.setCid("999");
			orders.add(order);
		}
		assert(orderService.findByCId("000").size() == 0);
		assert(orderService.findByCId("999").size() == 5);
	}

	@Test
	public void testCancel() {
		OrderService orderService = new OrderService();
		List<Order> orders = orderService.getOrders();
		
		Order order = new Order();
		order.setOid("111");
		order.setCid("999");
		order.setStatus("open");
		orders.add(order);
		
		assert(orderService.findByIds("111").getStatus().equals("open"));
		
		orderService.cancel("999", "111");
		
		assert(orderService.findByIds("111").getStatus().equals("cancelled"));
	}

	@Test
	public void testDeliver() {
		OrderService orderService = new OrderService();
		List<Order> orders = orderService.getOrders();
		
		Order order = new Order();
		order.setOid("111");
		order.setCid("999");
		order.setStatus("open");
		orders.add(order);
		
		assert(orderService.findByIds("111").getStatus().equals("open"));
		
		orderService.deliver("111");
		
		assert(orderService.findByIds("111").getStatus().equals("delivered"));
	}
	
	@Test
	public void getOrders() {
		OrderService orderService = new OrderService();
		List<Order> orders = orderService.getOrders();
		
		
		assert(orders.size() == 0);
	}

}
