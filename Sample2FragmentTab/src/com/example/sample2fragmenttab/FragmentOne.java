package com.example.sample2fragmenttab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class FragmentOne extends ChildFragment {
	boolean isInitialized = false;
	boolean isSetStartFragment = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		getChildFragmentManager().addOnBackStackChangedListener(
				new OnBackStackChangedListener() {

					@Override
					public void onBackStackChanged() {
						Toast.makeText(getActivity(), "backstack changed",
								Toast.LENGTH_SHORT).show();
					}
				});
	}

	View view = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_one_layout, container,
					false);
		} else {
			ViewGroup pv = (ViewGroup)view.getParent();
			pv.removeView(view);
		}
		return view;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (!isInitialized) {
			FragmentManager fm = getChildFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			FragmentSubOne f = new FragmentSubOne();
			f.setTargetFragment(this, 0);
			ft.replace(R.id.subContainer, f);
			ft.commit();
			isInitialized = isSetStartFragment;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
			int next = data.getIntExtra("next", -1);
			if (next == 1) {
				FragmentTransaction ft = getChildFragmentManager()
						.beginTransaction();
				FragmentSubTwo f = new FragmentSubTwo();
				f.setTargetFragment(this, 1);
				ft.replace(R.id.subContainer, f, "sf1");
				ft.addToBackStack("sub1");
				ft.commit();
			}
		} else if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
			getChildFragmentManager().popBackStack();
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_one, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}
}
