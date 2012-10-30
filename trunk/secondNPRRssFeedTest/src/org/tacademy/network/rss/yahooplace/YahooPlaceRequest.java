package org.tacademy.network.rss.yahooplace;

import java.net.HttpURLConnection;
import java.net.URLEncoder;

import org.tacademy.network.rss.Nework.NetworkRequest;


public class YahooPlaceRequest extends NetworkRequest {

	public YahooPlaceRequest(String keyward) {
		String encodedKeyward = URLEncoder.encode(keyward);
		urlString = "http://kr.open.gugi.yahoo.com/service/poi.php?appid=.Mx4R_TV34HNXSTfdXs1EfQB1mB5XimV_j1_3pY64jUPhj2IPAd3jqk4TDcCs.Q-" + 
					"&q="+encodedKeyward+"&encoding=euc-kr&output=xml&results=50";
		this.parser = new YahooPlaceParser();
	}
}
