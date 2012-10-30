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
		// mMemCache key 있는지 검색
		// 있으면 bitmap
		// 없으면 null
		String matchKey = null;
		Bitmap bitmap = null;
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
		//  file에 있는지 확인.
		// 있으면  File is 얻어와서 Bitmap생성
		// mMemCache추가 후 Bitmap return
		// 없으면 null return;
		Bitmap bitmap = null;
		if (exists(key)) {
			File file = getCacheFile(key);
			try {
				InputStream is = new FileInputStream(file);
				bitmap = createBitmapFromInputStream(is);
				if (bitmap != null) {
					
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return bitmap;
	}
	
	private void addMemcache(String key, Bitmap bitmap) {
		WeakReference<Bitmap> wr = new WeakReference<Bitmap>(bitmap);
		mMemCache.put(key, wr);
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
		// is을 file에 저장.
		// file에서 bitmap 생성.
		// mMemCache에 bitmap 등록.
		// bitmap return
		Bitmap bitmap = null;
		try {
			saveInputStream(key,is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// 이미 다른 Thread에서 write를 하고 있는 경우.
		}
		bitmap = getFileCacheBitmap(key);
		
		return bitmap;
	}
	
	private Bitmap createBitmapFromInputStream(InputStream is) {
		Bitmap bitmap = BitmapFactory.decodeStream(is);
		return bitmap;
	}

}
