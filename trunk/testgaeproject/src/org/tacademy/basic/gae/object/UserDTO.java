package org.tacademy.basic.gae.object;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType=IdentityType.APPLICATION)
public class UserDTO {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;

	@Persistent
	private String name;
	
	@Persistent
	private String email;
	
	@Persistent
	private PetDTO pet;
	
	public UserDTO(String name,String email) {
		this.name = name;
		this.email = email;
	}
	
	public UserDTO(String name,String email, PetDTO pet) {
		this.name = name;
		this.email = email;
		this.pet = pet;
	}
}
