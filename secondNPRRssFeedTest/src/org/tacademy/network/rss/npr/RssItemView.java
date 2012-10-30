package org.tacademy.network.rss.npr;

import org.tacademy.network.rss.R;
import org.tacademy.network.rss.R.id;
import org.tacademy.network.rss.R.layout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RssItemView extends LinearLayout {

	Context mContext;
	SingleNewsItem mItem;
	TextView title;
	TextView description;
	TextView link;
	TextView pubDate;
	
	public RssItemView(Context context) {
		super(context);
		mContext = context;
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.rss_layout, this);
		title = (TextView) layout.findViewById(R.id.title);
		description = (TextView) layout.findViewById(R.id.description);
		link = (TextView) layout.findViewById(R.id.link);
		link.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse(mItem.getLink()));
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mContext.startActivity(i);
			}
		});
		
		pubDate = (TextView) layout.findViewById(R.id.pubDate);
	}
	
	public void setData(SingleNewsItem item) {
		mItem = item;
		title.setText(mItem.getTitle());
		description.setText(mItem.getDescription());
		link.setText(mItem.getLink());
		pubDate.setText(mItem.getPubDate());
	}

}
