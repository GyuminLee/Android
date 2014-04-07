package com.example.sample3viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyAdapter extends FragmentPagerAdapter {

	public MyAdapter(FragmentManager fm) {
		super(fm);
	}
	
	@Override
	public Fragment getItem(int position) {
		switch(position) {
		case 0 :
			return new FragmentOne();
		case 1 :
			return new FragmentTwo();
		}
		return null;
	}

	@Override
	public int getCount() {
		return 2;
	}

}
