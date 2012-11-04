package com.magnitude.core;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import com.magnitude.app.R;

/**
 * A custom Android adapter class to show plugin list
 * @author Magnitude Client
 *
 */
public class PluginAdapter extends ArrayAdapter<Plugin> implements Filterable {

	private List<Plugin> items;
	private LayoutInflater vi;

	public PluginAdapter(Context context, int textViewResourceId,
			List<Plugin> objects, LayoutInflater vi) {
		super(context, textViewResourceId, objects);
		this.items = objects;
		this.vi = vi;
	}
	
	public void setLayoutInflater(LayoutInflater vi) {
		this.vi = vi;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			v = vi.inflate(R.layout.row, null);
		}
		Plugin p = items.get(position);
		if (p != null) {
			TextView name = (TextView) v.findViewById(R.id.name);
			TextView version = (TextView) v.findViewById(R.id.version);
			TextView status = (TextView) v.findViewById(R.id.status);

			if (name != null) {
				name.setText(p.getName());
			}
			if (version != null) {
				version.setText("v"+p.getVersion());
			}
			if (status != null) {
				if (p.getStatus().equals(Plugin.STATUS_INSTALLED)) {
					status.setTextColor(Color.GREEN);
				}
				else if (p.getStatus().equals(Plugin.STATUS_OUTDATED)) {
					status.setTextColor(Color.YELLOW);
				}
				else if (p.getStatus().equals(Plugin.STATUS_NOT_RELEASED)) {
					status.setTextColor(Color.DKGRAY);
				}
				else if (p.getStatus().equals(Plugin.STATUS_AVAILABLE)) {
					status.setTextColor(Color.WHITE);
				}
				status.setText(p.getStatus());
				}
			}
		
		return v;
	}
}
