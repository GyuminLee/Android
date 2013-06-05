package com.example.testmenusample2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	TextView messageView;
	ImageView imageView;
	
	private static final int MENU_ID_ONE = Menu.FIRST;
	private static final int MENU_ID_TWO = Menu.FIRST + 1;
	private static final int MENU_ID_THREE = Menu.FIRST + 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		messageView = (TextView)findViewById(R.id.message);
		imageView = (ImageView)findViewById(R.id.imageView1);
		registerForContextMenu(messageView);
		registerForContextMenu(imageView);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		
		menu.add(Menu.NONE, MENU_ID_ONE, 0, "Context One");
		switch(v.getId()) {
		case R.id.message :
			menu.add(Menu.NONE, MENU_ID_TWO, 0, "Context Two");
			break;
		case R.id.imageView1 :
			menu.add(Menu.NONE, MENU_ID_THREE, 0, "Context Three");
			break;
		}
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()) {
		case MENU_ID_ONE :
			Toast.makeText(this, "Context Menu One Click", Toast.LENGTH_SHORT).show();
			return true;
		case MENU_ID_TWO :
			Toast.makeText(this, "Context Menu Two Click", Toast.LENGTH_SHORT).show();
			return true;
		case MENU_ID_THREE :
			Toast.makeText(this, "Context Menu Three Click", Toast.LENGTH_SHORT).show();
			return true;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()) {
		case R.id.activity_start :
			Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
			startActivity(i);
			return true;
		case R.id.menuItem2 :
			Toast.makeText(this, "Menu Item 2 Click", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.item1 :
			return true;
		case R.id.item2 :
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
