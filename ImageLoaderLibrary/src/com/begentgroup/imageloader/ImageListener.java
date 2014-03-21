package com.begentgroup.imageloader;

import android.graphics.Bitmap;

public interface ImageListener {
	public void onImageLoadingStart(ImageRequest request);
	public void onImageLoaded(ImageRequest request, Bitmap bitmap);
	public void onImageLoadFail(ImageRequest request);
}
