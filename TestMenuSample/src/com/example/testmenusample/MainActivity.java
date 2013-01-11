package com.example.testmenusample;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	TextView textView;
	
	final static int MENU_FIRST = Menu.FIRST + 1;
	final static int MENU_SECOND = Menu.FIRST + 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		textView = (TextView)findViewById(R.id.textView1);
		
		registerForContextMenu(textView);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		if (v == textView) {
			// menu»ý¼º...
			menu.add(0, MENU_FIRST, 1, "MenuOne");
			menu.add(0, MENU_SECOND, 2, "MenuTwo");
		} 
		
//		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public View onCreatePanelView(int featureId) {
		// TODO Auto-generated method stub
		if (featureId == Window.FEATURE_OPTIONS_PANEL) {
			TextView tv = new TextView(this);
			tv.setText("menu custom test");
			tv.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(MainActivity.this, "test", Toast.LENGTH_SHORT).show();
				}
			});
			return tv;
		}
		return super.onCreatePanelView(featureId);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()) {
		case MENU_FIRST :
			// ...
			break;
		case MENU_SECOND :
			// ...
			break;
		}
		return true;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()) {
		case R.id.menuOne :
			Toast.makeText(this, "Menu One Selected", Toast.LENGTH_SHORT).show();
			break;
		case R.id.menuTwo :
			Toast.makeText(this, "Menu Two Selected", Toast.LENGTH_SHORT).show();
			break;
		case R.id.itemThree :
			Toast.makeText(this, "Menu Three Selected", Toast.LENGTH_SHORT).show();
			break;
			
		}
		return true;
	}

}
