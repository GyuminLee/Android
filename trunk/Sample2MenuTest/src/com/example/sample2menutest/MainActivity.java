package com.example.sample2menutest;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView tv;
	ImageView iv;
	private final static int MENU_TEXT_VIEW = Menu.FIRST;
	private final static int MENU_IMAGE_VIEW = Menu.FIRST + 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv = (TextView)findViewById(R.id.textView1);
		iv = (ImageView)findViewById(R.id.imageView1);
		registerForContextMenu(tv);
		registerForContextMenu(iv);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		switch(v.getId()) {
		case R.id.textView1 :
			MenuItem item = menu.add(Menu.NONE, MENU_TEXT_VIEW, 0, "Text View Menu");
			break;
		case R.id.imageView1 :
			menu.add(Menu.NONE, MENU_IMAGE_VIEW, 0, "Image View Menu");
			break;
		}
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case MENU_TEXT_VIEW :
		case MENU_IMAGE_VIEW :
			return true;
		}
		return super.onContextItemSelected(item);
	}

//	@Override
//	public View onCreatePanelView(int featureId) {
//		if (featureId == Window.FEATURE_OPTIONS_PANEL) {
//			TextView tv = new TextView(this);
//			tv.setText("menu");
//			tv.setOnClickListener(new View.OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					Toast.makeText(MainActivity.this, "menu clicked", Toast.LENGTH_SHORT).show();
//				}
//			});
//			return tv;
//		}
//		return super.onCreatePanelView(featureId);
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		MenuItem item = menu.findItem(R.id.menuOne);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menuOne:
			return true;
		case R.id.menuSub:
			return true;
		case R.id.submenu1:
			return true;
		case R.id.submenu2:
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
