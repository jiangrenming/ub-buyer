package com.ninetop.common.action;

import android.content.Context;
import android.content.Intent;

import com.ninetop.activity.index.WebViewActivity;
import com.ninetop.bean.index.BannerBean;
import com.ninetop.common.IntentExtraKeyConst;

/**
 * Created by jill on 2016/12/29.
 */

public class BannerWebViewAction implements BannerAction{

    @Override
    public void action(Context context, BannerBean content) {
        Intent intent  = new Intent(context, WebViewActivity.class);
        intent.putExtra(IntentExtraKeyConst.WEB_VIEW_TITLE, content.getTitle());
        intent.putExtra(IntentExtraKeyConst.WEB_VIEW_URL, content.getLink());
        context.startActivity(intent);
    }
}
