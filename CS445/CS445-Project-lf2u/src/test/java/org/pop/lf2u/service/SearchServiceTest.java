package org.pop.lf2u.service;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.pop.lf2u.configuration.AppConfiguration;
import org.pop.lf2u.configuration.AppInitializer;
import org.pop.lf2u.model.Customer;
import org.pop.lf2u.model.Farmer;
import org.pop.lf2u.model.Order;
import org.pop.lf2u.model.order.Detail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { AppConfiguration.class, AppInitializer.class })
public class SearchServiceTest {

	@Autowired
	SearchService searchService;

	@Autowired
	OrderService orderService;

	@Autowired
	CustomerService customerService;
	
	@Autowired
	FarmerService farmerService;
	
	
	@Test
	public void testSearchService() {
		assert (searchService != null);
	}

	@Test
	public void testSearch() {
		assert (searchService.search("farm", "key").size() == 0);
		assert (searchService.search("customer", "key").size() == 0);
		assert (searchService.search("order", "key").size() == 0);
		
		farmerService.add(new Farmer(null, "farmer"));
		
		Order order = new Order(null,new Date(),null,null,"placed","1");
		order.setDelivery_note("note");
		order.setOrder_detail(new ArrayList<Detail>());
		orderService.add(order);
		
		customerService.add(new Customer(null, "customer", "street", "600013", "123-456-7890", "email@uu.com"));
		
		assert(searchService.search("xxx", "xxx").size()==0);
		assert(searchService.search("farm", "xxx").size()==0);
		assert(searchService.search("farm", "farmer").size()==1);
		
		assert(searchService.search("order", "xxx").size()==0);
		assert(searchService.search("order", "note1").size()==0);
		
		assert(searchService.search("customer", "xxx").size()==0);
		assert(searchService.search("customer", "street").size()==1);
	}

	@Test
	public void testIsContainsTopic() {
		assert (!searchService.isContainsTopic("sdas"));
		assert (searchService.isContainsTopic("order"));
	}
	
}
