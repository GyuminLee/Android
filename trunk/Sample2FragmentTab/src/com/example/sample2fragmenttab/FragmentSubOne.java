package com.example.sample2fragmenttab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentSubOne extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		TextView tv = new TextView(getActivity());
		tv.setText("sub one");
		tv.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentOne f = (FragmentOne)getTargetFragment();
				int code = getTargetRequestCode();
				Intent data = new Intent();
				data.putExtra("next", 1);
				f.onActivityResult(code, Activity.RESULT_OK, data);
			}
		});
		return tv;
	}
}
