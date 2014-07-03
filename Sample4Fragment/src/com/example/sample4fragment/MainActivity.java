package com.example.sample4fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
	F1Fragment f1;
	F2Fragment f2;
	public static final String F1_TAG = "f1";
	public static final String F2_TAG = "f2";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Fragment f = getSupportFragmentManager().findFragmentByTag(
						F1_TAG);
				if (f == null) {
					FragmentTransaction ft = getSupportFragmentManager()
							.beginTransaction();
					ft.replace(R.id.container, f1, F1_TAG);

					ft.commit();
				}
			}
		});

		btn = (Button) findViewById(R.id.button2);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Fragment f = getSupportFragmentManager().findFragmentByTag(
						F2_TAG);
				if (f == null) {
					FragmentTransaction ft = getSupportFragmentManager()
							.beginTransaction();
					ft.replace(R.id.container, f2, F2_TAG);
					ft.commit();
				}
			}
		});

		btn = (Button)findViewById(R.id.button3);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, MyActivity.class);
				startActivity(i);
			}
		});
		f1 = new F1Fragment();
		f2 = new F2Fragment();
		f2.setOnReceiveMessageListener(new F2Fragment.OnReceiveMessageListener() {
			
			@Override
			public void onReceiveMessage(String message) {
				Toast.makeText(MainActivity.this, "message : " + message, Toast.LENGTH_SHORT).show();
			}
		});
		Bundle b = new Bundle();
		b.putString(F2Fragment.PARAM_MESSAGE, "test");
		f2.setArguments(b);

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, f1, F1_TAG).commit();
	}
	
}
