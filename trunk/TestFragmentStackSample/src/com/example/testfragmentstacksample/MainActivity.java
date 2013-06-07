package com.example.testfragmentstacksample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	Fragment[]  fList = new Fragment[3];
	int fIndex = -1;
	public static final String BACKSTACK_PREFIX = "backstack";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		FragmentF0 f = new FragmentF0();
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(R.id.container, f);
		ft.commit();
		fList[0] = new FragmentF1();
		fList[1] = new FragmentF2();
		fList[2] = new FragmentF3();
		Button btn = (Button)findViewById(R.id.prev);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (fIndex >= 0) {
					getSupportFragmentManager().popBackStack();
					fIndex--;
				} else {
					Toast.makeText(MainActivity.this, "current is base fragment", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		btn = (Button)findViewById(R.id.next);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (fIndex >= fList.length - 1) {
					getSupportFragmentManager().popBackStack(BACKSTACK_PREFIX+"0", FragmentManager.POP_BACK_STACK_INCLUSIVE);
				} else {
					FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
					fIndex++;
					ft.replace(R.id.container, fList[fIndex]);
					ft.addToBackStack(BACKSTACK_PREFIX+fIndex);
					ft.commit();
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
