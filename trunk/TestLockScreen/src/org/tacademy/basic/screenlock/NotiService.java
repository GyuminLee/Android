package org.tacademy.basic.screenlock;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class NotiService extends Service {

	private final static String TAG = "NotiService";

	BroadcastReceiver receiver;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		receiver = new ScreenOnOffReceiver();
		registerReceiver(receiver, filter);
		Log.i(TAG,"call onCreate");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.i(TAG,"call onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(receiver);
		Log.i(TAG,"call onDestroy");
		super.onDestroy();
	}

}
