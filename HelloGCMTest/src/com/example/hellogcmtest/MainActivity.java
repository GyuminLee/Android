package com.example.hellogcmtest;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		prefs = getSharedPreferences("regId", Context.MODE_PRIVATE);
		if (!checkGCM()) {
			Toast.makeText(this, "gcm not support", Toast.LENGTH_SHORT).show();
			finish();
		}
		
		regId = getRegistrationId();
		if (regId.equals("")) {
			
		} else {
			if (isRegistrationServer()) {
				//...
			} else {
				new Thread(new Runnable() {
					@Override
					public void run() {
						registerServer(regId);
					}
				}).start();
			}
		}
		
	}
	
	public static final String SENDER_ID = "972646024433";
	class RegIdTask extends AsyncTask<String,Integer,String> {
		@Override
		protected String doInBackground(String... params) {
			GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(MainActivity.this);
			try {
				regId = gcm.register(SENDER_ID);
				saveRegistrationID(regId);
				registerServer(regId);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}
	SharedPreferences prefs;

	String regId;
	public static final String REG_ID = "regId";
	public static final String REG_SERVER = "regServer";
	
	private String getRegistrationId() {
		String regId = prefs.getString(REG_ID, "");
		return regId;
	}
	
	private void saveRegistrationID(String id) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putString(REG_ID, id);
		edit.commit();
	}
	
	private void registerServer(String id) {
		// .....
		saveRegistrationServer();
	}
	
	private void saveRegistrationServer() {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putBoolean(REG_SERVER, true);
		edit.commit();
	}
	
	private boolean isRegistrationServer() {
		boolean isServer = prefs.getBoolean(REG_SERVER, false);
		return isServer;
	}
	
	private boolean checkGCM() {
		int result = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (result != ConnectionResult.SUCCESS) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
