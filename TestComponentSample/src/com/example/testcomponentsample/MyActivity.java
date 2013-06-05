package com.example.testcomponentsample;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class MyActivity extends Activity {
	
	public static final String PARAM_PERSON = "person";
	public static final String PARAM_NAME = "name";
	public static final String PARAM_AGE = "age";
	public static final String RESULT_PARAM_STATUS = "status";
	
	String status;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.my_activity_layout);
	    Configuration config = getResources().getConfiguration();
	    if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
	    	getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    }
	    Intent intent = getIntent();
	    Person p = intent.<Person>getParcelableExtra(PARAM_PERSON);
	    String name = p.name;
	    int age = p.age;
//	    String name = intent.getStringExtra(PARAM_NAME);
//	    int age = intent.getIntExtra(PARAM_AGE, -1);
	    
	    Toast.makeText(this, "name : " + name + ",age : " + age, Toast.LENGTH_SHORT).show();
	    if (age < 25) {
	    	status = "happy";
	    } else if (age >= 25 && age < 30) {
	    	status = "so so";
	    } else {
	    	status = "sad";
	    }
	    
	    Button btn = (Button)findViewById(R.id.finish);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent resultIntent = new Intent();
				resultIntent.putExtra(RESULT_PARAM_STATUS, status);
				setResult(Activity.RESULT_OK, resultIntent);
				finish();
			}
		});
	
	}

}
