package com.example.web;

import java.util.Date;

import javax.jdo.annotations.Embedded;
import javax.jdo.annotations.EmbeddedOnly;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Person {

	@PrimaryKey
	@Persistent(valueStrategy= IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private String firstName;
	
	@Persistent
	private String lastName;
	
	@Persistent
	private Date hireDate;
	
	@PersistenceCapable
	@EmbeddedOnly
	public static class ContactInfo {
		@Persistent
		private String street;
		
		@Persistent
		private String city;
	}
	
	@Persistent
	@Embedded
	private ContactInfo home;
}
