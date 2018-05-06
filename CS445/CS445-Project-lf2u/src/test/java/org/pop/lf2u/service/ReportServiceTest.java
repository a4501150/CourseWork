package org.pop.lf2u.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.pop.lf2u.common.Utils;
import org.pop.lf2u.configuration.AppConfiguration;
import org.pop.lf2u.configuration.AppInitializer;
import org.pop.lf2u.model.Farmer;
import org.pop.lf2u.model.Order;
import org.pop.lf2u.model.Product;
import org.pop.lf2u.model.Report;
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
public class ReportServiceTest {

	@Autowired
	ReportService reportService;
	@Autowired
	OrderService orderService;
	@Autowired
	ProductService productSerivce;
	@Autowired
	FarmerService farmerService;
	
	@Test
	public void testReportService() {
		assert(reportService != null);
		
		Product add = productSerivce.add(new Product("1", null, "1", "name", null, null, null, 1.0f, "lb", ""));
		farmerService.add(new Farmer(null, new Farm("1", "dsa", "asda", "dsad"), new Personal("name", "email", "123"), new ArrayList<String>()));
		Order order = new Order();
		Detail detail = new Detail();
		detail.setAmount("1.5");
		detail.setFspid(add.getFspid());
		List<Detail> details = new ArrayList<Detail>();
		details.add(detail);
		order.setOrder_detail(details);
		order.setFid("1");
		order.setStatus("placed");
		
		orderService.add(order);
		
	}

	@Test
	public void testGetReportsByFarmer() {
		ReportService reportService = new ReportService();
		assert(reportService.getReportsByFarmer().size() == 4);
	}

	@Test
	public void testGetReportDetailByFarmer() {
		Report report = new Report();
		assert(reportService.getReportDetailByFarmer(report) != null);
		report.setStart_date(new Date());
		report.setEnd_date(Utils.getTomorrowDate(new Date()));
		report.setFrid("1");
		reportService.getReportDetailByFarmer(report);
		
		report.setFrid("2");
		reportService.getReportDetailByFarmer(report);
		
		report.setFrid("3");
		reportService.getReportDetailByFarmer(report);
		
		report.setFrid("4");
		reportService.getReportDetailByFarmer(report);
		
		
		
	}

	@Test
	public void testGetReportsByManager() {
		ReportService reportService = new ReportService();
		assert(reportService.getReportsByManager().size() == 5);
	}

	@Test
	public void testGetReportDetailByManager() {
		Report report = new Report();
		assert(reportService.getReportDetailByManager(report) != null);
		
		report.setMrid("1");
		reportService.getReportDetailByManager(report);
		
		report.setMrid("2");
		reportService.getReportDetailByManager(report);
		
		report.setMrid("3");
		reportService.getReportDetailByManager(report);
		
		report.setMrid("4");
		reportService.getReportDetailByManager(report);
		
		report.setMrid("5");
		reportService.getReportDetailByManager(report);
	}

	@Test
	public void testIsContainsFrid() {
		ReportService reportService = new ReportService();
		assert(!reportService.isContainsFrid("000"));
		assert(reportService.isContainsFrid("1"));
	}

	@Test
	public void testIsContainsMrid() {
		ReportService reportService = new ReportService();
		assert(!reportService.isContainsMrid("000"));
		assert(reportService.isContainsMrid("1"));
	}

}
