package com.example.sample3facebook;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Request.GraphUserListCallback;
import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

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

		public static final List<String> PERMISSIONS = Arrays
				.asList("publish_actions");

		private boolean isSubsetOf(Collection<String> subset,
				Collection<String> superset) {
			for (String string : subset) {
				if (!superset.contains(string)) {
					return false;
				}
			}
			return true;
		}

		LoginButton loginButton;

		private static final String TAG = "PlaceholderFragment";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);

			loginButton = (LoginButton) rootView.findViewById(R.id.authButton);
			loginButton.setFragment(this);
			loginButton
					.setReadPermissions("user_likes", "user_status", "email");
			Button btn = (Button) rootView.findViewById(R.id.btnLogin);
			btn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Session.openActiveSession(getActivity(),
							PlaceholderFragment.this, true,
							new Session.StatusCallback() {

								@Override
								public void call(Session session,
										SessionState state, Exception exception) {
									if (session.isOpened()) {
										String token = session.getAccessToken();
										Request.newMeRequest(session,
												new GraphUserCallback() {

													@Override
													public void onCompleted(
															GraphUser user,
															Response response) {
														Toast.makeText(
																getActivity(),
																"my user id : "
																		+ user.getId(),
																Toast.LENGTH_SHORT)
																.show();
													}
												}).executeAsync();

										Request.newMyFriendsRequest(session,
												new GraphUserListCallback() {

													@Override
													public void onCompleted(
															List<GraphUser> users,
															Response response) {
														for (GraphUser user : users) {
															user.getId();
														}

													}
												}).executeAsync();
									}

								}
							});
				}
			});

			btn = (Button) rootView.findViewById(R.id.btnPost);
			btn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Session.openActiveSession(getActivity(),
							PlaceholderFragment.this, true,
							new StatusCallback() {

								@Override
								public void call(Session session,
										SessionState state, Exception exception) {
									if (session != null && session.isOpened()) {
										List<String> permission = session
												.getPermissions();
										if (!isSubsetOf(PERMISSIONS, permission)) {
											session.requestNewPublishPermissions(new Session.NewPermissionsRequest(
													PlaceholderFragment.this,
													PERMISSIONS));
											return;
										}

										Bundle postParams = new Bundle();
										postParams.putString("message",
												"facebook test message");
										postParams.putString("name",
												"Education Test for Android");
										postParams.putString("caption",
												"Test facebook capture.");
										postParams
												.putString(
														"description",
														"The Facebook SDK for Android makes it easier and faster to develop Facebook integrated Android apps.");
										postParams
												.putString("link",
														"https://developers.facebook.com/android");
										postParams
												.putString("picture",
														"https://raw.github.com/fbsamples/ios-3.x-howtos/master/Images/iossdk_logo.png");

										Request.Callback callback = new Request.Callback() {
											public void onCompleted(
													Response response) {
												JSONObject graphResponse = response
														.getGraphObject()
														.getInnerJSONObject();
												String postId = null;
												try {
													postId = graphResponse
															.getString("id");
												} catch (JSONException e) {
													Log.i(TAG, "JSON error "
															+ e.getMessage());
												}
												FacebookRequestError error = response
														.getError();
												if (error != null) {
													Toast.makeText(
															getActivity(),
															error.getErrorMessage(),
															Toast.LENGTH_SHORT)
															.show();
												} else {
													Toast.makeText(
															getActivity(),
															postId,
															Toast.LENGTH_LONG)
															.show();
												}
											}
										};

										Request request = new Request(session,
												"me/feed", postParams,
												HttpMethod.POST, callback);
										request.executeAsync();
//										RequestAsyncTask task = new RequestAsyncTask(
//												request);
//										task.execute();

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
			Session.getActiveSession().onActivityResult(getActivity(),
					requestCode, resultCode, data);
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

}
