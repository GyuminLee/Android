package com.example.sample3childfragmentmanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentOneChild1 extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_one_child1, container, false);
		Button btn = (Button)v.findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentManager fm = getParentFragment().getChildFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				FragmentOneChild2 f = new FragmentOneChild2();
				ft.replace(R.id.childcontainer, f);
				ft.commit();
			}
		});
		return v;
	}
}
