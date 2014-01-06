package com.example.sample2actionbartest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class FragmentTwo extends SherlockFragment {
	ActionBar actionBar;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		TextView tv = new TextView(getActivity());
		tv.setText("two");
		actionBar = ((SherlockFragmentActivity)getActivity()).getSupportActionBar();
		return tv;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		actionBar.setTitle("MyTab");
		actionBar.setDisplayHomeAsUpEnabled(false);
	}
}
