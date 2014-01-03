package com.example.sample2fragmenttest;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

public class OtherActivity extends FragmentActivity {

	FragmentTwo fragment;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    setContentView(R.layout.other_layout);
	    initFragment();
	}
	
	private void initFragment() {
		if (fragment == null) {
		    fragment = (FragmentTwo)getSupportFragmentManager().findFragmentById(R.id.fragment1);
		    fragment.showMessage();
		}
	}
	
}
