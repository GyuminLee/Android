package com.example.sample4menu;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	TextView tv;
	ActionBar actionBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv = (TextView)findViewById(R.id.message_view);
		registerForContextMenu(tv);
//		actionBar = getSupportActionBar();
//		actionBar.setTitle("title");
//		actionBar.setDisplayHomeAsUpEnabled(true);
//		actionBar.setHomeButtonEnabled(true);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		switch(v.getId()) {
		case R.id.message_view :
			getMenuInflater().inflate(R.menu.context_menu, menu);
			break;
		}
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.context_menu_one :
			Toast.makeText(this, "context one", Toast.LENGTH_SHORT).show();
			break;
		case R.id.context_menu_two :
			Toast.makeText(this, "context two", Toast.LENGTH_SHORT).show();
			break;
		}
		return true;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		MenuItem item = menu.findItem(R.id.menu_setting);
		SearchView view = (SearchView)MenuItemCompat.getActionView(item);
		view.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String arg0) {
				Toast.makeText(MainActivity.this, "keyword : " + arg0, Toast.LENGTH_SHORT).show();
				return true;
			}
			
			@Override
			public boolean onQueryTextChange(String arg0) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.menu_setting :
			Toast.makeText(this, "setting", Toast.LENGTH_SHORT).show();
			break;
		case R.id.menu_profile :
			Toast.makeText(this, "profile", Toast.LENGTH_SHORT).show();
			break;
		case R.id.sub_menu_one :
			Toast.makeText(this, "sub one", Toast.LENGTH_SHORT).show();
			break;
		case R.id.sub_menu_two :
			Toast.makeText(this, "sub two", Toast.LENGTH_SHORT).show();
			break;
		case android.R.id.home :
			Toast.makeText(this, "home click", Toast.LENGTH_SHORT).show();
			break;
		}
		return true;
	}
}
