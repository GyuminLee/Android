package org.tacademy.basic.gae.object;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class ContactInfo {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private String city;
	
	@Persistent(mappedBy="contactInfo")
	private Employee employee;
	
	public ContactInfo(String city) {
		this.city = city;
	}
	
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	public Employee getEmployee() {
		return employee;
	}

}
