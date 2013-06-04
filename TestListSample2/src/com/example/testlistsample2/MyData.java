package com.example.testlistsample2;

public class MyData {

	String name;
	int age;
	String desc;
	
	public MyData() {
		
	}
	
	public MyData(String name, int age, String desc) {
		this.name = name;
		this.age = age;
		this.desc = desc;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name + "(" + age + ")" + "\n\r" + desc;
	}
}
