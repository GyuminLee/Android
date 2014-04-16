package com.example.sample3camera;

import java.io.IOException;
import java.util.List;

import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements SurfaceHolder.Callback {

		Camera mCamera;
		
		public PlaceholderFragment() {
		}

		int face = Camera.CameraInfo.CAMERA_FACING_BACK;
		List<String> effectList;
		int currentEffect = 0;
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			mCamera = Camera.open(face);
			mCamera.setDisplayOrientation(90);
			effectList = mCamera.getParameters().getSupportedColorEffects();
		}
		
		@Override
		public void onDestroy() {
			super.onDestroy();
			if (mCamera != null) {
				mCamera.release();
				mCamera = null;
			}
		}
		SurfaceView screen;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			screen = (SurfaceView)rootView.findViewById(R.id.surfaceView1);
			screen.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
			screen.getHolder().addCallback(this);
			Button btn = (Button)rootView.findViewById(R.id.btnSwitch);
			btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (face == Camera.CameraInfo.CAMERA_FACING_BACK) {
						face = Camera.CameraInfo.CAMERA_FACING_FRONT;
					} else {
						face = Camera.CameraInfo.CAMERA_FACING_BACK;
					}
					if (mCamera != null) {
						mCamera.release();
						mCamera = null;
					}
					mCamera = Camera.open(face);
					mCamera.setDisplayOrientation(90);
					try {
						mCamera.setPreviewDisplay(screen.getHolder());
						mCamera.startPreview();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			btn = (Button)rootView.findViewById(R.id.btnEffect);
			btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (effectList != null && effectList.size() > 0) {
						String effect = effectList.get(currentEffect);
						Camera.Parameters params = mCamera.getParameters();
						params.setColorEffect(effect);
						mCamera.setParameters(params);
						currentEffect++;
						if (currentEffect >= effectList.size()) {
							currentEffect = 0;
						}
					}
				}
			});
			return rootView;
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
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			try {
				mCamera.stopPreview();
			} catch(Exception e) {
				
			}
//			Camera.Parameters params = mCamera.getParameters();
//			List<Size> sizes = params.getSupportedPreviewSizes();
//			for(Size s : sizes) {
//				s.width, s.height , width, height
//			}
//			params.setPreviewSize(width, height);
//			mCamera.setParameters(params);
			
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
		}
	}

}
