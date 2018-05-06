package org.pop.lf2u.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.pop.lf2u.common.Utils;
import org.pop.lf2u.model.Manager;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {
	private List<Manager> managers = new ArrayList<Manager>();

	public ManagerService() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2016, 10-1, 1);

		managers.add(
				new Manager("0", "Super User", "System", calendar.getTime(), "123-0987-654", "superuser@example.com"));
	}

	/**
	 * @return the managers
	 */
	public List<Manager> getAllManagers() {
		return managers;
	}

	public Manager findById(String mid) {
		for (Manager manager : managers) {
			if (manager.getMid().equals(mid))
				return manager;
		}
		return null;
	}

	public Manager add(Manager manager) {
		String lastId = null;
		if (managers.size() > 0)
			lastId = managers.get(managers.size() - 1).getMid();
		String newId = Utils.generateId(lastId);

		manager.setMid(newId);
		managers.add(manager);

		return manager;
	}

}
