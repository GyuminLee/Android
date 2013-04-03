package com.example.sampleactionbartest2;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class MainActivity extends Activity implements ActionBar.TabListener {

	String[] mItems = {"item1", "item2", "item3", "item4"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ActionBar actionBar = getActionBar();
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

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		ActionBar.Tab tab = actionBar.newTab();
		tab.setText("tab1");
		tab.setTabListener(this);
		actionBar.addTab(tab);
		
		tab = actionBar.newTab();
		tab.setText("tab2");
		tab.setTabListener(this);
		actionBar.addTab(tab);
	
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
