package youbao.shopping.ninetop.common.util;

import android.content.Context;
import android.telephony.TelephonyManager;

import youbao.shopping.ninetop.base.MyApplication;

/**
 * Created by jill on 2016/11/22.
 */

public class AndroidTools {

    public static String getDeviceId(){
        TelephonyManager tm = (TelephonyManager) MyApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }
}
