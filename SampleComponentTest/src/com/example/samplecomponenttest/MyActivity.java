package com.example.samplecomponenttest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MyActivity extends Activity {

	String name;
	int age;
	
	public static final String PARAM_NAME = "name";
	public static final String PARAM_AGE = "age";
	public static final String RESULT_AGE = "resultAge";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.my_activity_layout);
	    Intent i = getIntent();
	    name = i.getStringExtra(PARAM_NAME);
	    age = i.getIntExtra(PARAM_AGE, 0);
	    Toast.makeText(this, "name : " + name + ", age : " + age, Toast.LENGTH_SHORT).show();
	    Button btn = (Button)findViewById(R.id.button1);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent result = new Intent();
				result.putExtra(RESULT_AGE, age+10);
				setResult(RESULT_OK, result);
				finish();
			}
		});
	}

}
