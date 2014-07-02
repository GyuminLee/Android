package com.example.sample4fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentTransaction ft = getSupportFragmentManager()
						.beginTransaction();
				F1Fragment f = new F1Fragment();
				ft.replace(R.id.container, f);

				ft.commit();
			}
		});

		btn = (Button) findViewById(R.id.button2);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentTransaction ft = getSupportFragmentManager()
						.beginTransaction();
				F2Fragment f = new F2Fragment();
				ft.replace(R.id.container, f);
				ft.commit();
			}
		});

		F1Fragment f = new F1Fragment();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, f).commit();
	}
}
