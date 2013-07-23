package com.example.samplegsontest;

public class DateTime {

	long time;
	
	public DateTime(String time) {
		this.time = Long.parseLong(time);
	}
	
	public DateTime(long time) {
		this.time = time;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.valueOf(time);
	}
}
