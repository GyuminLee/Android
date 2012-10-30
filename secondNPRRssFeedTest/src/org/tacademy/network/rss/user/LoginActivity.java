package org.tacademy.network.rss.user;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.tacademy.network.rss.AndroNPR;
import org.tacademy.network.rss.ParentActivity;
import org.tacademy.network.rss.R;
import org.tacademy.network.rss.Nework.DownloadThread;
import org.tacademy.network.rss.Nework.LoginManager;
import org.tacademy.network.rss.Nework.NetworkRequest;
import org.tacademy.network.rss.upload.UploadResult;
import org.tacademy.network.rss.upload.UploadUrlRequest;
import org.tacademy.network.rss.util.DialogManager;
import org.tacademy.network.rss.util.PropertyManager;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends ParentActivity implements OnItemSelectedListener {

	Spinner mSpinner;
	ArrayList<String> userids;
	String mUserId;
	public static final String FINISH_ACTION_FIELD = "finishaction";
	public static final int FINISH_ACTION_LOGIN_CLOSE = 1;
	public static final int FINISH_ACTION_LOGIN_FORWARD = 2;
	public int finishAction;
	TextView userNameView;
	ImageView userImageView;
	String mFilePath;
	private String mUplaodUrl;
	
	private static final int REQUEST_CODE_GET_CONTENT = 1;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.login);
	    Intent i = getIntent();
	    finishAction = i.getIntExtra(FINISH_ACTION_FIELD, FINISH_ACTION_LOGIN_CLOSE);
	    
	    mSpinner = (Spinner)findViewById(R.id.spinner1);
	    userNameView = (TextView)findViewById(R.id.userName);
	    userImageView = (ImageView)findViewById(R.id.userPicture);
	    // TODO Auto-generated method stub
	    Button btn = (Button)findViewById(R.id.go);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String userid = (String)mSpinner.getSelectedItem();
				if (!LoginManager.getInstance().isLogin()) {
					LoginManager.getInstance().startLogin(userid, LoginActivity.this, new LoginManager.OnLoginResultListener() {
						
						@Override
						public void onLoginResult(int result, String name, int action) {
							// TODO Auto-generated method stub
							dismissProgress();
							postLogin();
						}
					});
					showProgress();
				} else {
					postLogin();
				}
			}
		});
	    
	    btn = (Button)findViewById(R.id.takePicture);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.setType("image/jpeg");
				startActivityForResult(i,REQUEST_CODE_GET_CONTENT);
				
			}
		});
	    
	    btn = (Button)findViewById(R.id.logout);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LoginManager.getInstance().startLogout(new LoginManager.OnLoginResultListener() {
					
					@Override
					public void onLoginResult(int result, String name, int action) {
						// TODO Auto-generated method stub
						finish();
					}
				});
			}
		});
	    
	    btn = (Button)findViewById(R.id.signup);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.setAction("android.settings.SYNC_SETTINGS");
                startActivity(intent);
			}
		});
	    
	    mUplaodUrl = PropertyManager.getInstance().getUploadUrl();
	    
	    if (mUplaodUrl.equals("")) {
	    	UploadUrlRequest request = new UploadUrlRequest("login");
	    	request.setOnDownloadCompletedListener(new NetworkRequest.OnDownloadCompletedListener() {
				
				public void onDownloadCompleted(int result, NetworkRequest request) {
					//DialogManager.getInstance().getLastDialog().dismiss();
					dismissProgress();
					if (result == NetworkRequest.PROCESS_SUCCESS) {
						mUplaodUrl = (String)request.getResult();
						PropertyManager.getInstance().setUploadUrl(mUplaodUrl);
					}
				}
			});
	    	
	    	DownloadThread th = new DownloadThread(new Handler(),request);
	    	th.start();
	    	showProgress();
	    }
	    
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
	    userids = LoginManager.getInstance().getAccount(this);
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,userids);
	    adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
	    mSpinner.setAdapter(adapter);
		super.onResume();
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
	}
	
	public void postLogin() {
		UserInfoUploadRequest request = new UserInfoUploadRequest(mUplaodUrl,userNameView.getText().toString(),mFilePath);
		
		request.setOnDownloadCompletedListener(new NetworkRequest.OnDownloadCompletedListener() {
			
			@Override
			public void onDownloadCompleted(int result, NetworkRequest request) {
				// TODO Auto-generated method stub
				dismissProgress();
				if (result == NetworkRequest.PROCESS_SUCCESS) {
					UploadResult ur = (UploadResult)request.getResult();
					if (ur.result == UploadResult.RESULT_SUCCESS) {
						PropertyManager.getInstance().setUserId(ur.resultId);
						if (finishAction == FINISH_ACTION_LOGIN_FORWARD) {
							Intent i = new Intent(LoginActivity.this,AndroNPR.class);
							startActivity(i);
						}			
						finish();			
					} else {
						Toast.makeText(LoginActivity.this, "User Info Upload Server Fail", Toast.LENGTH_LONG).show();
						finish();
					}
				} else {
					Toast.makeText(LoginActivity.this, "User Info Upload Exception", Toast.LENGTH_LONG).show();
					finish();
				}
				
			}
		});
		DownloadThread th = new DownloadThread(new Handler(),request);
		th.start();
		showProgress();
	}

	private String getFilePath(Uri uriPath) {
		String []proj = {MediaStore.Images.Media.DATA};
		Cursor cursor = managedQuery (uriPath, proj, null, null, null);
		int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		String path = cursor.getString(index);
		//path = path.substring(5);
		return path;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == REQUEST_CODE_GET_CONTENT) {
			if (resultCode == RESULT_OK) {
				try {
					Uri selPhotoUri = data.getData();
					Bitmap selPhoto = Images.Media.getBitmap( getContentResolver(), selPhotoUri );
					userImageView.setImageBitmap(selPhoto);
					// 임시로 처리하는 것임.
					mFilePath = getFilePath(selPhotoUri);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
}
