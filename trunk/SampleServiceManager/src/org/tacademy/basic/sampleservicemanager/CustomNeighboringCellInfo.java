package org.tacademy.basic.sampleservicemanager;

import java.lang.reflect.Method;

import android.os.RemoteException;

public class CustomNeighboringCellInfo {

    /**
     * Signal strength is not available
     */
    static final public int UNKNOWN_RSSI = 99;
    /**
     * Cell location is not available
     */
    static final public int UNKNOWN_CID = -1;
	
	
	Object mCellInfo;
	public CustomNeighboringCellInfo(Object object) {
		// TODO Auto-generated constructor stub
		mCellInfo = object;
	}
	
    /**
     * @return received signal strength or UNKNOWN_RSSI if unknown
     *
     * For GSM, it is in "asu" ranging from 0 to 31 (dBm = -113 + 2*asu)
     * 0 means "-113 dBm or less" and 31 means "-51 dBm or greater"
     * For UMTS, it is the Level index of CPICH RSCP defined in TS 25.125
     */
    public int getRssi() {
		try {
			Method getRssi = mCellInfo.getClass().getMethod("getRssi", null);
			return (Integer)getRssi.invoke(mCellInfo, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return 0;
    }

    /**
     * @return LAC in GSM, 0xffff max legal value
     *  UNKNOWN_CID if in UMTS or CMDA or unknown
     */
    public int getLac() {
		try {
			Method getLac = mCellInfo.getClass().getMethod("getLac", null);
			return (Integer)getLac.invoke(mCellInfo, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return 0;
    }

    /**
     * @return cell id in GSM, 0xffff max legal value
     *  UNKNOWN_CID if in UMTS or CDMA or unknown
     */
    public int getCid() {
		try {
			Method getCid = mCellInfo.getClass().getMethod("getCid", null);
			return (Integer)getCid.invoke(mCellInfo, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return 0;
    }

    /**
     * @return Primary Scrambling Code in 9 bits format in UMTS, 0x1ff max value
     *  UNKNOWN_CID if in GSM or CMDA or unknown
     */
    public int getPsc() {
		try {
			Method getPsc = mCellInfo.getClass().getMethod("getPsc", null);
			return (Integer)getPsc.invoke(mCellInfo, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return 0;
    }

    /**
     * @return Radio network type while neighboring cell location is stored.
     *
     * Return {@link TelephonyManager#NETWORK_TYPE_UNKNOWN TelephonyManager.NETWORK_TYPE_UNKNOWN}
     * means that the location information is unavailable.
     *
     * Return {@link TelephonyManager#NETWORK_TYPE_GPRS TelephonyManager.NETWORK_TYPE_GPRS} or
     * {@link TelephonyManager#NETWORK_TYPE_EDGE TelephonyManager.NETWORK_TYPE_EDGE}
     * means that Neighboring Cell information is stored for GSM network, in
     * which {@link NeighboringCellInfo#getLac NeighboringCellInfo.getLac} and
     * {@link NeighboringCellInfo#getCid NeighboringCellInfo.getCid} should be
     * called to access location.
     *
     * Return {@link TelephonyManager#NETWORK_TYPE_UMTS TelephonyManager.NETWORK_TYPE_UMTS},
     * {@link TelephonyManager#NETWORK_TYPE_HSDPA TelephonyManager.NETWORK_TYPE_HSDPA},
     * {@link TelephonyManager#NETWORK_TYPE_HSUPA TelephonyManager.NETWORK_TYPE_HSUPA},
     * or {@link TelephonyManager#NETWORK_TYPE_HSPA TelephonyManager.NETWORK_TYPE_HSPA}
     * means that Neighboring Cell information is stored for UMTS network, in
     * which {@link NeighboringCellInfo#getPsc NeighboringCellInfo.getPsc}
     * should be called to access location.
     */
    public int getNetworkType() {
		try {
			Method getNetworkType = mCellInfo.getClass().getMethod("getNetworkType", null);
			return (Integer)getNetworkType.invoke(mCellInfo, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return 0;
    }

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return mCellInfo.toString();
	}
	

}
