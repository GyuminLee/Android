package com.example.samplemp4example;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import android.os.Handler;

import com.coremedia.iso.boxes.Container;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;
import com.googlecode.mp4parser.authoring.tracks.ReplaceSampleTrack;

public class ReplaceSomeSamples extends ParentExample {

	public ReplaceSomeSamples(Handler handler, OnCompleteListener listener) {
		super(handler, listener);
	}

	@Override
	void executeExample() throws IOException {
        String audioEnglish = EXAMPLE_ROOT + "/count-english-audio.mp4";
        Movie originalMovie = MovieCreator.build(audioEnglish);

        Track audio = originalMovie.getTracks().get(0);

        Movie nuMovie = new Movie();

        nuMovie.addTrack(new ReplaceSampleTrack(
                new ReplaceSampleTrack(
                        new ReplaceSampleTrack(
                                audio,
                                25, ByteBuffer.allocate(5)),
                        27, ByteBuffer.allocate(5)),
                29, ByteBuffer.allocate(5)));
        Container out = new DefaultMp4Builder().build(nuMovie);
        FileOutputStream fos = new FileOutputStream(new File(EXAMPLE_ROOT + "/output.mp4"));
        FileChannel fc = fos.getChannel();
        out.writeContainer(fc);
        fos.close();
	}

}
