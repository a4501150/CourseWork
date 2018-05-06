package org.pop.lf2u.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.pop.lf2u.configuration.AppConfiguration;
import org.pop.lf2u.configuration.AppInitializer;
import org.pop.lf2u.model.Catalog;
import org.pop.lf2u.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { AppConfiguration.class, AppInitializer.class })
public class ProductServiceTest {
	
	@Autowired
	ProductService productService;
	@Autowired
	FarmerService farmerService;
	@Autowired
	CatalogService catalogService;

	@Test
	public void testAdd() {
		List<Product> products = productService.getProducts();
		Catalog add2 = catalogService.add(new Catalog(null,"tomato"));
		Product product = new Product("202",null,add2.getGcpid(),"name","note","10-01","", 0.29f,"lb",null);
		Product add = productService.add(product);
		assertEquals(add.getFid(), "202");
		assert(products.size() > 0);
		
		product = productService.add(new Product("202",null,add2.getGcpid(),"name3","note3","10-03","", 2.29f,"lb","image2"));
		assert(products.size() > 2);
	}

	@Test
	public void testFindById() {
		Catalog add2 = catalogService.add(new Catalog(null,"tomato"));
		
		productService.add(new Product("202",null,add2.getGcpid(),"name1","note1","10-01","", 0.29f,"lb","image"));
		productService.add(new Product("203",null,add2.getGcpid(),"name2","note2","10-02","", 1.29f,"lb",null));
		productService.add(new Product("202",null,add2.getGcpid(),"name3","note3","10-03","", 2.29f,"lb","image2"));
		
		
		assertNull(productService.findById("202", "126"));
		assertNotNull(productService.findById("202", "3"));
	}

	@Test
	public void testFindByFid() {
		Catalog add2 = catalogService.add(new Catalog(null,"tomato"));
		
		productService.add(new Product("202",null,add2.getGcpid(),"name1","note1","10-01","", 0.29f,"lb","image"));
		productService.add(new Product("203",null,add2.getGcpid(),"name2","note2","10-02","", 1.29f,"lb",null));
		productService.add(new Product("202",null,add2.getGcpid(),"name3","note3","10-03","", 2.29f,"lb","image2"));
		
		
		assert(productService.findByFid("203").size()>0);
		assert(productService.findByFid("201").size()==0);
		assert(productService.findByFid("202").size()>0);
	}

	@Test
	public void testUpdate() {
		productService.add(new Product("202",null,"125","name1","note1","10-01","", 0.29f,"lb","image"));
		productService.add(new Product("203",null,"126","name2","note2","10-02","", 1.29f,"lb",null));
		Product product = productService.add(new Product("202",null,"127","name3","note3","10-03","", 2.29f,"lb","image2"));
		
		product.setPrice(1.11f);
		assert(productService.update(product.getFid(), product.getFspid(), product));
		
		product = productService.findById(product.getFid(), product.getFspid());
		
		assert(product.getPrice() == 1.11f);
		
	}

	@Test
	public void testGetProducts() {
		ProductService productService = new ProductService();
		List<Product> products = productService.getProducts();
		assert(products.size() == 0);
	}
}
