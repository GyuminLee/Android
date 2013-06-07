package com.example.testviewpagersample2;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyPagerAdapter extends PagerAdapter {
	
	ArrayList<View> mViewList = new ArrayList<View>();
	
	public MyPagerAdapter(Context context) {
		TextView tv = new TextView(context);
		tv.setText("View #1");
		mViewList.add(tv);
		tv = new TextView(context);
		tv.setText("View #2");
		mViewList.add(tv);
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View v = mViewList.get(position);
		container.addView(v);
		return v;
	}
	

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		View v = (View)object;
		container.removeView(v);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mViewList.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

}
