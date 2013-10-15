package com.example.testasmack;

public class User {
	String userid;
	String name;
	String status;
	@Override
	public String toString() {
		return name + "(" + userid + ")" + "\n\r" + status;
	}
}
