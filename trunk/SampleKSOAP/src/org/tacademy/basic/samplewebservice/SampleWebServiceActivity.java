package org.tacademy.basic.samplewebservice;

import java.io.IOException;
import java.util.HashMap;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.tacademy.basic.samplewebservice.network.DownloadThread;
import org.tacademy.basic.samplewebservice.network.NetworkRequest;
import org.tacademy.basic.samplewebservice.network.SoapRequest;
import org.tacademy.basic.samplewebservice.network.SoapRequestObject;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SampleWebServiceActivity extends Activity {
    /** Called when the activity is first created. */
	private final static String SOAP_ACTION = "http://www.webserviceX.NET/ChangeLengthUnit";
	private final static String METHOD_NAME = "ChangeLengthUnit";
	private final static String NAMESPACE = "http://www.webserviceX.NET/";
	private final static String URL = "http://www.webservicex.net/length.asmx?WSDL";
	private final static String PARAM_LENGTH_VALUE = "LengthValue";
	private final static String PARAM_FROM_UNIT = "fromLengthUnit";
	private final static String PARAM_TO_UNIT = "toLengthUnit";
	private final static String PARAM_RESULT = "ChangeLengthUnitResult";
	
	Handler mHandler = new Handler();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SoapObject request = new SoapObject(NAMESPACE,METHOD_NAME);
//				ChangeLengthUnit unit = new ChangeLengthUnit((double)10.0, "Miles", "Meters");
//				request.addProperty(PARAM_LENGTH_VALUE, "10.0");
//				request.addProperty(PARAM_FROM_UNIT, "Miles");
//				request.addProperty(PARAM_TO_UNIT,"Meters");
				
//				PropertyInfo changeUnit = new PropertyInfo();
//				changeUnit.setName("ChangeLengthUnit");
//				changeUnit.setValue(unit);
//				changeUnit.setType(unit.getClass());
//				request.addProperty(changeUnit);

				PropertyInfo lengthValue = new PropertyInfo();
				lengthValue.setName(PARAM_LENGTH_VALUE);
				lengthValue.setValue("10.0");
				lengthValue.setNamespace(NAMESPACE);
				lengthValue.setType(double.class);
				request.addProperty(lengthValue);
				
				PropertyInfo fromUnit = new PropertyInfo();
				fromUnit.setName(PARAM_FROM_UNIT);
				fromUnit.setValue("Miles");
				fromUnit.setNamespace(NAMESPACE);
				fromUnit.setType(String.class);
				request.addProperty(fromUnit);
				
				PropertyInfo toUnit = new PropertyInfo();
				toUnit.setName(PARAM_TO_UNIT);
				toUnit.setValue("Meters");
				toUnit.setNamespace(NAMESPACE);
				toUnit.setType(String.class);
				request.addProperty(toUnit);
				
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				HttpTransportSE ht = new HttpTransportSE(URL);
				
				try {
					ht.call(SOAP_ACTION, envelope);
					SoapObject response = (SoapObject)envelope.bodyIn;
					SoapPrimitive value = (SoapPrimitive)response.getProperty(PARAM_RESULT);
					double d = Double.parseDouble(value.toString());
					Toast.makeText(SampleWebServiceActivity.this, "double : " + d, Toast.LENGTH_SHORT).show();
					
					// response Ã³¸®....
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (XmlPullParserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
        
        btn = (Button)findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SoapRequestObject map = new SoapRequestObject();
				map.add(PARAM_LENGTH_VALUE, "10.0");
				map.add(PARAM_FROM_UNIT, "Miles");
				map.add(PARAM_TO_UNIT, "Meters");
				SoapRequest request = new SoapRequest(URL, NAMESPACE, METHOD_NAME, SOAP_ACTION, map);
				request.setOnDownloadCompletedListener(new NetworkRequest.OnDownloadCompletedListener() {
					
					@Override
					public void onDownloadCompleted(int result, NetworkRequest request) {
						// TODO Auto-generated method stub
						if (result == NetworkRequest.PROCESS_SUCCESS) {
							HashMap<String,Object> data = (HashMap<String,Object>)request.getResult();
							String value = (String)data.get(PARAM_RESULT);
							Toast.makeText(SampleWebServiceActivity.this, "value : " + value, Toast.LENGTH_SHORT).show();
						}
					}
				});
				DownloadThread th = new DownloadThread(mHandler, request);
				th.start();
			}
		});
    }
}