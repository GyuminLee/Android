package com.begentgroup.imageloader;

public class Util {
	public static String makeKey(String url) {
		int centerpos = url.length() / 2;
		String key = String.valueOf(url.substring(0, centerpos - 1).hashCode());
		key += String.valueOf(url.substring(centerpos).hashCode());
		return key;
	}
}
