package com.example.sample2fragmenttab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentTwo extends ChildFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	FragmentTabHost tabHost;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_main, container, false);
		tabHost = (FragmentTabHost)v.findViewById(R.id.tabHost);
		tabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("tab1"), FragmentOne.class, null);
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("tab2"), FragmentThree.class, null);
		return v;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_two, menu);
	}
	
	@Override
	public boolean onBackPressed() {
		Fragment f = getChildFragmentManager().findFragmentByTag(tabHost.getCurrentTabTag());
		if (f instanceof ChildFragment) {
			if (((ChildFragment) f).onBackPressed()) {
				return true;
			}
		} else {
			if (f.getChildFragmentManager().popBackStackImmediate()) {
				return true;
			}
		}
		return super.onBackPressed();
	}
}
