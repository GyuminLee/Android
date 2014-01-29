package com.example.samplemp4example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Properties;

import android.os.Handler;
import android.util.Base64;

import com.coremedia.iso.boxes.Container;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.builder.FragmentedMp4Builder;
import com.googlecode.mp4parser.authoring.builder.TwoSecondIntersectionFinder;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;
import com.googlecode.mp4parser.authoring.tracks.Amf0Track;

public class MuxVideoWithAmf0 extends ParentExample {

	public MuxVideoWithAmf0(Handler handler, OnCompleteListener listener) {
		super(handler, listener);
	}

	@Override
	void executeExample() throws IOException {
        String videoFile = EXAMPLE_ROOT + "/example-sans-amf0.mp4";

        Movie video = MovieCreator.build(videoFile);

        Properties props = new Properties();
        props.load(new FileInputStream(new File(EXAMPLE_ROOT+"/amf0track.properties")));
        HashMap<Long, byte[]> samples = new HashMap<Long, byte[]>();
        for (String key : props.stringPropertyNames()) {
            samples.put(Long.parseLong(key), Base64.decode(props.getProperty(key),Base64.DEFAULT));
        }
        Track amf0Track = new Amf0Track(samples);
        amf0Track.getTrackMetaData();
        video.addTrack(amf0Track);

        FragmentedMp4Builder fragmentedMp4Builder = new FragmentedMp4Builder();
        fragmentedMp4Builder.setIntersectionFinder(new TwoSecondIntersectionFinder(video, 2));

        Container out = fragmentedMp4Builder.build(video);
        FileOutputStream fos = new FileOutputStream(new File(String.format("output.mp4")));

        FileChannel fc = fos.getChannel();
        out.writeContainer(fc);

        fos.close();
		
	}

}
