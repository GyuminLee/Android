package com.example.testservicesample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class EventReceiveReceiver extends BroadcastReceiver {
	
	OnEventReceivedListener mListener;
	
	public interface OnEventReceivedListener {
		public void onEventReceived(Intent i);
	}
	
	public void setOnEventReceivedListener(OnEventReceivedListener listener) {
		mListener = listener;
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if (mListener != null) {
			mListener.onEventReceived(intent);
		}
	}
}
