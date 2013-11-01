package com.example.helloactionbarsherlocktest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;

public class FragmentOne extends SherlockFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		TextView tv = new TextView(getActivity());
		tv.setText("one");
		return tv;
	}
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		//inflater.inflate(menuRes, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}
}
