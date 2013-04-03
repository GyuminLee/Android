package com.example.samplebackstacktest2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends FragmentActivity {

	Fragment[] flist;
	int mCurrentPosition;
	public static final int FRAGMENT_COUNT = 3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		flist= new Fragment[FRAGMENT_COUNT];
		flist[0] = new OneFragment();
		flist[1] = new TwoFragment();
		flist[2] = new ThreeFragment();
		mCurrentPosition = 0;
		BaseFragment f = new BaseFragment();
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(R.id.container, f);
		ft.commit();
		Button btn = (Button)findViewById(R.id.prev);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mCurrentPosition > 0) {
					getSupportFragmentManager().popBackStack();
					mCurrentPosition--;
				}
			}
		});
		btn = (Button)findViewById(R.id.next);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mCurrentPosition < FRAGMENT_COUNT) {
					FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
					ft.replace(R.id.container, flist[mCurrentPosition]);
					ft.addToBackStack("backstack"+mCurrentPosition);
					ft.commit();
					mCurrentPosition++;
				} else if ( mCurrentPosition ==  FRAGMENT_COUNT) {
					getSupportFragmentManager().popBackStack("backstack0", FragmentManager.POP_BACK_STACK_INCLUSIVE);
					mCurrentPosition = 0;
				}
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
