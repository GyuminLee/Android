package org.tacademy.basic.googleplaces.util;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.tacademy.basic.googleplaces.network.NetworkRequest;

import android.graphics.Bitmap;
import android.os.Handler;

public class ImageRequest extends NetworkRequest {

	Bitmap bitmap;
	
	public ImageRequest(String url) {
		this.urlString = url;
	}

	@Override
	public boolean process(Handler handler, InputStream is) {
//		bitmap = BitmapFactory.decodeStream(is);
//		//ImageManager.getInstance().setCache(urlString,bitmap);
//		handler.post(new Runnable() {
//
//			public void run() {
//				listener.onDownloadCompleted(PROCESS_SUCCESS, ImageRequest.this);
//			}
//			
//		});
		return true;
	}
	
	public File getFilePath() {
		String encodedPath = null;
		try {
			String filepath = null;
			if (urlString.length() > 100) {
				String[] sp = urlString.split("/");
				filepath = sp[sp.length-1];
			} else {
				filepath = urlString;
			}
			encodedPath = URLEncoder.encode(filepath, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new File(FileManager.getInstance().getCacheDirectory(),encodedPath);
	}
	
	public boolean process(Handler handler, Bitmap bitmap) {
		this.bitmap = bitmap;
		handler.post(new Runnable() {
			public void run() {
				listener.onDownloadCompleted(PROCESS_SUCCESS, ImageRequest.this);
			}
		});
		return true;
	}
	
	public Bitmap getBitmap() {
		return bitmap;
	}
	
	public Bitmap getBitmap(String url) {
		if (url != null && url.compareTo(urlString) == 0) {
			return bitmap;
		} 
		return null;
	}
}
