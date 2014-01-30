package org.jcodec.containers.mp4.boxes;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.LinkedList;

import org.jcodec.common.NIOUtils;
import org.jcodec.common.JCodecUtil;

/**
 * This class is part of JCodec ( www.jcodec.org ) This software is distributed
 * under FreeBSD License
 * 
 * File type box
 * 
 * 
 * @author Jay Codec
 * 
 */
public class FileTypeBox extends Box {
    private String majorBrand;
    private int minorVersion;
    private Collection<String> compBrands = new LinkedList<String>();

    public static String fourcc() {
        return "ftyp";
    }

    public FileTypeBox(String majorBrand, int minorVersion, Collection<String> compBrands) {
        super(new Header(fourcc()));
        this.majorBrand = majorBrand;
        this.minorVersion = minorVersion;
        this.compBrands = compBrands;
    }

    public FileTypeBox() {
        super(new Header(fourcc()));
    }

    public void parse(ByteBuffer input) {
        majorBrand = NIOUtils.readString(input, 4);
        minorVersion = input.getInt();

        String brand;
        while ((brand = NIOUtils.readString(input, 4)) != null) {
            compBrands.add(brand);
        }
    }

    public String getMajorBrand() {
        return majorBrand;
    }

    public Collection<String> getCompBrands() {
        return compBrands;
    }

    public void doWrite(ByteBuffer out) {
        out.put(JCodecUtil.asciiString(majorBrand));
        out.putInt(minorVersion);

        for (String string : compBrands) {
            out.put(JCodecUtil.asciiString(string));
        }
    }
}