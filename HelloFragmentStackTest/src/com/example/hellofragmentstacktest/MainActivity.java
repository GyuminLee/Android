package com.example.hellofragmentstacktest;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	Fragment[] fList = { 
			new FragmentOne(), 
			new FragmentTwo(), 
			new FragmentThree() };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		FragmentBase fb = new FragmentBase();
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.add(R.id.container, fb);
		Button btn = (Button)findViewById(R.id.btnPrev);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (getFragmentManager().getBackStackEntryCount() > 0) {
					getFragmentManager().popBackStack();
				}
				
			}
		});
		
		btn = (Button)findViewById(R.id.btnNext);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int count = getFragmentManager().getBackStackEntryCount(); 
				if (count < fList.length) {
					FragmentTransaction ft = getFragmentManager().beginTransaction();
					ft.replace(R.id.container, fList[count]);
					ft.addToBackStack("back"+count);
				} else {
					getFragmentManager().popBackStack("back0", FragmentManager.POP_BACK_STACK_INCLUSIVE);
				}
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
