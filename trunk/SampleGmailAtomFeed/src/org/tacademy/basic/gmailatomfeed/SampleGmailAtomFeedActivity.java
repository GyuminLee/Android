package org.tacademy.basic.gmailatomfeed;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SampleGmailAtomFeedActivity extends Activity {
    /** Called when the activity is first created. */
	TextView tv;
	AccountManager mAccountManager;
	String mAuthToken;
	Handler mHandler =  new Handler();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tv = (TextView)findViewById(R.id.textView1);
        Button btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Account account = new Account("dongja94@gmail.com","com.google");
				
				mAccountManager.getAuthToken(account, "mail", null, SampleGmailAtomFeedActivity.this, new AccountManagerCallback<Bundle>() {

					@Override
					public void run(AccountManagerFuture<Bundle> future) {
						// TODO Auto-generated method stub
						Bundle b;
						try {
							b = future.getResult();
							mAuthToken = b.getString(AccountManager.KEY_AUTHTOKEN);
							Toast.makeText(SampleGmailAtomFeedActivity.this, "authToken:" + mAuthToken, Toast.LENGTH_LONG).show();
						} catch (OperationCanceledException e) {
							e.printStackTrace();
						} catch (AuthenticatorException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}						
					}
					
				}, mHandler);
				
			}
		});
        
        btn = (Button)findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Thread th = new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							URL url = new URL("https://mail.google.com/mail/feed/atom");
							HttpURLConnection conn = (HttpURLConnection)url.openConnection();
							conn.setRequestProperty("Authorization", "XOAUTH "+mAuthToken);
							conn.setConnectTimeout(30000);
							int respCode = conn.getResponseCode();
							if (respCode == HttpURLConnection.HTTP_OK) {
								InputStream is = conn.getInputStream();
								BufferedReader br = new BufferedReader(new InputStreamReader(is));
								
								String str;
								StringBuffer sb = new StringBuffer();
								while((str = br.readLine())!=null) {
									sb.append(str).append("\n\r");
								}
								final String msg = sb.toString();
								mHandler.post(new Runnable() {

									@Override
									public void run() {
										// TODO Auto-generated method stub
										tv.setText(msg);
									}
									
								});
								
							} else {
								mHandler.post(new Runnable() {

									@Override
									public void run() {
										// TODO Auto-generated method stub
										Toast.makeText(SampleGmailAtomFeedActivity.this, "read fail", Toast.LENGTH_SHORT).show();
									}
									
								});
							}
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
					}
					
				});
				th.start();
			}
		});
        
        
        mAccountManager = AccountManager.get(this);
    }
}