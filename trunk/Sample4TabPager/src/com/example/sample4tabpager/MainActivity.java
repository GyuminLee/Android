package com.example.sample4tabpager;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.widget.TabHost;

public class MainActivity extends ActionBarActivity {

	ViewPager pager;
	TabHost tabHost;
	TabsAdapter mAdapter;
	public static final String PARAM_CURRENT_TAB = "currentTab";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tabHost = (TabHost)findViewById(R.id.tabhost);
		pager = (ViewPager)findViewById(R.id.pager);
		tabHost.setup();
		mAdapter = new TabsAdapter(this, getSupportFragmentManager(), tabHost, pager);
		
		mAdapter.addTab(tabHost.newTabSpec("tab1").setIndicator("TAB1"),Tab1Fragment.class , null);
		mAdapter.addTab(tabHost.newTabSpec("tab2").setIndicator("TAB2"),Tab2Fragment.class , null);
		mAdapter.addTab(tabHost.newTabSpec("tab3").setIndicator("TAB3"),Tab3Fragment.class , null);
		
		if (savedInstanceState != null) {
			mAdapter.onRestoreInstanceState(savedInstanceState);
			tabHost.setCurrentTabByTag(savedInstanceState.getString(PARAM_CURRENT_TAB));
		}
		
		getSupportActionBar().setDisplayShowHomeEnabled(false);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mAdapter.onSaveInstanceState(outState);
		outState.putString(PARAM_CURRENT_TAB, tabHost.getCurrentTabTag());
	}
}
