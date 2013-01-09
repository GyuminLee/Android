package com.example.testfragmentsample;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends FragmentActivity {

	FirstFragment f1;
	SecondFragment f2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		Button btn = (Button)findViewById(R.id.showf1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//				FirstFragment f = new FirstFragment();
				ft.replace(R.id.container, f1);
				ft.commit();
			}
		});
		btn = (Button)findViewById(R.id.showf2);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//				SecondFragment f = new SecondFragment();
				ft.replace(R.id.container, f2);
				ft.commit();
			}
		});
		f1 = new FirstFragment();
		f2 = new SecondFragment();
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//		FirstFragment f = new FirstFragment();
		ft.add(R.id.container, f1);
		ft.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
