package org.tacademy.network.rss.upload;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.tacademy.network.rss.ParentActivity;
import org.tacademy.network.rss.R;
import org.tacademy.network.rss.Nework.DownloadThread;
import org.tacademy.network.rss.Nework.NetworkRequest;
import org.tacademy.network.rss.util.DialogManager;
import org.tacademy.network.rss.util.PropertyManager;

import android.app.Dialog;
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
import android.widget.TextView;
import android.widget.Toast;

public class UploadActivity extends ParentActivity {

	TextView filePath;
	ImageView preview;
	EditText title;
	EditText description;
	
	public static final int REQUEST_CODE_TAKE_CONTENT = 1;
	
	private String uploadUrl;
		
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.upload_layout);
	    // TODO Auto-generated method stub
	    
	    filePath = (TextView)findViewById(R.id.imagePath);
	    preview = (ImageView)findViewById(R.id.preview);
	    title = (EditText)findViewById(R.id.title);
	    description = (EditText)findViewById(R.id.description);
	 
	    Button btn = (Button)findViewById(R.id.search);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.setType("image/jpeg");
				startActivityForResult(i,REQUEST_CODE_TAKE_CONTENT);
			}
		});
	    
	    btn = (Button)findViewById(R.id.upload);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				FileUploadTestRequest request = new FileUploadTestRequest(uploadUrl,title.getText().toString(),description.getText().toString(),filePath.getText().toString());
				request.setOnDownloadCompletedListener(new NetworkRequest.OnDownloadCompletedListener() {
					
					public void onDownloadCompleted(int result, NetworkRequest request) {
						dismissProgress();
						String message=null;
						if (result == NetworkRequest.PROCESS_SUCCESS) {
							UploadResult ur = (UploadResult)request.getResult();
							if (ur.result == 1) {
								message = "success";
							} else {
								message = "fail";
							}
						} else {
							message = "exception";
						}
						Toast.makeText(UploadActivity.this, message, Toast.LENGTH_LONG).show();
					}
				});
				
				DownloadThread th = new DownloadThread(new Handler(),request);
				th.start();
				showProgress();
				
			}
		});
	    
	    uploadUrl = PropertyManager.getInstance().getUploadTestUrl();
	    
	    if (uploadUrl.equals("")) {
	    	UploadUrlRequest request = new UploadUrlRequest("test");
	    	request.setOnDownloadCompletedListener(new NetworkRequest.OnDownloadCompletedListener() {
				
				public void onDownloadCompleted(int result, NetworkRequest request) {
					//DialogManager.getInstance().getLastDialog().dismiss();
					dismissProgress();
					if (result == NetworkRequest.PROCESS_SUCCESS) {
						uploadUrl = (String)request.getResult();
						PropertyManager.getInstance().setUploadTestUrl(uploadUrl);
					}
				}
			});
	    	
	    	DownloadThread th = new DownloadThread(new Handler(),request);
	    	th.start();
	    	showProgress();
	    }
	    
	    
	}

	
	
	@Override
	protected Dialog onCreateDialog(int id) {
		return DialogManager.getInstance().getDialog(this, id);
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
					preview.setImageBitmap(selPhoto);
					filePath.setText(getFilePath(selPhotoUri));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	
}
