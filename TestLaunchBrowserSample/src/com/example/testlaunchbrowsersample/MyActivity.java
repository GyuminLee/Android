package com.example.testlaunchbrowsersample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyActivity extends Activity {

	public static final String PARAM_NAME = "name";
	public static final String PARAM_AGE = "age";
	public static final String RESULT_PARAM_NAME = "resultName";
	public static final String RESULT_PARAM_AGE = "resultAge";
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    setContentView(R.layout.my_activity_layout);
	    Intent i = getIntent();
	    String name = i.getStringExtra(PARAM_NAME);
	    int age = i.getIntExtra(PARAM_AGE, -1);
	    
	    ((TextView)findViewById(R.id.textView1)).setText("name : " + name + ",age : " + age);
	    
	    Button btn = (Button)findViewById(R.id.result);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent resultIntent = new Intent();
				resultIntent.putExtra(RESULT_PARAM_NAME, "nameYSI");
				resultIntent.putExtra(RESULT_PARAM_AGE, 39);
				
				setResult(Activity.RESULT_OK,resultIntent);
				finish();
			}
		});
	}

}
