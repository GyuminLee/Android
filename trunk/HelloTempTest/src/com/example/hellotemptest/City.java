package com.example.hellotemptest;

public class City {
	int id;
	String name;
	String country;
	@Override
	public String toString() {
		return name+","+country;
	}
}
