package com.example.hellogae;

import java.util.ArrayList;

import com.example.shared.Calculator;
import com.example.shared.User;

public class CalculatorImpl implements Calculator {

	ArrayList<User> users = new ArrayList<User>();
	
	@Override
	public double add(double x, double y) {
		return x + y;
	}

	@Override
	public double multiply(double x, double y) {
		return x * y;
	}

	@Override
	public User addUser(String name, String password) {
		User u = new User();
		u.name = name;
		u.password = password;
		users.add(u);
		return u;
	}

}
