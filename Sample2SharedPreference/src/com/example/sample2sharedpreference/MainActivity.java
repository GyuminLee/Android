package com.example.sample2sharedpreference;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	EditText idView, passwordView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		idView = (EditText)findViewById(R.id.idView);
		passwordView = (EditText)findViewById(R.id.passwordView);
		idView.setText(PropertyManager.getInstance().getName());
		passwordView.setText(PropertyManager.getInstance().getPassword());
		Button btn = (Button)findViewById(R.id.btnOk);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String id = idView.getText().toString();
				String password = passwordView.getText().toString();
				PropertyManager.getInstance().setName(id);
				PropertyManager.getInstance().setPassword(password);
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
