package com.example.samplelockscreen;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class ScreenService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
		registerReceiver(receiver, filter);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	};

	ScreenReceiver receiver = new ScreenReceiver();
	
	class ScreenReceiver extends BroadcastReceiver {
		KeyguardManager keyguardManager = null;
		KeyguardManager.KeyguardLock keyguardLock = null;
		
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
				if (keyguardManager == null) {
					keyguardManager = (KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE);
				}
				if (keyguardLock == null) {
					keyguardLock = keyguardManager.newKeyguardLock(Context.KEYGUARD_SERVICE);
				}
				
				keyguardLock.disableKeyguard();
				
				Intent i = new Intent(ScreenService.this, LockScreenActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);
			}
		}
	}
}
