package com.example.samplenavermovies.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class CacheManager {

	private static CacheManager instance;
	
	public static final String CACHE_DIR = "cache";
	
	File appRoot = Environment.getExternalStorageDirectory();
	File cache = new File(appRoot,CACHE_DIR);
	
	public static CacheManager getInstance() {
		if (instance == null) {
			instance = new CacheManager();
		}
		return instance;
	}
	
	private CacheManager() {
		if (!appRoot.exists()) {
			appRoot.mkdirs();
		}
		if (!cache.exists()) {
			cache.mkdir();
		}
	}
	
	HashMap<String,WeakReference<Bitmap>> mMap = new HashMap<String,WeakReference<Bitmap>>();
	
	public Bitmap getMemoryCache(String key) {
		WeakReference<Bitmap> w = mMap.get(key);
		if (w != null) {
			if (w.get() != null) {
				return w.get();
			} else {
				mMap.remove(key);
			}
		}
		return null;
	}
	
	public boolean setMemoryCache(String key, Bitmap bm) {
		
		WeakReference w = mMap.get(key);
		if (w == null) {
			w = new WeakReference<Bitmap>(bm);
			mMap.put(key, w);
		} else {
			if (w.get() == null) {
				mMap.remove(key);
				w = new WeakReference<Bitmap>(bm);
				mMap.put(key, w);
			} else {
				return false;
			}
		}
		
		return true;
	}
	
	public Bitmap getFileCache(String key) {
		Bitmap b = getMemoryCache(key);
		if (b != null) return b;
		File f = new File(cache, key);
		try {
			FileInputStream fis = new FileInputStream(f);
			Bitmap bm = BitmapFactory.decodeStream(fis);
			setMemoryCache(key, bm);
			return bm;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Bitmap setFileCache(String key, InputStream is) {
		File f = new File(cache,key);
		try {
			FileOutputStream fos = new FileOutputStream(f);
			byte[] buffer = new byte[4096];
			int length;
			while((length = is.read(buffer)) != -1) {
				if (length > 0) {
					fos.write(buffer, 0, length);
				}
			}
			fos.flush();
			fos.close();
			is.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return getFileCache(key);
	}
}
