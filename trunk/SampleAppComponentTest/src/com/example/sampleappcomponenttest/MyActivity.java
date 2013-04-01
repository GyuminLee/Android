package com.example.sampleappcomponenttest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyActivity extends Activity {
	
	public static final String PARAM_FIELD_NAME = "name";
	public static final String PARAM_FIELD_AGE = "age";
	public static final String PARAM_RESULT_FIELD_MESSAGE = "message";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    // TODO Auto-generated method stub
	    setContentView(R.layout.my_activity_layout);
	    Intent i = getIntent();
	    String name = i.getStringExtra(PARAM_FIELD_NAME);
	    int age = i.getIntExtra(PARAM_FIELD_AGE, 0);
	    TextView message = (TextView)findViewById(R.id.message);
	    message.setText("name : " + name + ", age : " + age);
	    Button btn = (Button)findViewById(R.id.button1);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent resultIntent = new Intent();
				resultIntent.putExtra(PARAM_RESULT_FIELD_MESSAGE, "process ok");
				setResult(Activity.RESULT_OK, resultIntent);
				finish();
			}
		});
	}

}
