package youbao.shopping.ninetop.common;

import android.content.Context;

import youbao.shopping.ninetop.base.GlobalInfo;
import youbao.shopping.ninetop.common.constant.SharedKeyConstant;
import youbao.shopping.ninetop.common.util.MySharedPreference;

/**
 * Created by jill on 2016/11/25.
 */

public class LoginAction {

    public static void login(String token, Context context){
        MySharedPreference.save(SharedKeyConstant.TOKEN, token, context);
        GlobalInfo.userToken = token;
    }
    public static void logout(Context context){
        MySharedPreference.remove(SharedKeyConstant.TOKEN, context);
        GlobalInfo.userToken = "";
        GlobalInfo.shopCartCount = 0;
    }
    public static void saveCity(String city,Context context){
        MySharedPreference.save(SharedKeyConstant.SAVE_CITY,city,context);
        GlobalInfo.saveCity=city;
    }
    public static void saveMobile(String mobile,Context context){
        MySharedPreference.save(SharedKeyConstant.SAVE_MOBILE,mobile,context);
        GlobalInfo.saveMobile=mobile;
    }
    public static void saveGetStyle(String index,Context context){
        MySharedPreference.save(SharedKeyConstant.SAVE_GET_STYLE,index,context);
        GlobalInfo.saveGetStyle=index;
    }

    public static void saveUb(String ub,Context context){
        MySharedPreference.save(SharedKeyConstant.SAVE_UB,ub,context);
        GlobalInfo.saveUB=ub;
    }
    public static void saveUbPay(String ubPay,Context context){
        MySharedPreference.save(SharedKeyConstant.SAVE_PAY_SELLER,ubPay,context);
        GlobalInfo.savePay=ubPay;
    }

    public static void saveFranchiseeId(String franchiseeId,Context context){
        MySharedPreference.save(SharedKeyConstant.SAVE_FRANCHISEE_ID,franchiseeId,context);
        GlobalInfo.franchiseeId=franchiseeId;
    }
    public static void saveLON(String longitude,Context context){
        MySharedPreference.save(SharedKeyConstant.SEARCH_LON,longitude,context);
        GlobalInfo.cityLocation=longitude;
    }
    public static void saveLAT(String latitude,Context context){
        MySharedPreference.save(SharedKeyConstant.SEARCH_LAT,latitude,context);
        GlobalInfo.cityLocation=latitude;
    }
}
