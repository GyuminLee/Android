package org.tacademy.web.hellowebapp;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

public class RegistrationJDO {
	private static final PersistenceManagerFactory pmfInstance = JDOHelper
		.getPersistenceManagerFactory("transactions-optional");

	public static PersistenceManagerFactory getPersistenceManagerFactory() {
		return pmfInstance;
	}

	public void addRegistrationItem(RegistrationItem item) {
		PersistenceManager pm = getPersistenceManagerFactory()
				.getPersistenceManager();
		try {
			pm.makePersistent(item);
		} finally {
			pm.close();
		}
	}

	public List<RegistrationItem> listRegistrationItems() {
		return listRegistrationItems(null);
	}
	
	@SuppressWarnings("unchecked")
	public List<RegistrationItem> listRegistrationItems(String name) {
		PersistenceManager pm = getPersistenceManagerFactory()
				.getPersistenceManager();
		String query =  "select from " + RegistrationItem.class.getName();
		if (name != null && name.length() > 0) {
			query = query + " where name == \"" + name + "\"";
		}
		return (List<RegistrationItem>) pm.newQuery(query).execute();
	}
	
	public List<RegistrationItem> listRegistrtionItemsLike(String name) {
		List<RegistrationItem> items = listRegistrationItems();
		ArrayList<RegistrationItem> retItems = new ArrayList<RegistrationItem>();
		for (RegistrationItem item : items) {
			if (item.name.contains(name)) {
				retItems.add(item);
			}
		}
		return retItems;
	}
	
	
	public void removeRegistrationItem(RegistrationItem item) {
		PersistenceManager pm = getPersistenceManagerFactory()
				.getPersistenceManager();
		try {
			pm.currentTransaction().begin();

			// We don't have a reference to the selected Product.
			// So we have to look it up first,
			item = pm.getObjectById(RegistrationItem.class, item.id);
			pm.deletePersistent(item);

			pm.currentTransaction().commit();
		} catch (Exception ex) {
			pm.currentTransaction().rollback();
			throw new RuntimeException(ex);
		} finally {
			pm.close();
		}
	}

	public void updateRegitrationItem(RegistrationItem item) {
		PersistenceManager pm = getPersistenceManagerFactory()
				.getPersistenceManager();
		String name = item.name;
		String registrationId = item.registrationId;

		try {
			pm.currentTransaction().begin();
			// We don't have a reference to the selected Product.
			// So we have to look it up first,
			item = pm.getObjectById(RegistrationItem.class, item.id);
			item.name = name;
			item.registrationId = registrationId;
			
			pm.makePersistent(item);
			pm.currentTransaction().commit();
		} catch (Exception ex) {
			pm.currentTransaction().rollback();
			throw new RuntimeException(ex);
		} finally {
			pm.close();
		}
	}
	
}
