package com.example.hellonaveropenapi;

import android.support.v4.app.FragmentActivity;

public class ParentActivity extends FragmentActivity {
	@Override
	protected void onDestroy() {
		NetworkModel.getInstance().cleanUpRequest(this);
		super.onDestroy();
	}
}
