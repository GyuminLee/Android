package org.tacademy.basic.samplecamerapreview;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Camera;
import android.hardware.Camera.Face;
import android.hardware.Camera.PictureCallback;
import android.media.FaceDetector;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CameraPreviewActivity extends Activity implements SurfaceHolder.Callback {

	SurfaceHolder mHolder;
	Camera mCamera;
	HandlerThread mThread;
	Handler mHandler;

	public final static int MESSAGE_IMAGE_DECORD = 1;
	private final static int MAX_FACE_DETECT_NUMBER = 10;
	int mWidth = -1;
	int mHeight = -1;
	
    Camera.ShutterCallback mShutterCallback = new Camera.ShutterCallback() {
    	public void onShutter() {
    		Toast.makeText(CameraPreviewActivity.this, "shutter called", 
    				Toast.LENGTH_SHORT).show();
    	}
    };
    
    Camera.PreviewCallback mPreviewCallback = new Camera.PreviewCallback() {
		
		@Override
		public void onPreviewFrame(byte[] data, Camera camera) {
			// TODO Auto-generated method stub
			mHandler.removeMessages(MESSAGE_IMAGE_DECORD);
			mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_IMAGE_DECORD, 
					mWidth, mHeight, data));
			Log.i("CameraPreview","onPreviewFrame");
		}
	};
    
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
          return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                  Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
            "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
            "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }    
    private PictureCallback mPicture = new PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if (pictureFile == null){
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
            	e.printStackTrace();
            } catch (IOException e) {
            	e.printStackTrace();
            }
        }
    };    
    
    protected void onPause() {
    	mCamera.release();
    };

    Camera.FaceDetectionListener mFaceDetectionListener = new Camera.FaceDetectionListener() {

        @Override
        public void onFaceDetection(Face[] faces, Camera camera) {
        	for (Face face : faces) {
        		Point leftEye = face.leftEye;
        		Point rightEye = face.rightEye;
        		Point mouth = face.mouth;
        		Rect faceRect = face.rect;
        	}
        }
    };

    public void startFaceDetection(){
        // Try starting Face Detection
        Camera.Parameters params = mCamera.getParameters();

        // start face detection only *after* preview has started
        if (params.getMaxNumDetectedFaces() > 0){
            // camera supports face detection, so can start it:
            mCamera.startFaceDetection();
        }
    }    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_preview);
        mThread = new HandlerThread("imageProcess");
        mThread.start();
        mHandler = new Handler(mThread.getLooper()) {
        	@Override
        	public void handleMessage(Message msg) {
        		// TODO Auto-generated method stub
        		int type = msg.what;
        		byte[] data = (byte[])msg.obj;
        		int width = msg.arg1;
        		int height = msg.arg2;
        		
        		switch(type) {
        			case MESSAGE_IMAGE_DECORD : 
        				break;
        			default :
        		}
        		try {
        			int avgR,avgG,avgB;
        			avgR = avgG = avgB = 0;
        			for (int i = 0; i < data.length; i+=2) {
        				int color = data[i] << 8 | data[i+1];
        				int r = (color & 0xF800) >> 8;
        				int g = (color & 0x07E0) >> 3;
        				int b = (color & 0x001F) << 3;
        				avgR = (avgR + r) / 2;
        				avgG = (avgG + g) / 2;
        				avgB = (avgB + b) / 2;
        			}
        			Log.i("CameraPreviewActivity","AVG RGB R:" + avgR + ",G:" + avgG + ",B:" + avgB);
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
        		
        		super.handleMessage(msg);
        	}
        };
        if (!checkCameraHardware()) {
        	Toast.makeText(this, "camera hardward not exist", Toast.LENGTH_LONG).show();
        	finish();
        	return;
        }
        mCamera = openCamera();
        if (mCamera == null) {
            if (!checkCameraHardware()) {
            	Toast.makeText(this, "camera hardward not available", Toast.LENGTH_LONG).show();
            	finish();
            	return;
            }
        }
        SurfaceView v = (SurfaceView)findViewById(R.id.preview);
        mHolder = v.getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        Button btn = (Button)findViewById(R.id.takePicture);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
	            mCamera.takePicture(null, null, mPicture);
		    	
				Toast.makeText(CameraPreviewActivity.this, "Task Picture", Toast.LENGTH_SHORT).show();
			}
		});
    }

    
    private List<RectF> getFaceRect(Bitmap bitmap) {
    	List<RectF> list = new ArrayList<RectF>();
    	FaceDetector.Face[] faces = new FaceDetector.Face[MAX_FACE_DETECT_NUMBER];
    	FaceDetector detector = new FaceDetector(bitmap.getWidth(), bitmap.getHeight(), MAX_FACE_DETECT_NUMBER);
    	int numFaces = detector.findFaces(bitmap, faces);
    	for (FaceDetector.Face face : faces) {
    		PointF midPoint = new PointF();
    		face.getMidPoint(midPoint);
    		float eyeDistance = face.eyesDistance();
    		RectF rect = new RectF();
    		rect.left = midPoint.x - (eyeDistance * 2);
    		rect.right = midPoint.x + (eyeDistance * 2);
    		rect.top = midPoint.y - (eyeDistance * 2);
    		rect.bottom = midPoint.y + (eyeDistance * 2);
    		list.add(rect);
    	}
    	return list;
    }
    
    private boolean checkCameraHardware() {
    	return this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }
    
    private boolean checkAutoFocus() {
    	return this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_AUTOFOCUS);
    }
    
    private boolean checkFlash() {
    	return this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }
    
    private boolean checkFront() {
    	return this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT);
    }
    
    private Camera openCamera() {
    	Camera camera = null;
    	try {
    		camera = Camera.open();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return camera;
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_camera_preview, menu);
        return true;
    }

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		if (mHolder.getSurface() == null) {
			return;
		}
		
		try {
			mCamera.stopPreview();
		} catch(Exception e) {
			
		}
				
		try {
			Camera.Parameters p = mCamera.getParameters();
			mWidth = width;
			mHeight = height;
			p.setPreviewSize(width, height);
			mCamera.setParameters(p);
			mCamera.setFaceDetectionListener(mFaceDetectionListener);
			mCamera.setPreviewCallback(mPreviewCallback);
			List<Integer> types = p.getSupportedPreviewFormats();
			for (int i = 0; i < types.size(); i++) {
				int type = types.get(i);
				if (type == ImageFormat.RGB_565) {
					p.setPreviewFormat(ImageFormat.RGB_565);
					Log.i("CameraPreviewActivity","setPreviewFormat");
					break;
				}
			}
			
			mCamera.setPreviewDisplay(holder);
			mCamera.startPreview();
			startFaceDetection();			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		try {
			Camera.Parameters p = mCamera.getParameters();
			List<Integer> types = p.getSupportedPreviewFormats();
			for (int i = 0; i < types.size(); i++) {
				int type = types.get(i);
				if (type == ImageFormat.RGB_565) {
					p.setPreviewFormat(ImageFormat.RGB_565);
					Log.i("CameraPreviewActivity","setPreviewFormat");
					break;
				}
			}
			mCamera.setFaceDetectionListener(mFaceDetectionListener);
			mCamera.setPreviewCallback(mPreviewCallback);
			mCamera.setPreviewDisplay(holder);
			mCamera.startPreview();
			startFaceDetection();			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		try {
			mCamera.stopPreview();
			mCamera.release();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
}
