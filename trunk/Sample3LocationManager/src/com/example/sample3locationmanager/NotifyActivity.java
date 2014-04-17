package com.example.sample3locationmanager;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

public class NotifyActivity extends ActionBarActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.notify_layout);
	    Intent i = getIntent();
	    boolean isEnter = i.getBooleanExtra("isEnter", false);
	    Address address = i.getParcelableExtra("address");
	    TextView messageView = (TextView)findViewById(R.id.textView1);
	    messageView.setText("isEntering : " + isEnter + "\n\r" + "address : " + address.toString());
	}

}
