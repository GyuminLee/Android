package com.example.sample3fragmentbackstack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentOne extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		TextView view = new TextView(getActivity());
		view.setText("FragmentOne");
		view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Fragment target = getTargetFragment();
				int code = getTargetRequestCode();
				Intent i = new Intent();
				i.putExtra("result", "fragmentOne");
				target.onActivityResult(code, Activity.RESULT_OK, i);
			}
		});
		return view;
	}
	
}
