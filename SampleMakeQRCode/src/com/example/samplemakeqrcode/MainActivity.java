package com.example.samplemakeqrcode;

import java.io.UnsupportedEncodingException;
import java.nio.FloatBuffer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class MainActivity extends Activity {

	EditText qrTextView;
	ImageView qrImageView;
	Bitmap mQRImage = null;
	Paint mPaint = new Paint();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		qrTextView = (EditText)findViewById(R.id.editText1);
		qrImageView = (ImageView)findViewById(R.id.imageView1);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					String qrText = new String(qrTextView.getText().toString().getBytes("UTF-8"),"ISO-8859-1");
					int onColor = Color.BLACK;
					int offColor = Color.WHITE;
					int width = 230;
					int height = 230;
					QRCodeWriter writer = new QRCodeWriter();
					BitMatrix bitMatrix = writer.encode(qrText, BarcodeFormat.QR_CODE, width, height);
					if (mQRImage != null) {
						mQRImage.recycle();
						mQRImage = null;
					}
					mQRImage = Bitmap.createBitmap(width, height, Config.ARGB_8888);
					Canvas canvas = new Canvas(mQRImage);
					
					FloatBuffer floatBuffer = FloatBuffer.allocate(width * height * 2);
					
					for (int x = 0; x < width ; x++) {
						for (int y = 0; y < height; y++) {
							if (bitMatrix.get(x, y)) {
								floatBuffer.put(x);
								floatBuffer.put(y);
							}
						}
					}
					
					canvas.drawColor(offColor);
					mPaint.setColor(onColor);
					canvas.drawPoints(floatBuffer.array(), floatBuffer.arrayOffset(), floatBuffer.position(), mPaint);
					
					qrImageView.setImageBitmap(mQRImage);
					
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (WriterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
