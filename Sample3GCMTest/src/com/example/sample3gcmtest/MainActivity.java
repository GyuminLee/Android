package com.example.sample3gcmtest;

import java.io.IOException;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends ActionBarActivity {

	private static final String TAG = "MainActivity";
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

		if (checkPlayServices()) {
			final String regId = getRegistrationId();
			if (regId.equals("")) {
				registerInBackground();
			} else if (!isRegisteredServer()) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						setRegisteredServer(regId);
					}
				}).start();
			}
		} else {
			Toast.makeText(this, "no gcm service", Toast.LENGTH_SHORT).show();
			finish();
		}
		
	}

	GoogleCloudMessaging gcm;
	
	private void registerInBackground() {
	    new AsyncTask<Void,Integer,String>() {
	        @Override
	        protected String doInBackground(Void... params) {
	            String msg = "";
	            
	            try {
	                if (gcm == null) {
	                    gcm = GoogleCloudMessaging.getInstance(MainActivity.this);
	                }
	                String regid = gcm.register(CommonUtilities.SENDER_ID);
	                msg = "Device registered, registration ID=" + regid;

	                

	                storeRegistrationId(regid);
	                setRegisteredServer(regid);
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
	        }
	    }.execute(null, null, null);
	}	


	private String getRegistrationId() {
		return "";
	}
	
	private void storeRegistrationId(String regId) {
		// ...
	}
	
	private boolean isRegisteredServer() {
		return false;
	}
	
	private void setRegisteredServer(String regId) {
		// ...
		ServerUtilities.register(this, regId);
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
