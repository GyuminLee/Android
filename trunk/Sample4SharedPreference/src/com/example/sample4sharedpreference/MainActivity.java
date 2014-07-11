package com.example.sample4sharedpreference;

import com.example.sample4sharedpreference.manager.PropertyManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	EditText userIdView;
	EditText passwordView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		userIdView = (EditText)findViewById(R.id.editText1);
		passwordView = (EditText)findViewById(R.id.editText2);
		userIdView.setText(PropertyManager.getInstance().getUserId());
		passwordView.setText(PropertyManager.getInstance().getPassword());
		
		Button btn = (Button)findViewById(R.id.btn_ok);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				PropertyManager.getInstance().setUserId(userIdView.getText().toString());
				PropertyManager.getInstance().setPassword(passwordView.getText().toString());
			}
		});
	}
}
