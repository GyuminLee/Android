package com.example.sample3navermovie;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class CacheManager {
	private static CacheManager instance;

	public static CacheManager getInstance() {
		if (instance == null) {
			instance = new CacheManager();
		}
		return instance;
	}

	File cacheDir;
	Context mContext;

	private CacheManager() {
		cacheDir = MyApplication.getContext().getExternalCacheDir();
		if (!cacheDir.exists()) {
			cacheDir.mkdirs();
		}
	}

	public void saveFile(String key, InputStream is) {
		File saveFile = new File(cacheDir, key);
		OutputStream os = null;
		try {
			os = new FileOutputStream(saveFile);
			byte[] buffer = new byte[8096];
			int len;
			while ((len = is.read(buffer)) > 0) {
				os.write(buffer, 0, len);
			}
			os.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public Bitmap getFileBitmap(String key) {
		File saveFile = new File(cacheDir, key);
		if (!saveFile.exists())
			return null;

		try {
			FileInputStream fis = new FileInputStream(saveFile);
			Bitmap bm = BitmapFactory.decodeStream(fis);
			return bm;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	HashMap<String, WeakReference<Bitmap>> mMemCache = new HashMap<String, WeakReference<Bitmap>>();
	
	public void putMemoryBitmap(String key, Bitmap bitmap) {
		WeakReference<Bitmap> v = new WeakReference<Bitmap>(bitmap);
		mMemCache.put(key, v);
	}
	
	public Bitmap getMemoryBitmap(String key) {
		Bitmap bm = null;
		WeakReference<Bitmap> v = mMemCache.get(key);
		if (v != null) {
			bm = v.get();
			if (bm == null) {
				mMemCache.remove(key);
			}
		}
		return bm;
	}
	
	public Bitmap putCacheBitmap(ImageRequest request,InputStream is) {
		saveFile(request.getKey(), is);
		Bitmap bm = getFileBitmap(request.getKey());
		putMemoryBitmap(request.url,bm);
		return bm;
	}
	
	public Bitmap getCacheBitmap(ImageRequest request) {
		Bitmap bm = getMemoryBitmap(request.url);
		if (bm == null) {
			bm = getFileBitmap(request.getKey());
			if (bm != null) {
				putMemoryBitmap(request.url, bm);
			}
		}
		return bm;
	}
}
