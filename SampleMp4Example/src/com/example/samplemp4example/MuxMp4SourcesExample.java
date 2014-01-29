package com.example.samplemp4example;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Handler;

import com.coremedia.iso.boxes.Container;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.builder.FragmentedMp4Builder;
import com.googlecode.mp4parser.authoring.builder.SyncSampleIntersectFinderImpl;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;

public class MuxMp4SourcesExample extends ParentExample {

	public MuxMp4SourcesExample(Handler handler, OnCompleteListener listener) {
		super(handler, listener);
	}

	@Override
	void executeExample() throws IOException {
        String audioDeutsch = EXAMPLE_ROOT + "/count-deutsch-audio.mp4";
        String audioEnglish = EXAMPLE_ROOT + "/count-english-audio.mp4";
        String video = EXAMPLE_ROOT + "/count-video.mp4";


        Movie countVideo = MovieCreator.build(video);
        Movie countAudioDeutsch = MovieCreator.build(audioDeutsch);
        Movie countAudioEnglish = MovieCreator.build(audioEnglish);

        Track audioTrackDeutsch = countAudioDeutsch.getTracks().get(0);
        audioTrackDeutsch.getTrackMetaData().setLanguage("deu");
        Track audioTrackEnglish = countAudioEnglish.getTracks().get(0);
        audioTrackEnglish.getTrackMetaData().setLanguage("eng");

        countVideo.addTrack(audioTrackDeutsch);
        countVideo.addTrack(audioTrackEnglish);

        {
            Container out = new DefaultMp4Builder().build(countVideo);
            FileOutputStream fos = new FileOutputStream(new File(EXAMPLE_ROOT + "/output.mp4"));
            out.writeContainer(fos.getChannel());
            fos.close();
        }
        {
            FragmentedMp4Builder fragmentedMp4Builder = new FragmentedMp4Builder();
            fragmentedMp4Builder.setIntersectionFinder(new SyncSampleIntersectFinderImpl(countVideo, null, -1));
            Container out = fragmentedMp4Builder.build(countVideo);
            FileOutputStream fos = new FileOutputStream(new File(EXAMPLE_ROOT + "/output-frag.mp4"));
            out.writeContainer(fos.getChannel());
            fos.close();
        }
	}

}
