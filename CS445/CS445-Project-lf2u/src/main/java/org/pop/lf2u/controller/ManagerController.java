package org.pop.lf2u.controller;

import java.util.List;

import org.pop.lf2u.model.Manager;
import org.pop.lf2u.service.ManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ManagerController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	ManagerService managerService = new ManagerService();
	
	@RequestMapping(value = "/managers/accounts", method = RequestMethod.GET)
	public ResponseEntity<List<Manager>> listAll() {
		
		logger.debug("manager list all");
		
		List<Manager> allManagers = managerService.getAllManagers();
		
		return new ResponseEntity<List<Manager>>(allManagers,HttpStatus.OK) ;
	}
	
	@RequestMapping(value = "/managers/accounts/{mid}", method = RequestMethod.GET)
	public ResponseEntity<Manager> findByMid(@PathVariable("mid") String mid) {
		
		logger.debug("manager find by mid",mid);
		
		Manager manager = managerService.findById(mid);
		if(manager == null) return new ResponseEntity<Manager>(HttpStatus.NOT_FOUND) ;
		return new ResponseEntity<Manager>(manager,HttpStatus.OK) ;
	}
	
	

}
