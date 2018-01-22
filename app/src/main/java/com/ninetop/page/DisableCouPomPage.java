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

import com.ninetop.adatper.CouponDisableAdapter;
import com.ninetop.bean.user.CouponBean;

import java.util.List;

import youbao.shopping.R;

public class DisableCouPomPage extends BasePager {

    private ListView listview;
    private List<CouponBean> diaenable;
    private RelativeLayout rl_nocoupon;

    public DisableCouPomPage(Context context, List<CouponBean> diaenable) {
        super(context);
        this.diaenable=diaenable;
    }

    @Override
    public View initView() {
        View inflate = View.inflate(context, R.layout.page_layout, null);
        rl_nocoupon = (RelativeLayout) inflate.findViewById(R.id.rl_nocoupon);
        listview = (ListView) inflate.findViewById(R.id.listvew);
        return inflate;
    }

    @Override
    public void initData() {
        if (diaenable==null||diaenable.size()<=0){
            rl_nocoupon.setVisibility(View.VISIBLE);
            listview.setVisibility(View.GONE);
        }else {
            rl_nocoupon.setVisibility(View.GONE);
            listview.setVisibility(View.VISIBLE);
            listview.setAdapter(new CouponDisableAdapter(diaenable,context));
        }
    }
}
