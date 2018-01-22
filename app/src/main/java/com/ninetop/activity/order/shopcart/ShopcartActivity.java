package com.ninetop.activity.order.shopcart;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ninetop.activity.TabBaseActivity;
import com.ninetop.activity.order.OrderConfirmActivity;
import com.ninetop.activity.product.ProductDetailActivity;
import com.ninetop.activity.ub.order.UbConfirmOrderActivity;
import com.ninetop.bean.order.ShopCartItemBean;
import com.ninetop.common.ActivityActionHelper;
import com.ninetop.common.AssembleHelper;
import com.ninetop.common.IntentExtraKeyConst;
import com.ninetop.common.constant.TextConstant;
import com.ninetop.common.util.BigDecimalUtil;
import com.ninetop.common.util.FormatUtil;
import com.ninetop.common.util.Tools;
import com.ninetop.common.view.NumericStepperView;
import com.ninetop.common.view.SwipeListView;
import com.ninetop.common.view.listener.DataChangeListener;
import com.ninetop.service.impl.ProductService;
import com.ninetop.service.listener.CommonResultListener;
import com.ninetop.service.listener.ResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import youbao.shopping.R;
import okhttp3.Response;

/**
 * Created by FlashMorpho on 2016/11/8.
 */

public  class ShopcartActivity extends TabBaseActivity {
    @BindView(R.id.iv_selectall)
    ImageView ivSelectall;
    @BindView(R.id.tv_selectall)
    TextView tvSelectall;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.ll_pay)
    LinearLayout llPay;
    @BindView(R.id.lv_cart_list)
    SwipeListView lvCartList;
    @BindView(R.id.iv_edit)
    ImageView ivEdit;
    @BindView(R.id.tv_go_buy)
    TextView tvGoBuy;
    @BindView(R.id.ll_no_data)
    LinearLayout llNoData;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    @BindView(R.id.iv_back)
    ImageView ivBack;

    boolean isSelectAll = false;
    ShopcartItemAdapter shopcartItemAdapter;
    private List<ShopCartItemBean> selectedList = new ArrayList<>();
    private List<ShopCartItemBean> dataList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shopcart;
    }

    private ProductService productService;

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    protected void initData() {
        super.initData();
        selectedList.clear();
        refreshPrice();
//        productService.getShopCartList(new CommonResultListener<List<ShopCartItemBean>>(this) {
//            @Override
//            public void successHandle(List<ShopCartItemBean> result) {
//                dataList = result;
//                dataChangeHandle();
//                if(dataList == null || dataList.size() == 0)
//                    return;
                shopcartItemAdapter = new ShopcartItemAdapter(ShopcartActivity.this, dataList);
                lvCartList.setRightViewWidth(Tools.dip2px(ShopcartActivity.this, 60));
                lvCartList.setAdapter(shopcartItemAdapter);
                shopcartItemAdapter.setSelectList(selectedList);
//            }
//        });
    }

    @Override
    protected void initView() {
        productService = new ProductService(this);

        ivBack.setVisibility(View.GONE);
        String showBack = getIntentValue(IntentExtraKeyConst.SHOW_BACK);
        if ("1".equals(showBack)) {
            ivBack.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.iv_selectall, R.id.tv_selectall, R.id.iv_edit, R.id.tv_pay, R.id.tv_go_buy, R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_selectall:
                selectedAll();
                break;
            case R.id.tv_selectall:
                selectedAll();
                break;
            case R.id.iv_edit:
                changeEditStatusHandle();
                break;
            case R.id.tv_go_buy:
                ActivityActionHelper.goToMain(this);
                break;
            case R.id.tv_pay:
          startActivity(UbConfirmOrderActivity.class);
                break;
        }
    }

    private void selectedAll() {
        selectedList.clear();
        if (!isSelectAll) {
            selectedList.addAll(dataList);
            ivSelectall.setImageResource(R.mipmap.edit_select);
            isSelectAll = true;
        } else {
            ivSelectall.setImageResource(R.mipmap.edit_unselect);
            isSelectAll = false;
        }
        shopcartItemAdapter.notifyDataSetInvalidated();
        refreshPrice();
    }

    private void changeEditStatusHandle() {
        boolean isEditStatus = !isEditStatus();
        setStatusText(isEditStatus);
        if (dataList == null || dataList.size() == 0)
            return;

        shopcartItemAdapter.setIsEditStatus(isEditStatus);
        shopcartItemAdapter.notifyDataSetInvalidated();
    }

    private boolean isEditStatus() {
        String text = "完成";
        return "完成".equals(text) ? true : false;
    }

    private void setStatusText(boolean editStatus) {
        if (editStatus) {

            tvPay.setText("删除");
        } else {

            tvPay.setText("结算");
        }
    }

    private void removeCartList() {
        if (selectedList == null || selectedList.size() == 0) {
            return;
        }

        String cartIds = "";
        for (ShopCartItemBean bean : selectedList) {
            cartIds += bean.id + ",";
        }
        cartIds = cartIds.substring(0, cartIds.length() - 1);

        productService.removeCartList(cartIds, new CommonResultListener<String>(this) {
            @Override
            public void successHandle(String result) throws JSONException {
                if (selectedList == null || selectedList.size() == 0)
                    return;

                for (int i = 0; i < dataList.size(); i++) {
                    ShopCartItemBean oneBean = dataList.get(i);
                    for (ShopCartItemBean bean : selectedList) {
                        if (oneBean == bean) {
                            dataList.remove(i);
                            i--;
                            break;
                        }
                    }
                }

                dataChangeHandle();
                shopcartItemAdapter.notifyDataSetChanged();
            }
        });
    }

    private void submitCart() {
        if (selectedList == null || selectedList.size() == 0) {
            return;
        }

        String skuIds = "";
        for (ShopCartItemBean bean : selectedList) {
            skuIds += bean.skuId + ",";
        }
        skuIds = skuIds.substring(0, skuIds.length() - 1);

        productService.submitCart(skuIds, new CommonResultListener<JSONObject>(this) {
            @Override
            public void successHandle(JSONObject result) throws JSONException {
                String dataString = result.getString("data");

                Intent intent = new Intent(ShopcartActivity.this, OrderConfirmActivity.class);
                intent.putExtra(IntentExtraKeyConst.JSON_DATA, dataString);
                intent.putExtra(IntentExtraKeyConst.ORDER_FROM, "cart");
                startActivity(intent);
            }
        });
    }

    private void refreshPrice() {
        double totalPrice = 0;
        for (ShopCartItemBean bean : selectedList) {
            totalPrice = BigDecimalUtil.add(totalPrice, bean.price * bean.amount);
        }

        tvPrice.setText(TextConstant.MONEY + FormatUtil.format(totalPrice));
    }

    private void dataChangeHandle() {
        if (dataList == null || dataList.size() == 0) {
            llNoData.setVisibility(View.VISIBLE);
            ivEdit.setVisibility(View.GONE);
            llPay.setVisibility(View.GONE);
            lvCartList.setVisibility(View.GONE);
        } else {
            llNoData.setVisibility(View.GONE);
            ivEdit.setVisibility(View.VISIBLE);
            llPay.setVisibility(View.VISIBLE);
            lvCartList.setVisibility(View.VISIBLE);
        }

        changeShopcartCount();

        selectedChangeHandle();
    }

    private void changeShopcartCount() {
        int cartNum = 0;
        if (dataList != null && dataList.size() > 0) {
            for (ShopCartItemBean cartItem : dataList) {
                cartNum += cartItem.amount;
            }
        }

        ActivityActionHelper.changeMainCartNum(this, cartNum);
    }

    private void selectedChangeHandle() {
        if (dataList != null && dataList.size() == selectedList.size()) {
            ivSelectall.setImageResource(R.mipmap.edit_select);
            isSelectAll = true;
        } else {
            ivSelectall.setImageResource(R.mipmap.edit_unselect);
            isSelectAll = false;
        }

        refreshPrice();
    }

    @Override
    public boolean intercept() {
        return ivBack.getVisibility() == View.GONE;
    }

    class ShopcartItemAdapter extends BaseAdapter {

        List<ShopCartItemBean> dataList;
        ShopcartActivity activity;
        List<ShopCartItemBean> selectList;
        boolean isEditStatus = false;


        public ShopcartItemAdapter(ShopcartActivity activity, List<ShopCartItemBean> dataList) {
            this.dataList = dataList;
            this.activity = activity;
        }

        public void setData(List<ShopCartItemBean> dataList) {
            this.dataList.clear();
            this.dataList.addAll(dataList);
            notifyDataSetChanged();
        }

        public void setSelectList(List<ShopCartItemBean> selectList) {
            this.selectList = selectList;
        }

        public void setIsEditStatus(boolean isEditStatus) {
            this.isEditStatus = isEditStatus;
        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final HolderView holderView;
            final ShopCartItemBean itemBean = dataList.get(position);
            if (convertView == null) {
                holderView = new HolderView();
                convertView = LayoutInflater.from(activity).inflate(R.layout.item_shopcart_edit, null);
                holderView.iv_select = (ImageView) convertView.findViewById(R.id.iv_select);
                holderView.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
                holderView.nsv_number = (NumericStepperView) convertView.findViewById(R.id.nsv_number);
                holderView.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                holderView.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
                holderView.tv_prop = (TextView) convertView.findViewById(R.id.tv_prop);
                holderView.iv_product_image = (ImageView) convertView.findViewById(R.id.iv_product_image);
                holderView.item_right = convertView.findViewById(R.id.item_right);
                convertView.setTag(holderView);

                holderView.iv_product_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, String> map = new HashMap<>();
                        map.put(IntentExtraKeyConst.PRODUCT_CODE, itemBean.goodsCode);

                        activity.startActivity(ProductDetailActivity.class, map);
                    }
                });
            } else {
                holderView = (HolderView) convertView.getTag();
            }

            holderView.tv_name.setText(itemBean.goodsName);
            holderView.tv_price.setText(FormatUtil.formatMoney(itemBean.price));
            holderView.tv_prop.setText(AssembleHelper.assembleSku2(itemBean.params));
            holderView.tv_number.setText(TextConstant.MULTIPLY + itemBean.amount);
            holderView.nsv_number.setValue(itemBean.amount);
            Tools.ImageLoaderShow(activity, itemBean.icon2, holderView.iv_product_image);

            holderView.iv_select.setImageResource(R.mipmap.edit_unselect);
            holderView.iv_select.setVisibility(View.VISIBLE);
            if (selectList.size() > 0) {
                if (selectList.contains(dataList.get(position))) {
                    holderView.iv_select.setImageResource(R.mipmap.edit_select);
                }
            }

            holderView.nsv_number.setDataChangeListener(new DataChangeListener<Integer>() {
                public void handle(final Integer num) {
                    final int value = itemBean.amount;
                    itemBean.amount = num;

                    productService.editCartNumber(itemBean, new ResultListener<String>() {
                        @Override
                        public void successHandle(String result) {
                            refreshPrice();
                            changeShopcartCount();
                        }

                        @Override
                        public void failHandle(String code, String result) {
                            itemBean.amount = value;
                            holderView.nsv_number.setValue(value);
                            showToast(result);
                        }

                        public void errorHandle(Response response, Exception e) {
                            itemBean.amount = value;
                            holderView.nsv_number.setValue(value);
                            showToast("服务器出现异常");
                        }
                    });
                }
            });
               //在他最萌芽最出生的的地方-------适配器来一个单个删除item
            holderView.item_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    productService.removeCart(itemBean, new CommonResultListener<String>(ShopcartActivity.this) {
                        @Override
                        public void successHandle(String result) throws JSONException {
                            dataList.remove(position);
                            if (selectedList != null) {
                                for (ShopCartItemBean bean : selectedList) {
                                    if (bean == itemBean) {
                                        selectedList.remove(bean);
                                        break;
                                    }
                                }
                            }

                            notifyDataSetChanged();
                            dataChangeHandle();

                            lvCartList.hideAll();
                        }
                    });

                }
            });

            // 是否选中
            holderView.iv_select.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //所有购物车列表里面是否含有已选中的item
                    boolean contains = selectList.contains(itemBean);
                    if (!contains) {
                        //强势加入，并涂上颜色
                        selectList.add(itemBean);
                        holderView.iv_select.setImageResource(R.mipmap.edit_select);
                    } else {
                        //强势踢走，不吐颜色
                        selectList.remove(itemBean);
                        holderView.iv_select.setImageResource(R.mipmap.edit_unselect);
                    }

                    selectedChangeHandle();
                }
            });
               //是“完成”时就是可输入状态
            if (isEditStatus) {
                holderView.tv_number.setVisibility(View.GONE);
                holderView.nsv_number.setVisibility(View.VISIBLE);
            } else {
                holderView.tv_number.setVisibility(View.VISIBLE);
                holderView.nsv_number.setVisibility(View.GONE);
            }
            return convertView;
        }

        class HolderView {
            ImageView iv_select;
            ImageView iv_product_image;
            TextView tv_number;
            TextView tv_price;
            TextView tv_name;
            TextView tv_prop;
            NumericStepperView nsv_number;
            View item_right;

        }
    }


}
