package com.begentgroup.imageloader;

import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;

public interface DiskCache {
	public Bitmap getBitmap(String url);
	public Bitmap saveBitmap(String url, InputStream is) throws IOException ;
}
