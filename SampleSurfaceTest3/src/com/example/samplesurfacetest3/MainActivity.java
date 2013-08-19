package com.example.samplesurfacetest3;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity implements SurfaceHolder.Callback {

	SurfaceView surfaceView;
	Camera mCamera;
	LinearLayout menuLayout;
	SurfaceHolder mSurfaceHolder;
	
	ImageProcessHandler mImageProcessHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		surfaceView = (SurfaceView)findViewById(R.id.surfaceView);
		SurfaceHolder holder = surfaceView.getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		mCamera = Camera.open();
		
		menuLayout = (LinearLayout)findViewById(R.id.menuLayout);
		menuLayout.setVisibility(View.GONE);
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
				menuLayout.setVisibility(View.GONE);
			}
		});
		surfaceView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (menuLayout.getVisibility() == View.GONE) {
					menuLayout.setVisibility(View.VISIBLE);
				} else {
					menuLayout.setVisibility(View.GONE);
				}
			}
		});
		
		HandlerThread imageProcessThread = new HandlerThread("imagethread");
		imageProcessThread.start();
		mImageProcessHandler = new ImageProcessHandler(imageProcessThread.getLooper());
		
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
	
	Camera.PreviewCallback mPreviewCallback = new Camera.PreviewCallback() {
		int mCount = 0;
		
		@Override
		public void onPreviewFrame(byte[] data, Camera camera) {

			Camera.Parameters params = camera.getParameters();
			Size size = params.getPreviewSize();

			mImageProcessHandler.sendInputImageData(data, size.width, size.height);
			
			
			Log.i("Camera Preview", "preview frame :" + mCount++ );
		}
	};
	
	public class ImageProcessHandler extends Handler {
		
		public final static int MESSAGE_TYPE_INPUT_IMAGE_DATA = 1;
		public final static int MESSAGE_TYPE_IMAGE_UPDATE = 2;
		
		private byte[] mData;
		private int mWidth;
		private int mHeight;
		
		public ImageProcessHandler(Looper looper) {
			super(looper);
		}
		
		Bitmap mBitmap = null;
		
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case MESSAGE_TYPE_INPUT_IMAGE_DATA :
				mData = (byte[])msg.obj;
				mWidth = msg.arg1;
				mHeight = msg.arg2;
				processImage();
				break;
			case MESSAGE_TYPE_IMAGE_UPDATE :
				if (mData != null) {
					int[] pixels = ImageUtil.convertYUV420_NV21toARGB8888(mData, mWidth, mHeight);
					
					/*if (mBitmap != null && mBitmap.getWidth() == mWidth && mBitmap.getHeight() == mHeight) {
						mBitmap.setPixels(pixels, 0, mWidth, 0, 0, mWidth, mHeight);
					} else*/ if (mBitmap != null) {
						mBitmap.recycle();
						mBitmap = null;
						mBitmap = Bitmap.createBitmap(pixels, mWidth, mHeight, Bitmap.Config.ARGB_8888);
					} else {
						mBitmap = Bitmap.createBitmap(pixels, mWidth, mHeight, Bitmap.Config.ARGB_8888);
					}
					if (mSurfaceHolder != null) {
						Canvas canvas = null;
						try {
							canvas = mSurfaceHolder.lockCanvas();
							canvas.drawBitmap(mBitmap, 0, 0, null);
						} finally {
							if (canvas != null) {
								mSurfaceHolder.unlockCanvasAndPost(canvas);
							}
						}
					}
					mData = null;
				}
				break;
			default :
				break;
			}
		}
		
		public void sendInputImageData(byte[] data, int widht, int height) {
			sendMessage(obtainMessage(MESSAGE_TYPE_INPUT_IMAGE_DATA, widht, height, data));
		}
		
		public void processImage() {
			sendMessage(obtainMessage(MESSAGE_TYPE_IMAGE_UPDATE));
		}
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
		
		
		if (mCamera == null) return;
		
		try {
			mCamera.stopPreview();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		mSurfaceHolder = holder;
		
//		try {
//			Camera.Parameters params = mCamera.getParameters();
//			List<Size> sizes = params.getSupportedPreviewSizes();
//			
//			params.setPreviewSize(width, height);
//			mCamera.setParameters(params);
//			mCamera.setPreviewDisplay(holder);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		mCamera.startPreview();
		mCamera.setPreviewCallback(mPreviewCallback);
		
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		
		if (mCamera == null) return;

		mSurfaceHolder = holder;
		
//		try {
//			mCamera.setPreviewDisplay(holder);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		mCamera.startPreview();
		mCamera.setPreviewCallback(mPreviewCallback);
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		
		if (mCamera == null) return;
		mSurfaceHolder = null;
		mCamera.stopPreview();
	}
	

	@Override
	protected void onDestroy() {
		if (mCamera != null) {
			mCamera.release();
			mCamera = null;
		}
		mImageProcessHandler.getLooper().quit();
		super.onDestroy();
	}
}
