package com.example.sample2camera;

import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements SurfaceHolder.Callback {

	Camera mCamera;
	SurfaceView surfaceView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mCamera = Camera.open();
		mCamera.setDisplayOrientation(90);
		surfaceView = (SurfaceView) findViewById(R.id.surfaceView1);
		surfaceView.getHolder()
				.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		surfaceView.getHolder().addCallback(this);

		Button btn = (Button) findViewById(R.id.btnCapture);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mCamera.takePicture(null, null, new PictureCallback() {

					@Override
					public void onPictureTaken(byte[] data, Camera camera) {
						Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0,
								data.length);

						String url = MediaStore.Images.Media.insertImage(
								getContentResolver(), bitmap, "title",
								"description");
						Uri uri = Uri.parse(url);
					}
				});
			}
		});
	}

	@Override
	protected void onDestroy() {
		mCamera.release();
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		try {
			mCamera.stopPreview();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Camera.Parameters p = mCamera.getParameters();
		// List<Size> sizes = p.getSupportedPreviewSizes();
		// for (Size s : sizes) {
		// // ...
		// }
		// p.setPreviewSize(width, height);
		// mCamera.setParameters(p);

		try {
			mCamera.setPreviewDisplay(holder);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mCamera.startPreview();
		
		mCamera.setPreviewCallback(new Camera.PreviewCallback() {
			
			@Override
			public void onPreviewFrame(byte[] data, Camera camera) {
				
				
			}
		});
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
			mCamera.setPreviewDisplay(holder);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mCamera.startPreview();

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mCamera.stopPreview();
		try {
			mCamera.setPreviewDisplay(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
