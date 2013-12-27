package com.example.helloandroid;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	EditText inputView;
	TextView messageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		inputView = (EditText) findViewById(R.id.inputText);
		messageView = (TextView) findViewById(R.id.message);
		Button btn = (Button) findViewById(R.id.send);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				messageView.setText(inputView.getText().toString());
			}
		});

		btn = (Button) findViewById(R.id.showGoogle);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri
						.parse("http://www.google.com"));
//				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people"))
//				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//				intent.setType("image/jpg");
				startActivity(intent);
			}
		});
		
		btn = (Button)findViewById(R.id.myActivity);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, MyActivity.class);
				startActivity(intent);
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
