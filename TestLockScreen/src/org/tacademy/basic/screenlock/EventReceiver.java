package org.tacademy.basic.screenlock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class EventReceiver extends BroadcastReceiver {

	private final static String TAG = "EventReceiver";
	
	@Override
	public void onReceive(Context context, Intent intent) {
	    // TODO Auto-generated method stub
		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
			Intent i = new Intent(context,NotiService.class);
			context.startService(i);
			Log.i(TAG,"Receive Boot completed");
		}
	}

}
