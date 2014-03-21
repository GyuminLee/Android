package com.begentgroup.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class Configuration {
	public static final int DEFAULT_THREAD_COUNT = 5;
	CacheManager cacheManager;
	Options defaultOptions;
	int threadCount = DEFAULT_THREAD_COUNT;

	public Configuration(Context context) {
		cacheManager = new CacheManager(context);
	}

	public Configuration(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public Configuration(CacheManager cacheManager, Options defaultOptions) {
		this.cacheManager = cacheManager;
		this.defaultOptions = defaultOptions;
	}

	public Configuration(Context context, Options defaultOptions) {
		cacheManager = new CacheManager(context);
		this.defaultOptions = defaultOptions;
	}

	public Configuration(Context context, int defaultRes, int loadingRes,
			int failRes) {
		cacheManager = new CacheManager(context);
		defaultOptions = new Options(context, defaultRes, loadingRes, failRes);
	}

	public static class Options {
		Bitmap defaultImage;
		Bitmap loadingImage;
		Bitmap failImage;

		public Options(Context context, int defaultRes, int loadingRes,
				int failRes) {
			Drawable d;
			d = context.getResources().getDrawable(defaultRes);
			if (d instanceof BitmapDrawable) {
				defaultImage = ((BitmapDrawable) d).getBitmap();
			}
			d = context.getResources().getDrawable(loadingRes);
			if (d instanceof BitmapDrawable) {
				loadingImage = ((BitmapDrawable) d).getBitmap();
			}
			d = context.getResources().getDrawable(failRes);
			if (d instanceof BitmapDrawable) {
				failImage = ((BitmapDrawable) d).getBitmap();
			}
		}

		public Options(Drawable defalutDrawable, Drawable loadingDrawable,
				Drawable failDrawable) {
			if (defalutDrawable instanceof BitmapDrawable) {
				defaultImage = ((BitmapDrawable) defalutDrawable).getBitmap();
			}
			if (loadingDrawable instanceof BitmapDrawable) {
				loadingImage = ((BitmapDrawable) loadingDrawable).getBitmap();
			}
			if (failDrawable instanceof BitmapDrawable) {
				failImage = ((BitmapDrawable) failDrawable).getBitmap();
			}
		}

		public Options(Context context, Bitmap defaultBitmap,
				Bitmap loadingBitmap, Bitmap failBitmap) {
			defaultImage = defaultBitmap;
			loadingImage = loadingBitmap;
			failImage = failBitmap;
		}
	}
}
