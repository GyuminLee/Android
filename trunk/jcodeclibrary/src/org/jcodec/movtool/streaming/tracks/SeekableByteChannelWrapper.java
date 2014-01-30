package org.jcodec.movtool.streaming.tracks;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.jcodec.common.SeekableByteChannel;

/**
 * This class is part of JCodec ( www.jcodec.org ) This software is distributed
 * under FreeBSD License
 * 
 * A seekable byte channel wrappter
 * 
 * @author The JCodec project
 * 
 */
public class SeekableByteChannelWrapper implements SeekableByteChannel {
    protected SeekableByteChannel src;

    public SeekableByteChannelWrapper(SeekableByteChannel src) {
        this.src = src;
    }

    @Override
    public int read(ByteBuffer dst) throws IOException {
        return src.read(dst);
    }

    @Override
    public boolean isOpen() {
        return src.isOpen();
    }

    @Override
    public void close() throws IOException {
        src.close();
    }

    @Override
    public int write(ByteBuffer buf) throws IOException {
        return src.write(buf);
    }

    @Override
    public long position() throws IOException {
        return src.position();
    }

    @Override
    public SeekableByteChannel position(long newPosition) throws IOException {
        src.position(newPosition);
        return this;
    }

    @Override
    public long size() throws IOException {
        return src.size();
    }

    @Override
    public SeekableByteChannel truncate(long size) throws IOException {
        src.truncate(size);
        return this;
    }
}
