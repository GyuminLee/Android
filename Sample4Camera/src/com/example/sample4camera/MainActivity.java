package com.example.sample4camera;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements SurfaceHolder.Callback {

	SurfaceView surfaceView;
	Camera mCamera;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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
				for (int i = 0 ; i < effectlist.size(); i++) {
					list[i] = effectlist.get(i);
				}
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
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (mCamera == null) return;
		try {
			mCamera.setPreviewDisplay(holder);
			mCamera.startPreview();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
