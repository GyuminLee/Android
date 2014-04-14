package com.example.sample3database;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sample3database.DBConstant.PersonTable;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
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
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		ListView listView;
		// ArrayAdapter<Person> mAdapter;
		SimpleCursorAdapter mAdapter;

		Cursor mCursor = null;
		
		public static final int NOT_FIXED = -1;
		int mAgeColumnIndex = NOT_FIXED;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			listView = (ListView) rootView.findViewById(R.id.listView1);
			String[] from = { DBConstant.PersonTable.NAME,
					DBConstant.PersonTable.AGE };
			int[] to = { R.id.txtName, R.id.txtAge };
			mAdapter = new SimpleCursorAdapter(getActivity(),
					R.layout.item_layout, null, from, to, 0);
			mAdapter.setViewBinder(new ViewBinder() {
				
				@Override
				public boolean setViewValue(View view, Cursor c, int columnIndex) {
					if (mAgeColumnIndex == columnIndex) {
						TextView textView = (TextView)view;
						int age = c.getInt(columnIndex);
						textView.setText("(" + age + ")");
						return true;
					}
					return false;
				}
			});
			// mAdapter = new ArrayAdapter<Person>(getActivity(),
			// android.R.layout.simple_list_item_1,
			// DBManager.getInstance().getPersonList());
			listView.setAdapter(mAdapter);
			Button btn = (Button) rootView.findViewById(R.id.btnAdd);
			btn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent i = new Intent(getActivity(), DBAddActivity.class);
					startActivity(i);
				}
			});
			return rootView;
		}

		@Override
		public void onResume() {
			super.onResume();
			
			mCursor = DBManager.getInstance().getPersonCursor();
			mAgeColumnIndex = mCursor.getColumnIndex(PersonTable.AGE);
			mAdapter.swapCursor(mCursor);
			// mAdapter.clear();
			// ArrayList<Person> list = DBManager.getInstance().getPersonList();
			// for(Person p : list) {
			// mAdapter.add(p);
			// }
		}
		
		@Override
		public void onDestroyView() {
			if (mCursor != null) {
				mCursor.close();
			}
			super.onDestroyView();
		}
	}

}
