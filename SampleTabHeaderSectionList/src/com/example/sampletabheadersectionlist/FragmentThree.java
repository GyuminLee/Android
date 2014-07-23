package com.example.sampletabheadersectionlist;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.AbsListView.OnScrollListener;

public class FragmentThree extends Fragment {
	ListView listView;
	
	MyAdapter mAdapter;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.list_fragment, container, false);
		listView = (ListView)v.findViewById(R.id.listView1);
		mAdapter = new MyAdapter(getActivity(), 2 , new TabHost.OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				((MainActivity)getActivity()).setTabCurrent(tabId);
			}
		});
		listView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem == 0) {
					((MainActivity)getActivity()).setTabWidgetVisible(false);
				} else {
					((MainActivity)getActivity()).setTabWidgetVisible(true);
				}
			}
		});
		listView.setAdapter(mAdapter);
		
		initData();
		return v;
	}
	
	private void initData() {
		for (int i = 0 ; i < 100; i++) {
			mAdapter.add("tab3 item : " + i);
		}
	}

}
