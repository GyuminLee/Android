package com.example.sample3fragmentbackstack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentBase extends Fragment {

	TextView textView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		TextView view = new TextView(getActivity());
		view.setText("FragmentBase");
		textView = view;
		return view;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
			String result = data.getStringExtra("result");
			textView.setText(result);
		}
	}
}
