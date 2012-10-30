package org.tacademy.adv.accountmanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

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
import android.widget.Toast;

public class SampleAccountManagerActivity extends Activity {
    /** Called when the activity is first created. */
	static final String BASE_URL = "http://dongjahellowebapp.appspot.com";
	private static final String AUTH_URL = BASE_URL + "/_ah/login";
	private static final String AUTH_TOKEN_TYPE = "ah";
	AccountManager mAccountManager;
	String mAuthToken;
	Handler mHandler =  new Handler();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mAccountManager = AccountManager.get(this);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
		        Account[] accountArray = mAccountManager.getAccountsByType("com.google");
		        Account account = accountArray[0];
		        mAccountManager.getAuthToken(account, "ah", null, SampleAccountManagerActivity.this, new AccountManagerCallback<Bundle>() {

					public void run(AccountManagerFuture<Bundle> future) {
						Bundle b;
						try {
							b = future.getResult();
							mAuthToken = b.getString(AccountManager.KEY_AUTHTOKEN);
							Toast.makeText(SampleAccountManagerActivity.this, "authToken:" + mAuthToken, Toast.LENGTH_LONG).show();
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
			
			public void onClick(View v) {
				try {
					ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("param1","value1"));
					params.add(new BasicNameValuePair("param2","value2"));
					
					HttpResponse res = makeRequest("/testlogin", params);
					BufferedReader br = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
					String msg;
					String totalMessage = "";
					while((msg = br.readLine())!=null) {
						totalMessage += msg;
					}
					Toast.makeText(SampleAccountManagerActivity.this, totalMessage, Toast.LENGTH_LONG).show();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
    }
    
	public HttpResponse makeRequest(String urlPath, List<NameValuePair> params) throws Exception {
	    HttpResponse res = makeRequestNoRetry(urlPath, params, false);
	    if (res.getStatusLine().getStatusCode() == 500) {
	        res = makeRequestNoRetry(urlPath, params, true);
	    }
	    return res;
	}
	
	private HttpResponse makeRequestNoRetry(String urlPath, List<NameValuePair> params, boolean newToken)
	        throws Exception {
	    // Get auth token for account
	
	    // Get SACSID cookie
	    DefaultHttpClient client = new DefaultHttpClient();
	    String continueURL = BASE_URL;
	    URI uri = new URI(AUTH_URL + "?continue=" +
	            URLEncoder.encode(continueURL, "UTF-8") +
	            "&auth=" + mAuthToken);
	    HttpGet method = new HttpGet(uri);
	    final HttpParams getParams = new BasicHttpParams();
	    HttpClientParams.setRedirecting(getParams, false);  // continue is not used
	    method.setParams(getParams);
	
	    HttpResponse res = client.execute(method);
	    Header[] headers = res.getHeaders("Set-Cookie");
	    if (res.getStatusLine().getStatusCode() != 302 ||
	            headers.length == 0) {
	        return res;
	    }
	
	    String sascidCookie = null;
	    for (Header header: headers) {
	        if (header.getValue().indexOf("SACSID=") >=0) {
	            // let's parse it
	            String value = header.getValue();
	            String[] pairs = value.split(";");
	            sascidCookie = pairs[0];
	        }
	    }
	
	    // Make POST request
	    uri = new URI(BASE_URL + urlPath);
	    HttpPost post = new HttpPost(uri);
	    UrlEncodedFormEntity entity =
	        new UrlEncodedFormEntity(params, "UTF-8");
	    post.setEntity(entity);
	    post.setHeader("Cookie", sascidCookie);
	    post.setHeader("X-Same-Domain", "1");  // XSRF
	    res = client.execute(post);
	    return res;
	}
    
}