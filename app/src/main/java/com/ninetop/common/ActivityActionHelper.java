package com.ninetop.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.ninetop.activity.MainActivity;
import com.ninetop.base.BaseActivity;
import com.ninetop.base.GlobalInfo;

/**
 * Created by jill on 2016/12/23.
 */

public class ActivityActionHelper {

    public static void goToMain(Context context){
        goToMain(context, 1);

        GlobalInfo.needGoLogin = false;
    }

    public static void goToMain(Context context, int selectIndex){
        if(context instanceof BaseActivity){
            Activity activity = ((BaseActivity)context).getParent();
            if(activity != null && activity instanceof MainActivity){
                ((MainActivity)activity).setSelectedIndex(selectIndex);
                return;
            }
        }

        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(IntentExtraKeyConst.MAIN_SELECTED_INDEX, selectIndex + "");
        context.startActivity(intent);
    }

    public static void changeMainCartNum(Context context, int count){
        GlobalInfo.shopCartCount = count;

        if(context instanceof BaseActivity){
            Activity activity = ((BaseActivity)context).getParent();
            if(activity != null && activity instanceof MainActivity){
                ((MainActivity)activity).setShopCartCount(count);
                return;
            }
        }
    }
}
