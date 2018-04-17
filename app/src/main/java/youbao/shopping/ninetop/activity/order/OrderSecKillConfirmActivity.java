package youbao.shopping.ninetop.activity.order;

import android.view.View;

import youbao.shopping.ninetop.bean.order.OrderItemBean;
import youbao.shopping.ninetop.common.IntentExtraKeyConst;

/**
 * Created by jill on 2017/1/6.
 */

public class OrderSecKillConfirmActivity extends OrderConfirmActivity{

    private String productSaleID;

    @Override
    protected void initView() {
        super.initView();

        rlSelectCoupon.setVisibility(View.GONE);

        productSaleID = getIntentValue(IntentExtraKeyConst.PRODUCT_SECKILL_ID);

    }

    protected void buyNowSubmit(){
        if(productSaleID == null || productSaleID.length() == 0)
            return;

        OrderItemBean bean = preOrderBean.itemList.get(0);
        String amount = bean.amount;

        productService.buyNowSaleOrderSubmit(voucherId, productSaleID, amount, addressId, new OrderConfirmResultListener(this));
    }
}
