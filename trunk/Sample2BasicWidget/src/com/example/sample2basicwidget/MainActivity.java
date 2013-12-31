package com.example.sample2basicwidget;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends Activity {

	RadioGroup group;

	CheckBox check;
	
	EditText editText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		group = (RadioGroup) findViewById(R.id.radiogroup1);
		check = (CheckBox)findViewById(R.id.checkBox1);
		editText = (EditText)findViewById(R.id.editText1);
		editText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
								
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				String text = s.toString();
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
		
		check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
			}
		});

		group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				String message = null;
				switch (checkedId) {
				case R.id.radioOne:
					message = "One";
					break;
				case R.id.radioTwo:
					message = "Two";
					break;
				default:
					message = "Default";
					break;
				}
				
				Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
			}
		});
		
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int id = group.getCheckedRadioButtonId();
				boolean isChecked = check.isChecked();
			}
		});

		String mmString = this.getResources().getString(R.string.mmstring);
		editText.setText(mmString);
		String[] array = this.getResources().getStringArray(R.array.strArray);
		ImageView iv = (ImageView)findViewById(R.id.imageView1);
		Drawable d = getResources().getDrawable(R.drawable.gallery_photo_2);
		iv.setImageDrawable(d);
		iv.setImageResource(R.drawable.gallery_photo_3);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
