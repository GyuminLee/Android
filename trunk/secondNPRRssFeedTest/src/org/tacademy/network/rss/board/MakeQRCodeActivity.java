package org.tacademy.network.rss.board;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.tacademy.network.rss.ParentActivity;
import org.tacademy.network.rss.R;
import org.tacademy.network.rss.Nework.NetworkRequest;
import org.tacademy.network.rss.util.DialogManager;
import org.tacademy.network.rss.util.FileManager;
import org.tacademy.network.rss.util.ImageManager;
import org.tacademy.network.rss.util.ImageRequest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MakeQRCodeActivity extends ParentActivity {

	public static final int FILE_DIALOG_ID = DialogManager.USER_DIALOG_ID + 1;
	
	EditText qrTextView;
	ImageView qrImageView;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.make_qrcode);
	    // TODO Auto-generated method stub
	    qrTextView = (EditText)findViewById(R.id.qrText);
	    qrImageView = (ImageView)findViewById(R.id.qrimage);
	    Button btn = (Button)findViewById(R.id.qrmake);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					String encodeString = URLEncoder.encode(qrTextView.getText().toString(), "UTF-8");
					ImageRequest request = new ImageRequest("https://chart.googleapis.com/chart?chs=128x128&cht=qr&chl="+encodeString);
					request.setOnDownloadCompletedListener(new NetworkRequest.OnDownloadCompletedListener() {
						
						@Override
						public void onDownloadCompleted(int result, NetworkRequest request) {
							// TODO Auto-generated method stub
							dismissProgress();
							if (result == NetworkRequest.PROCESS_SUCCESS) {
								ImageRequest ir = (ImageRequest)request;
								Bitmap bm = ir.getBitmap();
								if (bm != null) {
									qrImageView.setImageBitmap(bm);
								}
							}
						}
					});
					ImageManager.getInstance().enqueue(request);
					showProgress();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
	    
	    btn = (Button)findViewById(R.id.saveImage);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(FILE_DIALOG_ID);
			}
		});
	}

	
    @Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
    	if (id == FILE_DIALOG_ID) {
    		View fileNameLayout = LayoutInflater.from(this).inflate(R.layout.file_name_dialog, null);
    		final EditText fileNameView = (EditText)fileNameLayout.findViewById(R.id.filename);
    		return new AlertDialog.Builder(this).setIcon(R.drawable.alert_dialog_icon)
    				.setTitle("File Name")
    				.setView(fileNameLayout)
    				.setPositiveButton("ok", new DialogInterface.OnClickListener() {
				
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							String fileName = fileNameView.getText().toString();
							File file = new File(FileManager.getInstance().getQRImageDirectory(),fileName);
							boolean isExists = FileManager.getInstance().exists(file);
							if (isExists) {
								Toast.makeText(MakeQRCodeActivity.this, "file save fail. \nexist file name.", Toast.LENGTH_SHORT).show();
							} else {
								Bitmap bm = getViewBitmap(qrImageView);
								try {
									if (bm != null) {
										OutputStream out = new FileOutputStream(file);
										boolean success = bm.compress(CompressFormat.JPEG, 100, out);
										if (success) {
											MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), file.getName(), qrTextView.getText().toString());
											Uri uri = MediaStore.Images.Media.getContentUri(fileName);
											sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));											
											Toast.makeText(MakeQRCodeActivity.this, "file save success.", Toast.LENGTH_SHORT).show();
										} else {
											Toast.makeText(MakeQRCodeActivity.this, "file save fail. \nfile save exception.", Toast.LENGTH_SHORT).show();
										}
									} else {
										Toast.makeText(MakeQRCodeActivity.this, "file save fail. \nbitmap creation fail.", Toast.LENGTH_SHORT).show();
									}
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									Toast.makeText(MakeQRCodeActivity.this, "file save fail. \nfile creation fail.", Toast.LENGTH_SHORT).show();
								}
								
							}
						}
					})
					.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
				
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					}).create();
    	}
		return super.onCreateDialog(id);
	}


	private Bitmap getViewBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);

        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);

        // Reset the drawing cache background color to fully transparent
        // for the duration of this operation
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);

        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();

        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        // Restore the view
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);

        return bitmap;
    }
	
}
