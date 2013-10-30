package com.example.hellolisttest;

public class MyData {
	int resId;
	String name;
	int age;
	
	public MyData(String name,int age) {
		this.resId = resId;
		this.name = name;
		this.age = age;
	}
	
	@Override
	public String toString() {
		return name + "(" + age + ")";
	}
}
