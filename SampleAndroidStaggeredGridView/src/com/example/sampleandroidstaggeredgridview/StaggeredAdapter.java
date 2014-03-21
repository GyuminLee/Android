package com.example.sampleandroidstaggeredgridview;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.begentgroup.imageloader.ImageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;


public class StaggeredAdapter extends ArrayAdapter<String> {


	
	public StaggeredAdapter(Context context, int textViewResourceId,
			String[] objects) {
		super(context, textViewResourceId, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;

		if (convertView == null) {
			LayoutInflater layoutInflator = LayoutInflater.from(getContext());
			convertView = layoutInflator.inflate(R.layout.row_staggered_demo,
					null);
			holder = new ViewHolder();
			holder.imageView = (ImageView) convertView .findViewById(R.id.imageView1);
			convertView.setTag(holder);
		}

		holder = (ViewHolder) convertView.getTag();

		ImageLoader.getInstance().displayImage(getItem(position), holder.imageView);
		return convertView;
	}

	static class ViewHolder {
		ImageView imageView;
	}
		
}

