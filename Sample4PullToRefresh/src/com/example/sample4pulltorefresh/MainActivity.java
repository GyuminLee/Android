package com.example.sample4pulltorefresh;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class MainActivity extends Activity {

	PullToRefreshListView pullView;
	ListView listView;
	ArrayAdapter<String> mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		pullView = (PullToRefreshListView)findViewById(R.id.listView);
		listView = pullView.getRefreshableView();
		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
		listView.setAdapter(mAdapter);
		pullView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// update data...
				mHandler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						pullView.onRefreshComplete();
					}
				}, 2000);
			}
		});
		pullView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				Toast.makeText(MainActivity.this, "show last item", Toast.LENGTH_SHORT).show();
			}
		});
		
		initData();
	}
	
	Handler mHandler = new Handler();
	
	
	private void initData() {
		for (int i = 0; i < 10; i++) {
			mAdapter.add("data" + i);
		}
	}
}
