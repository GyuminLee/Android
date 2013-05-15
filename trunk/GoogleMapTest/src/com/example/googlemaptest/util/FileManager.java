package com.example.googlemaptest.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.os.Environment;

public class FileManager {

	private static FileManager instance;
	
	private static final String DIRECTORY_PARAM = "/";
	private static final String APP_ROOT_DIR_NAME = "NPR" + DIRECTORY_PARAM;
	private static final String CACHE_DIR_NAME = "cache" + DIRECTORY_PARAM;
	private static final String QRCODE_DIR_NAME = "qrimage" + DIRECTORY_PARAM;
	private static final String IMAGE_DIR_NAME = "image" + DIRECTORY_PARAM;
	
	private File appRootDir;
	private File cacheDir;
	private File qrimageDir;
	private File imageDir;
	
	public static FileManager getInstance() {
		if (instance == null) {
			instance = new FileManager();
		}
		return instance;
	}
	
	private FileManager() {
		appRootDir = new File(Environment.getExternalStorageDirectory(),APP_ROOT_DIR_NAME);
		cacheDir = new File(appRootDir,CACHE_DIR_NAME);
		qrimageDir = new File(appRootDir,QRCODE_DIR_NAME);
		imageDir = new File(appRootDir,IMAGE_DIR_NAME);
		
		if (!appRootDir.exists()) {
			appRootDir.mkdirs();
		}
		
		if (!cacheDir.exists()) {
			cacheDir.mkdir();
		}
		
		if (!qrimageDir.exists()) {
			qrimageDir.mkdir();
		}
		
		if (!imageDir.exists()) {
			imageDir.mkdir();
		}
	}
	
	
	public boolean exists(File file) {
		return file.exists();
	}
	
	public InputStream getInputStream(File file) throws FileNotFoundException {
		InputStream is = null;

		is = new FileInputStream(file);

		return is; 
	}
	
	public File getCacheDirectory() {
		return cacheDir;
	}
	
	public File getQRImageDirectory() {
		return qrimageDir;
	}
	
	public File getImageDirectory() {
		return imageDir;
	}
	
	public void saveFile(File file, InputStream is) throws IOException {
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
	
	public InputStream saveCache(File file, InputStream is) throws IOException {
		// file save
		// file�� inputstream ��
		saveFile(file,is);
		return getInputStream(file);
	}

}
