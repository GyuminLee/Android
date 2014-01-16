package com.example.sample2location;

import android.app.Activity;
import android.location.Address;
import android.os.Bundle;
import android.widget.TextView;

public class AlertActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    setContentView(R.layout.alert_layout);
	    TextView tv = (TextView)findViewById(R.id.textView1);
	    
	    Address address = getIntent().getParcelableExtra("address");
	    tv.setText(address.toString());
	}

}
