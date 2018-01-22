package com.ninetop.activity.user;


import android.content.Context;

import com.ninetop.adatper.ReturnMoneyAdapter;
import com.ninetop.bean.user.ChangingOrRefundBean;

public interface IChangingOrRefunding {

    void update(Context ctx, ReturnMoneyAdapter.ViewHolder viewHolder, ChangingOrRefundBean.DataListBean dataListBean, int position, ReturnMoneyAdapter returnMoneyAdapter);

}
