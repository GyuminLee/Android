package org.jcodec.codecs.mjpeg;

import java.nio.ByteBuffer;

import org.jcodec.common.dct.IDCT2x2;
import org.jcodec.common.io.BitReader;
import org.jcodec.common.io.VLC;
import org.jcodec.common.model.Picture;
import org.jcodec.common.model.Rect;
import org.jcodec.common.tools.MathUtil;

/**
 * This class is part of JCodec ( www.jcodec.org ) This software is distributed
 * under FreeBSD License
 * 
 * Decodes JPEG in low res taking only first 4 coefficients from each DCT block
 * ( DCT 2x2 )
 * 
 * @author Jay Codec
 * 
 */
public class JpegToThumb2x2 extends JpegDecoder {

    public JpegToThumb2x2() {
        super();
    }

    public JpegToThumb2x2(boolean interlace, boolean topFieldFirst) {
        super(interlace, topFieldFirst);
    }

    private static final int mapping2x2[] = new int[] { 0, 1, 2, 4, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4,
            4, 4, 4, 4, 4, 4, 4 };

    @Override
    void decodeBlock(BitReader bits, int[] dcPredictor, int[][] quant, VLC[] huff, Picture result, int[] buf, int blkX,
            int blkY, int plane, int chroma, int field, int step) {
        buf[1] = buf[2] = buf[3] = 0;
        dcPredictor[plane] = buf[0] = readDCValue(bits, huff[chroma]) * quant[chroma][0] + dcPredictor[plane];
        readACValues(bits, buf, huff[chroma + 2], quant[chroma]);
        IDCT2x2.idct(buf, 0);

        putBlock2x2(result.getPlaneData(plane), result.getPlaneWidth(plane), buf, blkX, blkY, field, step);
    }

    private void putBlock2x2(int[] plane, int stride, int[] patch, int x, int y, int field, int step) {
        stride >>= 2;
        int dstride = stride * step;
        int off = field * stride + (y >> 2) * dstride + (x >> 2);
        plane[off] = MathUtil.clip(patch[0], 0, 255);
        plane[off + 1] = MathUtil.clip(patch[1], 0, 255);
        plane[off + dstride] = MathUtil.clip(patch[2], 0, 255);
        plane[off + dstride + 1] = MathUtil.clip(patch[3], 0, 255);
    }

    @Override
    void readACValues(BitReader in, int[] target, VLC table, int[] quantTable) {
        int code;
        int curOff = 1;
        do {
            code = table.readVLC16(in);
            if (code == 0xF0) {
                curOff += 16;
            } else if (code > 0) {
                int rle = code >> 4;
                curOff += rle;
                int len = code & 0xf;
                target[mapping2x2[curOff]] = toValue(in.readNBit(len), len) * quantTable[curOff];
                curOff++;
            }
        } while (code != 0 && curOff < 5);

        if (code != 0) {
            do {
                code = table.readVLC16(in);
                if (code == 0xF0) {
                    curOff += 16;
                } else if (code > 0) {
                    int rle = code >> 4;
                    curOff += rle;
                    int len = code & 0xf;
                    in.skip(len);
                    curOff++;
                }
            } while (code != 0 && curOff < 64);
        }
    }

    @Override
    public Picture decodeField(ByteBuffer data, int[][] data2, int field, int step) {
        Picture res = super.decodeField(data, data2, field, step);

        return new Picture(res.getWidth() >> 2, res.getHeight() >> 2, res.getData(), res.getColor(), new Rect(0, 0,
                res.getCroppedWidth() >> 2, res.getCroppedHeight() >> 2));
    }
}