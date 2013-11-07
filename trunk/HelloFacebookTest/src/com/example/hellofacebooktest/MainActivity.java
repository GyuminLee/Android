package com.example.hellofacebooktest;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;

public class MainActivity extends FragmentActivity {

	private static final List<String> PERMISSIONS = Arrays
			.asList("publish_actions");
	private static final String PENDING_PUBLISH_KEY = "pendingPublishReauthorization";
	private boolean pendingPublishReauthorization = false;

	private static final String TAG = "MainActivity";

	private boolean isSubsetOf(Collection<String> subset,
			Collection<String> superset) {
		for (String string : subset) {
			if (!superset.contains(string)) {
				return false;
			}
		}
		return true;
	}

	private MainFragment mainFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			mainFragment = new MainFragment();
			getSupportFragmentManager().beginTransaction().add(R.id.container,mainFragment,"mainFragment").commit();
		} else {
			mainFragment = (MainFragment)getSupportFragmentManager().findFragmentByTag("mainFragment");
		}
	}
	
	private void openActiveSession() {
		Session.openActiveSession(this, true, new Session.StatusCallback() {

			@Override
			public void call(Session session, SessionState state,
					Exception exception) {
				if (session.isOpened()) {

					List<String> permissions = session.getPermissions();
					if (!isSubsetOf(PERMISSIONS, permissions)) {
						pendingPublishReauthorization = true;
						Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(
								MainActivity.this, PERMISSIONS);
						session.requestNewPublishPermissions(newPermissionsRequest);
						return;
					}

					Bundle postParams = new Bundle();
					postParams.putString("name", "Facebook SDK for Android");
					postParams.putString("caption",
							"Build great social apps and get more installs.");
					postParams
							.putString(
									"description",
									"The Facebook SDK for Android makes it easier and faster to develop Facebook integrated Android apps.");
					postParams.putString("link",
							"https://developers.facebook.com/android");
					postParams
							.putString("picture",
									"https://raw.github.com/fbsamples/ios-3.x-howtos/master/Images/iossdk_logo.png");

					Request.Callback callback = new Request.Callback() {
						public void onCompleted(Response response) {
							JSONObject graphResponse = response
									.getGraphObject().getInnerJSONObject();
							String postId = null;
							try {
								postId = graphResponse.getString("id");
							} catch (JSONException e) {
								Log.i(TAG, "JSON error " + e.getMessage());
							}
							FacebookRequestError error = response.getError();
							if (error != null) {
								Toast.makeText(
										MainActivity.this
												.getApplicationContext(),
										error.getErrorMessage(),
										Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(
										MainActivity.this
												.getApplicationContext(),
										postId, Toast.LENGTH_LONG).show();
							}
						}
					};

					Request request = new Request(session, "me/feed",
							postParams, HttpMethod.POST, callback);

					RequestAsyncTask task = new RequestAsyncTask(request);
					task.execute();
					// Request.newMeRequest(session, new
					// Request.GraphUserCallback() {
					//
					// @Override
					// public void onCompleted(GraphUser user, Response
					// response) {
					// if (user != null) {
					// Toast.makeText(MainActivity.this, "Hello " +
					// user.getName(), Toast.LENGTH_SHORT).show();
					// }
					//
					// }
					// }).executeAsync();
					// Request.newStatusUpdateRequest(session,
					// "Android Test...", new Callback() {
					//
					// @Override
					// public void onCompleted(Response response) {
					// Toast.makeText(MainActivity.this, "updated...",
					// Toast.LENGTH_SHORT).show();
					//
					// }
					// }).executeAsync();
				}

			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
//		Session.getActiveSession().onActivityResult(this, requestCode,
//				resultCode, data);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
