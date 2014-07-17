package com.example.sample4googlemap;

import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;

public class MyInfoWindow implements InfoWindowAdapter {

	View infoView;
	ImageView iconView;
	TextView titleView;
	TextView descView;
	HashMap<Marker,MyData> dataResolver;
	
	public MyInfoWindow(Context context, HashMap<Marker,MyData> dataResolver) {
		infoView = LayoutInflater.from(context).inflate(R.layout.info_window_layout, null);
		iconView = (ImageView)infoView.findViewById(R.id.icon_view);
		iconView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(v.getContext(), "test", Toast.LENGTH_SHORT).show();
			}
		});
		titleView = (TextView)infoView.findViewById(R.id.title_view);
		descView = (TextView)infoView.findViewById(R.id.description_view);
		this.dataResolver = dataResolver;
	}
	
	@Override
	public View getInfoContents(Marker marker) {
		return null;
	}

	@Override
	public View getInfoWindow(Marker marker) {
		MyData data = dataResolver.get(marker);
		iconView.setImageResource(data.resId);
		titleView.setText(data.title);
		descView.setText(data.description);
		return infoView;
	}

}
