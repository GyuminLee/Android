package com.example.sample4fragmenttab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.widget.TabHost;

public class MainActivity extends FragmentActivity {

	FragmentTabHost tabHost;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tabHost = (FragmentTabHost)findViewById(R.id.tabhost);
		tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("TAB1"), Tab1Fragment.class, null);
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("TAB2"), Tab2Fragment.class, null);
		tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("TAB3"), Tab3Fragment.class, null);
		tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				if (tabId.equals("tab1")) {
					
				} else if (tabId.equals("tab2")) {
					
				} else if (tabId.equals("tab3")) {
				
				}
			}
		});
//		tabHost.setCurrentTabByTag("tab2");
	}
	
	@Override
	public void onAttachFragment(Fragment fragment) {
		super.onAttachFragment(fragment);
		if (fragment instanceof Tab1Fragment) {
			Tab1Fragment f = (Tab1Fragment)fragment;
			// register listener....
		}
	}
}
