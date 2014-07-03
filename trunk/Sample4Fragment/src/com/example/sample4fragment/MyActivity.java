package com.example.sample4fragment;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

public class MyActivity extends FragmentActivity {

	F2Fragment f2;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.my_activity);
	    f2 = (F2Fragment)getSupportFragmentManager().findFragmentById(R.id.fragment1);
	    f2.log();
	}

}
