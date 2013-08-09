package com.example.hellomyfirstweb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import com.example.shared.MyData;
import com.example.shared.MyWebInterface;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class MyWebInterfaceImpl implements MyWebInterface {

	HashMap<String,String> mRegisterMap = new HashMap<String,String>();
	Sender mSender;
	public static final String API_KEY = "AIzaSyDhYvAEYrUWTQyjih1EdzcUVhLNuY7It78";
	
	public MyWebInterfaceImpl() {
		mSender = new Sender(API_KEY);
	}
	
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
	
	public ArrayList<String> getRegistrationIds() {
		Set<String> keys = mRegisterMap.keySet();
		ArrayList<String> regIds = new ArrayList<String>();
		int index = 0;
		for (String key : keys) {
			regIds.add(mRegisterMap.get(key));
		}
		return regIds;
	}
	
	public String getRegistrationId(String name) {
		return mRegisterMap.get(name);
	}
	
	public void sendAll() {
		Message.Builder builder = new Message.Builder();
		builder.addData("message", "Hi GCM");
		try {
			MulticastResult mresult = mSender.send(builder.build(), getRegistrationIds(), 3);
			for (Result result : mresult.getResults()) {
				// ...
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void sendMessage(String name, String message) {
		String regId = getRegistrationId(name);
		Message.Builder builder = new Message.Builder();
		builder.addData("message", message);
		builder.delayWhileIdle(false);
		builder.collapseKey("news");
		try {
			Result result = mSender.sendNoRetry(builder.build(), regId);
			if (result.getMessageId() == null) {
				// fail...
			} else {
				if (result.getCanonicalRegistrationId() != null) {
					// db update
				} else {
					// success
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
