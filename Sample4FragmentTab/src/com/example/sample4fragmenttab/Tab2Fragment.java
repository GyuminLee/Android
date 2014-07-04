package com.example.sample4fragmenttab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class Tab2Fragment extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Toast.makeText(getActivity(), "Tab2 onCreate", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.tab2_layout, container, false);
		Toast.makeText(getActivity(), "Tab2 onCreateView", Toast.LENGTH_SHORT).show();
		return v;
	}

}
