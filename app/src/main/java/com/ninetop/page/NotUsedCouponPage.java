package com.ninetop.page;
/*
                   _ooOoo_
                  o8888888o
                  88" . "88
                  (| -_- |)
                  O\  =  /O
               ____/`---'\____
             .'  \\|     |//  `.
            /  \\|||  :  |||//  \
           /  _||||| -:- |||||-  \
           |   | \\\  -  /// |   |
           | \_|  ''\---/''  |   |
           \  .-\__  `-`  ___/-. /
         ___`. .'  /--.--\  `. . __
      ."" '<  `.___\_<|>_/___.'  >'"".
     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
     \  \ `-.   \_ __\ /__ _/   .-` /  /
======`-.____`-.___\_____/___.-`____.-'======
                   `=---='
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
         佛祖保佑       永无BUG
 * Created by Administrator on 2016/11/12.
 */


import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ninetop.adatper.CouponUsedAdapter;
import com.ninetop.bean.user.CouponBean;

import java.util.List;

import youbao.shopping.R;

public class NotUsedCouponPage extends BasePager {

    private ListView listvew;
    private List<CouponBean> enableData;
    private RelativeLayout rl_nocoupon;

    public NotUsedCouponPage(Context context, List<CouponBean> enableData) {
        super(context);
        this.enableData=enableData;
    }

    @Override
    public View initView() {
        View inflate = View.inflate(context, R.layout.page_layout, null);
        rl_nocoupon = (RelativeLayout) inflate.findViewById(R.id.rl_nocoupon);
        listvew = (ListView) inflate.findViewById(R.id.listvew);

        return inflate;
    }

    @Override
    public void initData() {
        if (enableData==null||enableData.size()<=0){
            rl_nocoupon.setVisibility(View.VISIBLE);
            listvew.setVisibility(View.GONE);
        }else {
            rl_nocoupon.setVisibility(View.GONE);
            listvew.setVisibility(View.VISIBLE);
            listvew.setAdapter(new CouponUsedAdapter(enableData,context));
        }

    }
}
