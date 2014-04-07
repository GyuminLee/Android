package com.example.sample3tabpager;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

public class MainActivity extends ActionBarActivity {

	TabsAdapter mAdapter;
	ViewPager pager;
	TabHost tabHost;
	
	public static final String KEY_CURRENT_TAB = "currentTab";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tabHost = (TabHost)findViewById(R.id.tabhost);
		tabHost.setup();
		pager = (ViewPager)findViewById(R.id.pager);
		mAdapter = new TabsAdapter(this, getSupportFragmentManager(), tabHost, pager);
		
		mAdapter.addTab(tabHost.newTabSpec("tab1").setIndicator("TabOne"), FragmentOne.class, null);
		mAdapter.addTab(tabHost.newTabSpec("tab2").setIndicator("TabTwo"), FragmentTwo.class, null);
		mAdapter.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				if (tabId.equals("tab1")) {
					
				} else if (tabId.equals("tab2")) {
					
				}
			}
		});
		
		if (savedInstanceState != null) {
			mAdapter.onRestoreInstanceState(savedInstanceState);
			tabHost.setCurrentTabByTag(savedInstanceState.getString(KEY_CURRENT_TAB));
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(KEY_CURRENT_TAB, tabHost.getCurrentTabTag());
		mAdapter.onSaveInstanceState(outState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
