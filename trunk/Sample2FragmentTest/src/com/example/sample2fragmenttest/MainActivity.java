package com.example.sample2fragmenttest;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button)findViewById(R.id.btnOne);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentOne f = new FragmentOne();
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				ft.replace(R.id.container, f);
				ft.commit();
			}
		});
		
		btn = (Button)findViewById(R.id.btnTwo);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentTwo f = new FragmentTwo();
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				ft.replace(R.id.container, f);
				ft.commit();
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
