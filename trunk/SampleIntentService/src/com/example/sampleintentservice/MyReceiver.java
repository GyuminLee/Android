package com.example.sampleintentservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
	    // TODO Auto-generated method stub
		intent.setClass(context, MyService.class);
		context.startService(intent);
	}

}
