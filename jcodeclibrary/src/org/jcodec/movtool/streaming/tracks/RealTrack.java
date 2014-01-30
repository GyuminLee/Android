package org.jcodec.movtool.streaming.tracks;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

import org.jcodec.common.SeekableByteChannel;
import org.jcodec.containers.mp4.MP4Packet;
import org.jcodec.containers.mp4.boxes.Box;
import org.jcodec.containers.mp4.boxes.Edit;
import org.jcodec.containers.mp4.boxes.MovieBox;
import org.jcodec.containers.mp4.boxes.SampleEntry;
import org.jcodec.containers.mp4.boxes.SampleSizesBox;
import org.jcodec.containers.mp4.boxes.TrakBox;
import org.jcodec.containers.mp4.demuxer.AbstractMP4DemuxerTrack;
import org.jcodec.containers.mp4.demuxer.FramesMP4DemuxerTrack;
import org.jcodec.containers.mp4.demuxer.PCMMP4DemuxerTrack;
import org.jcodec.movtool.streaming.VirtualPacket;
import org.jcodec.movtool.streaming.VirtualTrack;

/**
 * This class is part of JCodec ( www.jcodec.org ) This software is distributed
 * under FreeBSD License
 * 
 * Track taken from a real movie
 * 
 * @author The JCodec project
 * 
 */
public class RealTrack implements VirtualTrack {

    private TrakBox trak;
    private ByteChannelPool pool;
    private AbstractMP4DemuxerTrack demuxer;
    private MovieBox movie;

    public RealTrack(MovieBox movie, TrakBox trak, ByteChannelPool pool) {
        this.movie = movie;
        SampleSizesBox stsz = Box.findFirst(trak, SampleSizesBox.class, "mdia", "minf", "stbl", "stsz");
        if (stsz.getDefaultSize() == 0) {
            this.demuxer = new FramesMP4DemuxerTrack(movie, trak, null) {
                @Override
                protected ByteBuffer readPacketData(SeekableByteChannel ch, ByteBuffer buffer, long position, int size)
                        throws IOException {
                    return buffer;
                }
            };
        } else {
            this.demuxer = new PCMMP4DemuxerTrack(movie, trak, null) {
                @Override
                protected ByteBuffer readPacketData(SeekableByteChannel ch, ByteBuffer buffer, long position, int size)
                        throws IOException {
                    return buffer;
                }
            };
        }
        this.trak = trak;
        this.pool = pool;
    }

    @Override
    public VirtualPacket nextPacket() throws IOException {
        MP4Packet pkt = demuxer.nextFrame(null);
        if (pkt == null)
            return null;
        return new RealPacket(pkt);
    }

    @Override
    public SampleEntry getSampleEntry() {
        return trak.getSampleEntries()[0];
    }

    @Override
    public void close() {
//        System.out.println("CLOSING FILE");
        pool.close();
    }

    public class RealPacket implements VirtualPacket {

        private MP4Packet packet;

        public RealPacket(MP4Packet nextFrame) {
            this.packet = nextFrame;
        }

        @Override
        public ByteBuffer getData() throws IOException {
            ByteBuffer bb = ByteBuffer.allocate(packet.getSize());
            SeekableByteChannel ch = null;
            try {
                ch = pool.getChannel();
                if(packet.getFileOff() >= ch.size())
                    return null;
                ch.position(packet.getFileOff());
                ch.read(bb);
                bb.flip();
                return bb;
            } finally {
                if (ch != null)
                    ch.close();
            }
        }

        @Override
        public int getDataLen() {
            return packet.getSize();
        }

        @Override
        public double getPts() {
            return (double) packet.getMediaPts() / packet.getTimescale();
        }

        @Override
        public double getDuration() {
            return (double) packet.getDuration() / packet.getTimescale();
        }

        @Override
        public boolean isKeyframe() {
            return packet.isKeyFrame() || packet.isPsync();
        }

        @Override
        public int getFrameNo() {
            return (int) packet.getFrameNo();
        }
    }

    @Override
    public VirtualEdit[] getEdits() {
        List<Edit> edits = demuxer.getEdits();
        if (edits == null)
            return null;
        VirtualEdit[] result = new VirtualEdit[edits.size()];
        for (int i = 0; i < edits.size(); i++) {
            Edit ee = edits.get(i);
            result[i] = new VirtualEdit((double) ee.getMediaTime() / trak.getTimescale(), (double) ee.getDuration()
                    / movie.getTimescale());
        }
        return result;
    }

    @Override
    public int getPreferredTimescale() {
        return (int) demuxer.getTimescale();
    }
}