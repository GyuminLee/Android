package org.tacademy.network.rss.npr;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.tacademy.network.rss.DisplayItem;
import org.tacademy.network.rss.InAppBrowser;
import org.tacademy.network.rss.ParentActivity;
import org.tacademy.network.rss.R;
import org.tacademy.network.rss.Nework.DownloadThread;
import org.tacademy.network.rss.Nework.NetworkRequest;
import org.tacademy.network.rss.R.id;
import org.tacademy.network.rss.R.layout;
import org.tacademy.network.rss.database.DBScheme;
import org.tacademy.network.rss.database.DataProcessThread;
import org.tacademy.network.rss.database.DataRequest;
import org.tacademy.network.rss.util.DialogManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.Toast;

public class NPRNewsDetails extends ParentActivity {
	
	ArrayList<SingleNewsItem> newsList = new ArrayList<SingleNewsItem>();
	ListView myListView;
//	String urlAddress = "";
//	String urlCaption = "";
	SingleNewsItem selectedNewsItem;
	Context context;
	Handler mHandler = new Handler();
	RssItemAdapter aaNews;
	DisplayItem selectItem;
	
	public static final int MENU_ID_SAVE = Menu.FIRST + 1;
	public static final int MENU_ID_LOAD = Menu.FIRST + 2;
	public static final String MENU_TITLE_SAVE = "news save";
	public static final String MENU_TITLE_LOAD = "news load";
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	    
	    context = getApplicationContext();
	    
	    myListView = (ListView)findViewById(R.id.ListView01);
	    
	    Intent myLocalIntent = getIntent();
	    
//	    Bundle myBundle = myLocalIntent.getExtras();
//	    urlAddress = myBundle.getString("urlAddress");
//	    urlCaption = myBundle.getString("urlCaption");
	    
	    selectItem = myLocalIntent.getParcelableExtra("myData");
	    
	    this.setTitle("NPR - " + selectItem.caption);
	    
	    myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> _av, View _v, int _index,
					long _id) {
				// TODO Auto-generated method stub
				selectedNewsItem = newsList.get(_index);
				Toast.makeText(context, selectedNewsItem.toString(), Toast.LENGTH_LONG);				
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuItem item = menu.add(Menu.NONE, MENU_ID_SAVE, Menu.NONE, MENU_TITLE_SAVE);
		item = menu.add(Menu.NONE, MENU_ID_LOAD, Menu.NONE, MENU_TITLE_LOAD);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case MENU_ID_SAVE :
				//...
				insertDBFromItems(newsList);
				return true;
			case MENU_ID_LOAD :
				// ...
				// DB에서 데이터를 읽어서 보여주는 Activity를 호출
				Intent i = new Intent(this,NPRDBDetails.class);
				startActivity(i);
				return true;
		}
		
		return false;
	}

	NetworkRequest.OnDownloadCompletedListener rssListener = 
		new NetworkRequest.OnDownloadCompletedListener() {
		
			public void onDownloadCompleted(int result, NetworkRequest request) {
				dismissProgress();
				if (result == NetworkRequest.PROCESS_SUCCESS) {
					RssNews data = (RssNews)request.getResult();
					newsList = data.items;
					
					insertDBFromItems(data.items);
		
					aaNews = new RssItemAdapter(getApplicationContext(), data.items);
					
					
					// Thread로 변경할 경우 화면 Update는 ...
					myListView.setAdapter(aaNews);
					
					myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		
						public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
							// call item view
							SingleNewsItem item = aaNews.getData(position);
							Intent i = new Intent(getApplicationContext(),InAppBrowser.class);
							i.putExtra(InAppBrowser.FIELD_NAME_URL, item.link);
							startActivity(i);
							// 
						}
					});
				} else {
					Toast.makeText(NPRNewsDetails.this, "Data get fail", Toast.LENGTH_SHORT).show();
				}
				
			}
		};
	
	
	DataRequest.DataProcessedListener dataInsertRequest = new DataRequest.DataProcessedListener() {
		
		public void OnDataProcessed(int result, DataRequest request) {
			// Data Insert Competed ...
		}
	};
	
	public void insertDBFromItems(ArrayList<SingleNewsItem> items) {
//		DatabaseHelper helper = new DatabaseHelper(this);
//		SQLiteDatabase db = helper.getWritableDatabase();
		
		DataProcessThread th = DataProcessThread.getInstance();
		
		for(int i = 0; i < items.size(); i++) {
			RssInsertRequest request = new RssInsertRequest(items.get(i));
			request.setDataProcessedListener(dataInsertRequest);
			th.enqueue(request);
			//insertDBFromItem(db,items.get(i));
		}
		
//		db.close();
	}
	
	public void insertDBFromItem(SQLiteDatabase db, SingleNewsItem item) {
		ContentValues values = new ContentValues();

		values.put(DBScheme.ItemTable.TITLE, item.title);
		values.put(DBScheme.ItemTable.DESCRIPTION, item.description);
		values.put(DBScheme.ItemTable.LINK, item.link);
		values.put(DBScheme.ItemTable.PUBLISH_DATE, item.pubDate);
		values.put(DBScheme.ItemTable.CONTENT, item.content);
		values.put(DBScheme.ItemTable.CHANNEL_ID, 1);
		
		db.insert(DBScheme.ItemTable.TABLE_NAME, null, values);
	}
	
	NetworkRequest.OnDownloadCompletedListener naverSearchNewsListener =
		new NetworkRequest.OnDownloadCompletedListener() {
			
			public void onDownloadCompleted(int result, NetworkRequest request) {
				// Naver News 검색결과 처리...
				dismissProgress();
			}
		};
		
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		NetworkRequest request = selectItem.getRequest();
		// 해당 코드를 Thread를 사용하는 코드로 변경.
		switch (selectItem.type) {
			case DisplayItem.TYPE_NPR_NEWS :
				request.setOnDownloadCompletedListener(rssListener);
				break;
			case DisplayItem.TYPE_NAVER_SEARCH_NEWS :
				request.setOnDownloadCompletedListener(naverSearchNewsListener);
				break;
		}
		
//		RssRequest request = new RssRequest(selectItem.url,
//			new NetworkRequest.OnDownloadCompletedListener() {
//				
//				public void onDownloadCompleted(int result, NetworkRequest request) {
//					RssNews data = (RssNews)request.getResult();
//					newsList = data.items;
////					ArrayAdapter<SingleNewsItem> aaNews = new ArrayAdapter<SingleNewsItem>
////					(getApplicationContext(),android.R.layout.simple_list_item_1,newsList);
//
//					aaNews = new RssItemAdapter(getApplicationContext(), data.items);
//					// Thread로 변경할 경우 화면 Update는 ...
//					myListView.setAdapter(aaNews);
//					
//					myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//						public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//							// call item view
//							SingleNewsItem item = aaNews.getData(position);
//							// 
//						}
//					});
//				}
//			});
		DownloadThread th = new DownloadThread(mHandler, request);
		
//		DownloadThread th = new DownloadThread(mHandler, urlAddress);
//		
//		th.setDownloadCompleteListener(new DownloadThread.DownloadCompleteListener() {
//			
//			public void downloadCompleted(ArrayList<SingleNewsItem> items) {
//				newsList = items;
////				ArrayAdapter<SingleNewsItem> aaNews = new ArrayAdapter<SingleNewsItem>
////				(getApplicationContext(),android.R.layout.simple_list_item_1,newsList);
//
//				aaNews = new RssItemAdapter(getApplicationContext(), items);
//				// Thread로 변경할 경우 화면 Update는 ...
//				myListView.setAdapter(aaNews);
//				
//				myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//					public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//						// call item view
//						SingleNewsItem item = aaNews.getData(position);
//						// 
//					}
//				});
//				
//				
//			}
//		});
		
		th.start();
		showProgress();
//		try {
//			URL url = new URL(urlAddress);
//			URLConnection connection;
//			connection = url.openConnection();
//			
//			HttpURLConnection httpConnection = (HttpURLConnection)connection;
//			
//			// httpConnection.setRequestMethod("GET");
//			// httpConnection.setRequestProperty(key, value);
//			// httpConnection.setRequestProperty("user-agent", "xxx");
//			// httpConnection.setConnectTimeout(30000);
//			// OutputStream out = httpConnection.getOutputStream();
//			// httpConnection.connect();
//			
//			int responseCode = httpConnection.getResponseCode();
//			
//			if (responseCode == HttpURLConnection.HTTP_OK) {
//				InputStream in = httpConnection.getInputStream();
//				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//				DocumentBuilder db = dbf.newDocumentBuilder();
//				Document dom = db.parse(in);
//				Element docEle = dom.getDocumentElement();
//				NodeList nl = docEle.getElementsByTagName("item");
//				/*
//				NodeList channelNodeList = docEle.getElementsByTagName("channel");
//				if ((channelNodeList != null) && (channelNodeList.getLength() > 0)) {
//					for (int i = 0; i < channelNodeList.getLength(); i++) {
//						Element channel = (Element)channelNodeList.item(i);
//						NodeList childNodeList = channel.getChildNodes();
//						if ((childNodeList != null) && (childNodeList.getLength()>0)) {
//							Element child = (Element) childNodeList.item(i);
//							if (child.getTagName().compareTo("title") == 0 ) {
//								// ...
//							} else if (child.getTagName().compareTo("item") == 0 ) {
//								// ...
//							}
//						}
//					}
//				}
//				*/
//				
//				if ((nl != null) && (nl.getLength() > 0)) {
//					for (int i = 0; i < nl.getLength(); i++) {
//						dissectNode(nl,i);
//					}
//				}
//				
//				ArrayAdapter<SingleNewsItem> aaNews = new ArrayAdapter<SingleNewsItem>
//					(this,android.R.layout.simple_list_item_1,newsList);
//				
//				// Thread로 변경할 경우 화면 Update는 ...
//				myListView.setAdapter(aaNews);
//			}
//		} catch (Exception e) {
//			
//		}
		
	}
	
	// SAXParser를 이용하는 경우.
	class MyDefaultHandler extends DefaultHandler {
		
		StringBuilder content;
		ArrayList<SingleNewsItem> items = new ArrayList<SingleNewsItem>();
		SingleNewsItem item = null;
		boolean isItemElementOpen = false;
		boolean isChannelElementOpen = false;

		@Override
		public void startDocument() throws SAXException {
			super.startDocument();
		}

		@Override
		public void endDocument() throws SAXException {
			super.endDocument();
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			super.startElement(uri, localName, qName, attributes);
			// localName은 Tag명.
			// 
			content = null;
			
			if (localName.compareTo("channel") == 0) {
				isChannelElementOpen = true;
			}
			
			if (localName.compareTo("item") == 0) {
				isItemElementOpen = true;
				item = new SingleNewsItem();
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			super.endElement(uri, localName, qName);
			//
			// content...
			String str = content.toString();
			
			if (isItemElementOpen) {
				if (localName.compareTo("title") == 0) {
					// item.title = str;
				} else if (localName.compareTo("link") == 0) {
					
				} else if (localName.compareTo("description") == 0) {
					
				} else if (localName.compareTo("pubDate") == 0) {
					
				} else if (localName.compareTo("content:encoded") == 0) {
					
				} else {
				}
			} else if (isChannelElementOpen) {
				if (localName.compareTo("title") == 0) {
					
				}
			}
			
			if (localName.compareTo("item") == 0) {
				items.add(item);
				isItemElementOpen = false;
			}
			
			if (localName.compareTo("channel") == 0) {
				isChannelElementOpen = false;
			}		
		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			super.characters(ch, start, length);

			if (content == null) {
				content = new StringBuilder();
			}

			content.append(ch, start, length);
		}

		
	}
	
	public void saxParser(InputStream is) {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			XMLReader reader = parser.getXMLReader();
			MyDefaultHandler contentHandler = new MyDefaultHandler();
			reader.setContentHandler(contentHandler);

			InputSource src = new InputSource(is);
			reader.parse(src);
		} catch (ParserConfigurationException e) {
		} catch (SAXException e) {
		} catch (IOException e) {
		}

	}
	
	public void pullParser(InputStream is) {
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			parser.setInput(is, null);
			int parserEvent = parser.getEventType();
			
			while(parserEvent != XmlPullParser.END_DOCUMENT) {
				switch (parserEvent) {
					case XmlPullParser.START_DOCUMENT :
						break;
					case XmlPullParser.START_TAG :
						break;
					case XmlPullParser.END_TAG :
						break;
					case XmlPullParser.TEXT :
						break;
				}
				parserEvent = parser.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void dissectNode(NodeList nl, int i) {
		// TODO Auto-generated method stub
		try {
			Element entry = (Element)nl.item(i);
			Element title = (Element)entry.getElementsByTagName("title").item(0);
			Element description = (Element)entry.getElementsByTagName("description").item(0);
			Element pubDate = (Element)entry.getElementsByTagName("pubDate").item(0);
			Element link = (Element)entry.getElementsByTagName("link").item(0);
			Element content = (Element)entry.getElementsByTagName("content:encoded").item(0);
			
			String titleValue = title.getTextContent();//title.getFirstChild().getNodeValue();
			String descriptionValue = description.getTextContent(); //description.getFirstChild().getNodeValue();
			String pubDateValue = pubDate.getTextContent();//pubDate.getFirstChild().getNodeValue();
			String linkValue = link.getTextContent();//link.getFirstChild().getNodeValue();
			String contentValue = content.getTextContent();//content.getFirstChild().getNodeValue();
			
			SingleNewsItem singleItem = new SingleNewsItem(
					pubDateValue,
					titleValue,
					descriptionValue,
					linkValue,
					contentValue);
			
			newsList.add(singleItem);
			
		} catch (Exception e) {
			
		}
		
	}

}
