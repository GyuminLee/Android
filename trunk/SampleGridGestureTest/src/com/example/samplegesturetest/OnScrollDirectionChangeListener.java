package com.example.samplegesturetest;

import android.view.View;

public interface OnScrollDirectionChangeListener {
	public static final int DIRECTION_NONE = 0;
	public static final int DIRECTION_TOP_DOWN = 1;
	public static final int DIRECTION_BOTTOM_UP = 2;
	public void onScrollDirectionChanged(View view, int direction);
}
