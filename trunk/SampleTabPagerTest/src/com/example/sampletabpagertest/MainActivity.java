package com.example.sampletabpagertest;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.widget.TabHost;

public class MainActivity extends FragmentActivity {

	TabHost mTabHost;
	TabsAdapter mTabsAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTabHost = (TabHost)findViewById(android.R.id.tabhost);
		mTabHost.setup();
		
		ViewPager pager = (ViewPager)findViewById(R.id.pager);
		mTabsAdapter = new TabsAdapter(this, mTabHost, pager);
		
		mTabsAdapter.addTab(mTabHost.newTabSpec("spec1").setIndicator("tab1"), OneFragment.class, null);
		mTabsAdapter.addTab(mTabHost.newTabSpec("spec2").setIndicator("tab2"), TwoFragment.class, null);
		mTabsAdapter.addTab(mTabHost.newTabSpec("spec3").setIndicator("tab3"), ThreeFragment.class, null);
		
		if (savedInstanceState != null) {
			mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
		}
		
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putString("tab", mTabHost.getCurrentTabTag());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
