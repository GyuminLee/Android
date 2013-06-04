package com.example.testcustomlistsample;

public class MyData {

	String name;
	int age;
	String desc;
	boolean isSend = false;
	
	public MyData() {
		
	}
	
	public MyData(String name, int age, String desc) {
		this(name,age,desc,false);
	}
	
	public MyData(String name, int age, String desc, boolean isSend) {
		this.name = name;
		this.age = age;
		this.desc = desc;
		this.isSend = isSend;
	}
}
