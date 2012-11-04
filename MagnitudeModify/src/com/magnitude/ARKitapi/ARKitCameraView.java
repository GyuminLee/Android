package com.magnitude.ARKitapi;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * ARKitCameraView is a custom SurfaceView used to put camrecorder images onscreen
 * @author Chris Haseman - modified by Magnitude Client
 */
public class ARKitCameraView extends SurfaceView {
	
	/**
	 * The camera from which to get images.
	 */
	Camera camera;
	
	/**
	 * SurfaceHolder to put images on.
	 */
	SurfaceHolder previewHolder;

	// Callback for the surfaceholder
	SurfaceHolder.Callback surfaceHolderListener = new SurfaceHolder.Callback() {
		public void surfaceCreated(SurfaceHolder holder) {
			camera = Camera.open();
			try {
				camera.setPreviewDisplay(previewHolder);
			} catch (Throwable t) {
			}
		}

		public void surfaceChanged(SurfaceHolder surfaceHolder, int format,
				int w, int h) {
			camera.startPreview();
		}

		public void surfaceDestroyed(SurfaceHolder arg0) {
			closeCamera();
		}
	};

	/**
	 * Constructor. 
	 * @param ctx The current application Context.
	 */
	public ARKitCameraView(Context ctx) {
		super(ctx);
		previewHolder = this.getHolder();
		previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		previewHolder.addCallback(surfaceHolderListener);
		setBackgroundColor(Color.TRANSPARENT);
	}

	public ARKitCameraView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}

	public void closeCamera() {
		if (camera != null) {
			camera.release();
		}
	}

	public void dispatchDraw(Canvas c) {
		super.dispatchDraw(c);
	}
}
