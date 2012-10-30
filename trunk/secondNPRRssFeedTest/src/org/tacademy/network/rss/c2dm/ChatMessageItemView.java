package org.tacademy.network.rss.c2dm;

import org.tacademy.network.rss.R;
import org.tacademy.network.rss.util.PropertyManager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChatMessageItemView extends LinearLayout {

	TextView leftView;
	TextView rightView;
	TextView messageView;
	ChatMessage mMessage;
	public ChatMessageItemView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.chat_message_item, this);
		leftView = (TextView)findViewById(R.id.left);
		rightView = (TextView)findViewById(R.id.right);
		messageView = (TextView)findViewById(R.id.message);
	}
	
	public void setData(ChatMessage message) {
		mMessage = message;
		messageView.setText(message.message);
		if (message.fromid == PropertyManager.getInstance().getUserId()) {
			leftView.setVisibility(View.VISIBLE);
			rightView.setVisibility(View.GONE);
		} else {
			leftView.setVisibility(View.GONE);
			rightView.setVisibility(View.VISIBLE);
		}
	}

}
