package youbao.shopping.ninetop.activity.user;


import android.content.Context;

import youbao.shopping.ninetop.adatper.ReturnMoneyAdapter;
import youbao.shopping.ninetop.bean.user.ChangingOrRefundBean;

public interface IChangingOrRefunding {

    void update(Context ctx, ReturnMoneyAdapter.ViewHolder viewHolder, ChangingOrRefundBean.DataListBean dataListBean, int position, ReturnMoneyAdapter returnMoneyAdapter);

}
