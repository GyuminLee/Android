package com.example.sample3layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class LinearLayoutActivity extends Activity {

	CheckBox checkBox;
	TextView textView;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.linear_layout);
	    textView = (TextView)findViewById(R.id.textView3);
	    checkBox = (CheckBox)findViewById(R.id.checkBox1);
	    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					textView.setVisibility(View.VISIBLE);
				} else {
					textView.setVisibility(View.INVISIBLE);
				}
				
			}
		});
	    if (checkBox.isChecked()) {
	    	textView.setVisibility(View.VISIBLE);
	    } else {
	    	textView.setVisibility(View.INVISIBLE);
	    }
	}

}
