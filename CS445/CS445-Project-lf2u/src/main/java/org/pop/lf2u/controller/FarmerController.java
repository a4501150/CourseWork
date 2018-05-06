package org.pop.lf2u.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.pop.lf2u.common.Utils;
import org.pop.lf2u.model.Farmer;
import org.pop.lf2u.model.farmer.FarmInfo;
import org.pop.lf2u.service.FarmerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FarmerController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	FarmerService farmerService;

	@RequestMapping(value = {"/farmers","/farmers/"}, method = RequestMethod.POST)
	public ResponseEntity<Farmer> create(@Valid @RequestBody Farmer farmer, BindingResult result) {
		
		logger.debug("create",farmer.toString());
		
		if(result.hasErrors()) return new ResponseEntity<Farmer>(HttpStatus.BAD_REQUEST);
		
		farmer = farmerService.add(farmer);
		Farmer newFarmer = new Farmer(farmer.getFid());
		
		return new ResponseEntity<Farmer>(newFarmer,HttpStatus.CREATED) ;
	}
	
	@RequestMapping(value = {"/farmers/{fid}"}, method = RequestMethod.PUT )
	public ResponseEntity<Void> update(@PathVariable("fid") String fid, @Valid @RequestBody Farmer farmer, BindingResult result) {
		
		logger.debug("update",fid,farmer);
		
		if(farmerService.findById(fid) == null) return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		if(result.hasErrors()) return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		if(farmerService.update(fid,farmer)) return new ResponseEntity<Void>(HttpStatus.OK);
		
		return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR) ;
	}
	
	@RequestMapping(value = "/farmers/{fid}", method = RequestMethod.GET)
	public ResponseEntity<Farmer> view(@PathVariable("fid") String fid) {
		
		logger.debug("view farmer fid",fid);
		
		Farmer farmer = farmerService.findById(fid);
		if(farmer == null) return new ResponseEntity<Farmer>(HttpStatus.NOT_FOUND);
		
		Farmer result = new Farmer();
		result.setFarm_info(farmer.getFarm_info());
		result.setDelivers_to(farmer.getDelivers_to());
		result.setPersonal_info(farmer.getPersonal_info());
		return new ResponseEntity<Farmer>(result,HttpStatus.OK) ;
	}
	
	@RequestMapping(value = {"/farmers/","/farmers"}, method = RequestMethod.GET)
	public ResponseEntity<List<FarmInfo>> searchByZip(String zip) {
		
		logger.debug("search farmers by zipcode",zip);
		
		List<Farmer> findByZip = farmerService.findByZip(zip);
		List<FarmInfo> result = new ArrayList<FarmInfo>();
		for (Farmer farmer : findByZip) {
			result.add(new FarmInfo(farmer.getFid(),farmer.getFarm_info().getName(),null,null,null));
		}
		
		return new ResponseEntity<List<FarmInfo>>(result,HttpStatus.OK) ;
	}
	
	@RequestMapping(value = "farmers/{fid}/delivery_charge", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Float>> getDeliveryCharge(@PathVariable String fid) {
		
		logger.debug("get farm's delivery charge",fid);
		
		Farmer farmer = farmerService.findById(fid);
		if(farmer == null) return new ResponseEntity<Map<String,Float>>(HttpStatus.NOT_FOUND) ;
		
		Map<String,Float> result = new HashMap<String, Float>();
		result.put("delivery_charge", Utils.formatFloat(farmer.getFarm_info().getDelivery_charge()));
		return new ResponseEntity<Map<String,Float>>(result,HttpStatus.OK) ;
	}
	
	@RequestMapping(value = "farmers/{fid}/delivery_charge", method = RequestMethod.POST)
	public ResponseEntity<Void> setDeliveryCharge(@PathVariable String fid,@RequestBody Map<String,Float> params, HttpServletRequest request, HttpServletResponse response) {
		
		logger.debug("set farm's delivery charge",fid);
		
		if(params.get("delivery_charge") == null ) return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		if(params.get("delivery_charge") < 0 ) return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		
		
		Farmer farmer = farmerService.findById(fid);
		if(farmer == null) return new ResponseEntity<Void>(HttpStatus.NOT_FOUND) ;
		
		farmer.getFarm_info().setDelivery_charge(Utils.formatFloat(params.get("delivery_charge")));
		
		String location = String.format("/admin/%s/delivery_charge", fid);

		response.setHeader("Location", location);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
}
