package com.example.sample3facebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

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

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			Button btn = (Button)rootView.findViewById(R.id.btnLogin);
			btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Session.openActiveSession(getActivity(), PlaceholderFragment.this, true, new Session.StatusCallback() {
						
						@Override
						public void call(Session session, SessionState state, Exception exception) {
							if (session.isOpened()) {
								Request.newMeRequest(session, new GraphUserCallback() {
									
									@Override
									public void onCompleted(GraphUser user, Response response) {
										Toast.makeText(getActivity(), "my user id : " + user.getId() , Toast.LENGTH_SHORT).show();
									}
								}).executeAsync();
							}
						}
					});
				}
			});
			return rootView;
		}
		
		@Override
		public void onActivityResult(int requestCode, int resultCode,
				Intent data) {
			Session.getActiveSession().onActivityResult(getActivity(), requestCode, resultCode, data);
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

}
