package org.pop.lf2u.service;

import java.util.List;

import org.junit.Test;
import org.pop.lf2u.model.Manager;
import org.pop.lf2u.service.ManagerService;

public class ManagerServiceTest {

	@Test
	public void testManagerService() {
		ManagerService managerService = new ManagerService();
		assert (managerService != null);
	}

	@Test
	public void testGetAllManagers() {
		ManagerService managerService = new ManagerService();
		List<Manager> allManagers = managerService.getAllManagers();
		assert (allManagers.size() == 1);
	}

	@Test
	public void testFindById() {
		ManagerService managerService = new ManagerService();
		managerService.add(new Manager(null));
		managerService.add(new Manager(null));
		managerService.add(new Manager(null));

		assert (managerService.findById("000") == null);
		assert (managerService.findById("1").getMid().equals("1"));
	}

	@Test
	public void testAdd() {
		ManagerService managerService = new ManagerService();
		managerService.add(new Manager(null));
		managerService.add(new Manager(null));
		managerService.add(new Manager(null));

		assert (managerService.getAllManagers().size() == 4);
	}

}
