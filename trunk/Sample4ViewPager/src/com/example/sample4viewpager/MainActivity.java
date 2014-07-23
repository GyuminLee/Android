package com.example.sample4viewpager;

import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.TitlePageIndicator.IndicatorStyle;

import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

public class MainActivity extends ActionBarActivity {

	ViewPager pager;
	FragmentPagerAdapter mAdapter;
	CirclePageIndicator mIndicator;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		pager = (ViewPager)findViewById(R.id.pager);
		mIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
		mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
		pager.setAdapter(mAdapter);
		mIndicator.setViewPager(pager);
	}
}
