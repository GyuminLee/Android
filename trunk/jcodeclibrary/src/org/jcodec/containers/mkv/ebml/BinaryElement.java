/**
 * JEBML - Java library to read/write EBML/Matroska elements.
 * Copyright (C) 2004 Jory Stone <jebml@jory.info>
 * Based on Javatroska (C) 2002 John Cannon <spyder@matroska.org>
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

/*
 * Element.java
 *
 * 
 */

package org.jcodec.containers.mkv.ebml;

import java.nio.ByteBuffer;

/**
 * Defines the basic EBML element. Subclasses may provide child element access.
 * Created on November 19, 2002, 9:11 PM
 * 
 * @author John Cannon
 */
public class BinaryElement extends Element {

    public BinaryElement(byte[] type) {
        super(type);
    }

    /**
     * Getter for property data.
     * 
     * @return Value of property data.
     * 
     */
    public byte[] getData() {
        return this.data.array();
    }

    /**
     * Setter for property data.
     * 
     * @param data
     *            New value of property data.
     * 
     */
    public void setData(byte[] data) {
        this.data = ByteBuffer.allocate(data.length);
        this.data.put(data);
        this.data.flip();
        this.size = this.data.limit();
    }

}
