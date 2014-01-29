package com.example.samplemp4example;

import java.io.IOException;

import android.os.Handler;
import android.util.Log;

import com.coremedia.iso.IsoFile;

public class GetDuration extends ParentExample {

	private static final String TAG = "GetDuration";
	public GetDuration(Handler handler, OnCompleteListener listener) {
		super(handler, listener);
	}

	@Override
	void executeExample() throws IOException {
        String filename = EXAMPLE_ROOT + "/count-video.mp4";
        IsoFile isoFile = new IsoFile(filename);
        double lengthInSeconds = (double)
                isoFile.getMovieBox().getMovieHeaderBox().getDuration() /
                isoFile.getMovieBox().getMovieHeaderBox().getTimescale();
        Log.e(TAG,""+lengthInSeconds);
		
	}

}
