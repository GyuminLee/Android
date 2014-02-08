package com.example.sample2fragmenttab;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class ChildFragment extends Fragment {
	private static final String PARAM_REQUEST_CODE = "requestCode";
	private static final String PARAM_REQUEST_FRAGMENT = "requestFragment";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			int code = savedInstanceState.getInt(PARAM_REQUEST_CODE, -1);
			if (code != -1) {
				request = new ActivityRequest();
				request.requestCode = code;
				Fragment child = getChildFragmentManager().getFragment(savedInstanceState, PARAM_REQUEST_FRAGMENT);
			}
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (request != null) {
			outState.putInt(PARAM_REQUEST_CODE, request.requestCode);
			getChildFragmentManager().putFragment(outState, PARAM_REQUEST_FRAGMENT, request.child);
		}
	}
	
	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		
		Fragment parent = getParentFragment();
		if (parent != null) {
			if (parent instanceof ChildFragment) {
				((ChildFragment)parent).startActivityFromChildFragment(this, intent, requestCode);
				return;
			}
		}
		super.startActivityForResult(intent, requestCode);
	}
	
	public void startActivityFromChildFragment(Fragment child, Intent intent, int requestCode) {
		request = new ActivityRequest();
		request.child = child;
		request.requestCode = requestCode;
		requestCode = 0x00008000 | requestCode;
		Fragment parent = getParentFragment();
		if (parent != null && parent instanceof ChildFragment) {
			((ChildFragment)parent).startActivityFromChildFragment(this, intent, requestCode);
			return;
		}
		super.startActivityForResult(intent, requestCode);
	}
	
	class ActivityRequest {
		int requestCode;
		Fragment child;
	}
	
	ActivityRequest request = null;
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if ((requestCode & 0x00008000) == 0x00008000) {
			request.child.onActivityResult(request.requestCode, resultCode, data);
			return;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	public boolean onBackPressed() {
		return getChildFragmentManager().popBackStackImmediate();
	}
}
