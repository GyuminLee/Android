package com.example.sampletabpagertest;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class OneFragment extends PagerFragment {
	boolean isInitialized = false;
	boolean isSetStartFragment = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_one, container, false);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initFragment();
	}
	@Override
	public void onPageCurrent() {
		super.onPageCurrent();
		initFragment();
	}
	
	private void initFragment() {
		if (!isInitialized) {
			FragmentManager fm = getChildFragmentManager();
			int count = fm.getBackStackEntryCount();
			for (int i = 0; i < count; i++) {
				fm.popBackStack();
			}
			Fragment ff = fm.findFragmentByTag("first");
			if (ff == null) {
				FragmentTransaction ft = fm.beginTransaction();
				FragmentSubOne f = new FragmentSubOne();
				f.setTargetFragment(this, 0);
				ft.replace(R.id.subContainer, f, "first");
				ft.commit();
			}
			isInitialized = isSetStartFragment;
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
			int next = data.getIntExtra("next", -1);
			if (next == 1) {
				FragmentTransaction ft = getChildFragmentManager()
						.beginTransaction();
				FragmentSubTwo f = new FragmentSubTwo();
				f.setTargetFragment(this, 1);
				ft.replace(R.id.subContainer, f, "sf1");
				ft.addToBackStack("sub1");
				ft.commit();
			}
		} else if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
			getChildFragmentManager().popBackStack();
		}
	}	
}
