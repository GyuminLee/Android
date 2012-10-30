package org.tacademy.network.rss.c2dm;

import org.tacademy.network.rss.R;
import org.tacademy.network.rss.Nework.NetworkRequest;
import org.tacademy.network.rss.util.ImageManager;
import org.tacademy.network.rss.util.ImageRequest;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UserItemView extends LinearLayout {

	ImageView userImageView;
	TextView userNameView;
	TextView userEmailView;
	User mUser;
	ImageRequest userImageRequest;
	String sizeParam = "&width=50&height=50";
	
	public UserItemView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.user_item_view, this);
		userImageView = (ImageView)findViewById(R.id.userImage);
		userNameView = (TextView)findViewById(R.id.userName);
		userEmailView = (TextView)findViewById(R.id.userEmail);
	}
	
	public void setData(User data) {
		mUser = data;
		userNameView.setText(data.name);
		userEmailView.setText(data.email);
		userImageView.setImageResource(R.drawable.icon);
		if (userImageRequest != null) {
			ImageManager.getInstance().remove(userImageRequest);
			userImageRequest = null;
		}
		if (data.imageUrl != null && !data.imageUrl.equals("")) {
			userImageRequest = new ImageRequest(data.imageUrl+sizeParam);
			userImageRequest.setOnDownloadCompletedListener(new NetworkRequest.OnDownloadCompletedListener() {
				
				public void onDownloadCompleted(int result, NetworkRequest request) {
					if (result == NetworkRequest.PROCESS_SUCCESS) {
						if (userImageRequest == request) {
							Bitmap bmp = userImageRequest.getBitmap();
							userImageView.setImageBitmap(bmp);
							userImageRequest = null;
						}
					} else {
						// Error...
					}
				}
			});
			
			ImageManager.getInstance().enqueue(userImageRequest);
		}
		
	}

}
