package com.example.sample4googlemap;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.ImageView;
import android.widget.TextView;

public class InfoBitmap {

	View infoView;
	ImageView imageView;
	TextView titleView;
	TextView contentView;

	int measureSpec;
	public InfoBitmap(Context context) {
		infoView = LayoutInflater.from(context).inflate(R.layout.info_window_layout, null);
		imageView = (ImageView)infoView.findViewById(R.id.icon_view);
		titleView = (TextView)infoView.findViewById(R.id.title_view);
		contentView = (TextView)infoView.findViewById(R.id.description_view);
		measureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
	}

	public Bitmap getInfoBitmap(MyData data) {
		imageView.setImageResource(data.resId);
		titleView.setText(data.title);
		contentView.setText(data.description);
		
		infoView.measure(measureSpec, measureSpec);
		infoView.layout(0, 0, infoView.getMeasuredWidth(), infoView.getMeasuredHeight());
		
		return getViewBitmap(infoView);
	}
    private Bitmap getViewBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);

        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);

        // Reset the drawing cache background color to fully transparent
        // for the duration of this operation
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);

        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();

        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        // Restore the view
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);

        return bitmap;
    }
	
}
