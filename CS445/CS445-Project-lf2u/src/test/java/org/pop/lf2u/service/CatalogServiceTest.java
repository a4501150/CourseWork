package org.pop.lf2u.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.pop.lf2u.model.Catalog;
import org.pop.lf2u.service.CatalogService;

public class CatalogServiceTest {

	@Test
	public void testCatalogService() {
		CatalogService catalogService = new CatalogService();

		assert (catalogService != null);
	}

	@Test
	public void testGetAllCatalogs() {
		CatalogService catalogService = new CatalogService();

		List<Catalog> allCatalogs = catalogService.getAllCatalogs();
		assert (allCatalogs != null);
		assert (allCatalogs.size() == 0);
	}

	@Test
	public void testAdd() {
		CatalogService catalogService = new CatalogService();
		catalogService.add(new Catalog(null, "cata1"));
		catalogService.add(new Catalog(null, "cata2"));
		catalogService.add(new Catalog(null, "cata3"));

		assert (catalogService.getAllCatalogs().size() == 3);
	}

	@Test
	public void testUpdate() {
		CatalogService catalogService = new CatalogService();
		catalogService.add(new Catalog(null, "cata1"));
		Catalog catalog = catalogService.add(new Catalog(null, "cata1"));

		catalogService.update(catalog.getGcpid(), "cata2");

		assert(catalogService.getAllCatalogs().get(1).getName().equals("cata2"));
		assert(!catalogService.update("-1", "cata2"));
	}
	
	@Test
	public void testFind() {
		CatalogService catalogService = new CatalogService();
		catalogService.add(new Catalog(null, "cata1"));
		catalogService.add(new Catalog(null, "cata2"));
		catalogService.add(new Catalog(null, "cata3"));
		Assert.assertNull(catalogService.find("aaa"));
		Assert.assertNotNull(catalogService.find("1"));
	}

}
