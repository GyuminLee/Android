package com.example.sampleprocessbitmap3;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.media.FaceDetector;
import android.media.FaceDetector.Face;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	ImageView imageView;
	public static final int REQUEST_CODE_GET_IMAGE = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imageView = (ImageView)findViewById(R.id.showImage);
		Button btn = (Button)findViewById(R.id.getImage);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.setType("image/*");
				startActivityForResult(i, REQUEST_CODE_GET_IMAGE);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE_GET_IMAGE && 
				resultCode == RESULT_OK) {
			Uri uri = data.getData();
			try {
				Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
				
				Bitmap scaleBitmap = Bitmap.createScaledBitmap(bm, 400, 400, false);
				bm.recycle();

				Bitmap bm565 = Bitmap.createBitmap(scaleBitmap.getWidth(), scaleBitmap.getHeight(), Bitmap.Config.RGB_565);
				Canvas canvas = new Canvas(bm565);
				canvas.drawBitmap(scaleBitmap, 0, 0, null);

				FaceDetector detector = new FaceDetector(400, 400, 10);
				Face[] faces = new Face[10];
				int size = detector.findFaces(bm565, faces);
				for (int i = 0; i < size; i++) {
					PointF point = new PointF();
					faces[i].getMidPoint(point);
					float distance = faces[i].eyesDistance();
					
					float left = point.x - distance * 2;
					float right = point.x + distance * 2;
					float top = point.y - distance;
					float bottom = point.y + distance * 3;
					Toast.makeText(MainActivity.this, 
							"left : " + left +
							"\nright : " + right + 
							"\ntop : " + top +
							"\nbottom : " + bottom, Toast.LENGTH_SHORT).show();
				}
				bm565.recycle();
				
				imageView.setImageBitmap(scaleBitmap);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
