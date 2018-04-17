package youbao.shopping.ninetop.activity.ub.product;

import android.view.KeyEvent;

import youbao.shopping.ninetop.activity.ub.util.MyScrollView;
import youbao.shopping.ninetop.base.BaseActivity;

/**
 * Created by huangjinding on 2017/6/12.
 */

public abstract class HuangChangeActivity extends BaseActivity implements MyScrollView.onScrollChangedListener{
    protected long exitTime = 0L;
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(!intercept()){
            return super.onKeyDown(keyCode, event);
        }

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                showToast("再按一次退出");
                exitTime = System.currentTimeMillis();
            } else {
                System.exit(0);
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public boolean intercept(){
        return true;
    }
}
