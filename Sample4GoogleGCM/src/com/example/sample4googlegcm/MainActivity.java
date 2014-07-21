package com.example.sample4googlegcm;

import java.io.IOException;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

	private static final String SENDER_ID = "972646024433";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if (checkPlayServices()) {
			String regId = PropertyManager.getInstance().getRegistrationId();
			if (regId.equals("")) {
				registerInBackground();
			} else {
				Toast.makeText(this, "already registered", Toast.LENGTH_SHORT).show();
			}
		} else {
			finish();
		}
		
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						String regId = PropertyManager.getInstance().getRegistrationId();
						ServerUtilities.register(MainActivity.this, regId);
					}
				}).start();
			}
		});
	}
	
	private void registerInBackground() {
	    new AsyncTask<Void,Integer,String>() {
	        @Override
	        protected String doInBackground(Void... params) {
	            String msg = "";
	            GoogleCloudMessaging gcm = null;
	            String regid;
	            try {
	                if (gcm == null) {
	                    gcm = GoogleCloudMessaging.getInstance(MainActivity.this);
	                }
	                regid = gcm.register(SENDER_ID);
	                PropertyManager.getInstance().setRegistrationId(regid);
	            } catch (IOException ex) {
	            }
	            return msg;
	        }
	        
	        protected void onPostExecute(String result) {
	        	Toast.makeText(MainActivity.this, "register regId", Toast.LENGTH_SHORT).show();
	        }
	    }.execute(null, null, null);
	}	
	
	private boolean checkPlayServices() {
	    int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
	    if (resultCode != ConnectionResult.SUCCESS) {
	        if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
	            GooglePlayServicesUtil.getErrorDialog(resultCode, this,
	                    PLAY_SERVICES_RESOLUTION_REQUEST).show();
	        } else {
	            finish();
	        }
	        return false;
	    }
	    return true;
	}
	
}
