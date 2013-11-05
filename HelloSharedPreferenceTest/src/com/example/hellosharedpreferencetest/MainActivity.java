package com.example.hellosharedpreferencetest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	EditText userView;
	EditText passView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		userView =(EditText)findViewById(R.id.editText1);
		passView =(EditText)findViewById(R.id.editText2);
		userView.setText(PropertyManager.getInstance().getUserName());
		passView.setText(PropertyManager.getInstance().getUserPassword());
		
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String username = userView.getText().toString();
				String passwd = passView.getText().toString();
				PropertyManager.getInstance().setUserName(username);
				PropertyManager.getInstance().setUserPassword(passwd);
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
