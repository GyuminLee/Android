package org.tacademy.network.rss.c2dm;

import org.tacademy.network.rss.util.PropertyManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class C2DMReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
	    // TODO Auto-generated method stub
		if (intent.getAction().equals("com.google.android.c2dm.intent.REGISTRATION")) {
			handleRegistration(context,intent);
		} else if (intent.getAction().equals("com.google.android.c2dm.intent.RECEIVE")) {
			handleMessage(context,intent);
		}
	}

	private void handleMessage(Context context, Intent intent) {
		Toast.makeText(context, "receive data", Toast.LENGTH_LONG).show();
		int sender = Integer.parseInt(intent.getStringExtra("sender"));
		String type = intent.getStringExtra("type");
		String name = intent.getStringExtra("name");
		String message = intent.getStringExtra("message");
		Intent serviceIntent = new Intent(context,C2DMService.class);
		serviceIntent.putExtra(C2DMService.SERVICE_TYPE_KEY, C2DMService.SERVICE_TYPE_RECEIVE_DATA);
		serviceIntent.putExtra(C2DMService.MESSAGE_SENDER, sender);
		serviceIntent.putExtra(C2DMService.MESSAGE_TYPE, type);
		serviceIntent.putExtra(C2DMService.MESSAGE_NAME, name);
		serviceIntent.putExtra(C2DMService.MESSAGE_TEXT, message);
		context.startService(serviceIntent);
	}

	private void handleRegistration(Context context, Intent intent) {
		String registrationId = intent.getStringExtra("registration_id");
		String error = intent.getStringExtra("error");
		String removed = intent.getStringExtra("unregistered");
		if (removed != null) {
			// unregister
			Toast.makeText(context, "unregistered", Toast.LENGTH_LONG).show();
			PropertyManager.getInstance().setRegistrationId("");
		} else if (error != null) {
			if (error.equals("SERVICE_NOT_AVAILABLE")) {
				
			} else if (error.equals("ACCOUNT_MISSING")) {
				
			} else if (error.equals("AUTHENTICATION_FAILED")) {
				
			} else if (error.equals("TOO_MANY_REGISTRATIONS")) {
				
			} else if (error.equals("INVALID_SENDER")) {
				
			} else if (error.equals("PHONE_REGISTRATION_ERROR")) {
				
			}
			Toast.makeText(context, "error : " + error, Toast.LENGTH_LONG).show();
			PropertyManager.getInstance().setRegistrationId(null);
		} else {
			Toast.makeText(context, "registrationId : " + registrationId, Toast.LENGTH_LONG).show();
			PropertyManager.getInstance().setRegistrationId(registrationId);
			Intent serviceIntent = new Intent(context,C2DMService.class);
			serviceIntent.putExtra(C2DMService.SERVICE_TYPE_KEY, C2DMService.SERVICE_TYPE_REGISTRATION);
			serviceIntent.putExtra(C2DMService.REGISTRATION_ID, registrationId);
			context.startService(serviceIntent);
		}
		
	}
	
}
