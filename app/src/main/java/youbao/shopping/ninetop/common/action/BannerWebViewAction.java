package youbao.shopping.ninetop.common.action;

import android.content.Context;
import android.content.Intent;

import youbao.shopping.ninetop.activity.index.WebViewActivity;
import youbao.shopping.ninetop.bean.index.BannerBean;
import youbao.shopping.ninetop.common.IntentExtraKeyConst;

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
