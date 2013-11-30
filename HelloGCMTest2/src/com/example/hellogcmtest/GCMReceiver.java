package com.example.hellogcmtest;

import android.support.v4.content.WakefulBroadcastReceiver;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class GCMReceiver extends WakefulBroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
	    ComponentName comp = new ComponentName(context, GcmService.class);
	    startWakefulService(context, intent.setComponent(comp));
	    setResultCode(Activity.RESULT_OK);
	}

}
