package team6.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import team6.dao.HotelDAO;
import team6.entity.Deal;
import team6.entity.DealType;
import team6.entity.Hotel;
import team6.entity.RoomType;


public class DealMatches {
	private final String DEAL_FILE_PATH = "resources/DealMatches.txt";
	private HotelDAO hotelDao = new HotelDAO();
	/**
	 * Parse deal matches, apply deals to current hotel
	 */
	public void processDealMatch(ServletContext sc) {
		String dealFullFilePath = sc.getRealPath(DEAL_FILE_PATH);
		File dealFile = new File(dealFullFilePath);
		List<Deal> listDeal = new ArrayList<>();
		
		try(BufferedReader br = new BufferedReader
				(new InputStreamReader
						(new FileInputStream(dealFile), "UTF-8"))
		) {
			String line;
			while((line = br.readLine()) != null) {
				Deal deal = parseDeal(line);
				if(deal != null) {
					listDeal.add(deal);
				}
				
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		if(listDeal.size() == 0) {
			return;
		}
		
		Map<Deal, Hotel> mapDealListProduct = applyDeal(listDeal);
		if(mapDealListProduct == null || mapDealListProduct.size() == 0) {
			return;
		}
		sc.setAttribute("map-deal", mapDealListProduct);

	}

	private Deal parseDeal(String line) {
		Double amount = null;
		DealType dealType = null;
		String htmlLink = null;
		final String END_TOKEN = "#Deal";
		final String START_TOKEN = "Save";
		
		String[] lineSplit = line.split(" ");
		
		// Must start with start_token and end with end_tokens
		boolean format = 
				lineSplit[0].equals(START_TOKEN)
				&& (lineSplit[lineSplit.length - 1].equals(END_TOKEN) || lineSplit[lineSplit.length - 2].equals(END_TOKEN))
				&& lineSplit.length >= 5
		;
		if(format != true) {
			return null;
		}
		
		// must be a value with $ or %
		String value = lineSplit[1];
		if(value.substring(0,1).equals("$")) {	// $200			
			amount = Double.valueOf(value.substring(1, value.length()));
			dealType = DealType.REDUCTION;
		}
		else if(value.substring(value.length() - 1, value.length()).equals("%")) {
			// xx%
			amount = Double.valueOf(value.substring(0, value.length() - 1));
			dealType = DealType.PERCENTAGE;
		}
		int hotelIndex = -1;
		
		if(lineSplit[2].equals("when") && lineSplit[3].equals("booking") && lineSplit[4].equals("on")) {
			hotelIndex = 5;
		}
		else {
			return null;
		}
		
		StringBuilder hotelNameBuilder = new StringBuilder();
		for(int i = hotelIndex; i < lineSplit.length - 1 && !lineSplit[i].equals(END_TOKEN); i++) {
			hotelNameBuilder.append(lineSplit[i]);
			hotelNameBuilder.append(" ");
		}
		if(lineSplit[lineSplit.length - 1].contains("http")) {
			htmlLink = lineSplit[lineSplit.length - 1];
		}
		
		Deal deal = new Deal();
		deal.setAmount(amount);
		deal.setDealType(dealType);
		deal.setHotelName(hotelNameBuilder.toString());
		deal.setTweet(line);
		deal.setLink(htmlLink);
		
		return deal;
	}

	/**
	 * Apply found deal into all room in hotel, remove all existing discount.
	 * Return map of discounted hotel 
	 */
	private Map<Deal, Hotel> applyDeal(List<Deal> listDeal) {
		Map<Deal, Hotel> result = null;
		for(Deal deal: listDeal) {
			Hotel h = hotelDao.selectHotelByName(deal.getHotelName());
			if(h != null) {
				if(result == null) {
					result = new HashMap<>();
				}
				// apply deal
				List<RoomType> listRoomType = hotelDao.selectRoomTypeByHotel(h.getSeqNo().intValue());
				if(listRoomType != null) {
					switch(deal.getDealType()) {
						case PERCENTAGE:
						{
							for(RoomType rt: listRoomType) {
								double price = rt.getPrice().doubleValue();
								double discount = price * deal.getAmount().doubleValue() / 100;
								rt.setDiscount(Double.valueOf(discount));
							}
							break;
						}
						case REDUCTION:
						{
							for(RoomType rt: listRoomType) {
								rt.setDiscount(deal.getAmount());
							}
							break;
						}
					}
					for(RoomType rt: listRoomType) {
						hotelDao.updateRoomType(rt);
					}
					result.put(deal, h);
				}
			}
		}
		return result;
	}

}
