package org.pop.lf2u.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.pop.lf2u.model.Farmer;
import org.pop.lf2u.model.Product;
import org.pop.lf2u.service.FarmerService;
import org.pop.lf2u.service.ProductService;
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
public class ProductController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	ProductService productService;
	
	@Autowired
	FarmerService farmerService;

	@RequestMapping(value = "/farmers/{fid}/products", method = RequestMethod.GET)
	public ResponseEntity<List<Product>> listByFid(@PathVariable("fid") String fid) {
		
		logger.debug("product list by fid",fid);
		
		Farmer farmer = farmerService.findById(fid);
		if(farmer == null) return new ResponseEntity<List<Product>>(HttpStatus.NOT_FOUND);
		List<Product> products = productService.findByFid(fid);
		List<Product> result = new ArrayList<Product>();
		for (Product product : products) {
			Product product2 = new Product(product);
			product2.setGcpid(null);
			result.add(product2);
		}
		return new ResponseEntity<List<Product>>(result,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/farmers/{fid}/products", method = RequestMethod.POST)
	public ResponseEntity<Product> create(@PathVariable("fid") String fid, @Valid @RequestBody Product product, BindingResult result,HttpServletResponse response) {
		
		logger.debug("product create",product.toString());
		
		if(result.hasErrors()) return new ResponseEntity<Product>(HttpStatus.BAD_REQUEST);
		
		Farmer farmer = farmerService.findById(fid);
		if(farmer == null) return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
		
		product.setFid(fid);
		Product addProduct = productService.add(product);
		String location = String.format("/farmers/%s/products/%s", fid, addProduct.getFspid());
		response.setHeader("Location", location);
		
		Product resultProduct = new Product(addProduct.getFid(),addProduct.getFspid());
		return new ResponseEntity<Product>(resultProduct,HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/farmers/{fid}/products/{fspid}", method = RequestMethod.POST)
	public ResponseEntity<Void> update(@PathVariable("fid") String fid,@PathVariable("fspid") String fspid, @RequestBody Product product, HttpServletResponse response) {
		
		logger.debug("product update",product.toString());
		
		Product dbProduct = productService.findById(fid,fspid);
		if(dbProduct == null) return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		
		if(productService.update(fid, fspid, product)) {
			String location = String.format("/farmers/%s/products/%s", fid, fspid);
			response.setHeader("Location", location);
			
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/farmers/{fid}/products/{fspid}", method = RequestMethod.GET)
	public ResponseEntity<Product> findById(@PathVariable("fid") String fid,@PathVariable("fspid") String fspid,HttpServletResponse response) {
		
		logger.debug("product find by id",fid,fspid);
		
		Product dbProduct = productService.findById(fid,fspid);
		if(dbProduct == null) return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
		
		Product product = new Product(dbProduct);
		product.setGcpid(null);
		return new ResponseEntity<Product>(product,HttpStatus.OK);
	}
	
	
	
	
}
