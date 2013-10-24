package com.example.sampleskpopenapi;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sampleskpopenapi.BaseTask.OnResultListener;
import com.skp.openplatform.android.sdk.api.APIRequest;
import com.skp.openplatform.android.sdk.oauth.OAuthInfoManager;
import com.skp.openplatform.android.sdk.oauth.OAuthListener;
import com.skp.openplatform.android.sdk.oauth.PlanetXOAuthException;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initOAuth();
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					OAuthInfoManager.login(MainActivity.this, new OAuthListener() {
						
						@Override
						public void onError(String message) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onComplete(String message) {
							
							new ProfileTask(new OnResultListener<Profile>() {

								@Override
								public void onSuccess(Profile result) {
									Toast.makeText(MainActivity.this, "profile name : " + result.userName, Toast.LENGTH_SHORT).show();
								}

								@Override
								public void onError() {
									// TODO Auto-generated method stub
									
								}
							}).execute("");
						}
					});
				} catch (PlanetXOAuthException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
	}
	
	

	public void initOAuth() {
		OAuthInfoManager.clientId = "8934d520-60ae-3d59-a010-18ece73361db";
		OAuthInfoManager.clientSecret = "52f3aac6-d2eb-3cd4-8cbc-c814c4a51053";
		OAuthInfoManager.scope = "user,tcloud,tmap";
		
		APIRequest.setAppKey("458a10f5-c07e-34b5-b2bd-4a891e024c2a");
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
