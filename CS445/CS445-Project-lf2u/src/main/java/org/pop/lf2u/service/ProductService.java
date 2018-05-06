package org.pop.lf2u.service;

import java.util.ArrayList;
import java.util.List;

import org.pop.lf2u.common.Utils;
import org.pop.lf2u.model.Catalog;
import org.pop.lf2u.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ProductService {

	@Autowired
	private CatalogService catalogService;

	private List<Product> products = new ArrayList<Product>();

	public Product add(Product product) {
		String lastId = null;
		if (products.size() > 0)
			lastId = products.get(products.size() - 1).getFspid();
		String newId = Utils.generateId(lastId);
		product.setFspid(newId);

		// set start date and end date
		/*
		 * if(StringUtils.isEmpty(product.getStart_date())) {
		 * product.setStart_date("01-01"); }
		 * if(StringUtils.isEmpty(product.getEnd_date())) {
		 * product.setEnd_date("12-31"); }
		 */

		// set name
		if (catalogService != null) {
			Catalog catalog = catalogService.find(product.getGcpid());
			if (catalog != null)
				product.setName(catalog.getName());
		}
		products.add(product);

		return product;
	}

	public Product findById(String fid, String fspid) {
		for (Product product : products) {
			if (product.getFid().equals(fid) && product.getFspid().equals(fspid))
				return product;
		}
		return null;
	}

	public List<Product> findByFid(String fid) {
		List<Product> result = new ArrayList<Product>();
		for (Product product : products) {
			if (product.getFid().equals(fid))
				result.add(product);
		}
		return result;

	}

	public boolean update(String fid, String fspid, Product newProduct) {
		boolean result = false;

		for (Product product : products) {
			if (product.getFid().equals(fid) && product.getFspid().equals(fspid)) {
				if (!StringUtils.isEmpty(newProduct.getNote()))
					product.setNote(newProduct.getNote());
				if (!StringUtils.isEmpty(newProduct.getProduct_unit()))
					product.setProduct_unit(newProduct.getProduct_unit());
				if (!StringUtils.isEmpty(newProduct.getImage()))
					product.setImage(newProduct.getImage());
				/*
				 * if(StringUtils.isEmpty(product.getStart_date())) {
				 * product.setStart_date("01-01"); }
				 * if(StringUtils.isEmpty(product.getEnd_date())) {
				 * product.setEnd_date("12-31"); }
				 */
				if (!StringUtils.isEmpty(product.getStart_date())) {
					product.setStart_date(product.getStart_date());
				}
				if (!StringUtils.isEmpty(product.getEnd_date())) {
					product.setEnd_date(product.getEnd_date());
				}
				if (newProduct.getPrice() != null) {
					product.setPrice(newProduct.getPrice());
				}
				result = true;
			}
		}
		return result;
	}

	/**
	 * @return the products
	 */
	public List<Product> getProducts() {
		return products;
	}

}
