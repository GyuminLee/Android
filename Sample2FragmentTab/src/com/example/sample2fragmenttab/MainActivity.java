package com.example.sample2fragmenttab;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;

public class MainActivity extends FragmentActivity {

	FragmentTabHost tabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tabHost = (FragmentTabHost) findViewById(R.id.tabHost);
		tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("one"),
				FragmentOne.class, null);
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("two"),
				FragmentTwo.class, null);
		Bundle b = new Bundle();
		b.putString("message", "tab fragment...");
		tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("three"),
				FragmentThree.class, b);
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				if (tabId.equals("tab1")) {
					// ..
				}
			}
		});
		Toast.makeText(this, "onCreate....", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Toast.makeText(this, "onCreate....", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onBackPressed() {
		if (!getSupportFragmentManager().findFragmentByTag(tabHost.getCurrentTabTag()).getChildFragmentManager().popBackStackImmediate()) {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
