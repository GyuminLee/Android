package com.example.samplesupport7actionbar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class FragmentOne extends Fragment {

	FragmentTabHost tabHost;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.f1_layout, container, false);
		tabHost = (FragmentTabHost)v.findViewById(R.id.tabHost);
		tabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("CT1"), ChildFragmentOne.class, null);
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("CT2"), ChildFragmentTwo.class, null);
		return v;
	}
	
//	@Override
//	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//		super.onCreateOptionsMenu(menu, inflater);
//		inflater.inflate(R.menu.f1_menu, menu);
//	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case android.R.id.home :
			Toast.makeText(getActivity(), "Fragmet One Home Clicked", Toast.LENGTH_SHORT).show();
			return false;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
