package com.begentgroup.imageloader;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class ImageRequest implements Runnable {
	
	private static final String TAG = "ImageRequest";
	
	public static final int TYPE_DOWNLOAD_IMAGE = 0;
	public static final int TYPE_END_LOOP = 1;
	int type = TYPE_DOWNLOAD_IMAGE;
	String imageUrl;
	HttpURLConnection conn;
	InputStream is;
	boolean bCancel;
	boolean bNoUseThread;
	Object target;
	BitmapBinder binder;
	ImageListener mListener;
	Configuration.Options options;
	List<ImageRequest> sameRequest = new ArrayList<ImageRequest>();
	
	public static final int RESULT_CODE_OK = 1;
	public static final int RESULT_CODE_FAIL = 0;
	int resultCode = RESULT_CODE_FAIL;
	Bitmap resultBitmap;
	

	public interface BitmapBinder {
		public boolean bindBitmap(Object target, Bitmap bitmap);
	}

	public boolean isSameRequest(ImageRequest request) {
		if (imageUrl.equals(request.imageUrl)) {
			sameRequest.add(request);
			return true;
		}
		return false;
	}

	public void bindBitmap(Bitmap bitmap) {
		if (!bCancel) {
			if (target != null && bitmap != null) {
				boolean binding = false;
				if (binder != null) {
					binding = binder.bindBitmap(target, bitmap);
				}
				if (!binding) {
					if (target instanceof ImageView) {
						((ImageView) target).setImageBitmap(bitmap);
					}
				}
			}
		}
		for (ImageRequest request : sameRequest) {
			request.bindBitmap(bitmap);
		}
	}
	
	public void callOnImageLoaded(Bitmap bitmap) {
		if (!bCancel) {
			if (mListener != null) {
				mListener.onImageLoaded(this, bitmap);
			}
		}
		for (ImageRequest request : sameRequest) {
			request.callOnImageLoaded(bitmap);
		}
	}
	
	public void callOnImageLoadingStart() {
		if (!bCancel) {
			if (mListener != null) {
				mListener.onImageLoadingStart(this);
			}
		}
		for (ImageRequest request : sameRequest) {
			request.callOnImageLoadingStart();
		}
	}

	public void callOnImageLoadFail() {
		if (!bCancel) {
			if (mListener != null) {
				mListener.onImageLoadFail(this);
			}
		}
		for (ImageRequest request : sameRequest) {
			request.callOnImageLoadFail();
		}
	}
	
	
	public void setCancel() {
		bCancel = true;
		if (conn != null && sameRequest.size() == 0) {
			if (!bNoUseThread) {
				ImageLoader.getInstance().removeRequest(this);
			}
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public boolean isCacnel() {
		return bCancel && (sameRequest.size() == 0);
	}

	@Override
	public void run() {
		if (resultCode == RESULT_CODE_OK) {
			bindBitmap(resultBitmap);
			callOnImageLoaded(resultBitmap);
		} else {
			Bitmap fail = (options == null)? null:options.failImage;
			bindBitmap(fail);
			callOnImageLoadFail();
		}
		if (!bNoUseThread) {
			ImageLoader.getInstance().removeRunningQueue(this);
		}
	}

}
