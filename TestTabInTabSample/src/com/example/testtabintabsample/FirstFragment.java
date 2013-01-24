package com.example.testtabintabsample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FirstFragment extends Fragment {

	FragmentTabHost tabHost;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_layout_one, container, false);
		tabHost = (FragmentTabHost)v.findViewById(android.R.id.tabhost);
		tabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent2);
		tabHost.addTab(tabHost.newTabSpec("one").setIndicator("one"), ChildOneFragment.class, null);
		tabHost.addTab(tabHost.newTabSpec("two").setIndicator("two"), ChildTwoFragment.class, null);
		return v;
	}
}
