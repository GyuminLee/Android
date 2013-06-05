package com.example.testcomponentsample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class SMSReciver extends BroadcastReceiver {

	private static final String TAG = "SMSReceiver";
	@Override
	public void onReceive(Context context, Intent intent) {
//		Toast.makeText(context, "SMS Received", Toast.LENGTH_SHORT).show();
		Log.i(TAG, "SMSREceiver received");
	}

}
