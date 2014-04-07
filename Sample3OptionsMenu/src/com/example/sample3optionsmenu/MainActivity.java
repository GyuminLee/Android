package com.example.sample3optionsmenu;

import java.util.List;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	ActionBar actionBar;
	String[] items = {"item1","item2","item3"};
	FragmentOne f1;
	PlaceholderFragment f0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		f1 = new FragmentOne();
		f0 = new PlaceholderFragment();
		actionBar = getSupportActionBar();
//		actionBar.setCustomView(R.layout.custom_title);
//		TextView titleView = (TextView)actionBar.getCustomView();
//		actionBar.setDisplayShowCustomEnabled(true);
//		actionBar.setDisplayShowTitleEnabled(true);
//		actionBar.setTitle("");
//		actionBar.setDisplayHomeAsUpEnabled(true);
//		actionBar.setDisplayShowHomeEnabled(true);
//		actionBar.setHomeButtonEnabled(true);

//		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
//		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		actionBar.setListNavigationCallbacks(adapter, new OnNavigationListener() {
//			
//			@Override
//			public boolean onNavigationItemSelected(int position, long id) {
//				Toast.makeText(MainActivity.this, "selected : " + items[position], Toast.LENGTH_SHORT).show();
//				return true;
//			}
//		});
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		ActionBar.Tab tab = actionBar.newTab();
		tab.setText("TabOne");
		tab.setIcon(android.R.drawable.ic_search_category_default);
		tab.setTabListener(new ActionBar.TabListener() {
			
			@Override
			public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
				
			}
			
			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				ft.replace(R.id.container, f0);
			}
			
			@Override
			public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
				
			}
		});
		actionBar.addTab(tab);
		tab = actionBar.newTab();
		tab.setText("TabTwo");
		tab.setIcon(R.drawable.ic_launcher);
		tab.setTabListener(new ActionBar.TabListener() {
			
			@Override
			public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onTabSelected(Tab arg0, FragmentTransaction ft) {
				ft.replace(R.id.container, f1);
			}
			
			@Override
			public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
				// TODO Auto-generated method stub
				
			}
		});
		actionBar.addTab(tab);
//		if (savedInstanceState == null) {
//			getSupportFragmentManager().beginTransaction()
//					.add(R.id.container, new PlaceholderFragment()).commit();
//		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		MenuItem item = menu.findItem(R.id.menuM1);
		SearchView searchView = (SearchView)MenuItemCompat.getActionView(item);
		searchView.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String text) {
				Toast.makeText(MainActivity.this, "keyword : " + text, Toast.LENGTH_SHORT).show();
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(String text) {
				Log.i("MainActivity","string : " + text);
				return false;
			}
		});
		SearchableInfo info = null;
		SearchManager sm = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
		List<SearchableInfo> infos = sm.getSearchablesInGlobalSearch();
		for (SearchableInfo in : infos) {
			if (in.getSuggestAuthority().startsWith("application")) {
				info = in;
			}
		}
		if (info != null) {
			searchView.setSearchableInfo(info);
		}
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		} else if (id == R.id.menuM1) {
			Toast.makeText(this, "Select menu M1", Toast.LENGTH_SHORT).show();
			return true;
		} else if (id == android.R.id.home) {
			Toast.makeText(this, "Home Button clicked", Toast.LENGTH_SHORT).show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
