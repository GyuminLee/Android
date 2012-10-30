package org.tacademy.basic.mediaparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.tacademy.basic.mediaparser.soundFile.CheapSoundFile;

import android.content.Context;
import android.os.Handler;

public class MediaParser extends Thread {
	Context mContext;
	Handler mainHandler;
	String mFileName;
	
	public String mTitle;
	public String mArtist;
	public String mAlbum;
	public int mYear;
	public String mGenre;
	public CheapSoundFile mSoundFile;
	private boolean mIsReady = false;
	
	private static int MUTE_VALUE = 0;
	
	
	
	public static final int PROGRESS_DEFAULT_MAX = 100;
	public static final int PARSE_ERROR_SOUND_FILE_MAKE_FAIL = 1;
	private int mProgressMaxValue = PROGRESS_DEFAULT_MAX;
	private int mOldProgress = -1;
	private int mSampleRate = 0;
	private int mSamplePerFrame = 0;
	private int mFrameCount = 0;
	private int mDuration = 0;
	
	private boolean isCanceled = false;
	
	private ArrayList<MediaPart> mPartList;
	
	public interface OnProgressReportListener {
		public void onProgressReport(int progress);
		public void onParseEnd(MediaParser parser);
		public void onParseError(int errorCode,MediaParser parser);
	}
	
	OnProgressReportListener mListener;
	
	public void setOnProgressReportListener(OnProgressReportListener listener) {
		mListener = listener;
	}
	
	public MediaParser(Context context,Handler mainHandler) {
		mContext = context;
		this.mainHandler = mainHandler;
	}
	
	public void doParse(String fileName) {
		mFileName = fileName;
		start();
	}
	
	
	@Override
	public void run() {
		loadFromFile(mFileName);
	}

	private void loadFromFile(String fileName) {
		File file = new File(fileName);
		String extendtion = getExtensionFromFilename(fileName);
		SongMetadataReader metadataReader = new SongMetadataReader(mContext,fileName);
		
		try {
			mOldProgress = -1;
			mSoundFile = CheapSoundFile.create(fileName, new CheapSoundFile.ProgressListener() {
				
				public boolean reportProgress(double fractionComplete) {
					final int progress = (int)(mProgressMaxValue * fractionComplete);
					if (mOldProgress != progress) {
						mainHandler.post(new Runnable() {
	
							public void run() {
								if (mListener != null) {
									mListener.onProgressReport(progress);
								}
							}
							
						});
						mOldProgress = progress;
					}
					return !isCanceled;
				}
			});
			
			if (mSoundFile != null) {
				mSampleRate = mSoundFile.getSampleRate();
				mSamplePerFrame = mSoundFile.getSamplesPerFrame();
				mFrameCount = mSoundFile.getNumFrames();
				makeGapData();
				mainHandler.post(new Runnable() {

					public void run() {
						if (mListener != null) {
							mListener.onParseEnd(MediaParser.this);
						}
					}
					
				});
				mIsReady = true;
			} else {
				mainHandler.post(new Runnable() {

					public void run() {
						if (mListener != null) {
							mListener.onParseError(PARSE_ERROR_SOUND_FILE_MAKE_FAIL,MediaParser.this);
						}
					}					
				});
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isReady() {
		return mIsReady;
	}
	
	public int getMiliSecondFromFrame(int frameNumber) {
		int milisecond = 0;
		if (mSoundFile != null) {
			milisecond = 1000 * frameNumber * mSamplePerFrame / mSampleRate;
		} 
		return milisecond;
	}
	
	public int getFrameFromMiliSecond(int position) {
		int frame = 0;
		if (mSoundFile != null) {
			frame = (int) ((double)(position)/1000 * mSampleRate / mSamplePerFrame + 0.5);
		}
		return frame;
	}
	
	public ArrayList<MediaPart> getPartList() {
		return mPartList;
	}
	
	public int[] getFrameValues() {
		if (mSoundFile != null) {
			return mSoundFile.getFrameGains();
		}
		return null;
	}
	
	public int getFrameCount() {
		return mFrameCount;
	}
	
    private void makeGapData() {
    	mPartList = new ArrayList<MediaPart>();
    	boolean oldMuteState = true;
    	boolean muteState;
    	MediaPart mediaPart = null;
    	
    	int[] frameGains = mSoundFile.getFrameGains();
    	
    	for (int i = 0; i < mFrameCount; i++) {
    		if (frameGains[i] > MUTE_VALUE ) {
    			muteState = false;
    		} else {
    			muteState = true;
    		}
    		if (oldMuteState != muteState) {
    			int time = getMiliSecondFromFrame(i);
    			if (muteState) {
    				if (mediaPart != null) {
    					mediaPart.endTime = time;
    				}
    			} else {
    				if (mediaPart != null) {
    					mediaPart.muteEndTime = time;
    					mPartList.add(mediaPart);
    				}
    				mediaPart = new MediaPart();
    				mediaPart.startTime = time;
    			}
    		}
    	}
    	
    	if (mediaPart != null) {
    		mediaPart.muteEndTime = getMiliSecondFromFrame(mFrameCount);
    		if (mediaPart.endTime == 0) {
    			mediaPart.endTime = mediaPart.muteEndTime;
    		}
    		mPartList.add(mediaPart);
    	}
	}

	private String getExtensionFromFilename(String filename) {
        return filename.substring(filename.lastIndexOf('.'),
                                  filename.length());
    }
	
}
