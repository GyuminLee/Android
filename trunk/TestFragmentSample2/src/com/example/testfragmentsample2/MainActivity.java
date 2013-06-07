package com.example.testfragmentsample2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends FragmentActivity {

	View container;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		container = findViewById(R.id.container);
		Button btn = (Button)findViewById(R.id.f1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentF1 f = new FragmentF1();
				Bundle b = new Bundle();
				b.putString("count", "20");
				f.setArguments(b);
				f.setOnFragmentButtonClickListener(new FragmentF1.OnFragmentButtonClickListener() {
					
					@Override
					public void onButtonClick(Fragment f) {
						// TODO Auto-generated method stub
						// ...
					}
				});
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				ft.replace(R.id.container, f);
				ft.commit();
			}
		});
		
		btn = (Button)findViewById(R.id.f2);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentF2 f = new FragmentF2();
				f.setHasOptionsMenu(true);
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				ft.replace(R.id.container, f);
				ft.commit();
			}
		});
		
		FragmentF1 f = new FragmentF1();
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(R.id.container, f);
		ft.commit();		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
