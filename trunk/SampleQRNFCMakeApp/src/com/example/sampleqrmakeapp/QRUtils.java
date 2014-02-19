package com.example.sampleqrmakeapp;

import java.io.UnsupportedEncodingException;
import java.nio.FloatBuffer;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRUtils {
	public static Bitmap makeQRCode(String message) throws UnsupportedEncodingException, WriterException {
		String qrText = new String(message.getBytes("UTF-8"),"ISO-8859-1");
		int onColor = Color.BLACK;
		int offColor = Color.WHITE;
		int width = 230;
		int height = 230;
		QRCodeWriter writer = new QRCodeWriter();
		BitMatrix bitMatrix = writer.encode(qrText, BarcodeFormat.QR_CODE, width, height);
		Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		
		FloatBuffer floatBuffer = FloatBuffer.allocate(width * height * 2);
		
		for (int x = 0; x < width ; x++) {
			for (int y = 0; y < height; y++) {
				if (bitMatrix.get(x, y)) {
					floatBuffer.put(x);
					floatBuffer.put(y);
				}
			}
		}
		
		canvas.drawColor(offColor);
		Paint paint = new Paint();
		paint.setColor(onColor);
		canvas.drawPoints(floatBuffer.array(), floatBuffer.arrayOffset(), floatBuffer.position(), paint);	
		
		return bitmap;
	}
}
