package com.example.samplearcamera.library;

import android.content.Context;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;

import com.example.samplearcamera.MyApplication;

public class Utility {
	public static int getDisplayRotation() {
		Display display = ((WindowManager)MyApplication.getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		return display.getRotation();
	}
}
