package com.example.web;

import java.util.Arrays;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class JDOAction {

	public static void addPersion(Person person) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(person);
		} finally {
			pm.close();
		}
	}
	
	public static List<Person> searchPerson(String keyword) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(Person.class, ":p.contains(lastName)");
		List<Person> list = (List<Person>)q.execute(Arrays.asList("Smith", "Jones"));

		return list;
	}
}
