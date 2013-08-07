package com.example.samplenavermovies;

import com.example.samplenavermovies.model.NetworkManager;

import android.app.Activity;

public class ParentActivity extends Activity {

	@Override
	protected void onDestroy() {
		NetworkManager.getInstance().cancelAll(this);
		super.onDestroy();
	}
}
