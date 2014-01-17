package com.example.samplefacebooktest;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.widget.LoginButton;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final List<String> PERMISSIONS = Arrays
			.asList("publish_actions");

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		LoginButton authButton = (LoginButton)findViewById(R.id.authButton);
//		authButton.setReadPermissions("user_likes","user_status");
		authButton.setPublishPermissions("publish_actions");
		Button btn = (Button)findViewById(R.id.btnSend);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Session.openActiveSession(MainActivity.this, true, new Session.StatusCallback() {
					
					@Override
					public void call(Session session, SessionState state, Exception exception) {
						if (session.isOpened()) {

							List<String> permissions = session.getPermissions();
							if (!isSubsetOf(PERMISSIONS, permissions)) {
								Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(
										MainActivity.this, PERMISSIONS);
								session.requestNewPublishPermissions(newPermissionsRequest);
								return;
							}

							Bundle postParams = new Bundle();
							postParams.putString("message", "facebook test message");
							postParams.putString("name", "Education Test for Android");
							postParams.putString("caption",
									"Test facebook capture.");
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
						}
					}
				});
				
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode,
				resultCode, data);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
