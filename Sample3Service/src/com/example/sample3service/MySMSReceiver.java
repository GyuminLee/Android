package com.example.sample3service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MySMSReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
	    Log.i("MySMSReceiver","SMS Received");
//	    Toast.makeText(context, "SMS Received", Toast.LENGTH_SHORT).show();
	    Intent i = new Intent(context,MyService.class);
	    context.startService(i);
	}

}
