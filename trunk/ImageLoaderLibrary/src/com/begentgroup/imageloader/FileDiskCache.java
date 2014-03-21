package com.begentgroup.imageloader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class FileDiskCache implements DiskCache {

	private File rootDirectory;
	
	public FileDiskCache(File rootDirectory) {
		this.rootDirectory = rootDirectory;
		if (!rootDirectory.exists()) {
			rootDirectory.mkdirs();
		}
	}
	@Override
	public synchronized Bitmap getBitmap(String url) {
		String key = Util.makeKey(url);
		File file = new File(rootDirectory, key);
		if (file.exists()) {
			Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
			return bm;
		}
		return null;
	}

	@Override
	public synchronized Bitmap saveBitmap(String url, InputStream is) throws IOException {
		String key = Util.makeKey(url);
		File file = new File(rootDirectory, key);
		if (!file.exists()) {
			try {
				FileOutputStream fos = new FileOutputStream(file);
				byte[] buffer = new byte[8096];
				int len;
				while((len = is.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fos.flush();
				fos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} 
		}
		return getBitmap(url);
	}

}
