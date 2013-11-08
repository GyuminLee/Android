package com.example.hellowebapptest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class SendMessageClient {
	Sender sender;
	private static final String SERVER_KEY = "AIzaSyDhYvAEYrUWTQyjih1EdzcUVhLNuY7It78";
	private static final int RETRY_COUNT = 3;
	
	public SendMessageClient() {
		sender = new Sender(SERVER_KEY);
	}
	
	public void sendMessage(String regId,HashMap<String,String> data) {
		sendMessage(regId,data,null,true);
	}
	
	public void sendMessage(String regId,HashMap<String,String> data,String collapseKey, boolean delayWhileIdle){
		Message.Builder builder = new Message.Builder();
		Set<String> keys = data.keySet();
		for (String key : keys) {
			String value = data.get(key);
			builder.addData(key, value);
		}
		if (collapseKey != null && !collapseKey.equals("")) {
			builder.collapseKey(collapseKey);
		}
		builder.delayWhileIdle(delayWhileIdle);
		Message message = builder.build();
		Result result;
		try {
			result = sender.send(message, regId, RETRY_COUNT);
			if (result == null) {
				return;
			}
			if (result.getMessageId() != null) {
				String canonicalRegId = result.getCanonicalRegistrationId();
				if (canonicalRegId != null) {
					Datastore.updateRegistration(regId, canonicalRegId);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
