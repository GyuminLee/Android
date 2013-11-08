package com.example.hellowebapptest;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Transaction;

public class Datastore {
	static final int MULTICAST_SIZE = 1000;
	private static final String DEVICE_TYPE = "Device";
	private static final String DEVICE_USER_ID_PROPERTY = "userId";
	private static final String DEVICE_REG_ID_PROPERTY = "regId";
	private static final FetchOptions DEFAULT_FETCH_OPTIONS = FetchOptions.Builder
			.withPrefetchSize(MULTICAST_SIZE).chunkSize(MULTICAST_SIZE);
	private static final DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();

	private Datastore() {
		throw new UnsupportedOperationException();
	}

	public static void register(String userId, String regId) {
		Transaction txn = datastore.beginTransaction();
		try {
			if (findDeviceByRegId(regId) != null) {
				return;
			}
			Entity entity;
			entity = new Entity(DEVICE_TYPE);
			entity.setProperty(DEVICE_USER_ID_PROPERTY, userId);
			entity.setProperty(DEVICE_REG_ID_PROPERTY, regId);
			datastore.put(entity);
			txn.commit();
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
	}

	public static String findDeviceByRegId(String userId) {
		Query query = new Query(DEVICE_TYPE).addFilter(DEVICE_USER_ID_PROPERTY,
				FilterOperator.EQUAL, userId);
		PreparedQuery preparedQuery = datastore.prepare(query);
		List<Entity> entities = preparedQuery.asList(DEFAULT_FETCH_OPTIONS);
		Entity entity = null;
		if (!entities.isEmpty()) {
			entity = entities.get(0);
			String regId = (String)entity.getProperty(DEVICE_REG_ID_PROPERTY);
			
		}
		return null;
	}

	public static void updateRegistration(String regId, String canonicalRegId) {
		
	}

}
