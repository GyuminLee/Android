package com.example.sample2fragmentargumentpassing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class FragmentTwo extends Fragment {
	EditText keywordView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.f2_layout, container, false);
		keywordView = (EditText)v.findViewById(R.id.keywordView);
		Button btn = (Button)v.findViewById(R.id.btnSend);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Fragment target = getTargetFragment();
				int requestCode = getTargetRequestCode();
				Intent i = new Intent();
				i.putExtra("keyword", keywordView.getText().toString());
				target.onActivityResult(requestCode, Activity.RESULT_OK, i);
				getFragmentManager().popBackStack();
			}
		});
		return v;
	}
}
