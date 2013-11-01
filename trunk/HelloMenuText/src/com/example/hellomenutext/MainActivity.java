package com.example.hellomenutext;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView messageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		messageView = (TextView)findViewById(R.id.messageView);
		
		registerForContextMenu(messageView);	
		registerForContextMenu(findViewById(R.id.imageView1));
		
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.replace(R.id.container, new FragmentOne());
				ft.commit();
			}
		});
		
		btn = (Button)findViewById(R.id.button2);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.replace(R.id.container, new FragmentTwo());
				ft.commit();
			}
		});
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		switch(v.getId()) {
		case R.id.messageView :
			getMenuInflater().inflate(R.menu.context_menu, menu);
			break;
		case R.id.imageView1 :
			getMenuInflater().inflate(R.menu.image_menu, menu);
		}
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.contextOne :
		case R.id.contextTwo :
			break;
		}
		return true;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		MenuItem item = menu.findItem(R.id.menuContent);
		SearchView view = (SearchView)item.getActionView();
		view.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String query) {
				Toast.makeText(MainActivity.this, "query : " + query, Toast.LENGTH_SHORT).show();
				return true;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {
				
				return false;
			}
		});
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.menuContent :
//			Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//			i.setType("image/*");
//			startActivityForResult(i,0);
			break;
		}
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 0 && resultCode == RESULT_OK) {
			//....
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
