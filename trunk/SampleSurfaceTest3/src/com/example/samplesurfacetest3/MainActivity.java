package com.example.samplesurfacetest3;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements SurfaceHolder.Callback {

	SurfaceView surfaceView;
	Camera mCamera;
//	ImageProcessHandler mImageProcessHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		surfaceView = (SurfaceView)findViewById(R.id.surfaceView);
		SurfaceHolder holder = surfaceView.getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		mCamera = Camera.open();
		
		Button btn = (Button)findViewById(R.id.btnMenu);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(MainActivity.this, "Menu Button clicked", Toast.LENGTH_SHORT).show();
			}
		});
		
		btn = (Button)findViewById(R.id.btnShow);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(MainActivity.this, "Show Button cliecked", Toast.LENGTH_SHORT).show();
			}
		});
//		HandlerThread imageProcessThread = new HandlerThread("imagethread");
//		mImageProcessHandler = new ImageProcessHandler(imageProcessThread.getLooper());
//		imageProcessThread.start();
//		mCamera.setPreviewCallback(new Camera.PreviewCallback() {
//			
//			@Override
//			public void onPreviewFrame(byte[] data, Camera camera) {
//				Camera.Parameters params = camera.getParameters();
//				Size size = params.getPreviewSize();
//				mImageProcessHandler.sendImageProcess(data, size.width, size.height);
//				
//			}
//		});
	}
	
//	public class ImageProcessHandler extends Handler {
//
//		public static final int EVENT_TYPE_IMAGE_PROCESS = 1;
//		
//		public ImageProcessHandler(Looper looper) {
//			super(looper);
//		}
//		
//		@Override
//		public void handleMessage(Message msg) {
//			switch(msg.what) {
//				case EVENT_TYPE_IMAGE_PROCESS :
//					byte[] data = (byte[])msg.obj;
//					int width = msg.arg1;
//					int height = msg.arg2;
//					break;
//				default : 
//					break;
//			}
//		}
//		
//		public void sendImageProcess(byte[] data, int width, int height) {
//			sendMessage(obtainMessage(EVENT_TYPE_IMAGE_PROCESS, width, height, data));
//		}
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		
		
		if (mCamera == null) return;
		
		try {
			mCamera.stopPreview();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			Camera.Parameters params = mCamera.getParameters();
			List<Size> sizes = params.getSupportedPreviewSizes();
			
			params.setPreviewSize(width, height);
			mCamera.setParameters(params);
			mCamera.setPreviewDisplay(holder);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mCamera.startPreview();
		
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		
		if (mCamera == null) return;
		
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
		
		if (mCamera == null) return;
		mCamera.stopPreview();
	}
	

	@Override
	protected void onDestroy() {
		if (mCamera != null) {
			mCamera.release();
			mCamera = null;
		}
		super.onDestroy();
	}
}
