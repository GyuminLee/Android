package com.example.sample3tmap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;

import com.skp.Tmap.TMapMarkerItem2;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapView;

public class MyTMapMarker extends TMapMarkerItem2 {

	Matrix matrix;
	public MyTMapMarker() {
		matrix = new Matrix();
	}
	
	@Override
	public void draw(Canvas canvas, TMapView mapView, boolean showCallout) {
		super.draw(canvas, mapView, showCallout);
		Bitmap icon = getIcon();
		TMapPoint point = getTMapPoint();
		int x = mapView.getMapXForPoint(point.getLongitude(), point.getLatitude());
		int y = mapView.getMapYForPoint(point.getLongitude(), point.getLatitude());

		matrix.reset();
		matrix.setScale(1, -1, icon.getWidth()/2, icon.getHeight()/2);
		matrix.postTranslate(x, y);
		canvas.drawBitmap(icon, matrix, null);
	}
	
}
