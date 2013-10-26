package com.example.samplefragmentbackstacktest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends FragmentActivity {
	int current = 0;
	Fragment[] fragmentList = {new FragmentOne(), new FragmentTwo(), new FragmentThree()};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Fragment f = new FragmentBase();
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(R.id.container, f);
		ft.commit();
		
		Button btn = (Button)findViewById(R.id.btnPrev);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (current > 0) {
					getSupportFragmentManager().popBackStack();
					current--;
				}
			}
		});
		
		btn = (Button)findViewById(R.id.btnNext);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (current < fragmentList.length) {
					Fragment f = fragmentList[current];
					FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
					ft.replace(R.id.container, f);
					ft.addToBackStack("backId"+current);
					current++;
				} else {
					getSupportFragmentManager().popBackStack("backId0", FragmentManager.POP_BACK_STACK_INCLUSIVE);
					current = 0;
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
