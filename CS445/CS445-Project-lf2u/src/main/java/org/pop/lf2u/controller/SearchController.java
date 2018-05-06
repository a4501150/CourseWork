package org.pop.lf2u.controller;

import java.util.List;

import org.pop.lf2u.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

	@Autowired
	SearchService searchService;
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ResponseEntity<List<?>> getReports(String topic,String key) {
		
		if(!searchService.isContainsTopic(topic)) return new ResponseEntity<List<?>>(HttpStatus.NOT_FOUND);
		
		
		return new ResponseEntity<List<?>>(searchService.search(topic, key),HttpStatus.OK);
	}
	
}
