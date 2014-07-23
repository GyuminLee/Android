package com.example.sampletabheadersectionlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

public class ItemTabWidget extends FrameLayout {

	public ItemTabWidget(Context context) {
		super(context);
		init();
	}

	public void setOnTabChangeListener(OnTabChangeListener listener) {
		tabHost.setOnTabChangedListener(listener);
	}
	
	TabHost tabHost;
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_tabwidget, this);
		tabHost = (TabHost)findViewById(android.R.id.tabhost);
		tabHost.setup();
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("TAB1").setContent(R.id.tab1));
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("TAB2").setContent(R.id.tab2));
		tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("TAB3").setContent(R.id.tab3));	
	}
	
	public void setCurrentTab(int index) {
		tabHost.setCurrentTab(index);
	}
}
