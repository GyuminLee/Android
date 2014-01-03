package com.example.sample2servicetest;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class MyActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.my_activity);
	    Intent i = getIntent();
	    String message = i.getStringExtra("message");
	    int no = i.getIntExtra("no", 0);
	    Toast.makeText(this, "message : " + message, Toast.LENGTH_SHORT).show();
	}

}
