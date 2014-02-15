package com.example.sampleaubio;

public class TempoDetector {
	static {
		System.loadLibrary("aubio");
	}
	
	public static int detectTempo(String filepath) {
		return detectTempo(filepath,0,1024,256);
	}
	
	public static int detectTempo(String filepath,int samplerate) {
		return detectTempo(filepath,samplerate,1024,256);
	}
	
	public static int detectTempo(String filepath,int samplerate, int windowSize) {
		return detectTempo(filepath,samplerate,windowSize, windowSize / 4);
	}
	
	public static native int detectTempo(String filepath,int samplerate,int windowSize,int hopSize);
}
