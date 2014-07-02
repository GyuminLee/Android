package com.example.sample4autocompletetextview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class MainActivity extends Activity {

	AutoCompleteTextView autoTextView;
	ArrayAdapter<String> mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		autoTextView = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1);
		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.items));
		autoTextView.setAdapter(mAdapter);
	}
}
