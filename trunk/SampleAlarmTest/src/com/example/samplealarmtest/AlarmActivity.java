package com.example.samplealarmtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class AlarmActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    setTitle("AlarmActivity");
	    // TODO Auto-generated method stub
	    Intent intent = getIntent();
	    if ( (intent.getFlags() & Intent.FLAG_FROM_BACKGROUND) == Intent.FLAG_FROM_BACKGROUND) {
	    	Toast.makeText(this, "start Alarm", Toast.LENGTH_SHORT).show();
	    } else {
	    	Toast.makeText(this, "start Activity", Toast.LENGTH_SHORT).show();
	    }
	}

}
