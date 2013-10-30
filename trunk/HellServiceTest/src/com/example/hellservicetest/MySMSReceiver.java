package com.example.hellservicetest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MySMSReceiver extends BroadcastReceiver {

	private final String TAG = "MySMSReceiver";
	
	@Override
	public void onReceive(Context context, Intent intent) {
	   Log.i(TAG,"SMS Received...");
	   Intent i = new Intent(context, MyService.class);
	   context.startService(i);
	}

}
