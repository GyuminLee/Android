package org.jcodec.containers.mp4;

import java.util.EnumSet;

/**
 * This class is part of JCodec ( www.jcodec.org ) This software is distributed
 * under FreeBSD License
 * 
 * Creates MP4 file out of a set of samples
 * 
 * @author The JCodec project
 * 
 */
public enum TrackType {
    VIDEO("vide"), SOUND("soun"), TIMECODE("tmcd"), HINT("hint");

    private String handler;

    private TrackType(String handler) {
        this.handler = handler;
    }

    public String getHandler() {
        return handler;
    }

    public static TrackType fromHandler(String handler) {
        for (TrackType val : EnumSet.allOf(TrackType.class)) {
            if (val.getHandler().equals(handler))
                return val;
        }
        return null;
    }
}
