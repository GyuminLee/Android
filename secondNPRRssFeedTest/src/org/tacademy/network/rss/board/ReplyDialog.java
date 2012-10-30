package org.tacademy.network.rss.board;

import org.tacademy.network.rss.R;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ReplyDialog extends Dialog {

	TextView contentView;
	ReplyItemData mReply;
	
	public interface OnReplyDialogButtonClickListener {
		public void onOkButtonClick(ReplyItemData data);
		public void onCancelButtonClick(ReplyItemData data);
	}
	OnReplyDialogButtonClickListener mListener;
	
	public void setOnReplyDialogButtonClickListener(OnReplyDialogButtonClickListener listener) {
		mListener = listener;
	}
	
	public ReplyDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setContentView(R.layout.replay_dialog);
		contentView = (TextView)findViewById(R.id.replayText);
		Button btn = (Button)findViewById(R.id.ok);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if (mListener != null) {
					if (mReply == null) {
						mReply = new ReplyItemData();
					}
					mReply.content = contentView.getText().toString();

					mListener.onOkButtonClick(mReply);
				}
				dismiss();
			}
		});
		
		btn = (Button)findViewById(R.id.cancel);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mListener != null) {
					mListener.onCancelButtonClick(mReply);
				}
				dismiss();
			}
		});
		
	}
	
	public void setData(ReplyItemData data) {
		mReply = data;
		if (data != null) {
			contentView.setText(data.content);
		} else {
			contentView.setText("");
		}
	}

}
