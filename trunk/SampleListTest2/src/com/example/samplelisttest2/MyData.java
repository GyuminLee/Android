package com.example.samplelisttest2;

public class MyData {

	public String title;
	public String desc;
	
	public MyData(String title, String desc) {
		this.title = title;
		this.desc = desc;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return title + "\n" + desc;
	}
}
