package com.begentgroup.imageloader;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import android.graphics.Bitmap;

public class WeakMemoryCache implements MemoryCache {

	HashMap<String,WeakReference<Bitmap>> mCache = new HashMap<String,WeakReference<Bitmap>>();
	
	@Override
	public Bitmap getBitmap(String url) {
		WeakReference<Bitmap> value = mCache.get(url);
		if (value != null) {
			Bitmap bm = value.get();
			if (bm != null) {
				return bm;
			}
			mCache.remove(url);
		}
		return null;
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		WeakReference<Bitmap> value = new WeakReference<Bitmap>(bitmap);
		mCache.put(url, value);
	}

}
