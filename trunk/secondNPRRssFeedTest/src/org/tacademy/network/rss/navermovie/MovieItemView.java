package org.tacademy.network.rss.navermovie;

import org.tacademy.network.rss.R;
import org.tacademy.network.rss.Nework.NetworkRequest;
import org.tacademy.network.rss.R.drawable;
import org.tacademy.network.rss.R.id;
import org.tacademy.network.rss.R.layout;
import org.tacademy.network.rss.util.ImageManager;
import org.tacademy.network.rss.util.ImageRequest;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MovieItemView extends LinearLayout {

	ImageView image;
	TextView title;
	TextView subtitle;
	TextView director;
	TextView actor;
	
	NaverMovieItem mItem;
	Context mContext;
	ImageRequest imageRequest;
	
	public MovieItemView(Context context) {
		super(context);
		mContext = context;
		
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.movie_item_view, this);
		image = (ImageView)v.findViewById(R.id.image);
		title = (TextView)v.findViewById(R.id.title);
		subtitle = (TextView)v.findViewById(R.id.subtitle);
		director = (TextView)v.findViewById(R.id.director);
		actor = (TextView)v.findViewById(R.id.actor);
	}
	
	public void setData(NaverMovieItem item) {
		mItem = item;
		title.setText(item.title);
		subtitle.setText(item.subtitle);
		director.setText(item.director);
		actor.setText(item.actor);

		image.setImageResource(R.drawable.icon);
		if (imageRequest != null) {
			ImageManager.getInstance().remove(imageRequest);
			imageRequest.setCancel();
		}
		imageRequest = new ImageRequest(item.image);
		imageRequest.setOnDownloadCompletedListener(new NetworkRequest.OnDownloadCompletedListener() {
			
			public void onDownloadCompleted(int result, NetworkRequest request) {
				ImageRequest ir = (ImageRequest)request;
				if (imageRequest == ir) {
					Bitmap bm = ir.getBitmap();
					if (bm != null) {
						mItem.imageBitmap = bm;
						image.setImageBitmap(bm);
					}
					imageRequest = null;
				}
			}
		});
		ImageManager im = ImageManager.getInstance();
		im.enqueue(imageRequest);
	}
}
