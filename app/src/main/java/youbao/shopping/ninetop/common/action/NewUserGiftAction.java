package youbao.shopping.ninetop.common.action;

import android.content.Context;
import android.content.Intent;

import youbao.shopping.ninetop.activity.index.NewUserActivity;
import youbao.shopping.ninetop.bean.index.BannerBean;

/**
 * Created by jill on 2016/12/29.
 */

public class NewUserGiftAction implements BannerAction{

    @Override
    public void action(Context context, BannerBean content) {
        Intent intent  = new Intent(context, NewUserActivity.class);
        context.startActivity(intent);
    }
}
