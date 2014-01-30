package org.jcodec.codecs.mpeg12.bitstream;

import java.nio.ByteBuffer;

import org.jcodec.common.io.BitReader;
import org.jcodec.common.io.BitWriter;
import org.jcodec.common.model.TapeTimecode;

/**
 * This class is part of JCodec ( www.jcodec.org ) This software is distributed
 * under FreeBSD License
 * 
 * @author The JCodec project
 * 
 */
public class GOPHeader {

    private TapeTimecode timeCode;
    private boolean closedGop;
    private boolean brokenLink;

    public GOPHeader(TapeTimecode timeCode, boolean closedGop, boolean brokenLink) {
        super();
        this.timeCode = timeCode;
        this.closedGop = closedGop;
        this.brokenLink = brokenLink;
    }

    public static GOPHeader read(ByteBuffer bb) {
        BitReader in = new BitReader(bb);
        boolean dropFrame = in.read1Bit() == 1;
        short hours = (short) in.readNBit(5);
        byte minutes = (byte) in.readNBit(6);
        in.skip(1);

        byte seconds = (byte) in.readNBit(6);
        byte frames = (byte) in.readNBit(6);

        boolean closedGop = in.read1Bit() == 1;
        boolean brokenLink = in.read1Bit() == 1;

        return new GOPHeader(new TapeTimecode(hours, minutes, seconds, frames, dropFrame), closedGop, brokenLink);
    }

    public void write(ByteBuffer os) {
        BitWriter out = new BitWriter(os);
        if (timeCode == null)
            out.writeNBit(0, 25);
        else {
            out.write1Bit(timeCode.isDropFrame() ? 1 : 0);
            out.writeNBit(timeCode.getHour(), 5);
            out.writeNBit(timeCode.getMinute(), 6);
            out.write1Bit(1);
            out.writeNBit(timeCode.getSecond(), 6);
            out.writeNBit(timeCode.getFrame(), 6);
        }
        out.write1Bit(closedGop ? 1 : 0);
        out.write1Bit(brokenLink ? 1 : 0);
    }

    public TapeTimecode getTimeCode() {
        return timeCode;
    }

    public boolean isClosedGop() {
        return closedGop;
    }

    public boolean isBrokenLink() {
        return brokenLink;
    }
}