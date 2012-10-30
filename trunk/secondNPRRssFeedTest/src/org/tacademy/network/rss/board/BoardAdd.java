package org.tacademy.network.rss.board;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.tacademy.network.rss.ParentActivity;
import org.tacademy.network.rss.R;
import org.tacademy.network.rss.Nework.DownloadThread;
import org.tacademy.network.rss.Nework.NetworkRequest;
import org.tacademy.network.rss.upload.UploadResult;
import org.tacademy.network.rss.upload.UploadUrlRequest;
import org.tacademy.network.rss.util.DialogManager;
import org.tacademy.network.rss.util.ImageManager;
import org.tacademy.network.rss.util.ImageRequest;
import org.tacademy.network.rss.util.PropertyManager;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class BoardAdd extends ParentActivity {

	EditText titleEdit;
	EditText contentEdit;
	ImageView showImageView;
	String uploadUrl;
	String mFilePath;

	public static final int REQUEST_CODE_TAKE_CONTENT = 1;
	
	public static final String MODE_FIELD = "type";
	public static final int MODE_ADD = 1;
	public static final int MODE_MODIFY = 2;
	int currentMode = MODE_ADD;
	
	public static final String ITEM_FIELD = "item";
	
	public static final String RESULT_VALUE = "updateResult";
	public static final int RESULT_UPDATE_SUCCESS = 1;
	public static final int RESULT_UPDATE_FAIL = 2;
	
	public BoardItemData mItem;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.board_add);
	    titleEdit = (EditText)findViewById(R.id.title);
	    contentEdit = (EditText)findViewById(R.id.content);
	    showImageView = (ImageView)findViewById(R.id.showImage);
	    
	    Intent i = getIntent();
	    
	    currentMode = i.getIntExtra(MODE_FIELD,MODE_ADD);
	    if (currentMode == MODE_MODIFY) {
	    	mItem = (BoardItemData)i.getParcelableExtra(ITEM_FIELD);
	    	titleEdit.setText(mItem.title);
	    	contentEdit.setText(mItem.content);
	    	ImageRequest request = new ImageRequest(mItem.imageUrl + "&width=50&height=50");
	    	request.setOnDownloadCompletedListener(new NetworkRequest.OnDownloadCompletedListener() {
				
				@Override
				public void onDownloadCompleted(int result, NetworkRequest request) {
					// TODO Auto-generated method stub
					if (result == NetworkRequest.PROCESS_SUCCESS) {
						ImageRequest ir = (ImageRequest)request;
						Bitmap bm = ir.getBitmap();
						showImageView.setImageBitmap(bm);
					}
				}
			});
	    	ImageManager.getInstance().enqueue(request);
	    }
	    
	    Button btn = (Button)findViewById(R.id.searchImage);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.setType("image/jpeg");
				startActivityForResult(i,REQUEST_CODE_TAKE_CONTENT);
			}
		});

	    btn = (Button)findViewById(R.id.send);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mItem == null) {
					mItem = new BoardItemData();
				}
				mItem.title = titleEdit.getText().toString();
				mItem.content = contentEdit.getText().toString();
				BoardUploadRequest request = new BoardUploadRequest(uploadUrl,mItem,mFilePath);
				request.setOnDownloadCompletedListener(new NetworkRequest.OnDownloadCompletedListener() {
					
					@Override
					public void onDownloadCompleted(int result, NetworkRequest request) {
						// TODO Auto-generated method stub
						//DialogManager.getInstance().getLastDialog().dismiss();
						dismissProgress();
						String message = null;
						if (result == NetworkRequest.PROCESS_SUCCESS) {
							UploadResult ur = (UploadResult)request.getResult();
							if (ur.result == UploadResult.RESULT_SUCCESS) {
								if (currentMode == MODE_ADD) {
									message = getResources().getString(R.string.insert_success);
								} else if (currentMode == MODE_MODIFY) {
									message = getResources().getString(R.string.update_success);
								}
								processResult(message,RESULT_UPDATE_SUCCESS);
							} else {
								// 서버 처리 실패 처리
								message = getResources().getString(R.string.process_fail);
								processResult(message,RESULT_UPDATE_FAIL);
							}
						} else {
							// error 처리...
							message = getResources().getString(R.string.process_fail);
							processResult(message,RESULT_UPDATE_FAIL);
						}
					}
				});
				DownloadThread th = new DownloadThread(new Handler(),request);
				th.start();
				showProgress();
			}
		});
	    
	    if (currentMode == MODE_ADD) {
	    	uploadUrl = PropertyManager.getInstance().getUploadBoardInsertUrl();
	    } else if (currentMode == MODE_MODIFY) {
	    	uploadUrl = PropertyManager.getInstance().getUploadBoardUpdateUrl();
	    }
	    
	    if (uploadUrl.equals("")) {
	    	UploadUrlRequest request = null;
	    	if (currentMode == MODE_ADD) {
	    		request = new UploadUrlRequest("boardinsert");
	    	} else if (currentMode == MODE_MODIFY) {
	    		request = new UploadUrlRequest("boardupdate");
	    	}
	    	request.setOnDownloadCompletedListener(new NetworkRequest.OnDownloadCompletedListener() {
				
				public void onDownloadCompleted(int result, NetworkRequest request) {
					//DialogManager.getInstance().getLastDialog().dismiss();
					dismissProgress();
					if (result == NetworkRequest.PROCESS_SUCCESS) {
						uploadUrl = (String)request.getResult();
						if (currentMode == MODE_ADD) {
							PropertyManager.getInstance().setUploadBoardInsertUrl(uploadUrl);
						} else if (currentMode == MODE_MODIFY) {
							PropertyManager.getInstance().setUploadBoardUpdateUrl(uploadUrl);		
						}
					}
				}
			});
	    	
	    	DownloadThread th = new DownloadThread(new Handler(),request);
	    	th.start();
	    	showProgress();
	    }
	
	    // TODO Auto-generated method stub
	}

	private void processResult(String message,int code) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		Intent i = new Intent();
		i.putExtra(RESULT_VALUE, code);
		setResult(Activity.RESULT_OK,i);
		finish();
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
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == REQUEST_CODE_TAKE_CONTENT) {
			if (resultCode == RESULT_OK) {
				try {
					Uri selPhotoUri = data.getData();
					Bitmap selPhoto = Images.Media.getBitmap( getContentResolver(), selPhotoUri );
					showImageView.setImageBitmap(selPhoto);
					// 임시로 처리하는 것임.
					mFilePath = getFilePath(selPhotoUri);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
