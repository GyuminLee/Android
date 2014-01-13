package com.example.sample2navermovie;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import android.graphics.Bitmap;

public class ImageRequest extends NetworkRequest<Bitmap> {
	String imageUrl;

	ArrayList<ImageRequest> sameRequestList = new ArrayList<ImageRequest>();

	public ImageRequest(String url) {
		imageUrl = url;
	}

	public boolean isSameRequest(ImageRequest request) {
		if (imageUrl.equals(request.imageUrl)) {
			if (request != this) {
				sameRequestList.add(request);
			}
			return true;
		}
		return false;
	}

	public String getKey() {
		String key = null;
		try {
			key = URLEncoder.encode(imageUrl, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return key;
	}

	@Override
	public URL getURL() throws MalformedURLException,
			UnsupportedEncodingException {
		return new URL(imageUrl);
	}

	@Override
	public void process(InputStream is) {
		result = CacheManager.getInstance().saveImage(getKey(), is);
	}

	public void setResult(Bitmap bitmap) {
		result = bitmap;
	}

	boolean bFinalCancel = false;
	
	@Override
	public void cancel() {
		if (sameRequestList.size() > 0) {
			bFinalCancel = true;
		} else {
			super.cancel();
			NetworkModel.getInstance().remove(this);
		}
	}

	@Override
	public void sendSuccess() {
		if (bFinalCancel) {
			super.cancel();
		}
		super.sendSuccess();
		for (ImageRequest req : sameRequestList) {
			req.setResult(result);
			NetworkModel.getInstance().removeRequestMap(req);
			req.sendSuccess();
		}
	}

	@Override
	public void sendError(int error) {
		if (bFinalCancel) {
			super.cancel();
		}
		super.sendError(error);
		for (ImageRequest req : sameRequestList) {
			NetworkModel.getInstance().removeRequestMap(req);
			req.sendError(error);
		}
	}

}
