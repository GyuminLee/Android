package com.example.sample3fragmentbackstack;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sample3fragmentbackstack.FragmentThree.OnResultListener;

public class MainActivity extends ActionBarActivity {

	Fragment[] fraglist = new Fragment[] { new FragmentOne(),
			new FragmentTwo(), new FragmentThree() };

	FragmentManager fm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		FragmentThree fthree = (FragmentThree) fraglist[2];
		Bundle b = new Bundle();
		b.putString("name", "ysi");
		fthree.setArguments(b);
		fthree.setOnResultListener(new OnResultListener() {

			@Override
			public void onResult(String text) {
				Toast.makeText(MainActivity.this, "result : " + text,
						Toast.LENGTH_SHORT).show();
			}
		});
		getSupportFragmentManager().beginTransaction()
				.add(R.id.container, new FragmentBase()).commit();
		Fragment base = getSupportFragmentManager().findFragmentById(R.id.fragment1);
		
		for (int i = 0; i < fraglist.length; i++){
			Fragment f = fraglist[i];
			f.setTargetFragment(base, i);
		}
		
		fm = getSupportFragmentManager();

		Button btn = (Button) findViewById(R.id.btnPrev);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (fm.getBackStackEntryCount() > 0) {
					fm.popBackStack();
				}
			}
		});

		btn = (Button) findViewById(R.id.btnNext);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				int count = fm.getBackStackEntryCount();
				if (count < fraglist.length) {
					FragmentTransaction ft = fm.beginTransaction();
					ft.replace(R.id.container, fraglist[count]);
					ft.addToBackStack("backstack" + count);
					ft.commit();
				} else if (count == fraglist.length) {
					fm.popBackStack("backstack0",
							FragmentManager.POP_BACK_STACK_INCLUSIVE);
				}
			}
		});
		// if (savedInstanceState == null) {
		// getSupportFragmentManager().beginTransaction()
		// .add(R.id.container, new PlaceholderFragment()).commit();
		// }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	// public static class PlaceholderFragment extends Fragment {
	//
	// public PlaceholderFragment() {
	// }
	//
	// @Override
	// public View onCreateView(LayoutInflater inflater, ViewGroup container,
	// Bundle savedInstanceState) {
	// View rootView = inflater.inflate(R.layout.fragment_main, container,
	// false);
	// return rootView;
	// }
	// }

}
