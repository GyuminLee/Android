package org.jcodec.containers.mp4.demuxer;

import static org.jcodec.containers.mp4.boxes.Box.findFirst;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

import org.jcodec.common.SeekableDemuxerTrack;
import org.jcodec.common.NIOUtils;
import org.jcodec.common.SeekableByteChannel;
import org.jcodec.common.model.RationalLarge;
import org.jcodec.containers.mp4.MP4Packet;
import org.jcodec.containers.mp4.TrackType;
import org.jcodec.containers.mp4.boxes.Box;
import org.jcodec.containers.mp4.boxes.ChunkOffsets64Box;
import org.jcodec.containers.mp4.boxes.ChunkOffsetsBox;
import org.jcodec.containers.mp4.boxes.Edit;
import org.jcodec.containers.mp4.boxes.EditListBox;
import org.jcodec.containers.mp4.boxes.NameBox;
import org.jcodec.containers.mp4.boxes.NodeBox;
import org.jcodec.containers.mp4.boxes.SampleEntry;
import org.jcodec.containers.mp4.boxes.SampleToChunkBox;
import org.jcodec.containers.mp4.boxes.SampleToChunkBox.SampleToChunkEntry;
import org.jcodec.containers.mp4.boxes.SyncSamplesBox;
import org.jcodec.containers.mp4.boxes.TimeToSampleBox;
import org.jcodec.containers.mp4.boxes.TimeToSampleBox.TimeToSampleEntry;
import org.jcodec.containers.mp4.boxes.TrakBox;

/**
 * This class is part of JCodec ( www.jcodec.org ) This software is distributed
 * under FreeBSD License
 * 
 * Shared routines between PCM and Frames tracks
 * 
 * @author The JCodec project
 * 
 */
public abstract class AbstractMP4DemuxerTrack implements SeekableDemuxerTrack {
    protected TrakBox box;
    private TrackType type;
    private int no;
    protected SampleEntry[] sampleEntries;

    protected TimeToSampleEntry[] timeToSamples;
    protected SampleToChunkEntry[] sampleToChunks;
    protected long[] chunkOffsets;

    protected long duration;

    protected int sttsInd;
    protected int sttsSubInd;

    protected int stcoInd;

    protected int stscInd;

    protected long pts;
    protected long curFrame;
    protected int timescale;

    public AbstractMP4DemuxerTrack(TrakBox trak) {
        no = trak.getTrackHeader().getNo();
        type = MP4Demuxer.getTrackType(trak);
        sampleEntries = Box.findAll(trak, SampleEntry.class, "mdia", "minf", "stbl", "stsd", null);

        NodeBox stbl = trak.getMdia().getMinf().getStbl();

        TimeToSampleBox stts = findFirst(stbl, TimeToSampleBox.class, "stts");
        SampleToChunkBox stsc = findFirst(stbl, SampleToChunkBox.class, "stsc");
        ChunkOffsetsBox stco = findFirst(stbl, ChunkOffsetsBox.class, "stco");
        ChunkOffsets64Box co64 = findFirst(stbl, ChunkOffsets64Box.class, "co64");

        timeToSamples = stts.getEntries();
        sampleToChunks = stsc.getSampleToChunk();
        chunkOffsets = stco != null ? stco.getChunkOffsets() : co64.getChunkOffsets();

        for (TimeToSampleEntry ttse : timeToSamples) {
            duration += ttse.getSampleCount() * ttse.getSampleDuration();
        }
        box = trak;

        timescale = trak.getTimescale();
    }

    public int pts2Sample(long _tv, int _timescale) {
        long tv = _tv * timescale / _timescale;
        int ttsInd, sample = 0;
        for (ttsInd = 0; ttsInd < timeToSamples.length - 1; ttsInd++) {
            int a = timeToSamples[ttsInd].getSampleCount() * timeToSamples[ttsInd].getSampleDuration();
            if (tv < a)
                break;
            tv -= a;
            sample += timeToSamples[ttsInd].getSampleCount();
        }
        return sample + (int) (tv / timeToSamples[ttsInd].getSampleDuration());
    }

    public TrackType getType() {
        return type;
    }

    public int getNo() {
        return no;
    }

    public SampleEntry[] getSampleEntries() {
        return sampleEntries;
    }

    public TrakBox getBox() {
        return box;
    }

    public long getTimescale() {
        return timescale;
    }

    protected abstract void seekPointer(long frameNo);

    public boolean canSeek(long pts) {
        return pts >= 0 && pts < duration;
    }

    public synchronized boolean seek(long pts) {
        if (pts < 0)
            throw new IllegalArgumentException("Seeking to negative pts");
        if (pts >= duration)
            return false;

        long prevDur = 0;
        int frameNo = 0;
        for (sttsInd = 0; pts > prevDur + timeToSamples[sttsInd].getSampleCount()
                * timeToSamples[sttsInd].getSampleDuration()
                && sttsInd < timeToSamples.length - 1; sttsInd++) {
            prevDur += timeToSamples[sttsInd].getSampleCount() * timeToSamples[sttsInd].getSampleDuration();
            frameNo += timeToSamples[sttsInd].getSampleCount();
        }
        sttsSubInd = (int) ((pts - prevDur) / timeToSamples[sttsInd].getSampleDuration());
        frameNo += sttsSubInd;
        this.pts = prevDur + timeToSamples[sttsInd].getSampleDuration() * sttsSubInd;

        seekPointer(frameNo);

        return true;
    }

    protected long shiftPts(long frames) {
        long result = 0;
        int rem;
        while (frames > (rem = timeToSamples[sttsInd].getSampleCount() - sttsSubInd)) {
            frames -= rem;
            result += rem * timeToSamples[sttsInd].getSampleDuration();
            sttsInd++;
            sttsSubInd = 0;
            if (sttsInd >= timeToSamples.length)
                return result;
        }
        result += frames * timeToSamples[sttsInd].getSampleDuration();

        pts += result;

        return result;
    }

    protected void nextChunk() {
        if (stcoInd >= chunkOffsets.length)
            return;
        stcoInd++;

        if ((stscInd + 1 < sampleToChunks.length) && stcoInd + 1 == sampleToChunks[stscInd + 1].getFirst()) {
            stscInd++;
        }
    }

    public synchronized boolean gotoFrame(long frameNo) {
        if (frameNo < 0)
            throw new IllegalArgumentException("negative frame number");
        if (frameNo >= getFrameCount())
            return false;
        if (frameNo == curFrame)
            return true;

        seekPointer(frameNo);
        seekPts(frameNo);

        return true;
    }

    @Override
    public void seek(double second) {
        seek((long) (second * timescale));
    }

    private void seekPts(long frameNo) {
        pts = sttsInd = sttsSubInd = 0;
        shiftPts(frameNo);
    }

    public RationalLarge getDuration() {
        return new RationalLarge(box.getMediaDuration(), box.getTimescale());
    }

    public abstract long getFrameCount();

    public long getCurFrame() {
        return curFrame;
    }

    public List<Edit> getEdits() {
        EditListBox editListBox = Box.findFirst(box, EditListBox.class, "edts", "elst");
        if (editListBox != null)
            return editListBox.getEdits();
        return null;
    }

    public String getName() {
        NameBox nameBox = Box.findFirst(box, NameBox.class, "udta", "name");
        return nameBox != null ? nameBox.getName() : null;
    }

    public String getFourcc() {
        return getSampleEntries()[0].getFourcc();
    }

    protected ByteBuffer readPacketData(SeekableByteChannel input, ByteBuffer buffer, long offset, int size)
            throws IOException {
        ByteBuffer result = buffer.duplicate();
        synchronized (input) {
            input.position(offset);
            NIOUtils.read(input, result, size);
        }
        result.flip();
        return result;
    }

    public abstract MP4Packet nextFrame(ByteBuffer storage) throws IOException;
}