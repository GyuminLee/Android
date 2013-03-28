package com.example.samplemenutest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView tv1;
	TextView tv2;
	
	final static int MENU_FIRST = Menu.FIRST;
	final static int MENU_SECOND = Menu.FIRST + 1;
	final static int MENU_SUB_ONE = Menu.FIRST + 2;
	final static int MENU_SUB_TWO = Menu.FIRST + 3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv1 = (TextView)findViewById(R.id.textView1);
		tv2 = (TextView)findViewById(R.id.textView2);

		registerForContextMenu(tv1);
		registerForContextMenu(tv2);
		
		Button btn = (Button)findViewById(R.id.showAlert);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog dlg = new AlertDialog.Builder(MainActivity.this).setTitle("dialog title")
						.setIcon(R.drawable.ic_launcher)
						.setMessage("dialog message")
						.setPositiveButton("YES", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								
							}
							
						})
						.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								
							}
						})
						.setNegativeButton("No", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								
							}
						})
						.create();
				dlg.show();
			}
		});
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		if (v == tv1) {
			MenuItem item = menu.add(Menu.NONE, MENU_FIRST, 0, "content menu 1");
			item.setIcon(R.drawable.ic_launcher);
			menu.add(Menu.NONE, MENU_SECOND, 0, "content menu 2");
		} else if (v == tv2) {
			menu.add(Menu.NONE, MENU_SUB_ONE, 0, "content2 menu 1");
			menu.add(Menu.NONE, MENU_SUB_TWO, 0, "content2 menu 2");
		}
		
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()) {
		case MENU_FIRST :
			break;
		case MENU_SECOND :
			break;
		case MENU_SUB_ONE :
			break;
		case MENU_SUB_TWO :
			break;
		}
		return super.onContextItemSelected(item);
	}
	
	
//	@Override
//	public View onCreatePanelView(int featureId) {
//		// TODO Auto-generated method stub
//		if (featureId == Window.FEATURE_OPTIONS_PANEL) {
//			TextView tv = new TextView(this);
//			tv.setText("menu test");
//			return tv;
//		}
//		return super.onCreatePanelView(featureId);
//	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()) {
		case R.id.firstMenu :
			break;
		case R.id.secondMenu :
			break;
		case R.id.subMenu1 :
			break;
		case R.id.subMenu2 :
			break;
		}
		return true;
	}

}
