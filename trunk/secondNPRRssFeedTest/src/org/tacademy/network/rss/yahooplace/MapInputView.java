package org.tacademy.network.rss.yahooplace;

import org.tacademy.network.rss.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MapInputView extends LinearLayout {

	EditText nameView;
	EditText streetView;
	EditText cityView;
	double latitude;
	double longitude;
	
	public interface OnMapInfoAddListener {
		public void onAdd(YahooPlacesItem item);
	}
	
	private OnMapInfoAddListener mListener;
	
	public void setOnMapInfoAddListener(OnMapInfoAddListener listener) {
		mListener = listener;
	}
	
	public MapInputView(Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.map_input_layout, this);
		nameView = (EditText)findViewById(R.id.name);
		streetView = (EditText)findViewById(R.id.street);
		cityView = (EditText)findViewById(R.id.city);
		Button btn = (Button)findViewById(R.id.add);
		btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				YahooPlacesItem item = new YahooPlacesItem();
				item.num = -1;
				item.name = nameView.getText().toString();
				item.city = cityView.getText().toString();
				item.country = "¥Î«—πŒ±π";
				item.county = "";
				item.latitude = latitude;
				item.longitude = longitude;
				item.state = "";
				item.street = streetView.getText().toString();
				if (mListener != null) {
					mListener.onAdd(item);
				}
			}
		});
	}
	
	public void setLocation(double lat,double lng) {
		this.latitude = lat;
		this.longitude = lng;
	}

}
