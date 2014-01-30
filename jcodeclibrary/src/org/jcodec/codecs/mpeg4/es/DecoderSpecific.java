package org.jcodec.codecs.mpeg4.es;

import java.nio.ByteBuffer;

import org.jcodec.common.NIOUtils;

/**
 * This class is part of JCodec ( www.jcodec.org ) This software is distributed
 * under FreeBSD License
 * 
 * @author The JCodec project
 * 
 */
public class DecoderSpecific extends Descriptor {

    private ByteBuffer data;

    public DecoderSpecific(int tag, int size) {
        super(tag, size);
    }

    public DecoderSpecific(ByteBuffer data) {
        super(tag());
        this.data = data;
    }

    protected void doWrite(ByteBuffer out) {
        NIOUtils.write(out, data);
    }

    public static int tag() {
        return 0x5;
    }

    public ByteBuffer getData() {
        return data;
    }

    @Override
    protected void parse(ByteBuffer input) {
        data = NIOUtils.read(input);
    }
}
