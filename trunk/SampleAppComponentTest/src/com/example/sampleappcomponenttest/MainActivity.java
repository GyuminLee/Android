package com.example.sampleappcomponenttest;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	public static final int REQUEST_CODE_MYACTIVITY = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button)findViewById(R.id.callMyActivity);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, MyActivity.class);
				i.putExtra(MyActivity.PARAM_FIELD_NAME, "ysi");
				i.putExtra(MyActivity.PARAM_FIELD_AGE, 39);
				startActivityForResult(i , REQUEST_CODE_MYACTIVITY);
			}
		});
		
		btn = (Button)findViewById(R.id.callBrowser);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/"));
				startActivity(i);
			}
		});
		
		btn = (Button)findViewById(R.id.callContact);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people/"));
				startActivity(i);
			}
		});
		
		btn = (Button)findViewById(R.id.getPicture);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.setType("image/*");
				startActivityForResult(i,0);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == REQUEST_CODE_MYACTIVITY) {
			if (resultCode == Activity.RESULT_OK) {
				String message = data.getStringExtra(MyActivity.PARAM_RESULT_FIELD_MESSAGE);
				Toast.makeText(this, "result message : " + message, Toast.LENGTH_SHORT).show();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
