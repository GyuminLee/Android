package com.example.samplegcmgoogleplaytest;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";

    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static final String PROPERTY_SAVE_SERVER = "save_server";
    String SENDER_ID = "972646024433";
    GoogleCloudMessaging gcm;
    AtomicInteger msgId = new AtomicInteger();
    SharedPreferences prefs;
    String regid;
    
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	TextView mDisplay;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mDisplay = (TextView) findViewById(R.id.display);
		context = getApplicationContext();
		if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(this);
            regid = getRegistrationId(context);

            if (regid.equals("")) {
                registerInBackground();
            } 
//            else {
//            	if (!PropertyManager.getInstance().isRegisterServier()) {
//            		String id = PropertyManager.getIsntance().getId();
//            		if (!id.equals()) {
//	            		NetworkModel.getInstance().register("id",regId, new OnListener() {
//	            			public void onSuccess() {
//	            				PeropertyManager.getInstance.setRegisgterServer(true);
//	            				
//	            			}
//	            		});
//            		}
//            	}
//            }
        } else {
            Log.i(TAG, "No valid Google Play Services APK found.");
        }
	}

	private String getRegistrationId(Context context) {
	    final SharedPreferences prefs = getGCMPreferences(context);
	    String registrationId = prefs.getString(PROPERTY_REG_ID, "");
	    if (registrationId.isEmpty()) {
	        Log.i(TAG, "Registration not found.");
	        return "";
	    }
	    int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
	    int currentVersion = getAppVersion(context);
	    if (registeredVersion != currentVersion) {
	        Log.i(TAG, "App version changed.");
	        return "";
	    }
	    return registrationId;
	}
	
	private SharedPreferences getGCMPreferences(Context context) {
	    return getSharedPreferences(MainActivity.class.getSimpleName(),
	            Context.MODE_PRIVATE);
	}
	
	private static int getAppVersion(Context context) {
	    try {
	        PackageInfo packageInfo = context.getPackageManager()
	                .getPackageInfo(context.getPackageName(), 0);
	        return packageInfo.versionCode;
	    } catch (NameNotFoundException e) {
	        throw new RuntimeException("Could not get package name: " + e);
	    }
	}
	
	private void registerInBackground() {
	    new AsyncTask<Void,Integer,String>() {
	        @Override
	        protected String doInBackground(Void... params) {
	            String msg = "";
	            try {
	                if (gcm == null) {
	                    gcm = GoogleCloudMessaging.getInstance(context);
	                }
	                regid = gcm.register(CommonUtilities.SENDER_ID);
	                msg = "Device registered, registration ID=" + regid;

	                sendRegistrationIdToBackend(context,regid);

	                storeRegistrationId(context, regid);
	            } catch (IOException ex) {
	                msg = "Error :" + ex.getMessage();
	                // If there is an error, don't just keep trying to register.
	                // Require the user to click a button again, or perform
	                // exponential back-off.
	            }
	            return msg;
	        }

	        @Override
	        protected void onPostExecute(String msg) {
	            mDisplay.append(msg + "\n");
	        }
	    }.execute(null, null, null);
	}	

    private void sendRegistrationIdToBackend(Context context,String regId) {
    	ServerUtilities.register(context, regId);
    	setSavedServer(true);
	}
    
    public void onClick(final View view) {
        if (view == findViewById(R.id.send)) {
            new AsyncTask<Void,Integer,String>() {
                @Override
                protected String doInBackground(Void... params) {
                    String msg = "";
                    try {
                        Bundle data = new Bundle();
                            data.putString("my_message", "Hello World");
                            data.putString("my_action",
                                    "com.google.android.gcm.demo.app.ECHO_NOW");
                            String id = Integer.toString(msgId.incrementAndGet());
                            gcm.send(CommonUtilities.SENDER_ID + "@gcm.googleapis.com", id, data);
                            msg = "Sent message";
                    } catch (IOException ex) {
                        msg = "Error :" + ex.getMessage();
                    }
                    return msg;
                }

                @Override
                protected void onPostExecute(String msg) {
                    mDisplay.append(msg + "\n");
                }
            }.execute(null, null, null);
        } else if (view == findViewById(R.id.clear)) {
            mDisplay.setText("");
        }
    }
    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }
	
    private void setSavedServer(boolean isSaved) {
        final SharedPreferences prefs = getGCMPreferences(context);
    	SharedPreferences.Editor editor = prefs.edit();
    	editor.putBoolean(PROPERTY_SAVE_SERVER, isSaved);
    	editor.commit();
    }
    
    private boolean isSaveServer() {
        final SharedPreferences prefs = getGCMPreferences(context);
        boolean isSaved = prefs.getBoolean(PROPERTY_SAVE_SERVER, false);
        return isSaved;
    }
    
	@Override
	protected void onResume() {
	    super.onResume();
	    checkPlayServices();
	}
	
	private boolean checkPlayServices() {
	    int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
	    if (resultCode != ConnectionResult.SUCCESS) {
	        if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
	            GooglePlayServicesUtil.getErrorDialog(resultCode, this,
	                    PLAY_SERVICES_RESOLUTION_REQUEST).show();
	        } else {
	            Log.i(TAG, "This device is not supported.");
	            finish();
	        }
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
