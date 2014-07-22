package com.example.samplegesturetest;

import android.view.View;
import android.widget.AdapterView;

public interface OnItemSwipeListener {
	public boolean onItemSwipe(AdapterView parent, View v, int position, int orientation);
}
