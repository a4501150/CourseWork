package org.pop.lf2u.service;

import java.util.ArrayList;
import java.util.List;

import org.pop.lf2u.common.Utils;
import org.pop.lf2u.model.Catalog;
import org.springframework.stereotype.Service;

@Service
public class CatalogService {

	private List<Catalog> catalogs = new ArrayList<Catalog>();
	
	public List<Catalog> getAllCatalogs() {
		return catalogs;
	}
	
	public Catalog add(Catalog catalog) {
		String lastId = null;
		if(catalogs.size() > 0) lastId = catalogs.get(catalogs.size()-1).getGcpid();
		String newId = Utils.generateId(lastId);
		catalog.setGcpid(newId);
		
		catalogs.add(catalog);
		
		return catalog;
	}
	
	public boolean update(String gcpid,String name) {
		for (Catalog catalog : catalogs) {
			if(catalog.getGcpid().equals(gcpid)) {
				catalog.setName(name);
				return true;
			}
		}
		return false;
	}
	
	public Catalog find(String gcpid) {
		for (Catalog catalog : catalogs) {
			if(catalog.getGcpid().equals(gcpid)) {
				return catalog;
			}
		}
		return null;
	}
	
	
}
