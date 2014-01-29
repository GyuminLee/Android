package com.example.samplemp4example;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Handler;

import com.coremedia.iso.boxes.Container;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;
import com.googlecode.mp4parser.authoring.tracks.AppendTrack;
import com.googlecode.mp4parser.authoring.tracks.CroppedTrack;

public class RemoveSomeSamplesExample extends ParentExample {

	public RemoveSomeSamplesExample(Handler handler,
			OnCompleteListener listener) {
		super(handler, listener);
	}

	@Override
	void executeExample() throws IOException {
        String audioEnglish = EXAMPLE_ROOT + "/count-english-audio.mp4";
        Movie originalMovie = MovieCreator.build(audioEnglish);

        Track audio = originalMovie.getTracks().get(0);

        Movie nuMovie = new Movie();
        nuMovie.addTrack(new AppendTrack(new CroppedTrack(audio, 0, 10), new CroppedTrack(audio, 100, audio.getSamples().size())));

        Container out = new DefaultMp4Builder().build(nuMovie);
        FileOutputStream fos = new FileOutputStream(new File(EXAMPLE_ROOT + "/output.mp4"));
        out.writeContainer(fos.getChannel());
        fos.close();
	}

}
