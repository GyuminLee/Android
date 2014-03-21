package com.begentgroup.imageloader;

import android.graphics.Bitmap;

public interface MemoryCache {
	public Bitmap getBitmap(String url);
	public void putBitmap(String url,Bitmap bitmap);
}
