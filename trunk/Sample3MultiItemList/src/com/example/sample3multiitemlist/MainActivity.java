package com.example.sample3multiitemlist;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {

	ArrayList<MyData> mData = new ArrayList<MyData>();
	
	ListView listView;
	MyAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		makeData();

		listView = (ListView)findViewById(R.id.listView1);
		mAdapter = new MyAdapter(this,mData);
		listView.setAdapter(mAdapter);
//		if (savedInstanceState == null) {
//			getSupportFragmentManager().beginTransaction()
//					.add(R.id.container, new PlaceholderFragment()).commit();
//		}
	}

	private void makeData() {
		mData.add(new MyData(R.drawable.ic_launcher, "left1...", MyData.TYPE_LEFT));
		mData.add(new MyData(R.drawable.ic_launcher, "right1...", MyData.TYPE_RIGHT));
		mData.add(new MyData(R.drawable.ic_launcher, "left2...", MyData.TYPE_LEFT));
		mData.add(new MyData(R.drawable.ic_launcher, "center1...", MyData.TYPE_CENTER));
		mData.add(new MyData(R.drawable.ic_launcher, "left3...", MyData.TYPE_LEFT));
		mData.add(new MyData(R.drawable.ic_launcher, "right2...", MyData.TYPE_RIGHT));
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
//	public static class PlaceholderFragment extends Fragment {
//
//		public PlaceholderFragment() {
//		}
//
//		@Override
//		public View onCreateView(LayoutInflater inflater, ViewGroup container,
//				Bundle savedInstanceState) {
//			View rootView = inflater.inflate(R.layout.fragment_main, container,
//					false);
//			return rootView;
//		}
//	}

}
