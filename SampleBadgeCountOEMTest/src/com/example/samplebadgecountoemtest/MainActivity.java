package com.example.samplebadgecountoemtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
/*
 * 
 * This is Samsung Electronics and LG Electronics OEM Specification.
 * 
 */
public class MainActivity extends Activity {

	EditText countView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		countView = (EditText)findViewById(R.id.editText1);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int count = Integer.parseInt(countView.getText().toString());
				Intent i = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
				i.putExtra("badge_count", count);
				i.putExtra("badge_count_package_name", getApplicationContext().getPackageName());
				i.putExtra("badge_count_class_name", MainActivity.class.getName());
				sendBroadcast(i);				
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
