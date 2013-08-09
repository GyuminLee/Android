package com.example.hellomyfirstweb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import com.example.shared.MyData;
import com.example.shared.MyWebInterface;

public class MyWebInterfaceImpl implements MyWebInterface {

	HashMap<String,String> mRegisterMap = new HashMap<String,String>();
	
	@Override
	public double add(double x, double y) {
		// TODO Auto-generated method stub
		return x + y;
	}

	@Override
	public void add(MyData data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MyData get(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void register(String name, String regId) {
		String oldId = mRegisterMap.get(name);
		if (oldId == null) {
			mRegisterMap.put(name, regId);
		}
	}
	
	public ArrayList<String> getUserList() {
		Set<String> keys = mRegisterMap.keySet();
		ArrayList<String> userList = new ArrayList<String>();
		for(String key : keys) {
			userList.add(key);
		}
		return userList;
	}
	
	public String[] getRegistrationIds() {
		Set<String> keys = mRegisterMap.keySet();
		String[] regIds = new String[keys.size()];
		int index = 0;
		for (String key : keys) {
			regIds[index++] = mRegisterMap.get(key);
		}
		return regIds;
	}
	
	public String getRegistrationId(String name) {
		return mRegisterMap.get(name);
	}

	@Override
	public void sendMessage(String name, String message) {
		
	}
}
