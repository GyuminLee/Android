package com.example.sample4childfragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button) findViewById(R.id.btn_tab1);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.container, new F1Fragment()).commit();

			}
		});
		btn = (Button) findViewById(R.id.btn_tab2);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.container, new F2Fragment()).commit();

			}
		});
		getSupportFragmentManager().beginTransaction()
				.add(R.id.container, new F1Fragment()).commit();
	}
}
