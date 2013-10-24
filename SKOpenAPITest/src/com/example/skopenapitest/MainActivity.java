package com.example.skopenapitest;

import java.util.HashMap;
import java.util.Map;

import org.skplanetx.openapi.BaseTask.OnResultListener;
import org.skplanetx.openapi.elevenst.CategoryListTask;
import org.skplanetx.openapi.elevenst.CategoryResponse;
import org.skplanetx.openapi.profile.MyProfileTask;
import org.skplanetx.openapi.profile.Profile;
import org.skplanetx.openapi.social.OAuthUrlTask;
import org.skplanetx.openapi.social.ResultOAuthUrl;
import org.skplanetx.openapi.tcloud.Storage;
import org.skplanetx.openapi.tcloud.UploadFileTask;
import org.skplanetx.openapi.tcloud.UploadUrlTask;
import org.skplanetx.openapi.tmap.CarRouteInfo;
import org.skplanetx.openapi.tmap.CarRouteTask;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.skp.openplatform.android.sdk.api.APIRequest;
import com.skp.openplatform.android.sdk.common.RequestBundle;
import com.skp.openplatform.android.sdk.oauth.OAuthInfoManager;
import com.skp.openplatform.android.sdk.oauth.OAuthListener;
import com.skp.openplatform.android.sdk.oauth.PlanetXOAuthException;

public class MainActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initOAuthData();
		Button btn = (Button)findViewById(R.id.btnProfile);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					OAuthInfoManager.login(MainActivity.this, new OAuthListener() {
						
						@Override
						public void onError(String message) {
							Toast.makeText(MainActivity.this, "error : " + message, Toast.LENGTH_SHORT).show();							
						}
						
						@Override
						public void onComplete(String message) {
							Toast.makeText(MainActivity.this, "message : " + message, Toast.LENGTH_SHORT).show();							
							new MyProfileTask(new MyProfileTask.OnProfileListener() {
								
								@Override
								public void onSuccess(Profile profile) {
									Toast.makeText(MainActivity.this, "UserName : " + profile.userName, Toast.LENGTH_SHORT).show();
								}
								
								@Override
								public void onError() {
									Toast.makeText(MainActivity.this, "Error ", Toast.LENGTH_SHORT).show();
								}
							}).execute("");
						}
					});
				} catch (PlanetXOAuthException e) {					
					e.printStackTrace();
				}
			}
		});
		
		btn = (Button)findViewById(R.id.btnLogin);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					OAuthInfoManager.login(MainActivity.this, new OAuthListener() {
						
						@Override
						public void onError(String message) {
							Toast.makeText(MainActivity.this, "error : " + message, Toast.LENGTH_SHORT).show();							
						}
						
						@Override
						public void onComplete(String message) {
							Toast.makeText(MainActivity.this, "message : " + message, Toast.LENGTH_SHORT).show();							
						}
					});
				} catch (PlanetXOAuthException e) {					
					e.printStackTrace();
				}
			}
		});
		
		btn = (Button)findViewById(R.id.btnCategory);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					OAuthInfoManager.login(MainActivity.this, new OAuthListener() {
						
						@Override
						public void onError(String message) {
							Toast.makeText(MainActivity.this, "error : " + message, Toast.LENGTH_SHORT).show();							
						}
						
						@Override
						public void onComplete(String message) {
							Toast.makeText(MainActivity.this, "message : " + message, Toast.LENGTH_SHORT).show();
							new CategoryListTask(new CategoryListTask.OnCategoryResponseListener() {
								
								@Override
								public void onSuccess(CategoryResponse response) {
									Toast.makeText(MainActivity.this, "category : " + response.rootCategory.categoryName , Toast.LENGTH_SHORT).show();
								}
								
								@Override
								public void onError() {
									Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
								}
							}).execute("");
						}
					});
				} catch (PlanetXOAuthException e) {					
					e.printStackTrace();
				}
			}
		});
		
		btn = (Button)findViewById(R.id.btnTest);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new CarRouteTask(new OnResultListener<CarRouteInfo>() {

					@Override
					public void onSuccess(CarRouteInfo result) {
						Toast.makeText(MainActivity.this, "route : " + result.type, Toast.LENGTH_SHORT).show();						
					}

					@Override
					public void onError() {
												
					}
				}, 37.56468648536046, 126.98217734415019, 35.17883196265564, 129.07579349764512).execute("");
				
			}
		});
		
		btn = (Button)findViewById(R.id.btnUpload);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.setType("image/jpeg");
				startActivityForResult(i, 0);
			}
		});
		
		btn = (Button)findViewById(R.id.btnSocialLogin);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new OAuthUrlTask(new OnResultListener<ResultOAuthUrl>() {

					@Override
					public void onSuccess(ResultOAuthUrl result) {
						String oauthurl = result.oAuthUrl;
						Intent i = new Intent(MainActivity.this,InAppBrowserActivity.class);
						i.putExtra(InAppBrowserActivity.PARAM_URL, oauthurl);
						startActivityForResult(i, 1);
					}

					@Override
					public void onError() {
						
						
					}
				}, "facebook").execute("");
				
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
			Uri uri = data.getData();
			String[] projection = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA};
			
			Cursor c = getContentResolver().query(uri, projection, null, null, null);
			if (c.moveToNext()) {
				final String file = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
				try {
					OAuthInfoManager.login(MainActivity.this, new OAuthListener() {
						
						@Override
						public void onError(String message) {
							Toast.makeText(MainActivity.this, "error : " + message, Toast.LENGTH_SHORT).show();							
						}
						
						@Override
						public void onComplete(String message) {
							Toast.makeText(MainActivity.this, "message : " + message, Toast.LENGTH_SHORT).show();
							new UploadUrlTask(new OnResultListener<Storage>() {

								@Override
								public void onSuccess(Storage result) {
									final String uploadUrl = result.token;
									Toast.makeText(MainActivity.this, "Get Token Success!!!", Toast.LENGTH_SHORT).show();
									new UploadFileTask(new OnResultListener<Boolean>() {

										@Override
										public void onSuccess(Boolean result) {
											Toast.makeText(MainActivity.this, "Upload Success!!!", Toast.LENGTH_SHORT).show();
										}

										@Override
										public void onError() {
											Toast.makeText(MainActivity.this, "Upload Fail!!!", Toast.LENGTH_SHORT).show();
										}
									}, uploadUrl, file).execute("");
								}

								@Override
								public void onError() {
									Toast.makeText(MainActivity.this, "error UploadUrlTask ", Toast.LENGTH_SHORT).show();
								}
							}).execute("");
						}
					});
				} catch (PlanetXOAuthException e) {					
					e.printStackTrace();
				}
			} else {
				Toast.makeText(MainActivity.this, "File Not Exist", Toast.LENGTH_SHORT).show();
			}
		} else if (requestCode == 1) {
			if (resultCode == RESULT_OK) {
				String accessToken = data.getStringExtra(InAppBrowserActivity.RESULT_ACCESS_TOKEN);
				Toast.makeText(MainActivity.this, "access token : " + accessToken, Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(MainActivity.this, "access token not find", Toast.LENGTH_SHORT).show();
			}
		}
	}
	public void initOAuthData() {

		// Input here.
		APIRequest.setAppKey("458a10f5-c07e-34b5-b2bd-4a891e024c2a");
		
		OAuthInfoManager.clientId = "8934d520-60ae-3d59-a010-18ece73361db";
		OAuthInfoManager.clientSecret = "52f3aac6-d2eb-3cd4-8cbc-c814c4a51053";
		OAuthInfoManager.scope = "tcloud,user,tmap";
//		Map<String,Object> header = RequestBundle.getHeader();
//		if (header == null) {
//			header = new HashMap<String,Object>();
//			RequestBundle.setHeader(header);
//		}
//		header.put("x-skpop-userId", "dongja94@gmail.com");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
