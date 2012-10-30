package org.tacademy.basic.sampletabfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.TabHost;

public class TabFragmentActivity extends FragmentActivity {
	TabManager mTabManager;
	int mContentId;
	TabHost mTabHost;
	Bundle mSaveInstanceState;
	
	@Override
	protected void onCreate(Bundle saveInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(saveInstanceState);
		if (saveInstanceState != null) {
			mSaveInstanceState = saveInstanceState;
		}
	}

	protected void setupTab(int contentId) {

		mTabHost = (TabHost)findViewById(android.R.id.tabhost);
		mTabHost.setup();
		mContentId = contentId;
        mTabManager = new TabManager(this,mTabHost,contentId);
        if (mSaveInstanceState != null) {
        	mTabManager.restoreTabInfo(mSaveInstanceState);
        }
	}
	
	protected TabHost.TabSpec getNewTabSpec(String tag) {
		return mTabHost.newTabSpec(tag);
	}
	
	protected TabHost getTabHost() {
		return mTabHost;
	}
	
	protected void setCurrentTab(int index) {
		mTabHost.setCurrentTab(index);
	}
	
	protected void setCurrentTabByTag(String tag) {
		mTabHost.setCurrentTabByTag(tag);
	}

	protected void addTab(TabHost.TabSpec tabSpec,Class fragmentClass, Bundle b) {
		addTab(tabSpec, fragmentClass, b, true);
	}
	
	protected void addTab(TabHost.TabSpec tabSpec,Class fragmentClass,Bundle b,boolean isFirstShow) {
		mTabManager.addTab(tabSpec, fragmentClass, b, isFirstShow);
	}
	
	public void addFragmentAtCurrentTab(TabFragment f) {
		mTabManager.addFragmentAtCurrentTab(f);
	}
	
	public void addFragmentStackAtCurrentTab(TabFragment f) {
		mTabManager.addFragmentStackAtCurrentTab(f);
	}
	
	public boolean popBackStackAtCurrentTab(Fragment f, Bundle b) {
		return mTabManager.backStackFragmentAtCurrentTab(f,b);
	}
	
	public void moveFirstFragmentAtCurrentTab() {
		mTabManager.moveFirstFragmentAtCurrentTab();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		boolean isBackProcessed = mTabManager.backPressed();
		if (isBackProcessed == false ) {
			boolean isSuccess = mTabManager.backStackFragmentAtCurrentTab();
			if (!isSuccess) {
				super.onBackPressed();
			}
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		if (mTabManager != null) {
			mTabManager.saveTabInfo(outState);
		}
	}
	
}
