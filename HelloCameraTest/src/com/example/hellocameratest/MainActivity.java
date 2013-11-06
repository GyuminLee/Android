package com.example.hellocameratest;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainActivity extends Activity implements SurfaceHolder.Callback {

	Camera mCamera;
	SurfaceView surfaceView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		surfaceView = (SurfaceView)findViewById(R.id.surfaceView1);
		SurfaceHolder holder = surfaceView.getHolder();
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		holder.addCallback(this);
		mCamera = Camera.open();
		mCamera.setDisplayOrientation(90);
		
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
			mCamera.setPreviewDisplay(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Camera.Parameters params = mCamera.getParameters();
		List<Size> sizes = params.getSupportedPreviewSizes();
		for (Size size : sizes) {
			//size.width, size.height
		}
		params.setPreviewSize(width, height);
		
		mCamera.setParameters(params);
		
		try {
			mCamera.setPreviewDisplay(holder);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mCamera.startPreview();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
			mCamera.setPreviewDisplay(holder);
			mCamera.startPreview();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
