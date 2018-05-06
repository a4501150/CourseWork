package team6.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import team6.entity.Hotel;
import team6.entity.Location;
import team6.entity.Order;
import team6.entity.OrderStatus;
import team6.entity.RoomType;
import team6.servlet.ServletReport;

public class ReportManager {
	private HotelManager hm = new HotelManager();
	private OrderManager om = new OrderManager();
	
	public Map<Hotel, List<RoomType>> prepareHotelReportList() {
		Map<Hotel, List<RoomType>> mapHotelRoomType;
		List<Hotel> listHotel = hm.getAvailableHotel();
		List<RoomType> listRoomType = hm.getAvailableRoomType();
		mapHotelRoomType = new HashMap<>();
		for(Hotel h: listHotel) {
			List<RoomType> listRoomTypeHotel = new ArrayList<>();
			for(RoomType rt: listRoomType) {
				if(rt.getHotelBelong().getSeqNo().equals(h.getSeqNo())) {
					listRoomTypeHotel.add(rt);
				}
			}
			mapHotelRoomType.put(h, listRoomTypeHotel);
		}
		return mapHotelRoomType;
	}
	

	/**
	 *	Build a bar chart of <Location, Hotel> 
	 */
	public Map<String, Integer> prepareHotelReportBarchart() {
		Map<String, Integer> mapLocationHotelCount;
		List<Hotel> listHotel = hm.getAvailableHotel();
		List<Location> listLocation = hm.getAvailableLocation();
		
		mapLocationHotelCount = new HashMap<>();
		for(Location l: listLocation) {
			int count = 0;
			for(Hotel h: listHotel) {
				if(h.getLocation().getSeqNo().equals(l.getSeqNo())) {
					count++;
				}
			}
			mapLocationHotelCount.put(l.toString(), Integer.valueOf(count));
		}
		return mapLocationHotelCount;
	}

	public Map<Hotel, List<RoomType>> prepareHotelReportDiscount() {
		Map<Hotel, List<RoomType>> mapHotelRoomType;
		List<Hotel> listHotel = hm.getAvailableHotel();
		List<RoomType> listRoomType = hm.getAvailableRoomType();
		mapHotelRoomType = new HashMap<>();
		for(Hotel h: listHotel) {
			List<RoomType> listRoomTypeHotel = new ArrayList<>();
			for(RoomType rt: listRoomType) {
				if(rt.getHotelBelong().getSeqNo().equals(h.getSeqNo()) && (rt.getDiscount() != null && rt.getDiscount() > 0.0)) {
					listRoomTypeHotel.add(rt);
				}
			}
			mapHotelRoomType.put(h, listRoomTypeHotel);
		}
		return mapHotelRoomType;
	}
	
	public Map<Hotel, List<Order>> prepareSalesReportList() {
		List<Order> listOrder = om.getListAllOrder();
		List<Order> listCancelledOrder = new ArrayList<>();
		for(Order o: listOrder) {
			if(o.getStatus().equals(OrderStatus.CANCELLED)) {
				listCancelledOrder.add(o);
			}
		}
		listOrder.removeAll(listCancelledOrder);
		
		Map<Hotel, List<Order>> mapHotelOrder = new HashMap<>();
		Set<Hotel> listHotel = new HashSet<>();
		for(Order o: listOrder) {
			listHotel.add(o.getHotel());
		}
		
		for(Hotel h: listHotel) {
			List<Order> listHotelOrder = new ArrayList<>();
			for(Order o: listOrder) {
				if(o.getHotel().getSeqNo().equals(h.getSeqNo())) {
					listHotelOrder.add(o);
				}
			}
			
			mapHotelOrder.put(h, listHotelOrder);
		}
		return mapHotelOrder;
	}
	
	public Map<String, Double> prepareSalesReportBarchart() {
		List<Order> listOrder = om.getListAllOrder();
		List<Hotel> listHotel = hm.getAvailableHotel();
		
		Map<Hotel, List<Order>> mapHotelOrder = new HashMap<>();
		for(Hotel h: listHotel) {
			List<Order> listHotelOrder = new ArrayList<>();
			for(Order o: listOrder) {
				if(o.getHotel().getSeqNo().equals(h.getSeqNo())) {
					listHotelOrder.add(o);
				}
			}
			mapHotelOrder.put(h, (listHotelOrder.size() == 0 ? null : listHotelOrder));
		}
		
		Map<String, Double> mapHotelSales = new HashMap<>();
		for(Map.Entry<Hotel, List<Order>> entry: mapHotelOrder.entrySet()) {
			String key = entry.getKey().getName();
			List<Order> value = entry.getValue();
			if(value == null) {
				mapHotelSales.put(key, Double.valueOf(0.0));
				continue;
			}
			
			double totalSales = 0.0;
			for(Order o: value) {
				totalSales += o.getPrice().doubleValue();
			}
			mapHotelSales.put(key, Double.valueOf(totalSales));
		}
		return mapHotelSales;
	}
	
	public Map<LocalDate, Double> prepareSalesReportByDay() {
		List<Order> listOrder = om.getListAllOrder();
		List<Order> listCancelledOrder = new ArrayList<>();
		for(Order o: listOrder) {
			if(o.getStatus().equals(OrderStatus.CANCELLED)) {
				listCancelledOrder.add(o);
			}
		}
		listOrder.removeAll(listCancelledOrder);
		
		Set<LocalDate> setSalesDate = new HashSet<>();
		for(Order o: listOrder) {
			setSalesDate.add(o.getOrderDate());
		}
		Map<LocalDate, Double> mapDateSales = new HashMap<>();
		for(LocalDate date: setSalesDate) {
			double sales = 0.0;
			for(Order o: listOrder) {
				if(o.getOrderDate().equals(date)) {
					sales += o.getPrice().doubleValue();
				}
			}
			mapDateSales.put(date, Double.valueOf(sales));
		}
		
		// build new map sorted from current date descending
		LocalDate now = LocalDate.now();
		
		Map<LocalDate, Double> sortedMapDateSales = new LinkedHashMap<>();
		while(!mapDateSales.isEmpty()) {
			if(mapDateSales.containsKey(now)) {
				sortedMapDateSales.put(now, mapDateSales.get(now));
				mapDateSales.remove(now);
			}
			now = now.minusDays(1);
		}
		return sortedMapDateSales;
	}
}
