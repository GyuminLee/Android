package com.example.sample2parcelable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MyActivity extends Activity {

	public static final String PARAM_NAME = "name";
	public static final String PARAM_AGE = "age";
	public static final String PARAM_WEIGHT = "weight";
	public static final String PARAM_PERSON = "person";
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.my_activity_layout);
	    Intent i = getIntent();
	    Person p = i.getParcelableExtra(PARAM_PERSON);
//	    p.name = i.getStringExtra(PARAM_NAME);
//	    p.age = i.getIntExtra(PARAM_AGE, 0);
//	    p.weight = i.getIntExtra(PARAM_WEIGHT, 0);
	}

}
