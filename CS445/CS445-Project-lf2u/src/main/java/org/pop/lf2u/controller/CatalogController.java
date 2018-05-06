package org.pop.lf2u.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.pop.lf2u.model.Catalog;
import org.pop.lf2u.service.CatalogService;
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
public class CatalogController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	CatalogService catalogService = new CatalogService();

	@RequestMapping(value = "/managers/catalog", method = RequestMethod.GET)
	public ResponseEntity<List<Catalog>> listAll() {
		
		logger.debug("catalog listAll");
		
		List<Catalog> allCatalogs = catalogService.getAllCatalogs();
		
		return new ResponseEntity<List<Catalog>>(allCatalogs,HttpStatus.OK) ;
	}
	
	@RequestMapping(value = "/managers/catalog", method = RequestMethod.POST)
	public ResponseEntity<Catalog> create(@Valid @RequestBody Catalog catalog, BindingResult result) {
		
		logger.debug("catalog create",catalog);
		
		if(result.hasErrors()) return new ResponseEntity<Catalog>(HttpStatus.BAD_REQUEST);
		
		catalog = catalogService.add(catalog);
		Catalog newCatalog = new Catalog(catalog.getGcpid());
		return new ResponseEntity<Catalog>(newCatalog,HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/managers/catalog/{gcpid}", method = RequestMethod.POST)
	public ResponseEntity<Void> update(@PathVariable("gcpid") String gcpid,@Valid @RequestBody Catalog catalog, BindingResult result,HttpServletResponse response) {
		
		logger.debug("catalog update",catalog);
		
		if(result.hasErrors()) return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		
		if(catalogService.update(gcpid, catalog.getName())) {
			
			//String location = String.format("/lf2u/admin/%s/delivery_charge", gcpid);
		//	response.setHeader("Location", location);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
	

}
