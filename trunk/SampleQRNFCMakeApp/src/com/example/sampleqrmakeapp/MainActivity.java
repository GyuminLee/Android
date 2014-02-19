package com.example.sampleqrmakeapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sampleqrmakeapp.NetworkModel.OnResultListener;

public class MainActivity extends Activity {

	EditText editId, editPassword;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		editId = (EditText)findViewById(R.id.editID);
		editPassword = (EditText)findViewById(R.id.editPassword);
		Button btn = (Button)findViewById(R.id.btnLogin);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				NetworkModel.getInstance().login(editId.getText().toString(), editPassword.getText().toString(), new OnResultListener<Boolean>() {
					
					@Override
					public void onSuccess(Boolean result) {
						
					}
					
					@Override
					public void onError(int code) {
						
					}
				});
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
