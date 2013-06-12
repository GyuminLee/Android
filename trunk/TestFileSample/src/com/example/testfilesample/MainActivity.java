package com.example.testfilesample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	EditText username;
	EditText password;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		username = (EditText)findViewById(R.id.name);
		password = (EditText)findViewById(R.id.password);
		Button btn = (Button)findViewById(R.id.btnSave);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				PropertyManager.getInstance().setUserName(username.getText().toString());
				PropertyManager.getInstance().setPassword(password.getText().toString());
			}
		});
		username.setText(PropertyManager.getInstance().getUserName());
		password.setText(PropertyManager.getInstance().getPassword());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
