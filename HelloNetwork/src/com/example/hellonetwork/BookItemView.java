package com.example.hellonetwork;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class BookItemView extends FrameLayout {

	URLImageView bookImage;
	TextView title;
	TextView isbn;
	NaverBookItem item;
	
	public BookItemView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.book_item_layout, this);
		bookImage = (URLImageView)findViewById(R.id.bookimage);
		title = (TextView)findViewById(R.id.booktitle);
		isbn = (TextView)findViewById(R.id.bookisbn);
	}
	
	public void setData(NaverBookItem data) {
		item = data;
		title.setText(data.title);
		isbn.setText(data.isbn);
		bookImage.setImageURL(data.image);
	}

}
