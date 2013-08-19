package com.example.samplesurfacetest3;

public class ImageUtil {
	/**
	 * Converts YUV420 NV21 to ARGB8888
	 * 
	 * @param data byte array on YUV420 NV21 format.
	 * @param width pixels width
	 * @param height pixels height
	 * @return a ARGB8888 pixels int array. Where each int is a pixels ARGB. 
	 */
	public static int[] convertYUV420_NV21toARGB8888(byte [] data, int width, int height) {
	    int size = width*height;
	    int offset = size;
	    int[] pixels = new int[size];
	    int u, v, y1, y2, y3, y4;
	 
	    // i along Y and the final pixels
	    // k along pixels U and V
	    for(int i=0, k=0; i < size; i+=2, k+=1) {
	        y1 = data[i  ]&0xff;
	        y2 = data[i+1]&0xff;
	        y3 = data[width+i  ]&0xff;
	        y4 = data[width+i+1]&0xff;
	 
	        u = data[offset+k  ]&0xff;
	        v = data[offset+width+k+1]&0xff;
	        u = u-128;
	        v = v-128;
	 
	        pixels[i  ] = convertYUVtoARGB(y1, u, v);
	        pixels[i+1] = convertYUVtoARGB(y2, u, v);
	        pixels[width+i  ] = convertYUVtoARGB(y3, u, v);
	        pixels[width+i+1] = convertYUVtoARGB(y4, u, v);
	 
	        if (i!=0 && (i+2)%width==0)
	            i+=width;
	    }
	 
	    return pixels;
	}
	 
	private static int convertYUVtoARGB(int y, int u, int v) {
	    int r,g,b;
	 
	    r = y + (int)1.402f*u;
	    g = y - (int)(0.344f*v +0.714f*u);
	    b = y + (int)1.772f*v;
	    r = r>255? 255 : r<0 ? 0 : r;
	    g = g>255? 255 : g<0 ? 0 : g;
	    b = b>255? 255 : b<0 ? 0 : b;
	    return 0xff000000 | (r<<16) | (g<<8) | b;
	}
}
