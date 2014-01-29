package com.example.samplemp4example;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {

	Handler mHandler = new Handler();
	ParentExample.OnCompleteListener mListener = new ParentExample.OnCompleteListener() {
		
		@Override
		public void onSuccess() {
			Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
		}
		
		@Override
		public void onError() {
			Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new Thread(new AppendExample(mHandler, mListener)).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
