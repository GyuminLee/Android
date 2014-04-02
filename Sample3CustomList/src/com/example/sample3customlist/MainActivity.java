package com.example.sample3customlist;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	ArrayList<MyData> mItems = new ArrayList<MyData>();
	int[] iconList = { R.drawable.gallery_photo_1, 
						R.drawable.gallery_photo_2,
						R.drawable.gallery_photo_3,
						R.drawable.gallery_photo_4,
						R.drawable.gallery_photo_5,
						R.drawable.gallery_photo_6,
						R.drawable.gallery_photo_7,
					};
	ListView listView;
	MyAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		makeData();
		listView = (ListView)findViewById(R.id.listView1);
		mAdapter = new MyAdapter(this, mItems);
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				MyData d = (MyData)listView.getItemAtPosition(position);
				Toast.makeText(MainActivity.this, "name : " + d.name, Toast.LENGTH_SHORT).show();				
			}
		});
		
		// adapter
		// listview ...

//		if (savedInstanceState == null) {
//			getSupportFragmentManager().beginTransaction()
//					.add(R.id.container, new PlaceholderFragment()).commit();
//		}
	}
	
	private void makeData() {
		for (int i = 0; i < 10; i++) {
			MyData d = new MyData();
			d.name = "name " + i;
			d.desc = "desc " + i;
			d.resId = iconList[i % iconList.length];
			mItems.add(d);
		}
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
