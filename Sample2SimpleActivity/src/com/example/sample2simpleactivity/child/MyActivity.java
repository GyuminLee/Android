package com.example.sample2simpleactivity.child;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sample2simpleactivity.R;

public class MyActivity extends Activity {

	public static final String PARAM_NAME = "name";
	public static final String PARAM_AGE = "age";
	public static final String PARAM_RESULT = "result";
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.child_layout);
		Intent intent = getIntent();
		String name = intent.getStringExtra(PARAM_NAME);
		int age = intent.getIntExtra(PARAM_AGE, 0);
		Toast.makeText(this, "name : " + name + ", age : " + age,
				Toast.LENGTH_SHORT).show();
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent result = new Intent();
				result.putExtra(PARAM_RESULT, "old....");
				setResult(RESULT_OK, result);
				finish();
			}
		});
	}

}
