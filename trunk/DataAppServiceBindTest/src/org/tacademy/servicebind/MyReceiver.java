package org.tacademy.servicebind;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class MyReceiver extends BroadcastReceiver {

	public interface OnEventArrivedListener {
		public void onEventArrived(Intent i);
	}
	
	OnEventArrivedListener listener;
	
	public void setOnEventArrivedListener(OnEventArrivedListener l) {
		listener = l;
	}
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if (listener != null) {
			listener.onEventArrived(intent);
		}

	}

}
