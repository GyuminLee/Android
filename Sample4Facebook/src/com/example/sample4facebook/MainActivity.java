package com.example.sample4facebook;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Request.Callback;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

public class MainActivity extends Activity {

	LoginButton authButton;
	public static final List<String> PERMISSIONS = Arrays
			.asList("publish_actions");

	public static final List<String> READ_PERMISSIONS = Arrays.asList("read_stream");
	
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
		authButton = (LoginButton)findViewById(R.id.btn_auth);
		authButton.setReadPermissions("user_likes", "user_status", "email","read_stream");
		authButton.setSessionStatusCallback(new StatusCallback() {
			
			@Override
			public void call(Session session, SessionState state, Exception exception) {
				if (session.isOpened()) {
					Request.newMeRequest(session, new GraphUserCallback() {
						
						@Override
						public void onCompleted(GraphUser user, Response response) {
							if (user != null) {
								Toast.makeText(MainActivity.this, "user : " + user.getId(), Toast.LENGTH_SHORT).show();
							}
							
						}
					}).executeAsync();
				}
			}
		});
		Button btn = (Button)findViewById(R.id.btn_login);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Session.openActiveSession(MainActivity.this, true, new StatusCallback() {
					
					@Override
					public void call(Session session, SessionState state, Exception exception) {
						if (session.isOpened()) {
							Request.newMeRequest(session, new GraphUserCallback() {
								
								@Override
								public void onCompleted(GraphUser user, Response response) {
									if (user != null) {
										Toast.makeText(MainActivity.this, "user : " + user.getId(), Toast.LENGTH_SHORT).show();
									}
									
								}
							}).executeAsync();
						}
					}
				});
			}
		});
		
		btn = (Button)findViewById(R.id.btn_post);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Session.openActiveSession(MainActivity.this, true, new StatusCallback() {
					
					@Override
					public void call(Session session, SessionState state, Exception exception) {
						if (session != null && session.isOpened()) {
							List<String> permission = session
									.getPermissions();
							if (!isSubsetOf(PERMISSIONS, permission)) {
								session.requestNewPublishPermissions(new Session.NewPermissionsRequest(
										MainActivity.this,
										PERMISSIONS));
								return;
							}

							Request.newStatusUpdateRequest(session, "test message", new Callback() {
								
								@Override
								public void onCompleted(Response response) {
									if (response.getGraphObject() == null) {
										FacebookRequestError error = response.getError();
										Toast.makeText(MainActivity.this, "error : " + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
										return;
									}
									JSONObject obj = response.getGraphObject().getInnerJSONObject();
									try {
										String id = obj.getString("id");
										Toast.makeText(MainActivity.this, "success : " + id, Toast.LENGTH_SHORT).show();
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
								}
							}).executeAsync();
						}
					}
				});
			}
		});
		
		btn = (Button)findViewById(R.id.btn_post2);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Session.openActiveSession(MainActivity.this, true, new StatusCallback() {
					
					@Override
					public void call(Session session, SessionState state, Exception exception) {
						if (session.isOpened()) {
							List<String> permission = session
									.getPermissions();
							if (!isSubsetOf(PERMISSIONS, permission)) {
								session.requestNewPublishPermissions(new Session.NewPermissionsRequest(
										MainActivity.this,
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
							Request request = new Request(session, "me/feed", postParams, HttpMethod.POST, new Request.Callback() {
								
								@Override
								public void onCompleted(Response response) {
									if (response.getGraphObject() != null) {
										JSONObject obj = response.getGraphObject().getInnerJSONObject();
										try {
											String id = obj.getString("id");
											Toast.makeText(MainActivity.this, "id : " + id, Toast.LENGTH_SHORT).show();
										} catch (JSONException e) {
											e.printStackTrace();
										}
									} else {
										FacebookRequestError error = response.getError();
										Toast.makeText(MainActivity.this, "error : " + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
									}
									
								}
							});
							request.executeAsync();
						}
					}
				});
			}
		});
		btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Session.openActiveSession(MainActivity.this, true, new Session.StatusCallback() {
					
					@Override
					public void call(Session session, SessionState state, Exception exception) {
						if (session.isOpened()) {
							List<String> permission = session
									.getPermissions();
							if (!isSubsetOf(READ_PERMISSIONS, permission)) {
								session.requestNewReadPermissions(new Session.NewPermissionsRequest(
										MainActivity.this,
										READ_PERMISSIONS));
								return;
							}
							Request request = new Request(session, "me/feed", null, HttpMethod.GET, new Callback() {
								
								@Override
								public void onCompleted(Response response) {
									if (response.getGraphObject() != null) {
										JSONObject obj = response.getGraphObject().getInnerJSONObject();
										Toast.makeText(MainActivity.this, "obj : " + obj.toString(), Toast.LENGTH_SHORT).show();
									} else {
										// error
									}
								}
							});
							request.executeAsync();
						}
						
					}
				});
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (Session.getActiveSession() != null) {
			Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
		}
	}
}
