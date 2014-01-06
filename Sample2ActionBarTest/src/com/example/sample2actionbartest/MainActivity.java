package com.example.sample2actionbartest;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.actionbarsherlock.widget.SearchView.OnQueryTextListener;

public class MainActivity extends SherlockFragmentActivity {

	ActionBar actionBar;
	ArrayAdapter<String> mAdapter;
	String[] mData = {"item1","item2","item3"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		actionBar = getSupportActionBar();
		actionBar.setTitle("SherlockTest");
//		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
//		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mData);
//		mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		actionBar.setListNavigationCallbacks(mAdapter, new ActionBar.OnNavigationListener() {
//			
//			@Override
//			public boolean onNavigationItemSelected(int itemPosition, long itemId) {
//				String text = mAdapter.getItem(itemPosition);
//				Toast.makeText(MainActivity.this, "selected : " + text, Toast.LENGTH_SHORT).show();
//				return false;
//			}
//		});
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		ActionBar.Tab tab = actionBar.newTab();
		tab.setIcon(android.R.drawable.ic_menu_search);
		tab.setText("search");
		tab.setTabListener(new ActionBar.TabListener() {
			
			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				
			}
			
			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				FragmentOne f = new FragmentOne();
				ft.replace(R.id.container, f);
			}
			
			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				
			}
		});
		actionBar.addTab(tab);
		tab = actionBar.newTab();
		tab.setIcon(R.drawable.ic_launcher);
		tab.setText("mytab");
		tab.setTabListener(new ActionBar.TabListener() {
			
			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				FragmentTwo f = new FragmentTwo();
				ft.replace(R.id.container, f);
			}
			
			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}
		});
		actionBar.addTab(tab);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main, menu);
		MenuItem item = menu.findItem(R.id.menuSearch);
		SearchView view = (SearchView)item.getActionView();
		view.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String query) {
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {
				return false;
			}
		});
		return true;
	}

}
