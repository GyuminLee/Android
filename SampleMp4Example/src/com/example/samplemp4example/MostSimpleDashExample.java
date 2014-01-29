package com.example.samplemp4example;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import android.os.Handler;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.Container;
import com.coremedia.iso.boxes.TrackBox;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Mp4TrackImpl;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;

public class MostSimpleDashExample extends ParentExample {

	public MostSimpleDashExample(Handler handler, OnCompleteListener listener) {
		super(handler, listener);
	}

	@Override
	void executeExample() throws IOException {
        String basePath = EXAMPLE_ROOT + "/dash/";

        Movie m = new Movie();
        IsoFile baseIsoFile = new IsoFile(basePath + "redbull_100kbit_dash.mp4");
        List<IsoFile> fragments = new LinkedList<IsoFile>();
        for (int i = 1; i < 9; i++) {
            fragments.add(new IsoFile(basePath + "redbull_10sec" + i + ".m4s"));
        }

        m.addTrack(new Mp4TrackImpl(
                baseIsoFile.getMovieBox().getBoxes(TrackBox.class).get(0),
                fragments.toArray(new IsoFile[fragments.size()])));


        DefaultMp4Builder builder = new DefaultMp4Builder();
        Container stdMp4 = builder.build(m);
        FileOutputStream fos = new FileOutputStream(EXAMPLE_ROOT + "/out.mp4");
        stdMp4.writeContainer(fos.getChannel());
        fos.close();
	}

}
