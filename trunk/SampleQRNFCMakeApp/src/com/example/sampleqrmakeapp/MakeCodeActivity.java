package com.example.sampleqrmakeapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sampleqrmakeapp.NetworkModel.OnResultListener;
import com.google.zxing.WriterException;

public class MakeCodeActivity extends Activity {

	EditText editBedNumberView;
	TextView showCodeView;
	ImageView imageQRCode;
	EditText editEmail;
	Bitmap qrBitmap;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.make_code_layout);
	    editBedNumberView = (EditText)findViewById(R.id.editNumber);
	    showCodeView = (TextView)findViewById(R.id.showCode);
	    imageQRCode = (ImageView)findViewById(R.id.imageQR);
	    editEmail = (EditText)findViewById(R.id.editEmail);
	    
	    Button btn = (Button)findViewById(R.id.btnGetCode);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				NetworkModel.getInstance().getBedUrl(editBedNumberView.getText().toString(), new OnResultListener<String>() {
					
					@Override
					public void onSuccess(String result) {
						showCodeView.setText(result);
					}
					
					@Override
					public void onError(int code) {
						
					}
				});
			}
		});
	    
	    btn = (Button)findViewById(R.id.btnMakeQR);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					Bitmap old = qrBitmap;
					qrBitmap = QRUtils.makeQRCode(showCodeView.getText().toString());
					imageQRCode.setImageBitmap(qrBitmap);
					if (old != null) {
						old.recycle();
					}
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (WriterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	    
	    btn = (Button)findViewById(R.id.btnMakeNFC);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
	    
	    btn = (Button)findViewById(R.id.btnSendQR);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (qrBitmap == null) return;
				
				File out = new File(Environment.getExternalStorageDirectory(),"image.jpg");
				FileOutputStream os;
				try {
					os = new FileOutputStream(out);
					qrBitmap.compress(CompressFormat.JPEG, 100, os);
					Intent emailIntent = new Intent(Intent.ACTION_SEND);
					emailIntent.putExtra(Intent.EXTRA_EMAIL, editEmail.getText().toString());
					emailIntent.putExtra(Intent.EXTRA_SUBJECT, "qr code");
					emailIntent.putExtra(Intent.EXTRA_TEXT, " url " + showCodeView.getText());
					emailIntent.setType("image/jpeg");
					emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(out));
					startActivity(Intent.createChooser(emailIntent, "Send your email in : "));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

}
