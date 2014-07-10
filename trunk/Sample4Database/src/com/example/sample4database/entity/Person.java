package com.example.sample4database.entity;

public class Person {
	public long _id = -1;
	public String name;
	public int age;
	@Override
	public String toString() {
		return name + "(" + age + ")";
	}
}
