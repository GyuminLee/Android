package com.example.hellowebapptest;

import java.util.HashMap;

import com.example.shared.MyServerMethod;
import com.example.shared.User;

public class MyServerMethodImpl implements MyServerMethod {

	@Override
	public double add(double x, double y) {
		return x + y;
	}

	@Override
	public User addUser(String name, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendMessage(String regId) {
		SendMessageClient client = new SendMessageClient();
		HashMap<String,String> data = new HashMap<String,String>();
		data.put("message", "message data....");
		client.sendMessage(regId, data);
	}


}
