package com.example.sample4menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	TextView tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv = (TextView)findViewById(R.id.message_view);
		registerForContextMenu(tv);
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
		}
		return true;
	}
}
