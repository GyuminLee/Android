package com.example.samplearcamera.library;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.Face;
import android.hardware.Camera.Size;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraView extends SurfaceView implements SurfaceHolder.Callback {

	private static final String TAG = "CameraView";
	Camera mCamera;
	SurfaceHolder mHolder;
	int mWidth, mHeight;
	boolean isFaceDetection = false;
	boolean isFaceDetecting = false;
	boolean isGetPreviewData = false;
	boolean isPreview = false;
	
	public enum ViewState {
		VIEW_STATE_NOT_PREVIEW,
		VIEW_STATE_PREVIEW
	};
	
	ViewState mViewState = ViewState.VIEW_STATE_PREVIEW;
	
	public static final int IMAGE_FORMAT_RGB_565 = ImageFormat.RGB_565;
	public static final int IMAGE_FORMAT_DEFAULT = ImageFormat.NV21;
	public static final int IMAGE_FORMAT_NOT_DEFINE = -1;

	public int mPreviewImageFormat = IMAGE_FORMAT_NOT_DEFINE;
	
	Camera.PreviewCallback mPreviewCallback = new Camera.PreviewCallback() {
		
		@Override
		public void onPreviewFrame(byte[] data, Camera camera) {
			// TODO Auto-generated method stub
			ImageProcessHandler handler = ImageProcessHandler.getInstance();
			handler.setDataType(mPreviewImageFormat);
			handler.sendImageProcessEvent(mWidth, mHeight, data);
		}
	};
	
	Camera.PictureCallback mJpegCallback = new Camera.PictureCallback() {
		
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			// TODO Auto-generated method stub
			
		}
	};
	
	Camera.FaceDetectionListener mFaceListener = new Camera.FaceDetectionListener() {
		
		@Override
		public void onFaceDetection(Face[] faces, Camera camera) {
			// TODO Auto-generated method stub
			
		}
	};
	
	public CameraView(Context context) {
		this(context,null,0);
	}

	public CameraView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		if (!checkCameraHardware(context)) {
			Log.i(TAG,"Not Support Carmera Hardware");
			return;
		}
		
		mCamera = Camera.open();
		
		if (mCamera == null) {
			Log.i(TAG,"Camera open fail");
			return;
		}
		
		Camera.Parameters p = mCamera.getParameters();
		float angleX = p.getHorizontalViewAngle();
		float angleY = p.getVerticalViewAngle();
		ARCamera.getInstance().setAngle(angleX, angleY);
		mHolder = getHolder();
		mHolder.addCallback(this);
		
		// Support under Android 2.3.3
		// deprecate Android ICS
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	public CameraView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public void releaseCamera() {
		if (mCamera != null) {
			if (isPreview) {
				mCamera.stopPreview();
			}
			mCamera.release();
			mCamera = null;
		}
	}
	public boolean checkCameraHardware(Context context) {
		return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
	}
	
	public void setViewState(ViewState viewState) {
		if (mViewState == viewState) return;

		mViewState = viewState;

		if (viewState == ViewState.VIEW_STATE_NOT_PREVIEW) {
			stopCameraPreview(mHolder);
		} else {
			startCameraPreview(mHolder);
		}
	}
	
	public void setGetPreviewData(boolean enable) {
		
		if (isGetPreviewData == enable) return;
		
		isGetPreviewData = enable;
		if (enable) {
			if (isPreview) {
				startGetPreviewData();
			}
		} else {
			if (isPreview) {
				stopGetPreviewData();
			}
		}
	}
	
	public void setFaceDetectionEnable(boolean enable) {
		if (isFaceDetection == enable) return;
		isFaceDetection = enable;
		
		if (enable) {
			if (isPreview) {
				startFaceDetection();
			}
		} else {
			if (isPreview) {
				stopFaceDetection();
			}
		}
	}
	
	public Size getMatchSize(int width, int height, List<Size> sizes) {
		Size size = null;
		ArrayList<Size> sList = new ArrayList<Size>();
		
		for(Size s : sizes) {
			if (s.width <= width && s.height <= height) {
				addSizeList(sList,s);
			}
		}
		if (sList.size() == 0) {
			for(Size s : sizes) {
				addSizeList(sList,s);
			}
			if (sList.size() > 0) {
				size = sList.get(sList.size() - 1);
			} else {
				// sizes is empty
				size = null;				
			}
		} else {
			size = sList.get(0);
		}
		return size;
	}
	
	private void addSizeList(List<Size> sList,Size s) {
		int index = 0;
		for (int i = 0; i < sList.size(); i++, index++) {
			Size item = sList.get(i);
			
			if (item.width < s.width) {
				break;
			}
			
			if (item.width == s.width) {
				if (item.height <= s.height) {
					break;
				}
			}
		}
		sList.add(index, s);
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		mHolder = holder;
		if (mHolder.getSurface() == null || mCamera == null) {
			Log.i(TAG,"Camera don't use Surface");
			return;
		}
		
		
		try {
			stopCameraPreview(holder);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			if (mViewState == ViewState.VIEW_STATE_PREVIEW) {
				Camera.Parameters p = mCamera.getParameters();
				Size size = getMatchSize(width, height, p.getSupportedPreviewSizes());
				if (size != null) {
					p.setPreviewSize(size.width, size.height);
					mWidth = size.width;
					mHeight = size.height;
				} else {
					p.setPreviewSize(width, height);
					mWidth = width;
					mHeight = height;
				}
				mCamera.setParameters(p);
				startCameraPreview(holder);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void startGetPreviewData() {
		if (mCamera == null) return;
		Camera.Parameters p = mCamera.getParameters();
		if (mPreviewImageFormat == IMAGE_FORMAT_NOT_DEFINE) {
			acceptPreviewImageFormat(p);
		}
		p.setPreviewFormat(mPreviewImageFormat);
		mCamera.setParameters(p);
		mCamera.setPreviewCallback(mPreviewCallback);
	}
	
	private void stopGetPreviewData() {
		mCamera.setPreviewCallback(null);
	}
	

	private void startCameraPreview(SurfaceHolder holder) {
		
		if (mViewState != ViewState.VIEW_STATE_PREVIEW) return;
		if (mCamera == null) return;
		
		if (isGetPreviewData) {
			startGetPreviewData();
		}
		
		if (isFaceDetection) {
			try {
				mCamera.setFaceDetectionListener(mFaceListener);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		try {
			mCamera.setPreviewDisplay(holder);
			mCamera.startPreview();
			isPreview = true;
			if (isFaceDetection) {
				startFaceDetection();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

    public void startFaceDetection(){
        // Try starting Face Detection
    	if (!isFaceDetection) return;
    	if (mCamera == null) return;
    	
        Camera.Parameters params = mCamera.getParameters();

        // start face detection only *after* preview has started
        if (params.getMaxNumDetectedFaces() > 0){
            // camera supports face detection, so can start it:
            mCamera.startFaceDetection();
            isFaceDetecting = true;
        }
    }    
	
    public void stopFaceDetection() {
    	if (mCamera == null) return;
    	if (isFaceDetecting) {
    		mCamera.stopFaceDetection();
    		isFaceDetecting = false;
    	}
    }
    
	private void acceptPreviewImageFormat(Camera.Parameters p) {
		List<Integer> types = p.getSupportedPreviewFormats();
		for (int i = 0; i < types.size(); i++) {
			int type = types.get(i);
			if (type == ImageFormat.RGB_565) {
				mPreviewImageFormat = IMAGE_FORMAT_RGB_565;
				return;
			}
		}
		mPreviewImageFormat = IMAGE_FORMAT_DEFAULT;
	}
	
	private void stopCameraPreview(SurfaceHolder holder) {
		if (mCamera == null) return;
		if (isPreview) {
			if (isFaceDetection) {
				stopFaceDetection();
			}
			mCamera.stopPreview();
			isPreview = false;
		}
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		mHolder = holder;
		if (mHolder.getSurface() == null) return;
		if (mCamera == null) return;
		startCameraPreview(holder);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		stopCameraPreview(holder);
	}

}
