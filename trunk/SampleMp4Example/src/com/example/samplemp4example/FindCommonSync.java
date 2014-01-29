package com.example.samplemp4example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import android.os.Handler;
import android.util.Log;

import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;

public class FindCommonSync extends ParentExample {

	private static final String TAG = "FindCommonSync";
	
	public FindCommonSync(Handler handler, OnCompleteListener listener,String... args) {
		super(handler, listener, args);
	}

    public <T extends Comparable<? super T>> List<T> asSortedList(Collection<T> c) {
        List<T> list = new ArrayList<T>(c);
        java.util.Collections.sort(list);
        return list;
    }
	
	@Override
	void executeExample() throws IOException {
        HashMap<Integer, Integer> common = new HashMap<Integer, Integer>();
        boolean first = true;
        for (String arg : args) {
            Movie invideo = MovieCreator.build(arg);
            List<Track> tracks = invideo.getTracks();

            for (Track t : tracks) {
                String type = t.getMediaHeaderBox().getType();
                String ctype = t.getSampleDescriptionBox().getSampleEntry().getType();
                Log.i(TAG,"Track of type " + type + " (" + ctype + ")");
                if (type.equals("vmhd")) {
                    HashMap<Integer, Integer> previous = (HashMap<Integer, Integer>) common.clone();
                    common.clear();
                    Log.i(TAG,"Found video track in " + arg);
                    long[] syncSamples = t.getSyncSamples();
                    long timescale = t.getTrackMetaData().getTimescale();
                    long tts = t.getSampleDurations()[0];
                    for (long syncSample : syncSamples) {
                        long time = 1000 * tts * (syncSample - 1) / timescale;
                        int inttime = (int) time;
                        if (first || previous.containsKey(inttime)) {
                            common.put(inttime, 1);
                        }
                    }
                    first = false;
                }
            }
        }
        // Print the common times
        Set<Integer> keys = common.keySet();

        List<Integer> inorder = asSortedList(keys);
        Integer previous = 0;
        int wrong = 0;
        for (Integer sync : inorder) {
            Integer delta = sync - previous;
            Log.i(TAG,"Common sync point: " + (double) sync / 1000.0 + " delta: " + (double) delta / 1000.0);
            if (delta > 3000) {
                Log.i(TAG,"WARNING WARNING! > 3sek");
                wrong++;
            }
            previous = sync;
        }
        int commonCount = inorder.size();
        Log.i(TAG,"Durations that are too long: " + wrong + "/" + commonCount);
	}

}
