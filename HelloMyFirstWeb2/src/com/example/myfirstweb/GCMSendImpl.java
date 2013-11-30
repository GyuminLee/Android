package com.example.myfirstweb;

import java.io.IOException;

import com.example.shared.GCMSend;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class GCMSendImpl implements GCMSend {
	Sender sender;
	
	public GCMSendImpl() {
		sender = new Sender("AIzaSyDhYvAEYrUWTQyjih1EdzcUVhLNuY7It78");
	}
	@Override
	public boolean send(String regId) {
		Message.Builder builder = new Message.Builder();
		builder.addData("name", "ysi");
		builder.addData("age", "39");
		try {
			Result result = sender.sendNoRetry(builder.build(), regId);
			if (result.getMessageId() != null) {
				if (result.getCanonicalRegistrationId() != null) {
					// ...
				}
				return true;
			} 
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
