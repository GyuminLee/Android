package com.example.samplearcamera.library;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

public class ImageProcessHandler extends Handler {

	private static ImageProcessHandler instance;
	public static final int MESSAGE_TYPE_IMAGE_PROCESS = 1;
	private int mType = CameraView.IMAGE_FORMAT_DEFAULT;
	
	public static ImageProcessHandler getInstance() {
		if (instance == null) {
			HandlerThread th = new HandlerThread("HandleThread");
			th.start();
			instance = new ImageProcessHandler(th.getLooper());
		}
		return instance;
	}
	
	private ImageProcessHandler(Looper looper) {
		super(looper);
	}
	
	public void sendImageProcessEvent(int width, int height, byte[] data) {
		this.removeMessages(MESSAGE_TYPE_IMAGE_PROCESS);
		sendMessage(obtainMessage(MESSAGE_TYPE_IMAGE_PROCESS, width, height, data));
	}
	
	@Override
	public void handleMessage(Message msg) {
		switch(msg.what) {
		case MESSAGE_TYPE_IMAGE_PROCESS :
			int width = msg.arg1;
			int height = msg.arg2;
			byte[] data = (byte[])msg.obj;
			processData(data,width,height);
		}
	}
	
	public void setDataType(int type) {
		mType = type;
	}
	
	private void processData(byte[] data,int width,int height) {
		short[] imageDataRGB565;
		
		switch(mType) {
			case CameraView.IMAGE_FORMAT_DEFAULT :
				imageDataRGB565 = ImageConvert.convertYUV420_NV21toRGB8888(data, width, height);
				break;
			case CameraView.IMAGE_FORMAT_RGB_565 :
				imageDataRGB565 = ByteBuffer.wrap(data).order(ByteOrder.BIG_ENDIAN).asShortBuffer().array();
				break;
			default :
				imageDataRGB565 = null;
		}
		
		if (imageDataRGB565 != null) {
			// imageData Processing
		}
	}
}
