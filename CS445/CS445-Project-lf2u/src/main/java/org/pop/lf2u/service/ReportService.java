package org.pop.lf2u.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.pop.lf2u.common.Utils;
import org.pop.lf2u.model.Order;
import org.pop.lf2u.model.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

	@Autowired
	OrderService orderService;

	private Map<String, String> farmerReportMap = new HashMap<String, String>();
	private Map<String, String> managerReportMap = new HashMap<String, String>();

	public ReportService() {
		farmerReportMap.put("1", "Orders to deliver today");
		farmerReportMap.put("2", "Orders to deliver tomorrow");
		farmerReportMap.put("3", "Revenue report");
		farmerReportMap.put("4", "Orders delivery report");

		managerReportMap.put("1", "Orders placed today");
		managerReportMap.put("2", "Orders placed yesterday");
		managerReportMap.put("3", "Revenue for previous month");
		managerReportMap.put("4", "Revenue yesterday");
		managerReportMap.put("5", "Revenue yesterday by zip code");

	}

	public List<Report> getReportsByFarmer() {
		List<Report> reports = new ArrayList<Report>();
		for (Map.Entry<String, String> entry : farmerReportMap.entrySet()) {
			reports.add(new Report(entry.getKey(), entry.getValue()));
		}
		return reports;
	}

	public Report getReportDetailByFarmer(Report report) {
		String frid = report.getFrid();
		report.setName(farmerReportMap.get(frid));
		if ("1".equals(frid)) {
			report.setOrders(toDeliverOrders(frid, new Date()));
		} else if ("2".equals(frid)) {
			report.setOrders(toDeliverOrders(frid, Utils.getTomorrowDate(new Date())));
		} else if ("3".equals(frid)) {
			report = revenueReport(report);
		} else if ("4".equals(frid)) {
			report.setOrders(deliveredOrders(frid));
		}
		return report;
	}

	public List<Order> toDeliverOrders(String fid, Date date) {
		List<Order> orders = orderService.getOrders();
		List<Order> deliverOrders = new ArrayList<Order>();

		for (Order order : orders) {
			if (order.getFid().equals(fid)) {
				if (Utils.isSameDay(order.getPlanned_delivery_date(), date)) {
					deliverOrders.add(order);
				}
			}
		}
		return deliverOrders;
	}

	public List<Order> deliveredOrders(String fid) {
		List<Order> orders = orderService.getOrders();
		List<Order> deliverOrders = new ArrayList<Order>();

		for (Order order : orders) {
			if (order.getFid().equals(fid)) {
				if ("delivered".equals(order.getStatus()))
					deliverOrders.add(order);
			}
		}
		return deliverOrders;
	}

	public Report revenueReport(Report report) {
		Date start = report.getStart_date();
		Date end = report.getEnd_date();
		if (start == null && end == null)
			return report;

		List<Order> orders = orderService.getOrders();

		int placed = 0;
		int cancelled = 0;
		int delivered = 0;
		float products_revenue = 0;
		float delivery_revenue = 0;
		for (Order order : orders) {
			if (Utils.isBetween(start, end, order.getOrder_date())) {
				if ("placed".equals(order.getStatus()))
					placed += 1;
				if ("canceld".equals(order.getStatus()))
					cancelled += 1;
				if ("delivered".equals(order.getStatus()))
					delivered += 1;
			}
		}
		report.setOrders_cancelled(cancelled);
		report.setOrders_delivered(delivered);
		report.setOrders_placed(placed);
		report.setProducts_revenue(products_revenue);
		report.setDelivery_revenue(delivery_revenue);

		return report;
	}

	public List<Report> getReportsByManager() {
		List<Report> reports = new ArrayList<Report>();
		for (Map.Entry<String, String> entry : managerReportMap.entrySet()) {
			reports.add(new Report(entry.getKey(), entry.getValue()));
		}
		return reports;
	}

	public Report getReportDetailByManager(Report report) {
		String mrid = report.getMrid();
		report.setName(farmerReportMap.get(mrid));
		if ("1".equals(mrid)) {
			report = placedOrders(report, new Date());
		} else if ("2".equals(mrid)) {
			report = placedOrders(report, Utils.getTomorrowDate(new Date()));
		} else if ("3".equals(mrid)) {
			report = placedOrders(report);
		} else if ("4".equals(mrid)) {
			//TODO
		} else if ("5".equals(mrid)) {
			//TODO
		}
		return report;
	}

	public Report placedOrders(Report report,Date date) {
		List<Order> orders = orderService.getOrders();
		int placed = 0;
		int delivered = 0;
		int open = 0;
		int cancelled = 0;
		for (Order order : orders) {
			if (Utils.isSameDay(order.getOrder_date(), date)) {
				placed += 1;
			}
			if (Utils.isSameDay(order.getActual_delivery_date(), date)) {
				delivered += 1;
			}
			if (Utils.isSameDay(order.getCancel_date(), date)) {
				cancelled += 1;
			}
		}
		report.setOrders_placed(placed);
		report.setOrders_cancelled(cancelled);
		report.setOrders_delivered(delivered);
		report.setOrders_open(open);
		return report;
	}
	
	public Report placedOrders(Report report) {
		Date start = report.getStart_date();
		Date end = report.getEnd_date();
		List<Order> orders = orderService.getOrders();
		int placed = 0;
		int delivered = 0;
		int open = 0;
		int cancelled = 0;
		for (Order order : orders) {
			if (Utils.isBetween(start, end, order.getOrder_date())) {
				placed += 1;
			}
			if (Utils.isBetween(start, end, order.getActual_delivery_date())) {
				delivered += 1;
			}
			if (Utils.isBetween(start, end, order.getCancel_date())) {
				cancelled += 1;
			}
		}
		report.setOrders_placed(placed);
		report.setOrders_cancelled(cancelled);
		report.setOrders_delivered(delivered);
		report.setOrders_open(open);
		return report;
	}

	public boolean isContainsFrid(String frid) {
		return farmerReportMap.containsKey(frid);
	}

	public boolean isContainsMrid(String mrid) {
		return managerReportMap.containsKey(mrid);
	}

}
