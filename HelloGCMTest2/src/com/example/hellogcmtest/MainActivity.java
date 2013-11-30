package com.example.hellogcmtest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.rpc.client.HttpJsonRpcClientTransport;
import org.json.rpc.client.JsonRpcInvoker;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shared.GCMSend;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class MainActivity extends Activity {

	public static final String SENDER_ID = "972646024433";
	private SharedPreferences prefs;
	private static final String PREFS = "myprefs";
	private static final String FIELD_REG_ID = "regId";
	private static final String FIELD_REG_SERVER = "serverReg";
	
	private GCMSend gcmSend;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		prefs = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
		if (checkGooglePlayService()) {
			String regId = getRegId();
			if (regId.equals("")) {
				registreationId();
			} else {
				if (isServerReg()) {
					// ..
					// Nothing...
				} else {
					new AsyncTask<String, Integer, Boolean>() {
						@Override
						protected Boolean doInBackground(String... params) {
							String regId = params[0];
							sendServer(regId);
							return null;
						}
					}.execute(regId);
				}
			}
		} else {
			Toast.makeText(this, "GCM Not Support!!!", Toast.LENGTH_SHORT).show();
			finish();
		}
		
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new AsyncTask<String, Integer, Boolean>() {

					@Override
					protected Boolean doInBackground(String... params) {
						
						return sendGCMMessage();
					}
					
					protected void onPostExecute(Boolean result) {
						if (result) {
							Toast.makeText(MainActivity.this, "message sended", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(MainActivity.this, "message fail", Toast.LENGTH_SHORT).show();
						}
					}
					
					
				}.execute("");
				
			}
		});
	}
	
	private boolean sendGCMMessage() {
		if (gcmSend == null) {
			try {
				HttpJsonRpcClientTransport tranport = new HttpJsonRpcClientTransport(new URL("http://dongjaguestbook.appspot.com/hellomyfirstweb"));
				JsonRpcInvoker invoker = new JsonRpcInvoker();
				gcmSend = invoker.get(tranport, "gcm", GCMSend.class);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (gcmSend != null) {
			return gcmSend.send(getRegId());
		}
		return false;
	}
	
	

	private String getRegId() {
		return prefs.getString(FIELD_REG_ID, "");
	}
	
	private boolean isServerReg() {
		return prefs.getBoolean(FIELD_REG_SERVER, false);
	}
	
	private boolean checkGooglePlayService() {
		int result = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (result == ConnectionResult.SUCCESS) {
			return true;
		}
		return false;
	}
	
	private void registreationId() {
		new AsyncTask<String, Integer, Boolean>() {

			@Override
			protected Boolean doInBackground(String... params) {
				GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(MainActivity.this);
				try {
					String regId = gcm.register(SENDER_ID);
					SharedPreferences.Editor editor = prefs.edit();
					editor.putString(FIELD_REG_ID, regId);
					editor.commit();
					sendServer(regId);
					return true;
				} catch (IOException e) {
					e.printStackTrace();
				}
				return false;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				if (result != null && result == true) {
					Toast.makeText(MainActivity.this, "Success RegId", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(MainActivity.this, "Fail RegId", Toast.LENGTH_SHORT).show();
				}
			};
			
		}.execute("");
	}
	
	private void sendServer(String regId) {
		// ...
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean(FIELD_REG_SERVER, true);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
