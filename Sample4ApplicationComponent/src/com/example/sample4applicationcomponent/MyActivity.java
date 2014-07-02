package com.example.sample4applicationcomponent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MyActivity extends Activity {

	/** Called when the activity is first created. */
	TextView message;
	
	public final static String PARAM_NAME = "name";
	public final static String PARAM_AGE = "age";
	public final static String PARAM_RESULT = "result";
	
	String name;
	int age;
	EditText editView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.my_activity);
	    message = (TextView)findViewById(R.id.textView1);
	    editView = (EditText)findViewById(R.id.editText1);
	    Intent i = getIntent();
	    name = i.getStringExtra(PARAM_NAME);
	    age = i.getIntExtra(PARAM_AGE, 0);
	    message.setText("name : "+name);
	    Button btn = (Button)findViewById(R.id.button1);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int middle = Integer.parseInt(editView.getText().toString());
				String message = null;
				if (age < middle) {
					message = name + " is young";
				} else {
					message = name + " is old";
				}
				Intent data = new Intent();
				data.putExtra(PARAM_RESULT, message);
				
				setResult(Activity.RESULT_OK, data);
				
				finish();
			}
		});
	}

}
