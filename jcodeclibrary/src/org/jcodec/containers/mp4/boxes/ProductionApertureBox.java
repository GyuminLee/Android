package org.jcodec.containers.mp4.boxes;


/**
 * This class is part of JCodec ( www.jcodec.org ) This software is distributed
 * under FreeBSD License
 * 
 * @author The JCodec project
 * 
 */
public class ProductionApertureBox extends ClearApertureBox {

    public static String fourcc() {
        return "prof";
    }

    public ProductionApertureBox(Header atom) {
        super(atom);
    }

    public ProductionApertureBox() {
        super(new Header(fourcc()));
    }

    public ProductionApertureBox(int width, int height) {
        super(new Header(fourcc()), width, height);
    }
}
