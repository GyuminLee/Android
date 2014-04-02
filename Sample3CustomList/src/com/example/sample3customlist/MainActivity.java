package com.example.sample3customlist;

import java.util.ArrayList;
import java.util.Random;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sample3customlist.MyAdapter.OnAdapterItemClickListener;

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
	EditText keywordView;
	Random rnd = new Random();
	ImageView imageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		makeData();
		listView = (ListView)findViewById(R.id.listView1);
		mAdapter = new MyAdapter(this, mItems);
		mAdapter.setOnAdapterItemClickListener(new OnAdapterItemClickListener() {
			
			@Override
			public void onAdapterItemClick(View v, MyData data) {
				Toast.makeText(MainActivity.this, "image clicked : " + data.name, Toast.LENGTH_SHORT).show();
				imageView.setImageResource(data.resId);
				imageView.setVisibility(View.VISIBLE);
			}
		});
		imageView = (ImageView)findViewById(R.id.imageView1);
		imageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				imageView.setVisibility(View.GONE);
			}
		});
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				MyData d = (MyData)listView.getItemAtPosition(position);
				Toast.makeText(MainActivity.this, "name : " + d.name, Toast.LENGTH_SHORT).show();				
			}
		});
		keywordView = (EditText)findViewById(R.id.editText1);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String name = keywordView.getText().toString();
				MyData d = new MyData();
				
				d.name = getResources().getString(R.string.capName) + name;
				d.desc = "desc " + name;
				d.resId = iconList[rnd.nextInt(iconList.length)];
				
				mAdapter.add(d);
				// d... 
			}
		});
		
		listView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem + visibleItemCount == totalItemCount) {
					if (!isLoading) {
						isLoading = true;
						loadMore();
					}
				}
				
			}
		});
		// adapter
		// listview ...

//		if (savedInstanceState == null) {
//			getSupportFragmentManager().beginTransaction()
//					.add(R.id.container, new PlaceholderFragment()).commit();
//		}
	}
	
	boolean isLoading = false;
	
	private void loadMore() {
		ArrayList<MyData> moreData = new ArrayList<MyData>();
		for (int i = 0; i < 10; i++) {
			MyData d = new MyData();
			d.name = "more " + i;
			d.desc = "more desc " + i;
			d.resId = iconList[0];
		}
		
		mAdapter.addAll(moreData);
		isLoading = false;
	}
	
	private void makeData() {
		String nameCap = this.getResources().getString(R.string.capName);
		String descCap = this.getResources().getString(R.string.capDesc);
		for (int i = 0; i < 10; i++) {
			MyData d = new MyData();
			d.name = nameCap + i;
			d.desc = descCap + i;
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
