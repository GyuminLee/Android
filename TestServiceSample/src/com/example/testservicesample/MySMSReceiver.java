package com.example.testservicesample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MySMSReceiver extends BroadcastReceiver {

	private final static String TAG = "MySMSReceiver";
	
	@Override
	public void onReceive(Context context, Intent intent) {
	    // TODO Auto-generated method stub
		Log.i(TAG,"sms received");
	}

}
