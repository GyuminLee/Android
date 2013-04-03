package com.example.sampletabfragmenttest;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.widget.TabHost;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		FragmentTabHost tabs = (FragmentTabHost)findViewById(android.R.id.tabhost);
		tabs.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		tabs.addTab(tabs.newTabSpec("spec1").setIndicator("tab1"), TabOneFragment.class, null);
		tabs.addTab(tabs.newTabSpec("spec2").setIndicator("tab2"), TabTwoFragment.class, null);
		tabs.setCurrentTabByTag("spec1");
		tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				if (tabId.equals("spec1")) {
					
				} else if (tabId.equals("spec2")) {
					
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
