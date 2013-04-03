package com.example.sampleviewpagertest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyAdapter extends FragmentPagerAdapter {

	public MyAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		Fragment f;
		switch (position) {
			case 0:
				f = new OneFragment();
				break;
			case 1:
				f = new TwoFragment();
				break;
			case 2:
				f = new ThreeFragment();
				break;
			default:
				f = new OneFragment();
				break;
		}
		return f;
	}

	@Override
	public int getCount() {
		return 3;
	}

}
