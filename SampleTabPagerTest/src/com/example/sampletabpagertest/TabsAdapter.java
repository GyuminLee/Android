package com.example.sampletabpagertest;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.Toast;

public class TabsAdapter extends FragmentPagerAdapter implements
		TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
	private final Context mContext;
	private final TabHost mTabHost;
	private final ViewPager mViewPager;
	private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

	static final class TabInfo {
		private final String tag;
		private final Class<?> clss;
		private final Bundle args;
		public Fragment fragment;

		TabInfo(String _tag, Class<?> _class, Bundle _args) {
			tag = _tag;
			clss = _class;
			args = _args;
		}
	}

	static class DummyTabFactory implements TabHost.TabContentFactory {
		private final Context mContext;

		public DummyTabFactory(Context context) {
			mContext = context;
		}

		@Override
		public View createTabContent(String tag) {
			View v = new View(mContext);
			v.setMinimumWidth(0);
			v.setMinimumHeight(0);
			return v;
		}
	}

	public TabsAdapter(FragmentActivity activity, TabHost tabHost,
			ViewPager pager) {
		super(activity.getSupportFragmentManager());
		mContext = activity;
		mTabHost = tabHost;
		mViewPager = pager;
		mTabHost.setOnTabChangedListener(this);
		mViewPager.setAdapter(this);
		mViewPager.setOnPageChangeListener(this);
	}

	public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
		tabSpec.setContent(new DummyTabFactory(mContext));
		String tag = tabSpec.getTag();

		TabInfo info = new TabInfo(tag, clss, args);
		mTabs.add(info);
		mTabHost.addTab(tabSpec);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mTabs.size();
	}

	@Override
	public Fragment getItem(int position) {
		TabInfo info = mTabs.get(position);
		info.fragment = Fragment.instantiate(mContext, info.clss.getName(),
				info.args);
		return info.fragment;
	}

	OnTabChangeListener mTabChangeListener;

	public void setOnTabChangedListener(OnTabChangeListener listener) {
		mTabChangeListener = listener;
	}

	@Override
	public void onTabChanged(String tabId) {
		int position = mTabHost.getCurrentTab();
		mViewPager.setCurrentItem(position);
		if (mTabChangeListener != null) {
			mTabChangeListener.onTabChanged(tabId);
		}
		Fragment f = getTabFragment(position);
		if (f instanceof PagerFragment) {
			((PagerFragment)f).onPageCurrent();
		}
	}

	public Fragment getTabFragment(int position) {
		TabInfo info = mTabs.get(position);
		if (info != null) {
			return info.fragment;
		}
		return null;
	}
	
	public Fragment getCurrentTabFragment() {
		return getTabFragment(mTabHost.getCurrentTab());
	}

	OnPageChangeListener mPageChangeListener;

	public void setOnPageChangeListener(OnPageChangeListener listener) {
		mPageChangeListener = listener;
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		if (mPageChangeListener != null) {
			mPageChangeListener.onPageScrolled(position, positionOffset,
					positionOffsetPixels);
		}
	}

	@Override
	public void onPageSelected(int position) {
		TabWidget widget = mTabHost.getTabWidget();
		int oldFocusability = widget.getDescendantFocusability();
		widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
		mTabHost.setCurrentTab(position);
		widget.setDescendantFocusability(oldFocusability);
		if (mPageChangeListener != null) {
			mPageChangeListener.onPageSelected(position);
		}
	}

	@Override
	public void onPageScrollStateChanged(int state) {
		if (mPageChangeListener != null) {
			mPageChangeListener.onPageScrollStateChanged(state);
		}
	}
}