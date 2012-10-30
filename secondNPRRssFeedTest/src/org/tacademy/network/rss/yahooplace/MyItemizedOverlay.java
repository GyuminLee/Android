package org.tacademy.network.rss.yahooplace;

import java.util.ArrayList;

import org.tacademy.network.rss.util.TextDrawable;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class MyItemizedOverlay extends ItemizedOverlay<OverlayItem> {

	Drawable marker;
	ArrayList<YahooPlacesItem> items;
	Context mContext;
	
	public interface OnMapItemClickListener {
		public void onMapItemClick(YahooPlacesItem items);
		public void onMapClick(GeoPoint point);
	}
	
	OnMapItemClickListener mListener;
	
	public void setOnMapItemClickListener(OnMapItemClickListener listener) {
		mListener = listener;
	}
	
	public MyItemizedOverlay(Drawable defaultMarker) {
		super(defaultMarker);
		marker = defaultMarker;
		this.boundCenterBottom(marker);
	}
	
	public MyItemizedOverlay(Drawable defaultMarker,Context context) {
		super(defaultMarker);
		marker = defaultMarker;
		this.boundCenter(marker);
		mContext = context;
		setDrawFocusedItem(true);
	}

	public void setYahooPlacesItems(ArrayList<YahooPlacesItem> items){
		this.items = items;
		populate();
	}
	
	public void addYahooPlacesItems(YahooPlacesItem item) {
		if (items == null) {
			items = new ArrayList<YahooPlacesItem>();
		}
		items.add(item);
		populate();
	}
	
	@Override
	protected OverlayItem createItem(int position) {
		int stateBitset = OverlayItem.ITEM_STATE_FOCUSED_MASK | OverlayItem.ITEM_STATE_PRESSED_MASK | OverlayItem.ITEM_STATE_SELECTED_MASK;
		if (items != null) {
			YahooPlacesItem item = items.get(position);
			OverlayItem oi = new OverlayItem(
				new GeoPoint((int)(item.latitude * 1E6), (int)(item.longitude * 1E6)) ,
				item.name,
				item.state + " " + item.county + " " + item.city + " " + item.street
				);
			Drawable drawable = new TextDrawable(marker,item.name);
//			OverlayItem.setState(drawable, stateBitset);
			oi.setMarker(drawable);
			
			
			return oi;
		}
		return null;
	}

	@Override
	public int size() {
		if (items != null) {
			return items.size();
		}
		return 0;
	}

	@Override
	public boolean onTap(GeoPoint p, MapView mapView) {
		boolean isItemTap = super.onTap(p, mapView);
		if (isItemTap == false) {
			if (mListener != null) {
				mListener.onMapClick(p);
			}
		}
		return true;
	}

	@Override
	protected boolean onTap(int index) {
		if (index < items.size()) {
			Toast.makeText(mContext, items.get(index).name, Toast.LENGTH_LONG).show();
			if (mListener != null) {
				mListener.onMapItemClick(items.get(index));
			}
		}
		return true;
	}

}
