package com.example.sample3tabpager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class FragmentOne extends PagerFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_one, container, false);
		
		return v;
	}
	
	@Override
	public void onPageCurrent() {
		super.onPageCurrent();
		Toast.makeText(getActivity(), "FragmentOne", Toast.LENGTH_SHORT).show();
	}
}
