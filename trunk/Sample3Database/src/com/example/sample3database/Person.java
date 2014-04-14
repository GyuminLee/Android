package com.example.sample3database;

public class Person {
	long _id = -1;
	String name;
	int age;
	@Override
	public String toString() {
		return name + "(" + age + ")";
	}
}
