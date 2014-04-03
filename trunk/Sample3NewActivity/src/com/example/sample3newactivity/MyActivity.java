package com.example.sample3newactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MyActivity extends Activity {

	/** Called when the activity is first created. */
	TextView nameView;
	TextView ageView;
	RadioGroup group;
	
	public static final String PARAM_NAME = "name";
	public static final String PARAM_AGE = "age";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.my_activity_layout);
	    nameView = (TextView)findViewById(R.id.textName);
	    ageView = (TextView)findViewById(R.id.textAge);
	    group = (RadioGroup)findViewById(R.id.radioGroup1);
	    Intent i = getIntent();
	    String name = i.getStringExtra(PARAM_NAME);
	    int age = i.getIntExtra(PARAM_AGE, 0);
	    nameView.setText(name);
	    ageView.setText("" + age);
	    Button btn = (Button)findViewById(R.id.button1);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	public void onBackPressed() {
		//super.onBackPressed();
		Toast.makeText(this, "press button", Toast.LENGTH_SHORT).show();
	}
}
