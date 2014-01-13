package com.example.sample2navermovie;

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

	public static CacheManager getInstance() {
		if (instance == null) {
			instance = new CacheManager();
		}
		return instance;
	}

	File cacheDirectory;

	private CacheManager() {
		cacheDirectory = new File(Environment.getExternalStorageDirectory(),
				"/imagecache");
		if (!cacheDirectory.exists()) {
			cacheDirectory.mkdir();
		}
	}

	HashMap<String, WeakReference<Bitmap>> mMemCache = new HashMap<String, WeakReference<Bitmap>>();	

	public void saveImage(String key, Bitmap bitmap) {
		WeakReference<Bitmap> weak = mMemCache.get(key);
		if (weak == null) {
			weak = new WeakReference<Bitmap>(bitmap);
		} else {
			Bitmap b = weak.get();
			if (b != null) {
				b.recycle();
			}
			mMemCache.remove(key);
			weak = new WeakReference<Bitmap>(bitmap);
		}
		mMemCache.put(key, weak);
	}

	public Bitmap getCacheBitmap(String key) {
		Bitmap b = getMemCacheBitmap(key);
		if (b == null) {
			b = getFileCahceBitmap(key);
		}
		return b;
	}
	
	public Bitmap getMemCacheBitmap(String key) {
		WeakReference<Bitmap> weak = mMemCache.get(key);
		if (weak != null) {
			Bitmap b = weak.get();
			if (b != null) {
				return b;
			}
			mMemCache.remove(key);
		}
		return null;
	}

	public Bitmap getFileCahceBitmap(String key) {
		File savedFile = new File(cacheDirectory, key);
		try {
			FileInputStream fis = new FileInputStream(savedFile);
			Bitmap bitmap = BitmapFactory.decodeStream(fis);
			saveImage(key, bitmap);
			return bitmap;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Bitmap saveImage(String key, InputStream is) {
		File saveFile = new File(cacheDirectory, key);
		try {
			FileOutputStream fos = new FileOutputStream(saveFile);
			byte[] buffer = new byte[4096];
			int length;
			while ((length = is.read(buffer)) > 0) {
				fos.write(buffer);
			}
			fos.flush();
			fos.close();
			FileInputStream fis = new FileInputStream(saveFile);
			Bitmap bitmap = BitmapFactory.decodeStream(fis);
			saveImage(key, bitmap);
			return bitmap;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
