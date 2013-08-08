package NET.webserviceX.www;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public final class LengthUnitSoap {
    public  double changeLengthUnit(double lengthValue, NET.webserviceX.www.Lengths fromLengthUnit, NET.webserviceX.www.Lengths toLengthUnit) throws Exception {
        SoapObject _client = new SoapObject("", "changeLengthUnit");
        _client.addProperty("lengthValue", lengthValue + "");
        _client.addProperty("fromLengthUnit", fromLengthUnit);
        _client.addProperty("toLengthUnit", toLengthUnit);
        SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        _envelope.bodyOut = _client;
        HttpTransportSE _ht = new HttpTransportSE(Configuration.getWsUrl());
        _ht.call("", _envelope);
        return Double.parseDouble(_envelope.getResponse().toString());
    }


}
