package com.example.sample4viewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

	public MyFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		Bundle b = new Bundle();
		b.putInt("number", position);
		Fragment f = new NumberFragment();
		f.setArguments(b);
		return f;
	}

	@Override
	public int getCount() {
		return 5;
	}

}
