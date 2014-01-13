package com.example.sample2navermovie;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;

public class ParentActivity extends FragmentActivity {

	@Override
	protected void onDestroy() {
		super.onDestroy();
		NetworkModel.getInstance().cancelRequestMap(this);
	}
	
}
