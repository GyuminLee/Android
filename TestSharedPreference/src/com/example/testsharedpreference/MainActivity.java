package com.example.testsharedpreference;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	EditText idView;
	EditText pwView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		idView = (EditText)findViewById(R.id.editText1);
		pwView = (EditText)findViewById(R.id.editText2);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				User user = new User();
				user.id = idView.getText().toString();
				user.pw = pwView.getText().toString();
				user.age = 0;
				PropertyManager.getInstance().setUser(user);
			}
		});
		User user = PropertyManager.getInstance().getUser();
		idView.setText(user.id);
		pwView.setText(user.pw);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
