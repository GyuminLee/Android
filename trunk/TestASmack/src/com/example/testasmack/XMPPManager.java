package com.example.testasmack;

import java.util.ArrayList;
import java.util.Collection;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

import android.os.AsyncTask;
import android.os.Handler;

public class XMPPManager {

	XMPPConnection mXmppConnection;
	public static final String DOMAIN = "192.168.209.180";
	public static final int PORT = 5222;
	Handler mHandler;

	private static XMPPManager instance;

	public interface OnLoginResultListener {
		public void onSuccess(String userid);

		public void onFail(String userid);
	}

	public interface OnChatActivateListener {
		public void onChatActivated(Chat chat, Message message);
	}

	ArrayList<OnChatActivateListener> mActivateListener = new ArrayList<OnChatActivateListener>();

	public void addOnChatActivateListener(OnChatActivateListener listener) {
		mActivateListener.add(listener);
	}

	public void removeChatActivateListener(OnChatActivateListener listener) {
		mActivateListener.remove(listener);
	}

	public static XMPPManager getInstance() {
		if (instance == null) {
			instance = new XMPPManager();
		}
		return instance;
	}

	private XMPPManager() {
		mHandler = new Handler();
		ConnectionConfiguration config = new ConnectionConfiguration(DOMAIN,
				PORT);
		mXmppConnection = new XMPPConnection(config);
	}

	public void login(String userid, String password,
			OnLoginResultListener listener) {
		new LoginTask(userid, password, listener).execute("");
	}

	class LoginTask extends AsyncTask<String, Integer, Boolean> {
		String mUserId;
		String mPassword;
		OnLoginResultListener mListener;

		public LoginTask(String userid, String password,
				OnLoginResultListener listener) {
			super();
			mUserId = userid;
			mPassword = password;
			mListener = listener;
		}

		@Override
		protected Boolean doInBackground(String... params) {
			try {
				if (!mXmppConnection.isConnected()) {
					mXmppConnection.connect();
				}
				mXmppConnection.login(mUserId, mPassword);
				ChatManager chatManager = mXmppConnection.getChatManager();
				chatManager.addChatListener(new ChatManagerListener() {

					@Override
					public void chatCreated(Chat chat, boolean isLocal) {
						if (!isLocal) {
							chat.addMessageListener(new MessageListener() {

								@Override
								public void processMessage(Chat chat,
										Message message) {
									final Chat c = chat;
									final Message m = message;
									mHandler.post(new Runnable() {

										@Override
										public void run() {
											for (OnChatActivateListener listener : mActivateListener) {
												listener.onChatActivated(c, m);
											}

										}
									});

								}
							});
						}

					}
				});
				Presence p = new Presence(Presence.Type.available);
				p.setStatus("sleep...");
				Roster roster = mXmppConnection.getRoster();
				roster.addRosterListener(new RosterListener() {
					
					@Override
					public void presenceChanged(Presence presence) {
						//presence.getFrom();
						//presence.getStatus();
						//
					}
					
					@Override
					public void entriesUpdated(Collection<String> arg0) {
						
					}
					
					@Override
					public void entriesDeleted(Collection<String> arg0) {
						
					}
					
					@Override
					public void entriesAdded(Collection<String> arg0) {
												
					}
				});
				mXmppConnection.sendPacket(p);
				return true;
			} catch (XMPPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (result == true) {
				mListener.onSuccess(mUserId);
			} else {
				mListener.onFail(mUserId);
			}
		}
	}

	public interface OnResultListener {
		public void onSuccess(String userid);

		public void onFail(String userid);
	}

	public void addAccount(String userid, String password,
			OnResultListener listener) {
		new AccountAddTask(userid, password, listener).execute("");
	}

	class AccountAddTask extends AsyncTask<String, Integer, Boolean> {
		String mUserId;
		String mPassword;
		OnResultListener mListener;

		public AccountAddTask(String userid, String password,
				OnResultListener listener) {
			mUserId = userid;
			mPassword = password;
			mListener = listener;
		}

		@Override
		protected Boolean doInBackground(String... params) {
			try {
				if (!mXmppConnection.isConnected()) {
					mXmppConnection.connect();
				}
				AccountManager am = mXmppConnection.getAccountManager();
				if (am.supportsAccountCreation()) {
					am.createAccount(mUserId, mPassword);
					return true;
				}
			} catch (XMPPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			if (result == true) {
				mListener.onSuccess(mUserId);
			} else {
				mListener.onFail(mUserId);
			}
		}
	}
	
	
	public interface OnUserListListener {
		public void onUserList(ArrayList<User> list);
	}
	public void getRoster(OnUserListListener listener) {
		new GetRosterTask(listener).execute("");
	}
	
	class GetRosterTask extends AsyncTask<String, Integer, ArrayList<User>> {
		OnUserListListener mListener;
		public GetRosterTask(OnUserListListener listener) {
			mListener = listener;
		}
		
		@Override
		protected ArrayList<User> doInBackground(String... params) {
			Roster roster = mXmppConnection.getRoster();
			Collection<RosterEntry> entries = roster.getEntries();
			ArrayList<User> users = new ArrayList<User>();
			for (RosterEntry entry : entries) {
				User user = new User();
				user.userid = entry.getUser();
				user.name = entry.getName();
				Presence p = roster.getPresence(user.userid);
				user.status = p.getStatus();
				users.add(user);
			}
			return users;
		}
		
		@Override
		protected void onPostExecute(ArrayList<User> result) {
			mListener.onUserList(result);
		}	
	}
	
	public interface OnMessageReceiveListener {
		public void onMessageReceived(String userid, String message);
	}
	
	public Chat createChat(String userid, final OnMessageReceiveListener listener) {
		return mXmppConnection.getChatManager().createChat(userid, new MessageListener() {
			
			@Override
			public void processMessage(Chat chat, Message message) {
				final String user = message.getFrom();
				final String msg = message.getBody();
				mHandler.post(new Runnable() {
					
					@Override
					public void run() {
						listener.onMessageReceived(user, msg);
					}
				});
			}
		});
	}
	
	public interface OnMessageSendListener {
		public void onMessageSended(String message);
	}
	
	public void sendMessage(Chat chat, String message, OnMessageSendListener listener) {
		new MessageSendTask(chat, message, listener).execute("");
	}
	
	class MessageSendTask extends AsyncTask<String, Integer, Boolean> {
		Chat mChat;
		String mMessage;
		OnMessageSendListener mListener;
		
		public MessageSendTask(Chat chat, String message, OnMessageSendListener listener) {
			mChat = chat;
			mMessage = message;
			mListener = listener;
		}
		@Override
		protected Boolean doInBackground(String... params) {
			try {
				mChat.sendMessage(mMessage);
				return true;
			} catch (XMPPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			if (result == true) {
				mListener.onMessageSended(mMessage);
			}
		}
	}
	
}
