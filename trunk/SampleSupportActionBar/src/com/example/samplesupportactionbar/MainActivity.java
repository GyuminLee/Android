package com.example.samplesupportactionbar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	ActionBar actionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		actionBar = getSupportActionBar();
		
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		MenuItem item = menu.findItem(R.id.activityMenu1);
		
		SearchView view = (SearchView)MenuItemCompat.getActionView(item);
		
		view.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String arg0) {
				Toast.makeText(MainActivity.this, "Keyword : " + arg0, Toast.LENGTH_SHORT).show();
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
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		} else if (id == R.id.activityMenu1) {
			Toast.makeText(this, "Activity Menu Clicked", Toast.LENGTH_SHORT).show();
			return true;
		} else if (id == android.R.id.home) {
			Toast.makeText(this, "Home clicked", Toast.LENGTH_SHORT).show();
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
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setHasOptionsMenu(true);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
		
		@Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			super.onCreateOptionsMenu(menu, inflater);
			inflater.inflate(R.menu.fragment_menu, menu);
		}
		
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			int id = item.getItemId();
			if (id == R.id.fragment_menu1) {
				Toast.makeText(getActivity(), "fragment menu clicked", Toast.LENGTH_SHORT).show();
				return true;
			}
			return super.onOptionsItemSelected(item);
		}
	}

}
