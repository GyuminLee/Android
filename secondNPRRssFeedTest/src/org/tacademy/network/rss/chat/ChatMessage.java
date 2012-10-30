package org.tacademy.network.rss.chat;

import org.jivesoftware.smack.packet.Message;

public class ChatMessage {
	public static final int MESSAGE_TYPE_SEND = 0;
	public static final int MESSAGE_TYPE_RECEIVE = 1;
	public static final int MESSAGE_SUBTYPE_NOT_SEND = 0;
	public static final int MESSAGE_SUBTYPE_SENT = 1;
	public static final int MESSAGE_SUBTYPE_CONFIRM = 2;
	public static final int MESSAGE_SUBTYPE_NOT_READ = 0;
	public static final int MESSAGE_SUBTYPE_READ = 1;
	public int type;
	public int subtype;
	Message message;
	
	public ChatMessage(){
		type = MESSAGE_TYPE_SEND;
	}
	
	public ChatMessage(int type) {
		this.type = type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public void setSubType(int subtype) {
		this.subtype = subtype;
	}
	
	public void setMessage(Message message) {
		this.message = message;
	}
}
