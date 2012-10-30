package org.tacademy.basic.sampleservicemanager;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.telephony.NeighboringCellInfo;

public class Telephony {
	
	public static Telephony instance;
	public static Telephony getInstance() {
		if (instance == null) {
			instance = new Telephony();
		}
		return instance;
	}
	Object mITelephony;
	public static final String mStubName = "com.android.internal.telephony.ITelephony$Stub";
	private Telephony() {
		IBinder binder = CustomServiceManager.getService(Context.TELEPHONY_SERVICE);
		try {
			Method asInterface = Class.forName(mStubName).getMethod("asInterface", IBinder.class);
			mITelephony = asInterface.invoke(null, binder);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void dial(String number) throws RemoteException  {
		try {
			Method dial = mITelephony.getClass().getMethod("dial", String.class);
			dial.invoke(mITelephony, number);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RemoteException();
		}
	}
	
	public void call(String number) throws RemoteException  {
		try {
			Method call = mITelephony.getClass().getMethod("call", String.class);
			call.invoke(mITelephony, number);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RemoteException();
		}
	}
	
    public boolean showCallScreen() throws RemoteException {
		try {
			Method showCallScreen = mITelephony.getClass().getMethod("showCallScreen", null);
			return (Boolean)showCallScreen.invoke(mITelephony, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	throw new RemoteException();
    }
    
    public boolean showCallScreenWithDialpad(boolean showDialpad) throws RemoteException {
		try {
			Method showCallScreenWithDialpad = mITelephony.getClass().getMethod("showCallScreenWithDialpad", Boolean.class);
			return (Boolean)showCallScreenWithDialpad.invoke(mITelephony, (Boolean)showDialpad);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	throw new RemoteException();
    }
    
    public boolean endCall() throws RemoteException {
		try {
			Method endCall = mITelephony.getClass().getMethod("endCall", null);
			return (Boolean)endCall.invoke(mITelephony, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	throw new RemoteException();
    }
    
    public void answerRingingCall() throws RemoteException  {
		try {
			Method answerRingingCall = mITelephony.getClass().getMethod("answerRingingCall", null);
			answerRingingCall.invoke(mITelephony, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	    	throw new RemoteException();
		}
    }
    
    public void silenceRinger() throws RemoteException  {
		try {
			Method silenceRinger = mITelephony.getClass().getMethod("silenceRinger", null);
			silenceRinger.invoke(mITelephony, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	    	throw new RemoteException();
		}
    }
	
    public boolean isOffhook() throws RemoteException  {
		try {
			Method isOffhook = mITelephony.getClass().getMethod("isOffhook", null);
			return (Boolean)isOffhook.invoke(mITelephony, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	throw new RemoteException();
    }
    
    public boolean isRinging() throws RemoteException  {
		try {
			Method isRinging = mITelephony.getClass().getMethod("isRinging", null);
			return (Boolean)isRinging.invoke(mITelephony, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	throw new RemoteException();
    }
    
    public boolean isIdle() throws RemoteException  {
		try {
			Method isIdle = mITelephony.getClass().getMethod("isIdle", null);
			return (Boolean)isIdle.invoke(mITelephony, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	throw new RemoteException();
    }
    
    public boolean isRadioOn() throws RemoteException  {
		try {
			Method isRadioOn = mITelephony.getClass().getMethod("isRadioOn", null);
			return (Boolean)isRadioOn.invoke(mITelephony, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	throw new RemoteException();
    }

    public boolean isSimPinEnabled() throws RemoteException  {
		try {
			Method isSimPinEnabled = mITelephony.getClass().getMethod("isSimPinEnabled", null);
			return (Boolean)isSimPinEnabled.invoke(mITelephony, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	throw new RemoteException();
    }
    
    public void cancelMissedCallsNotification() throws RemoteException  {
		try {
			Method cancelMissedCallsNotification = mITelephony.getClass().getMethod("cancelMissedCallsNotification", null);
			cancelMissedCallsNotification.invoke(mITelephony, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	    	throw new RemoteException();
		}
    	
    }
    
    public boolean supplyPin(String pin) throws RemoteException  {
		try {
			Method supplyPin = mITelephony.getClass().getMethod("supplyPin", String.class);
			return (Boolean)supplyPin.invoke(mITelephony, pin);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	throw new RemoteException();
    }
    
    public boolean handlePinMmi(String dialString) throws RemoteException  {
		try {
			Method handlePinMmi = mITelephony.getClass().getMethod("handlePinMmi", String.class);
			return (Boolean)handlePinMmi.invoke(mITelephony, dialString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	throw new RemoteException();
    }
    
    public void toggleRadioOnOff() throws RemoteException  {
		try {
			Method toggleRadioOnOff = mITelephony.getClass().getMethod("toggleRadioOnOff", null);
			toggleRadioOnOff.invoke(mITelephony, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	    	throw new RemoteException();
		}
    	
    }
    
    public boolean setRadio(boolean turnOn) throws RemoteException  {
		try {
			Method setRadio = mITelephony.getClass().getMethod("setRadio", Boolean.class);
			return (Boolean)setRadio.invoke(mITelephony, (Boolean)turnOn);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	throw new RemoteException();
    }
    
    public void updateServiceLocation() throws RemoteException  {
		try {
			Method updateServiceLocation = mITelephony.getClass().getMethod("updateServiceLocation", null);
			updateServiceLocation.invoke(mITelephony, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	    	throw new RemoteException();
		}
    	
    }
    
    public void enableLocationUpdates() throws RemoteException  {
		try {
			Method enableLocationUpdates = mITelephony.getClass().getMethod("enableLocationUpdates", null);
			enableLocationUpdates.invoke(mITelephony, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	    	throw new RemoteException();
		}
    	
    }
    
    public void disableLocationUpdates() throws RemoteException  {
		try {
			Method disableLocationUpdates = mITelephony.getClass().getMethod("disableLocationUpdates", null);
			disableLocationUpdates.invoke(mITelephony, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	    	throw new RemoteException();
		}
    	
    }
    
    public int enableApnType(String type) throws RemoteException  {
		try {
			Method enableApnType = mITelephony.getClass().getMethod("enableApnType", String.class);
			return (Integer)enableApnType.invoke(mITelephony, type);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	throw new RemoteException();
    }
    
    public int disableApnType(String type) throws RemoteException  {
		try {
			Method disableApnType = mITelephony.getClass().getMethod("disableApnType", String.class);
			return (Integer)disableApnType.invoke(mITelephony, type);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	throw new RemoteException();
    }
    
    public boolean enableDataConnectivity() throws RemoteException  {
		try {
			Method enableDataConnectivity = mITelephony.getClass().getMethod("enableDataConnectivity", null);
			return (Boolean)enableDataConnectivity.invoke(mITelephony, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	throw new RemoteException();
    }
    
    public boolean disableDataConnectivity() throws RemoteException  {
		try {
			Method disableDataConnectivity = mITelephony.getClass().getMethod("disableDataConnectivity", null);
			return (Boolean)disableDataConnectivity.invoke(mITelephony, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	throw new RemoteException();
    }
    
    public boolean isDataConnectivityPossible() throws RemoteException  {
		try {
			Method isDataConnectivityPossible = mITelephony.getClass().getMethod("isDataConnectivityPossible", null);
			return (Boolean)isDataConnectivityPossible.invoke(mITelephony, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	throw new RemoteException();
    }
    
    public Bundle getCellLocation() throws RemoteException  {
		try {
			Method getCellLocation = mITelephony.getClass().getMethod("getCellLocation", null);
			return (Bundle)getCellLocation.invoke(mITelephony, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	throw new RemoteException();
    }
    
    public List<NeighboringCellInfo> getNeighboringCellInfo() throws RemoteException  {
		try {
			Method getNeighboringCellInfo = mITelephony.getClass().getMethod("getNeighboringCellInfo", null);
			return (List<NeighboringCellInfo>)getNeighboringCellInfo.invoke(mITelephony, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	throw new RemoteException();
    }
    
    public int getCallState() throws RemoteException  {
		try {
			Method getCallState = mITelephony.getClass().getMethod("getCallState", null);
			return (Integer)getCallState.invoke(mITelephony, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	throw new RemoteException();
    }
    
    public int getDataActivity() throws RemoteException  {
		try {
			Method getDataActivity = mITelephony.getClass().getMethod("getDataActivity", null);
			return (Integer)getDataActivity.invoke(mITelephony, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	throw new RemoteException();
    }
    
    public int getDataState() throws RemoteException  {
		try {
			Method getDataState = mITelephony.getClass().getMethod("getDataState", null);
			return (Integer)getDataState.invoke(mITelephony, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	throw new RemoteException();
    }
    
    public int getActivePhoneType() throws RemoteException  {
		try {
			Method getActivePhoneType = mITelephony.getClass().getMethod("getActivePhoneType", null);
			return (Integer)getActivePhoneType.invoke(mITelephony, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	throw new RemoteException();
    }
    
    public int getCdmaEriIconIndex() throws RemoteException  {
		try {
			Method getCdmaEriIconIndex = mITelephony.getClass().getMethod("getCdmaEriIconIndex", null);
			return (Integer)getCdmaEriIconIndex.invoke(mITelephony, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	throw new RemoteException();
    }
    
    public int getCdmaEriIconMode() throws RemoteException  {
		try {
			Method getCdmaEriIconMode = mITelephony.getClass().getMethod("getCdmaEriIconMode", null);
			return (Integer)getCdmaEriIconMode.invoke(mITelephony, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	throw new RemoteException();
    }
    
    public String getCdmaEriText() throws RemoteException  {
		try {
			Method getCdmaEriText = mITelephony.getClass().getMethod("getCdmaEriText", null);
			return (String)getCdmaEriText.invoke(mITelephony, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	throw new RemoteException();
    }
    
    public boolean getCdmaNeedsProvisioning() throws RemoteException  {
		try {
			Method getCdmaNeedsProvisioning = mITelephony.getClass().getMethod("getCdmaNeedsProvisioning", null);
			return (Boolean)getCdmaNeedsProvisioning.invoke(mITelephony, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	throw new RemoteException();
    }
    
    public int getVoiceMessageCount() throws RemoteException  {
		try {
			Method getVoiceMessageCount = mITelephony.getClass().getMethod("getVoiceMessageCount", null);
			return (Integer)getVoiceMessageCount.invoke(mITelephony, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	throw new RemoteException();
    }
    
    public int getNetworkType() throws RemoteException  {
		try {
			Method getNetworkType = mITelephony.getClass().getMethod("getNetworkType", null);
			return (Integer)getNetworkType.invoke(mITelephony, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	throw new RemoteException();
    }
    
    public boolean hasIccCard() throws RemoteException  {
		try {
			Method hasIccCard = mITelephony.getClass().getMethod("hasIccCard", null);
			return (Boolean)hasIccCard.invoke(mITelephony, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	throw new RemoteException();
    }
    
}
