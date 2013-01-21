package com.example.testcamerasample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Face;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements SurfaceHolder.Callback {

	Camera mCamera;
	SurfaceView surfaceView;
	FaceView faceView;
	String filePrefix = "myimage";
	String fileExt = ".jpg";
	File mDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
	
	
	ShutterCallback shutterCB = new ShutterCallback() {
		
		@Override
		public void onShutter() {
			// TODO Auto-generated method stub
			
		}
	};
	
	PictureCallback jpegCB = new PictureCallback() {
		
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			// TODO Auto-generated method stub
			long time = (System.currentTimeMillis() / 1000);
			String fileName = filePrefix + "_" + time + fileExt;
			File filePath = new File(mDirectory, fileName);
			try {
				FileOutputStream fos = new FileOutputStream(filePath);
				fos.write(data);
				fos.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				MediaStore.Images.Media.insertImage(getContentResolver(), filePath.getAbsolutePath(), 
						"testimage" + time, "test description");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Uri uri = MediaStore.Images.Media.getContentUri(filePath.getAbsolutePath());
			sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,uri));
			
			mCamera.startPreview();
			
		}
	};
	
	Camera.FaceDetectionListener mDetectionListener = new Camera.FaceDetectionListener() {
		
		@Override
		public void onFaceDetection(Face[] faces, Camera camera) {
			// TODO Auto-generated method stub
			faceView.setFaces(faces);
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mCamera = Camera.open();
		mCamera.setDisplayOrientation(90);
		mCamera.setFaceDetectionListener(mDetectionListener);
		surfaceView = (SurfaceView)findViewById(R.id.surfaceView1);
		surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		surfaceView.getHolder().addCallback(this);
		
		faceView = (FaceView)findViewById(R.id.faceView);
		
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mCamera.takePicture(shutterCB, null, jpegCB);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		try {
			if (mCamera.getParameters().getMaxNumDetectedFaces() > 0) {
				mCamera.stopFaceDetection();
			}
			mCamera.stopPreview();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			Camera.Parameters param = mCamera.getParameters();
			mCamera.setParameters(param);
			mCamera.setPreviewDisplay(holder);
			mCamera.startPreview();
			if (mCamera.getParameters().getMaxNumDetectedFaces() > 0) {
				mCamera.startFaceDetection();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		try {
			Camera.Parameters param = mCamera.getParameters();
			mCamera.setParameters(param);
			mCamera.setPreviewDisplay(holder);
			mCamera.startPreview();
			if (mCamera.getParameters().getMaxNumDetectedFaces() > 0) {
				mCamera.startFaceDetection();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		if (mCamera.getParameters().getMaxNumDetectedFaces() > 0) {
			mCamera.stopFaceDetection();
		}
		mCamera.stopPreview();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (mCamera != null) {
			mCamera.release();
		}
	}

}
