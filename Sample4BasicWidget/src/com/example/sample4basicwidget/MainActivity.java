package com.example.sample4basicwidget;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	Button selectButton;
	CheckBox checkBox;
	TextView textView;
	RadioGroup group;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_main);
		selectButton = (Button)findViewById(R.id.button1);
		selectButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Button btn = (Button)v;
				btn.setSelected(!btn.isSelected());
			}
		});
		checkBox = (CheckBox)findViewById(R.id.checkBox1);
		textView = (TextView)findViewById(R.id.textView1);
		checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					textView.setVisibility(View.VISIBLE);
				} else {
					textView.setVisibility(View.GONE);
				}
			}
		});
		
		Button btn = (Button)findViewById(R.id.button2);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (checkBox.isChecked()) {
					String message = getResources().getString(R.string.check_true_message);
					Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
				} else {
					String message = getResources().getString(R.string.check_false_message);
					Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();					
				}
				
			}
		});
		
		if (checkBox.isChecked()) {
			textView.setVisibility(View.VISIBLE);
		} else {
			textView.setVisibility(View.INVISIBLE);
		}
		
		group = (RadioGroup)findViewById(R.id.radioGroup1);
		group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				changeVisible(checkedId);
			}
		});
		
		changeVisible(group.getCheckedRadioButtonId());
		
		btn = (Button)findViewById(R.id.check_radio);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int id = group.getCheckedRadioButtonId();
				switch(id) {
				case R.id.radio_visible:
				case R.id.radio_invisible :
				case R.id.radio_gone :
				}
			}
		});
		
		String html_message = "Android <font color=\"red\">Text</font> test";
		textView.setText(Html.fromHtml(html_message));
	}
	
	private void changeVisible(int checkedId) {
		switch(checkedId) {
		case R.id.radio_visible :
			textView.setVisibility(View.VISIBLE);
			break;
		case R.id.radio_invisible :
			textView.setVisibility(View.INVISIBLE);
			break;
		case R.id.radio_gone :
			textView.setVisibility(View.GONE);
			break;
		}
	}
} 
