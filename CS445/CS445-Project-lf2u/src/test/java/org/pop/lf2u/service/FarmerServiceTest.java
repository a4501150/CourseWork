package org.pop.lf2u.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.pop.lf2u.model.Farmer;
import org.pop.lf2u.model.farmer.Farm;
import org.pop.lf2u.model.farmer.Personal;
import org.pop.lf2u.service.FarmerService;

public class FarmerServiceTest{

	
	@Test
	public void testAdd() {
		FarmerService farmerService = new FarmerService();
		List<Farmer> farmers = farmerService.getFarmers();
		assert(farmers.size() == 0);
		Farmer farmer = new Farmer("101",new Farm("farm_name", "847-346-2313","address", "www.uuu.com"),new Personal("personal_name", "email@e.com", "666-333-1231"),new ArrayList<String>(Arrays.asList("30001","30002")));
		farmers.add(farmer);
		assert(farmers.size() == 1);
		assert(farmers.get(0).equals(farmer));
		assert(farmers.get(0).getFid().equals(farmer.getFid()));
	}

	@Test
	public void testUpdate() 
	{
		FarmerService farmerService = new FarmerService();
		Farmer farmer = new Farmer(null,new Farm("farm_name_old", "847-346-2313","address", "www.uuu.com"),new Personal("personal_name1", "email@e.com", "666-333-1231"),new ArrayList<String>(Arrays.asList("30001","30002")));
		Farmer add = farmerService.add(farmer);
		add.getFarm_info().setName("farm_name_new");
		add.getPersonal_info().setName("personal_name_new");
		List<String> delivers_to = add.getDelivers_to();
		delivers_to.add("30003");
		
		farmerService.update(add.getFid(), add);
		
		List<Farmer> farmers = farmerService.getFarmers();
		assert(farmers.get(farmers.size()-1).getFarm_info().getName().equals("farm_name_new"));
		assert(farmers.get(farmers.size()-1).getPersonal_info().getName().equals("personal_name_new"));
		assert(farmers.get(farmers.size()-1).getDelivers_to().size() == 3);
		
		assert(!farmerService.update(null,add));
	}

	@Test
	public void testFindById() {
		FarmerService farmerService = new FarmerService();
		farmerService.add(new Farmer(null,new Farm("farm_name_1", "847-346-2313","address", "www.uuu.com"),new Personal("personal_name1", "email@e.com", "666-333-1231"),new ArrayList<String>(Arrays.asList("300001","300002"))));
		farmerService.add(new Farmer(null,new Farm("farm_name_2", "847-346-2313","address", "www.uuu.com"),new Personal("personal_name1", "email@e.com", "666-333-1231"),new ArrayList<String>(Arrays.asList("300001","300002"))));
		Farmer farmer = farmerService.add(new Farmer(null,new Farm("farm_name_3", "847-346-2313", "address","www.uuu.com"),new Personal("personal_name1", "email@e.com", "666-333-1231"),new ArrayList<String>(Arrays.asList("300001","300002"))));
		
		assert(farmer.equals(farmerService.findById(farmer.getFid())));
		assert(farmerService.findById("-1") == null);
		
		
	}

	@Test
	public void testFindByZip() {
		FarmerService farmerService = new FarmerService();
		farmerService.add(new Farmer(null,new Farm("farm_name_1", "847-346-2313","address", "www.uuu.com"),new Personal("personal_name1", "email@e.com", "666-333-1231"),new ArrayList<String>(Arrays.asList("300001","300002"))));
		farmerService.add(new Farmer(null,new Farm("farm_name_2", "847-346-2313","address", "www.uuu.com"),new Personal("personal_name1", "email@e.com", "666-333-1231"),new ArrayList<String>(Arrays.asList("400001","400002"))));
		farmerService.add(new Farmer(null,new Farm("farm_name_3", "847-346-2313", "address","www.uuu.com"),new Personal("personal_name1", "email@e.com", "666-333-1231"),new ArrayList<String>(Arrays.asList("400001","500002"))));

		List<Farmer> farmers = farmerService.findByZip("400001");
		assert(farmers.size() == 2);
		
		farmers = farmerService.findByZip(null);
		assert(farmers.size() == 3);
	}
	
	@Test
	public void testetFarmers() {
		FarmerService farmerService = new FarmerService();
		List<Farmer> farmers = farmerService.getFarmers();
		assert(farmers.size() == 0);
	}

}
