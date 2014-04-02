package com.example.sample3choicelist;

import java.util.ArrayList;
import java.util.Collections;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {

	String[] data = {"item1","item2",
			"item3","item4",
			"item5","item6",
			"item7","item8",
			"item9","item10"
	};
	
	ArrayList<String> mData = new ArrayList<String>();
	ListView listView;
	MyAdapter mAdapter;
	int choiceMode = ListView.CHOICE_MODE_NONE;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Collections.addAll(mData, data);
		listView = (ListView)findViewById(R.id.listView1);
		mAdapter = new MyAdapter(this, mData);
		listView.setAdapter(mAdapter);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (choiceMode == ListView.CHOICE_MODE_NONE) {
					listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
					choiceMode = ListView.CHOICE_MODE_SINGLE;
					mAdapter.notifyDataSetChanged();
				} else if (choiceMode == ListView.CHOICE_MODE_SINGLE) {
					listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
					choiceMode = ListView.CHOICE_MODE_MULTIPLE;
					mAdapter.notifyDataSetChanged();
				} else {
					listView.setChoiceMode(ListView.CHOICE_MODE_NONE);
					choiceMode = ListView.CHOICE_MODE_NONE;
					mAdapter.notifyDataSetChanged();
				}
			}
		});
//		if (savedInstanceState == null) {
//			getSupportFragmentManager().beginTransaction()
//					.add(R.id.container, new PlaceholderFragment()).commit();
//		}
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
//	public static class PlaceholderFragment extends Fragment {
//
//		public PlaceholderFragment() {
//		}
//
//		@Override
//		public View onCreateView(LayoutInflater inflater, ViewGroup container,
//				Bundle savedInstanceState) {
//			View rootView = inflater.inflate(R.layout.fragment_main, container,
//					false);
//			return rootView;
//		}
//	}

}
