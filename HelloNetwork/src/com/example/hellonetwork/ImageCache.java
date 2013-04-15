package com.example.hellonetwork;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class ImageCache {
	
	private static final String DIRECTORY_PARAM = "/";
	private static final String APP_ROOT_DIR_NAME = "HelloNetwork" + DIRECTORY_PARAM;
	private static final String CACHE_DIR_NAME = "cache" + DIRECTORY_PARAM;
	private static final String IMAGE_DIR_NAME = "image" + DIRECTORY_PARAM;
	private static final int MAX_FILE_NAME_LENGTH = 100;

	private File appRootDir;
	private File cacheDir;
	private File imageDir;
	
	HashMap<String,WeakReference<Bitmap>> mMemCache = new HashMap<String,WeakReference<Bitmap>>();
	
	private static ImageCache instance;
	
	public static ImageCache getInstance() {
		if (instance == null) {
			instance = new ImageCache();
		}
		return instance;
	}
	
	private ImageCache() {
		appRootDir = new File(Environment.getExternalStorageDirectory(),APP_ROOT_DIR_NAME);
		cacheDir = new File(appRootDir,CACHE_DIR_NAME);
		imageDir = new File(appRootDir,IMAGE_DIR_NAME);

		if (!appRootDir.exists()) {
			appRootDir.mkdirs();
		}
		
		if (!cacheDir.exists()) {
			cacheDir.mkdir();
		}
		
		if (!imageDir.exists()) {
			imageDir.mkdir();
		}

	}
	
	
	public Bitmap getMemoryCacheBitmap(String searchKey) {
		Bitmap bitmap = null;
		synchronized (this) {
			String matchKey = null;
			Set keys = mMemCache.keySet();
			Iterator iter = keys.iterator();
			while(iter.hasNext()) {
				String key = (String)iter.next();
				if (key.equals(searchKey)) {
					WeakReference<Bitmap> wr = mMemCache.get(key);
					matchKey = key;
					bitmap = wr.get();
					break;
				}
			}
			
			if (matchKey != null && bitmap == null) {
				mMemCache.remove(matchKey);
			}
			
		}
		return bitmap;
	}
	
	public boolean exists(String key) {
		File file = getCacheFile(key);
		return file.exists();
	}
	
	private File getCacheFile(String key) {
		String name = key;
		if (key.length() > MAX_FILE_NAME_LENGTH) {
			name = key.substring(key.length() - MAX_FILE_NAME_LENGTH);
		}
		return new File(cacheDir,name);
	}
	
	public Bitmap getFileCacheBitmap(String key) {
		Bitmap bitmap = null;
		if (exists(key)) {
			File file = getCacheFile(key);
			try {
				InputStream is = new FileInputStream(file);
				bitmap = createBitmapFromInputStream(is);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		if (bitmap != null) {
			addMemcache(key, bitmap);
		}
		return bitmap;
	}
	
	private void addMemcache(String key, Bitmap bitmap) {
		synchronized (this) {
			WeakReference<Bitmap> wr = new WeakReference<Bitmap>(bitmap);
			mMemCache.put(key, wr);
		}
	}
	
	private void saveInputStream(String key, InputStream is) throws IOException {
		File file = getCacheFile(key);
		FileOutputStream fos = new FileOutputStream(file);
		byte[] data = new byte[2048];
		int length = 0;
		while(length != -1) {
			length = is.read(data);
			if (length > 0) {
				fos.write(data, 0, length);
			}
		}
		fos.flush();
		fos.close();
		is.close();	
	}
	
	public Bitmap getCacheBitmap(String key) {
		Bitmap bitmap = null;
		bitmap = getMemoryCacheBitmap(key);
		if (bitmap != null) return bitmap;
		bitmap = getFileCacheBitmap(key);
		return bitmap;
	}
	
	public Bitmap createBitmapFromNetwork(String key, InputStream is) {
		// is�� file�� ����.
		// file���� bitmap ��.
		// mMemCache�� bitmap ���.
		// bitmap return
		Bitmap bitmap = null;
		try {
			saveInputStream(key,is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// �̹� �ٸ� Thread���� write�� �ϰ� �ִ� ���.
		}
		bitmap = getFileCacheBitmap(key);
		
		return bitmap;
	}
	
	private Bitmap createBitmapFromInputStream(InputStream is) {
		Bitmap bitmap = BitmapFactory.decodeStream(is);
		return bitmap;
	}

}
