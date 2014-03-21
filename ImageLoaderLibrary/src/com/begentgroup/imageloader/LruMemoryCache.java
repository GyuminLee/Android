package com.begentgroup.imageloader;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class LruMemoryCache extends LruCache<String, Bitmap> implements
		MemoryCache {

	public LruMemoryCache(int maxSize) {
		super(maxSize);
	}

	@Override
	protected int sizeOf(String key, Bitmap value) {
		return value.getRowBytes() * value.getHeight();
	}
	
	@Override
	public Bitmap getBitmap(String url) {
		return get(url);
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		put(url,bitmap);
	}

}
