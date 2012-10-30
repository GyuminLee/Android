package org.tacademy.basic.screenlock;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ScreenOnOffReceiver extends BroadcastReceiver {

	private final static String TAG = "ScreenOnOffReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
			Log.i(TAG,"receive screen on");
			KeyguardManager manager = (KeyguardManager)context.getSystemService(Context.KEYGUARD_SERVICE);  
			KeyguardLock lock = manager.newKeyguardLock(Context.KEYGUARD_SERVICE);  
			lock.disableKeyguard();
			Intent i = new Intent(context,TestLockScreenActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);
		} else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
			Log.i(TAG,"receive screen off");
			KeyguardManager manager = (KeyguardManager)context.getSystemService(Context.KEYGUARD_SERVICE);  
			KeyguardLock lock = manager.newKeyguardLock(Context.KEYGUARD_SERVICE);  
			lock.disableKeyguard();
			Intent i = new Intent(context,TestLockScreenActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);
		}

	}

}
