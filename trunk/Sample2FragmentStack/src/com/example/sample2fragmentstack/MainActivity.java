package com.example.sample2fragmentstack;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends FragmentActivity {

	Fragment[] fragmentArray = {new FragmentOne(), new FragmentTwo(), new FragmentThree() };
	FragmentManager fragmentManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button)findViewById(R.id.prev);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int stackCount = fragmentManager.getBackStackEntryCount();
				if (stackCount > 0) {
					fragmentManager.popBackStack();
				}
			}
		});
		
		btn = (Button)findViewById(R.id.next);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int stackCount = fragmentManager.getBackStackEntryCount();
				if (stackCount >= fragmentArray.length) {
					fragmentManager.popBackStack("backstack0", FragmentManager.POP_BACK_STACK_INCLUSIVE);
				} else {
					FragmentTransaction ft = fragmentManager.beginTransaction();
					ft.replace(R.id.container, fragmentArray[stackCount]);
					ft.addToBackStack("backstack"+stackCount);
					ft.commit();
				}
			}
		});
		
		FragmentBase f = new FragmentBase();
		fragmentManager = getSupportFragmentManager();
		FragmentTransaction ft = fragmentManager.beginTransaction();
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
