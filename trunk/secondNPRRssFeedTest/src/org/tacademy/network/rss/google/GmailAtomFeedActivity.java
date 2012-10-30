package org.tacademy.network.rss.google;

import java.net.HttpURLConnection;

import org.tacademy.network.rss.InAppBrowser;
import org.tacademy.network.rss.ParentActivity;
import org.tacademy.network.rss.R;
import org.tacademy.network.rss.Nework.DownloadThread;
import org.tacademy.network.rss.Nework.NetworkRequest;
import org.tacademy.network.rss.util.DialogManager;
import org.tacademy.network.rss.util.PropertyManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class GmailAtomFeedActivity extends ParentActivity {

	public static final int REQUEST_CODE_WEBVIEW = 0;

	ListView list;
	Handler mHandler = new Handler();
	ArrayAdapter<GmailEntry> mAdapter;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.gmail_atom_feed);
	    list = (ListView)findViewById(R.id.listView1);
	    Button btn = (Button)findViewById(R.id.button1);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AccessToken token = PropertyManager.getInstance().getAccessToken();
				String code = PropertyManager.getInstance().getGmailFeedAuthorizationCode();
				if ( token != null) {
					getGmailAtomFeed(token);
				} else if (!code.equals("")) {
					getAccessToken(code);
				} else {
					getAuthorizationCode();
				}
			}
		});
	    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				GmailEntry entry = (GmailEntry)mAdapter.getItem(position);
				Toast.makeText(GmailAtomFeedActivity.this, "title : " + entry.title + 
						"\nauthor : " + entry.author.name + 
						"\nemail : " + entry.author.email, Toast.LENGTH_SHORT).show();
			}
		});
	    // TODO Auto-generated method stub
	}
	
	private void getAuthorizationCode() {
		Intent i = new Intent(GmailAtomFeedActivity.this,InAppBrowser.class);
		i.putExtra(InAppBrowser.FIELD_NAME_URL, GoogleConstants.getAuthenticationEndPoint());
		startActivityForResult(i,REQUEST_CODE_WEBVIEW);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == REQUEST_CODE_WEBVIEW) {
			if (resultCode != Activity.RESULT_OK || data == null) {
				return;
			}
			String code = data.getStringExtra(GoogleConstants.FIELD_NAME_CODE);
			String state = data.getStringExtra(GoogleConstants.FIELD_NAME_STATE);
			PropertyManager.getInstance().setGmailFeedAuthorizationCode(code);
			getAccessToken(code);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void getAccessToken(String code) {
		AccessTokenRequest request = new AccessTokenRequest(code);
		request.setOnDownloadCompletedListener(new NetworkRequest.OnDownloadCompletedListener() {
			
			@Override
			public void onDownloadCompleted(int result, NetworkRequest request) {
				// TODO Auto-generated method stub
				dismissDialog(DialogManager.WAIT_PROGRESS);
				if (result == NetworkRequest.PROCESS_SUCCESS) {
					AccessToken token = (AccessToken)request.getResult();
					PropertyManager.getInstance().setAccessToken(token);
					getGmailAtomFeed(token);
				} else {
					int resultCode = (Integer)request.getResult();
					if (resultCode >= 400 && resultCode < 500) {
						PropertyManager.getInstance().setGmailFeedAuthorizationCode("");
						getAuthorizationCode();
					} else {
						Toast.makeText(GmailAtomFeedActivity.this, "access token request fail", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		DownloadThread th = new DownloadThread(mHandler,request);
		th.start();
		showDialog(DialogManager.WAIT_PROGRESS);		
	}
	
	private void getGmailAtomFeed(AccessToken token) {
		GmailAtomFeedRequest gmailrequest = new GmailAtomFeedRequest(token.access_token);
		gmailrequest.setOnDownloadCompletedListener(new NetworkRequest.OnDownloadCompletedListener() {
			
			@Override
			public void onDownloadCompleted(int result, NetworkRequest request) {
				// TODO Auto-generated method stub
				dismissDialog(DialogManager.WAIT_PROGRESS);
				if (result == NetworkRequest.PROCESS_SUCCESS) {
					GmailFeed feed = (GmailFeed)request.getResult();
					mAdapter = new ArrayAdapter(GmailAtomFeedActivity.this,android.R.layout.simple_list_item_1,feed.items);
					list.setAdapter(mAdapter);
				} else {
					int resultCode = (Integer)request.getResult();
					if (resultCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
						AccessToken token = PropertyManager.getInstance().getAccessToken();
						if (token != null && token.refresh_token != null) {
							refreshAccessToken(token.refresh_token);
						} else {
							PropertyManager.getInstance().setAccessToken(null);
							String code = PropertyManager.getInstance().getGmailFeedAuthorizationCode();
							if (!code.equals("")) {
								getAccessToken(code);
							} else {
								getAuthorizationCode();
							}
						}
					} else {
						Toast.makeText(GmailAtomFeedActivity.this, "gmail request fail", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		DownloadThread th = new DownloadThread(mHandler,gmailrequest);
		th.start();
		showDialog(DialogManager.WAIT_PROGRESS);
	}
	
	private void refreshAccessToken(String refreshToken) {
		RefreshAccessTokenRequest request = new RefreshAccessTokenRequest(refreshToken);
		request.setOnDownloadCompletedListener(new NetworkRequest.OnDownloadCompletedListener() {
			
			@Override
			public void onDownloadCompleted(int result, NetworkRequest request) {
				// TODO Auto-generated method stub
				dismissDialog(DialogManager.WAIT_PROGRESS);
				if (result == NetworkRequest.PROCESS_SUCCESS) {
					AccessToken token = (AccessToken)request.getResult();
					if (token.refresh_token == null) {
						AccessToken oldtoken = PropertyManager.getInstance().getAccessToken();
						token.refresh_token = oldtoken.refresh_token;
					}
					PropertyManager.getInstance().setAccessToken(token);
					getGmailAtomFeed(token);
				} else {
					Toast.makeText(GmailAtomFeedActivity.this, "refresh request fail", Toast.LENGTH_SHORT).show();
				}
			}
		});
		showDialog(DialogManager.WAIT_PROGRESS);
	}
	
}
