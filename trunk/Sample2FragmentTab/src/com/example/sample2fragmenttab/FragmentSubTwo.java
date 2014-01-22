package com.example.sample2fragmenttab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentSubTwo extends Fragment {


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		TextView tv = new TextView(getActivity());
		tv.setText("sub two");
		tv.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Fragment f = getTargetFragment();
				int code = getTargetRequestCode();
				f.onActivityResult(code, Activity.RESULT_OK, new Intent());
			}
		});
		return tv;
	}
}
