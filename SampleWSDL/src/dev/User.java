package dev;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = -1129402159048345204L;
	
    private String name;
    
    private int id;
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
}
