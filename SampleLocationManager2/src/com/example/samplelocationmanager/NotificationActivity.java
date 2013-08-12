package com.example.samplelocationmanager;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;

public class NotificationActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    Intent i = getIntent();
	    Address addr = i.getParcelableExtra(ProximityService.PARAM_ADDRESS);
	    setTitle(addr.getLocality());
	    
	}

}
