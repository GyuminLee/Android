package org.tacademy.basic.samplecamerapreview;

import java.io.OutputStream;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;

public class ImageCaptureCallback implements PictureCallback  {

	private OutputStream filoutputStream;
	
	public ImageCaptureCallback(OutputStream filoutputStream) {
		this.filoutputStream = filoutputStream;
	}
	
	public void onPictureTaken(byte[] data, Camera camera) {
		try {
			filoutputStream.write(data);
			filoutputStream.flush();
			filoutputStream.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}