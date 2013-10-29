package com.example.helloandroidcomonenttest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MyActivity extends Activity {

	TextView messageView;
	EditText resultEdit;
	public static final String PARAM_NAME = "name";
	public static final String PARAM_AGE = "age";
	public static final String PARAM_PERSION = "person";
	public static final String RESULT_AGE = "resultAge";
	public static final String RESULT_STRING = "resultString";
	int resultAge;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
	    Intent i = getIntent();
	    Person p = i.getParcelableExtra(PARAM_PERSION);
	    String name = p.name;
	    int age = p.age;
	    messageView = (TextView)findViewById(R.id.textView1);
	    resultEdit = (EditText)findViewById(R.id.editText1);
	    messageView.setText("name : " + name + ", age : " + age);
	    resultAge = age - 10;
	    Button btn = (Button)findViewById(R.id.button1);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent resultIntent = new Intent();
				resultIntent.putExtra(RESULT_AGE, resultAge);
				resultIntent.putExtra(RESULT_STRING, resultEdit.getText().toString());
				setResult(Activity.RESULT_OK, resultIntent);
				finish();
			}
		});
	    
	}

}
