package com.example.helloandroid;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	TextView messageView;
	EditText keywordView;
	CheckBox checkBox;
	RadioGroup radioGroup;
	EditText editText;
	
	private final static String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		messageView = (TextView) findViewById(R.id.message);
		keywordView = (EditText) findViewById(R.id.keyword);
		Button btn = (Button) findViewById(R.id.btnOk);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String text = keywordView.getText().toString();
				messageView.setText(text);
			}
		});

		btn = (Button) findViewById(R.id.btnNewActivity);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, MyActivity.class);
				startActivity(i);
			}
		});
		btn = (Button) findViewById(R.id.btnBrowser);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_VIEW, Uri
						.parse("http://www.google.com"));
				startActivity(i);
			}
		});

		btn = (Button) findViewById(R.id.btnGetImage);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.setType("image/*");
				startActivity(i);
			}
		});

		btn = (Button) findViewById(R.id.btnShowToast);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(MainActivity.this, "Call Toast Popup",
						Toast.LENGTH_SHORT).show();

			}
		});
		checkBox = (CheckBox)findViewById(R.id.checkBox1);
		if (checkBox.isChecked()) {
			checkBox.setChecked(false);
		}
		checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					Toast.makeText(MainActivity.this, "checked", Toast.LENGTH_SHORT).show();
				}
			}
		});
		radioGroup = (RadioGroup)findViewById(R.id.radioGroup1);
		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch(checkedId) {
				case R.id.radio0 :
					break;
				case R.id.radio1 :
					break;
				case R.id.radio2 :
					break;
				}
			}
		});
		editText = (EditText)findViewById(R.id.editText1);
		editText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String str = s.toString();
				Log.i(TAG,"changed string : " + str);
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
