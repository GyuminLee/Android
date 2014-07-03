package com.example.sample4service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SMSReceiver extends BroadcastReceiver {

	private static final String TAG = "SMSRReceiver";
	@Override
	public void onReceive(Context context, Intent intent) {
//	    Toast.makeText(context, "Receive SMS", Toast.LENGTH_SHORT).show();
		Log.i(TAG,"Receive SMS");
		Intent i = new Intent(context, MyService.class);
		context.startService(i);
	}

}
