package com.example.samplesupport7actionbar;

import java.util.List;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	ActionBar actionBar;
	
	String[] items = {"item1","item2","item3"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		ActionBar.Tab tab = actionBar.newTab();
		tab.setText("Tab1");
		tab.setIcon(android.R.drawable.ic_search_category_default);
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
		tab.setText("Tab2");
		tab.setIcon(R.drawable.ic_launcher);
		tab.setTabListener(new ActionBar.TabListener() {
			
			@Override
			public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				FragmentTwo f = new FragmentTwo();
				ft.replace(R.id.container, f);
			}
			
			@Override
			public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
				// TODO Auto-generated method stub
				
			}
		});
		actionBar.addTab(tab);
		actionBar.setDisplayHomeAsUpEnabled(true);
//		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, items);
//		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		actionBar.setListNavigationCallbacks(adapter, new ActionBar.OnNavigationListener() {
//			
//			@Override
//			public boolean onNavigationItemSelected(int position, long id) {
//				Toast.makeText(MainActivity.this, items[position], Toast.LENGTH_SHORT).show();
//				return true;
//			}
//		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		MenuItem item = menu.findItem(R.id.item1);
		SearchView view = (SearchView)MenuItemCompat.getActionView(item);
		view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String text) {
				Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(String text) {
				return false;
			}
		});
		SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
		if (searchManager != null) {
			List<SearchableInfo> searchable = searchManager.getSearchablesInGlobalSearch();
			SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
			for (SearchableInfo inf : searchable) {
				if (inf.getSuggestAuthority() != null && inf.getSuggestAuthority().startsWith("applications")) {
					info = inf;
				}
			}
			view.setSearchableInfo(info);
		}
		return true;
	}

}
