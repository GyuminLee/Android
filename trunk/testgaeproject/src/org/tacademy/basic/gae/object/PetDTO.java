package org.tacademy.basic.gae.object;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
@PersistenceCapable(identityType = IdentityType.DATASTORE)
public class PetDTO {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;
	
	@Persistent
	private String pet_name;

	public PetDTO(String pet_name) {
		super();
		this.pet_name = pet_name;
	}
	
	
}
	