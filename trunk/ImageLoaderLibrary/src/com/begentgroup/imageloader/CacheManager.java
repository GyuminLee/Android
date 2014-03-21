package com.begentgroup.imageloader;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;

public class CacheManager {
	DiskCache mDiskCache;
	MemoryCache mMemoryCache;
	public CacheManager(Context context) {
		this(context,null,null);
	}
	
	public CacheManager(Context context, DiskCache diskCache) {
		this(context, null, diskCache);
	}
	
	public CacheManager(Context context, MemoryCache memoryCache) {
		this(context, memoryCache, null);
	}
	
	public CacheManager(Context context,MemoryCache memoryCache, DiskCache diskCache) {
		mDiskCache = diskCache;
		mMemoryCache = memoryCache;
		if (mDiskCache == null) {
			mDiskCache = new FileDiskCache(context.getExternalCacheDir());
		}
		if (mMemoryCache == null) {
			mMemoryCache = new WeakMemoryCache();
		}
	}
	
	public Bitmap getBitmap(String url) {
		Bitmap bm = mMemoryCache.getBitmap(url);
		if (bm == null) {
			bm = mDiskCache.getBitmap(url);
			if (bm != null) {
				mMemoryCache.putBitmap(url, bm);
			}
		}
		return bm;
	}
	
	public Bitmap saveBitmap(String url, InputStream is) throws IOException {
		Bitmap bm = mDiskCache.saveBitmap(url, is);
		if (bm != null) {
			mMemoryCache.putBitmap(url, bm);
		}
		return bm;
	}
}
