package com.example.sample3fragmenttab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentOne extends Fragment {

	FragmentTabHost tabHost;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.tab_one, container, false);
		tabHost = (FragmentTabHost)v.findViewById(R.id.tabHost);
		tabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);
		tabHost.addTab(tabHost.newTabSpec("child1").setIndicator("ChildOne"), FragmentOneChild1.class, null);
		tabHost.addTab(tabHost.newTabSpec("child2").setIndicator("ChildTwo"), FragmentOneChild2.class, null);
		return v;
	}
}
