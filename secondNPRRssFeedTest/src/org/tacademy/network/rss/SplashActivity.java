package org.tacademy.network.rss;

import org.jivesoftware.smack.XMPPConnection;
import org.tacademy.network.rss.Nework.DownloadThread;
import org.tacademy.network.rss.Nework.LoginManager;
import org.tacademy.network.rss.Nework.NetworkRequest;
import org.tacademy.network.rss.chat.XMPPLoginManager;
import org.tacademy.network.rss.upload.UploadResult;
import org.tacademy.network.rss.user.LoginActivity;
import org.tacademy.network.rss.util.Config;
import org.tacademy.network.rss.util.PropertyManager;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class SplashActivity extends ParentActivity {

	boolean isRetry = false;
	/** Called when the activity is first created. */
	Handler mHandler = new Handler();
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.splash);
	    if (PropertyManager.getInstance().getRegistrationId().equals("")) {
			Intent registrationIntent = new Intent("com.google.android.c2dm.intent.REGISTER");
			registrationIntent.putExtra("app", PendingIntent.getBroadcast(SplashActivity.this, 0, new Intent(), 0));
			registrationIntent.putExtra("sender", "dongja94@gmail.com");
			startService(registrationIntent);
	    }
	    if (!LoginManager.getInstance().isAutoLogin() ) {
	    	mHandler.postDelayed(new Runnable() {

				public void run() {
					startLoginActivity();
				}
	    		
	    	}, 3000);
	    } else {
	    	loginTry();
	    	//loginXmppTry();
	    }
	    // TODO Auto-generated method stub
	}
	
	private void loginTry() {
    	LoginManager.getInstance().startAutoLogin(this, new LoginManager.OnLoginResultListener() {
			
			@Override
			public void onLoginResult(int result, String name, int action) {
				// TODO Auto-generated method stub
				dismissProgress();
				if (PropertyManager.getInstance().getUserId() == -1) {
					GetUserIdRequest request = new GetUserIdRequest();
					request.setOnDownloadCompletedListener(new NetworkRequest.OnDownloadCompletedListener() {
						
						@Override
						public void onDownloadCompleted(int result, NetworkRequest request) {
							// TODO Auto-generated method stub
							dismissProgress();
							UploadResult ur = (UploadResult)request.getResult();
							if (ur.result == UploadResult.RESULT_SUCCESS) {
								PropertyManager.getInstance().setUserId(ur.resultId);
								startAndroNPR();
							} else if (ur.result == UploadResult.RESULT_NOT_LOGIN) {
								if (isRetry == false) {
									mHandler.post(new Runnable() {
	
										@Override
										public void run() {
											// TODO Auto-generated method stub
											LoginManager.getInstance().invalidate();
											loginTry();
										}
										
									});
									isRetry = true;
								} else {
									Toast.makeText(SplashActivity.this, "Login Retry Fail", Toast.LENGTH_LONG).show();
									finish();
								}
							} else if (ur.result == UploadResult.RESULT_NOT_USER_ADD) {
								startLoginActivity();
							} else {
								Toast.makeText(SplashActivity.this, "Unknown Error", Toast.LENGTH_LONG).show();
								finish();
							}
						}
					});
					DownloadThread th = new DownloadThread(new Handler(),request);
					th.start();
					showProgress();
				} else {
					startAndroNPR();
				}
			}
		});
    	showProgress();
	}
	
	private void loginXmppTry() {
		// TODO Auto-generated method stub
		XMPPLoginManager.getInstance().startAutoLogin(this, new XMPPLoginManager.OnXMPPLoginListener() {
			
			@Override
			public void onLoginComplete(int result, String name, int type,
					XMPPConnection connection) {
				// TODO Auto-generated method stub
				dismissProgress();
				if (result == XMPPLoginManager.XMPP_LOGIN_OK) {
					Toast.makeText(SplashActivity.this, "gtalk login ok", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(SplashActivity.this, "gtalk login fail", Toast.LENGTH_SHORT).show();
				}
			}
		});
		showProgress();
	}
	
	
	private void startAndroNPR() {
		Intent i = new Intent(this,AndroNPR.class);
		startActivity(i);
		finish();		
	}
	
	private void startLoginActivity() {
		Intent i = new Intent(this,LoginActivity.class);
		i.putExtra(LoginActivity.FINISH_ACTION_FIELD, LoginActivity.FINISH_ACTION_LOGIN_FORWARD);
		startActivity(i);
		finish();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			// ..
			// Grid... 4
		} else {
			// .. 
			// .. 5...
		}
	}

}
