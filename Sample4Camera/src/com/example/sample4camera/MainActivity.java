package com.example.sample4camera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements SurfaceHolder.Callback {

	SurfaceView surfaceView;
	Camera mCamera;
	LinearLayout imageList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imageList = (LinearLayout)findViewById(R.id.image_list);
		surfaceView = (SurfaceView)findViewById(R.id.surfaceView1);
		surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		surfaceView.getHolder().addCallback(this);
		mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
		mCamera.setDisplayOrientation(90);
		
		Button btn = (Button)findViewById(R.id.button2);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Camera.Parameters params = mCamera.getParameters();
				final List<String> effectlist = params.getSupportedColorEffects();
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setIcon(R.drawable.ic_launcher);
				builder.setTitle("Color Effect");
				final String[] list = new String[effectlist.size()];
				effectlist.toArray(list);
				builder.setSingleChoiceItems(list, 0, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String effect = list[which];
						Camera.Parameters params = mCamera.getParameters();
						params.setColorEffect(effect);
						mCamera.setParameters(params);
						dialog.dismiss();
					}
				});
				builder.create().show();
			}
		});
		btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCamera.takePicture(null, null, new PictureCallback() {
					
					@Override
					public void onPictureTaken(byte[] data, Camera camera) {
						BitmapFactory.Options opt = new BitmapFactory.Options();
						opt.inSampleSize = 4;
						Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length, opt);
						ImageView iv = (ImageView)getLayoutInflater().inflate(R.layout.image, null);
						iv.setImageBitmap(bm);
						imageList.addView(iv);
						mCamera.startPreview();
//						String url = Images.Media.insertImage(getContentResolver(), bm, "My Picture", "Description");
//						if (url != null) {
//							Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(url));
//							sendBroadcast(intent);
//						}
						
						File cache = getExternalCacheDir();
						if (!cache.exists()) {
							cache.mkdirs();
						}
						File savedFile = new File(cache, "mypicture.jpg");
						try {
							FileOutputStream fos = new FileOutputStream(savedFile);
							try {
								bm.compress(CompressFormat.JPEG, 100, fos);
							} finally {
								try {
									fos.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}						
					}
				});
			}
		});
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (mCamera == null) return;
		try {
			mCamera.setPreviewDisplay(holder);
			mCamera.startPreview();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		if (mCamera == null) return;
		try {
			mCamera.stopPreview();
		} catch(Exception e) {
			
		}
		
//		Camera.Parameters params = mCamera.getParameters();
//		List<Size> sizes = params.getSupportedPreviewSizes();
//		int swidth = 0;
//		int sheight = 0;
//		for (Size s : sizes) {
//			if (matchSize(s)) {
//				swidth = s.width;
//				sheight = s.height;
//			}
//		}
//		params.setPreviewSize(swidth, sheight);
//		mCamera.setParameters(params);
		
		try {
			mCamera.setPreviewDisplay(holder);
			mCamera.startPreview();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private boolean matchSize(Size s) {
		//  ....
		return true;
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if (mCamera == null) return;
		mCamera.stopPreview();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(mCamera != null) {
			mCamera.release();
			mCamera = null;
		}
	}
}
