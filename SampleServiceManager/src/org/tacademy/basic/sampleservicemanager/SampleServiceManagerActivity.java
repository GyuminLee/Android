package org.tacademy.basic.sampleservicemanager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SampleServiceManagerActivity extends Activity {
	/** Called when the activity is first created. */
	CustomStatusBarManager mStatusBarManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				ITelephony telephony = ITelephony.Stub
//						.asInterface(CustomServiceManager
//								.getService(Context.TELEPHONY_SERVICE));
//				Toast.makeText(SampleServiceManagerActivity.this,
//						"telephony : " + telephony, Toast.LENGTH_SHORT).show();
			}
		});

		btn = (Button) findViewById(R.id.button2);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mStatusBarManager.expand();
			}
		});

		btn = (Button) findViewById(R.id.button3);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					boolean isIdle = Telephony.getInstance().isIdle();
					Toast.makeText(SampleServiceManagerActivity.this, "is idle : " + isIdle, Toast.LENGTH_SHORT).show();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		mStatusBarManager = new CustomStatusBarManager(
				getSystemService(CustomStatusBarManager.STATUS_BAR_SERVICE));
	}
}