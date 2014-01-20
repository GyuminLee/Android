package com.example.sample2alarm;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;

public class AlarmActivity extends Activity {
	Vibrator mVibrator;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.alarm_layout);
	    mVibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
	    long[] pattern = {200,500, 400, 200};
	    mVibrator.vibrate(pattern, 10);
	}

}
