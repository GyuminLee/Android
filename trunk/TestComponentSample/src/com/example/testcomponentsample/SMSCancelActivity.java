package com.example.testcomponentsample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SMSCancelActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    TextView tv = new TextView(this);
	    tv.setText("SMS Cancel");
	    setContentView(tv);
	    setTitle("SMS Cancel");
	}

}
