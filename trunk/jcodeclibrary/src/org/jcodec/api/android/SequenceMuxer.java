package org.jcodec.api.android;

import java.io.File;
import java.io.IOException;

import org.jcodec.common.NIOUtils;
import org.jcodec.common.SeekableByteChannel;
import org.jcodec.common.model.Size;
import org.jcodec.containers.mp4.Brand;
import org.jcodec.containers.mp4.MP4Packet;
import org.jcodec.containers.mp4.TrackType;
import org.jcodec.containers.mp4.muxer.FramesMP4MuxerTrack;
import org.jcodec.containers.mp4.muxer.MP4Muxer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * This class is part of JCodec ( www.jcodec.org ) This software is distributed
 * under FreeBSD License
 * 
 * @author The JCodec project
 * 
 */
public class SequenceMuxer {
    private SeekableByteChannel ch;
    private FramesMP4MuxerTrack outTrack;
    private int frameNo;
    private MP4Muxer muxer;
    private Size size;

    public SequenceMuxer(File out) throws IOException {
        this.ch = NIOUtils.writableFileChannel(out);

        // Muxer that will store the encoded frames
        muxer = new MP4Muxer(ch, Brand.MP4);

        // Add video track to muxer
        outTrack = muxer.addTrack(TrackType.VIDEO, 25);
    }

    public void encodeImage(File png) throws IOException {
        if (size == null) {
            Bitmap read = BitmapFactory.decodeFile(png.getAbsolutePath());
            size = new Size(read.getWidth(), read.getHeight());
        }
        // Add packet to video track
        outTrack.addFrame(new MP4Packet(NIOUtils.fetchFrom(png), frameNo, 25, 1, frameNo, true, null, frameNo, 0));

        frameNo++;
    }

    public void finish() throws IOException {
        // Push saved SPS/PPS to a special storage in MP4
        outTrack.addSampleEntry(MP4Muxer.videoSampleEntry("png ", size, "JCodec"));

        // Write MP4 header and finalize recording
        muxer.writeHeader();
        NIOUtils.closeQuietly(ch);
    }
}
