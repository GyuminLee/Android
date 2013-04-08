package com.example.sampleactionbartest2;

import java.util.List;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.actionbarsherlock.widget.SearchView.OnQueryTextListener;

public class MainActivity extends SherlockActivity implements ActionBar.TabListener {

	String[] mItems = {"item1", "item2", "item3", "item4"};
	ActionMode mActionMode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ActionBar actionBar = getSupportActionBar();
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems);
//		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		actionBar.setListNavigationCallbacks(adapter, new OnNavigationListener() {
//			
//			@Override
//			public boolean onNavigationItemSelected(int itemPosition, long itemId) {
//				// TODO Auto-generated method stub
//				Toast.makeText(MainActivity.this, "selected item" + mItems[itemPosition], Toast.LENGTH_SHORT).show();
//				return true;
//			}
//		});
//		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

//		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//		ActionBar.Tab tab = actionBar.newTab();
//		tab.setText("tab1");
//		tab.setTabListener(this);
//		actionBar.addTab(tab);
//		
//		tab = actionBar.newTab();
//		tab.setText("tab2");
//		tab.setTabListener(this);
//		actionBar.addTab(tab);
		

		mActionMode = startActionMode(new ActionMode.Callback() {
			
			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void onDestroyActionMode(ActionMode mode) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				// TODO Auto-generated method stub
				menu.add("item1").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
				return true;
			}
			
			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				// TODO Auto-generated method stub
				switch(item.getItemId()) {
			
				}
				mActionMode.finish();
				return true;
			}
		});
	
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.main, menu);
		MenuItem item = menu.findItem(R.id.item1);
		SearchView view = (SearchView)item.getActionView();
		view.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {
				// TODO Auto-generated method stub
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

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		if (tab.getText().equals("tab1")) {
			ft.replace(R.id.container, new TabOneFragment());
		} else if (tab.getText().equals("tab2")) {
			ft.replace(R.id.container, new TabTwoFragment());
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

}
