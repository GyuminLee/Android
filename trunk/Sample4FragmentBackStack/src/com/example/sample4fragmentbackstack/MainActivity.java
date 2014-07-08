package com.example.sample4fragmentbackstack;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

public class MainActivity extends FragmentActivity {

	Fragment[] fragmentList = new Fragment[] { new FragmentOne(),
			new FragmentTwo(), new FragmentThree() };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getSupportFragmentManager().beginTransaction()
				.add(R.id.container, new FragmentBase()).commit();
		
		Button btn = (Button)findViewById(R.id.btn_next);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int count = getSupportFragmentManager().getBackStackEntryCount();
				if (count < fragmentList.length) {
					FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
					ft.setCustomAnimations(R.anim.view_set, R.anim.view_translate, R.anim.abc_fade_in, R.anim.abc_fade_out);
					ft.replace(R.id.container, fragmentList[count]);
					ft.addToBackStack("backstack"+count);
					ft.commit();
				} else {
					// backstack pop all
					getSupportFragmentManager().popBackStack("backstack0", FragmentManager.POP_BACK_STACK_INCLUSIVE);
				}
			}
		});
		
		btn = (Button)findViewById(R.id.btn_prev);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int count = getSupportFragmentManager().getBackStackEntryCount();
				if (count > 0) {
					getSupportFragmentManager().popBackStack();
				}
			}
		});
	}
}
