package com.example.testdatabasesample2;

public class Person {

	int id;
	String name;
	String address;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name + "\n\r" + address;
	}
}
