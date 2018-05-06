package org.pop.lf2u.service;

import java.util.ArrayList;
import java.util.List;

import org.pop.lf2u.common.Utils;
import org.pop.lf2u.model.Farmer;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class FarmerService {

	private List<Farmer> farmers = new ArrayList<Farmer>();
	
	public Farmer add(Farmer farmer) {
		String lastId = null;
		if(farmers.size() > 0) lastId = farmers.get(farmers.size()-1).getFid();
		String newId = Utils.generateId(lastId);
		
		farmer.setFid(newId);
		farmers.add(farmer);
		
		return farmer;
	}

	public boolean update(String fid,Farmer newFarmer) {
		for (Farmer farmer : farmers) {
			if(farmer.getFid().equals(fid)) {
				farmer.setDelivers_to(newFarmer.getDelivers_to());
				farmer.setFarm_info(newFarmer.getFarm_info());
				farmer.setPersonal_info(newFarmer.getPersonal_info());
				return true;
			}
		}
		return false;
	}
	
	public Farmer findById(String fid) {
		for (Farmer farmer : farmers) {
			if(farmer.getFid().equals(fid)) return farmer;
		}
		return null;
	}
	
	public List<Farmer> findByZip(String zipcode) {
		if(StringUtils.isEmpty(zipcode)){
			return getFarmers();
		}
		List<Farmer> result = new ArrayList<Farmer>();
		for (Farmer farmer : farmers) {
			if(farmer.getDelivers_to().contains(zipcode)){
				result.add(farmer);
			}
		}
		return result;
	}

	/**
	 * @return the farmers
	 */
	public List<Farmer> getFarmers() {
		return farmers;
	}
	

}
