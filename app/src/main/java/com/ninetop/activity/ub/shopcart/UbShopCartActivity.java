package com.ninetop.activity.ub.shopcart;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ninetop.UB.cartshop.ChildListView;
import com.ninetop.UB.cartshop.UbShopCartBean;
import com.ninetop.UB.product.New.ShopCartItemListBean;
import com.ninetop.UB.product.UbProductService;
import com.ninetop.activity.TabBaseActivity;
import com.ninetop.activity.ub.order.UbConfirmOrderActivity;
import com.ninetop.activity.ub.product.UbProductInfoActivity;
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
import okhttp3.Response;
import youbao.shopping.R;

import static android.R.attr.value;
import static com.ninetop.config.AppConfig.BASE_IMAGE_URL;


/**
 * Created by huangjinding on 2017/6/14.
 */

public class UbShopCartActivity extends TabBaseActivity {
    @BindView(R.id.iv_selectall)
    ImageView ivSelectAll;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.ll_pay)
    LinearLayout llPay;
    @BindView(R.id.lv_cart_list)
    SwipeListView lvCartList;
    @BindView(R.id.iv_edit)
    ImageView ivEdit;
    @BindView(R.id.ll_totol_price)
    LinearLayout llTotalPrice;
    @BindView(R.id.ll_no_data)
    LinearLayout llNoData;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    private int franchiseeId;
    private ShopCartItemAdapter shopcartItemAdapter;
    private ShopCartMainAdapter shopcartMainAdapter;
    private UbProductService ubProductService;
    private List<UbShopCartBean> mainList = new ArrayList<>();
    private List<UbShopCartBean> selectedMainList = new ArrayList<>();
    private List<ShopCartItemListBean> selectedList = new ArrayList<>();
    private List<ShopCartItemListBean> dataList = new ArrayList<>();
    boolean isSelectAll = false;
    boolean isEdit = false;
    private ShopCartItemListBean mBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shopcart;
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    protected void initData() {
        super.initData();
        selectedList.clear();
        refreshPrice();
        ubProductService.getShopList("", new CommonResultListener<List<UbShopCartBean>>(this) {
            @Override
            public void successHandle(List<UbShopCartBean> result) throws JSONException {
                for (int i = 0; i < result.size(); i++) {
                    UbShopCartBean bean = result.get(i);
                    dataList = bean.shopCartItemList;
                    franchiseeId = bean.franchiseeId;
                    mainList = result;
                }
                dataChangeHandle();
                if (dataList == null || dataList.size() == 0) {
                    //        ivEdit.setVisibility(View.GONE);
                    return;
                }

                shopcartMainAdapter = new ShopCartMainAdapter(UbShopCartActivity.this, mainList);
                shopcartItemAdapter = new ShopCartItemAdapter(UbShopCartActivity.this, dataList);
//                lvCartList.setRightViewWidth(Tools.dip2px(UbShopCartActivity.this, 60));
                lvCartList.setRightViewWidth(Tools.dip2px(UbShopCartActivity.this, 0));
                lvCartList.setAdapter(shopcartMainAdapter);
                shopcartMainAdapter.setSelectList(selectedMainList);

            }
        });
    }

    @Override
    protected void initView() {
        ubProductService = new UbProductService(this);
        ivBack.setVisibility(View.GONE);
        String showBack = getIntentValue(IntentExtraKeyConst.SHOW_BACK);
        if ("1".equals(showBack)) {
            ivBack.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.iv_selectall, R.id.tv_selectall, R.id.iv_edit, R.id.tv_pay, R.id.tv_go_buy, R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_selectall:
                selectedAll();
                break;
            case R.id.tv_selectall:
                selectedAll();
                break;
            case R.id.iv_edit:
                setStatus();
                break;
            case R.id.tv_go_buy:
                ActivityActionHelper.goToMain(this);
                break;
            case R.id.tv_pay:
                List<Map> productList = new ArrayList<>();
                List<Integer> deleteList = new ArrayList<>();
                if (selectedList == null || selectedList.size() == 0) {
                    showToast("请选择商品");
                    return;
                }
                for (ShopCartItemListBean bean : selectedList) {
                    Map<String, Integer> map = new HashMap<>();
                    int shopCartId = bean.shopCartId;
                    int amount = bean.amount;
                    int skuId = bean.skuId;

                    map.put("franchiseeId", 1);
                    map.put("shopcartId", shopCartId);
                    map.put("amount", amount);
                    map.put("skuId", skuId);
                    productList.add(map);
                    //增加删除id
                    deleteList.add(bean.shopCartId);

                }
                if ("删除".equals(tvPay.getText().toString())) {
                    removeCartList(deleteList);
                } else {
                    submitCart(productList);
                }
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    protected void addSelectedItem(ShopCartItemListBean bean) {
        if (selectedList == null) {
            selectedList = new ArrayList<>();
        }

        if (!selectedList.contains(bean)) {
            selectedList.add(bean);
        }
    }

    protected void removeSelectedItem(ShopCartItemListBean bean) {
        if (selectedList == null) {
            selectedList = new ArrayList<>();
        }

        if (selectedList.contains(bean)) {
            selectedList.remove(bean);
        }
    }

    private void selectedAll() {
        selectedList.clear();
        if (!isSelectAll) {
            //赋值
            selectedList.addAll(dataList);
            ivSelectAll.setImageResource(R.mipmap.edit_select);
            isSelectAll = true;
        } else {
            ivSelectAll.setImageResource(R.mipmap.edit_unselect);
            isSelectAll = false;
        }
        if (shopcartMainAdapter != null) {
            shopcartMainAdapter.notifyDataSetInvalidated();
        }
        refreshPrice();
    }

    private void setStatus() {
        if (dataList == null || dataList.size() == 0)
            return;

        if (!isEdit) {
            ivEdit.setImageResource(R.mipmap.shopcar_delete_grey);
            //暂时结算。删除批量有问题
            tvPay.setText("结算");
            llTotalPrice.setVisibility(View.GONE);
            shopcartMainAdapter.setIsEditStatus(true);
            isEdit = true;
        } else {
            ivEdit.setImageResource(R.mipmap.shopcar_delete_grey_copy);
            tvPay.setText("结算");
            llTotalPrice.setVisibility(View.VISIBLE);
            shopcartMainAdapter.setIsEditStatus(false);
            isEdit = false;
        }
        shopcartMainAdapter.notifyDataSetChanged();
    }

    private void dataChangeHandle() {
        if (dataList == null || dataList.size() == 0) {
            llNoData.setVisibility(View.VISIBLE);
            llPay.setVisibility(View.GONE);
            lvCartList.setVisibility(View.GONE);
        } else {
            llNoData.setVisibility(View.GONE);
            llPay.setVisibility(View.VISIBLE);
            lvCartList.setVisibility(View.VISIBLE);
        }
        changeShopcartCount();
        selectedChangeHandle();
    }

    private void refreshPrice() {
        double totalPrice = 0;
        for (ShopCartItemListBean bean : selectedList) {
            totalPrice = BigDecimalUtil.add(totalPrice, bean.productPrice * bean.amount);
        }
        tvPrice.setText(FormatUtil.format(totalPrice));
    }

//    protected void addItemSelected() {
//
//    }

    @Override
    public boolean intercept() {
        return ivBack.getVisibility() == View.GONE;
    }

    private void changeShopcartCount() {
        int cartNum = 0;
        if (dataList != null && dataList.size() > 0) {
            for (ShopCartItemListBean cartItem : dataList) {
                cartNum += cartItem.amount;
            }
        }
        ActivityActionHelper.changeMainCartNum(this, cartNum);
    }

    private void selectedChangeHandle() {
        if (dataList != null && dataList.size() == selectedList.size()) {
            ivSelectAll.setImageResource(R.mipmap.edit_select);
            isSelectAll = true;
        } else {
            ivSelectAll.setImageResource(R.mipmap.edit_unselect);
            isSelectAll = false;
        }
        if (shopcartMainAdapter != null) {
            shopcartMainAdapter.notifyDataSetInvalidated();
        }
        refreshPrice();
    }

    //购物车结算
    private void submitCart(final List<Map> productList) {
        ubProductService.postEMSOrder(1, 0, 0, "", productList, new CommonResultListener<JSONObject>(this) {
            @Override
            public void successHandle(JSONObject result) throws JSONException {
                String dataString = result.getString("data");
                Intent intent = new Intent(UbShopCartActivity.this, UbConfirmOrderActivity.class);
                Gson gson = new Gson();
                String cartListJsonString = gson.toJson(productList);
                intent.putExtra(IntentExtraKeyConst.JSON_DATA, dataString);
                intent.putExtra(IntentExtraKeyConst.PRODUCT_LIST, cartListJsonString);
                intent.putExtra(IntentExtraKeyConst.SHOPCART_LIST, cartListJsonString);
                intent.putExtra(IntentExtraKeyConst.ORDER_FROM, "cart");
                startActivity(intent);
            }
        });
    }

    //购物车批量删除
    private void removeCartList(final List<Integer> deleteList) {
        ubProductService.postShopcartDelete(deleteList, new CommonResultListener<String>(UbShopCartActivity.this) {
            @Override
            public void successHandle(String result) throws JSONException {


                //
            }

        });

    }

    private class ShopCartMainAdapter extends BaseAdapter {
        List<UbShopCartBean> dataList;
        UbShopCartActivity activity;
        List<UbShopCartBean> selectList;
        boolean isEditStatus = false;

        ShopCartMainAdapter(UbShopCartActivity activity, List<UbShopCartBean> dataList) {
            this.dataList = dataList;
            this.activity = activity;
        }

        public void setData(List<UbShopCartBean> dataList) {
            this.dataList.clear();
            this.dataList.addAll(dataList);
            notifyDataSetChanged();
        }

        void setIsEditStatus(boolean isEditStatus) {
            this.isEditStatus = isEditStatus;
        }

        void setSelectList(List<UbShopCartBean> selectList) {
            this.selectList = selectList;
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
            final HolderMain holderMain;
            final UbShopCartBean mainBean = dataList.get(position);
            if (convertView == null) {
                holderMain = new HolderMain();
                convertView = LayoutInflater.from(activity).inflate(R.layout.ub_shopcart, parent, false);
                holderMain.tv_sellerName = (TextView) convertView.findViewById(R.id.tv_franchiseeName);
                holderMain.iv_edit = (ImageView) convertView.findViewById(R.id.iv_edit);
                holderMain.iv_select = (ImageView) convertView.findViewById(R.id.iv_select);
                holderMain.listView = (ChildListView) convertView.findViewById(R.id.listview);
                convertView.setTag(holderMain);
            } else {
                holderMain = (HolderMain) convertView.getTag();
            }
            holderMain.tv_sellerName.setText(mainBean.franchiseeName);
            holderMain.iv_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isEditStatus) {
                        ivEdit.setImageResource(R.mipmap.shopcar_delete_grey_copy);
                        shopcartItemAdapter.setIsEditStatus(true);
                    } else {
                        shopcartItemAdapter.setIsEditStatus(false);
                        ivEdit.setImageResource(R.mipmap.shopcar_delete_grey);
                    }
                }
            });
            final ShopCartItemAdapter adapter = new ShopCartItemAdapter(activity, mainBean.shopCartItemList);
            adapter.setIsEditStatus(!isEditStatus);
            adapter.setSelectList(selectedList);
            holderMain.listView.setAdapter(adapter);
            if (isSelectedList(mainBean.shopCartItemList)) {
                holderMain.iv_select.setImageResource(R.mipmap.edit_select);
            } else {
                holderMain.iv_select.setImageResource(R.mipmap.edit_unselect);
            }

            holderMain.iv_select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView imageView = (ImageView) v;
                    if (isSelected((ImageView) v)) {
                        for (ShopCartItemListBean bean : mainBean.shopCartItemList) {
                            addSelectedItem(bean);
                        }
                        imageView.setImageResource(R.mipmap.edit_select);
                    } else {
                        for (ShopCartItemListBean bean : mainBean.shopCartItemList) {
                            removeSelectedItem(bean);
                        }
                        imageView.setImageResource(R.mipmap.edit_unselect);
                    }
                    selectedChangeHandle();
                    adapter.notifyDataSetInvalidated();
                }
            });
            return convertView;
        }

        private boolean isSelected(ImageView imageView) {
            if (imageView.getDrawable().getCurrent().getConstantState().
                    equals(getResources().getDrawable(R.mipmap.edit_unselect).getConstantState())) {
                return true;
            }
            return false;
        }

        private boolean isSelectedList(List<ShopCartItemListBean> beanList) {
            for (ShopCartItemListBean bean : beanList) {
                if (!selectedList.contains(bean)) {
                    return false;
                }
            }
            return true;
        }

        class HolderMain {
            TextView tv_sellerName;
            ImageView iv_edit;
            ChildListView listView;
            ImageView iv_select;
        }
    }


    private class ShopCartItemAdapter extends BaseAdapter {

        List<ShopCartItemListBean> dataList;
        UbShopCartActivity activity;
        List<ShopCartItemListBean> selectList;
        boolean isEditStatus = false;


        ShopCartItemAdapter(UbShopCartActivity activity, List<ShopCartItemListBean> dataList) {
            this.dataList = dataList;
            this.activity = activity;

        }

        public void setData(List<ShopCartItemListBean> dataList) {
            this.dataList.clear();
            this.dataList.addAll(dataList);
            notifyDataSetChanged();
        }

        void setSelectList(List<ShopCartItemListBean> selectedList) {
            this.selectList = selectedList;
        }

        void setIsEditStatus(boolean isEditStatus) {
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
            final HolderView holdeView;
            final ShopCartItemListBean itemBean = dataList.get(position);

            if (convertView == null) {
                holdeView = new HolderView();
                convertView = LayoutInflater.from(activity).inflate(R.layout.item_shopcart, parent, false);
                holdeView.iv_select = (ImageView) convertView.findViewById(R.id.iv_select);

                //  holdeView.nsv_number = (NumericStepperView) convertView.findViewById(R.id.nsv_number);
                holdeView.nsv_number_edit = (NumericStepperView) convertView.findViewById(R.id.nsv_number_edit);
                holdeView.tv_num = (TextView) convertView.findViewById(R.id.tv_number);
                holdeView.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                holdeView.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
                holdeView.tv_prop = (TextView) convertView.findViewById(R.id.tv_prop);
                holdeView.iv_product_image = (ImageView) convertView.findViewById(R.id.iv_product_image);
                holdeView.delete = (TextView) convertView.findViewById(R.id.tv_delete);

                holdeView.rl_shopcart_edit = (RelativeLayout) convertView.findViewById(R.id.rl_shopcart_edit);
                holdeView.rl_shopcart_first = (RelativeLayout) convertView.findViewById(R.id.rl_shopcart_first);

                convertView.setTag(holdeView);
                holdeView.iv_product_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, String> map = new HashMap<>();
                        map.put(IntentExtraKeyConst.PRODUCT_ID, itemBean.productId + "");
                        activity.startActivity(UbProductInfoActivity.class, map);
                    }
                });
            } else {
                holdeView = (HolderView) convertView.getTag();
            }
            holdeView.tv_name.setText(itemBean.productName);
            holdeView.tv_price.setText(itemBean.productPrice + "");

//                    for(SingleSkuBean bean:itemBean.attrList){
//                        String nameProp=bean.attrName+":"+bean.attrValue;
//
//                        holdeView.tv_prop.setText(nameProp);
//                    }
            holdeView.tv_prop.setText(AssembleHelper.assembleSkuUb(itemBean.attrList));
            holdeView.tv_num.setText(TextConstant.MULTIPLY + itemBean.amount);
            //    holdeView.nsv_number.setValue(itemBean.amount);
            holdeView.nsv_number_edit.setValue(itemBean.amount);
            Tools.ImageLoaderShow(activity, BASE_IMAGE_URL + itemBean.icon, holdeView.iv_product_image);
            holdeView.iv_select.setImageResource(R.mipmap.edit_unselect);
            holdeView.iv_select.setVisibility(View.VISIBLE);
            if (selectList != null) {
                if (selectList.contains(dataList.get(position))) {
                    holdeView.iv_select.setImageResource(R.mipmap.edit_select);
                } else {
                    holdeView.iv_select.setImageResource(R.mipmap.edit_unselect);
                }
            }

            //输入状态件数
            holdeView.nsv_number_edit.setDataChangeListener(new DataChangeListener<Integer>() {
                public void handle(final Integer num) {
                    final int value = itemBean.amount;
                    itemBean.amount = num;

                    ubProductService.postShopcartCount(franchiseeId, itemBean, new ResultListener<String>() {
                        @Override
                        public void successHandle(String result) {
                            refreshPrice();
                            changeShopcartCount();
                        }

                        @Override
                        public void failHandle(String code, String result) {
                            itemBean.amount = value;
                            holdeView.nsv_number_edit.setValue(value);
                            showToast(result);
                        }

                        public void errorHandle(Response response, Exception e) {
                            itemBean.amount = value;
                            holdeView.nsv_number_edit.setValue(value);
                            showToast("服务器出现异常");
                        }
                    });
                }
            });
//            holdeView.nsv_number.setDataChangeListener(new DataChangeListener<Integer>() {
//                public void handle(final Integer num) {
//                    final int value = itemBean.amount;
//                    itemBean.amount = num;
//
//                    ubProductService.postShopcartCount(franchiseeId, itemBean, new ResultListener<String>() {
//                        @Override
//                        public void successHandle(String result) {
//                            refreshPrice();
//                            changeShopcartCount();
//                        }
//
//                        @Override
//                        public void failHandle(String code, String result) {
//                            itemBean.amount = value;
//                            holdeView.nsv_number.setValue(value);
//                            showToast(result);
//                        }
//
//                        public void errorHandle(Response response, Exception e) {
//                            itemBean.amount = value;
//                            holdeView.nsv_number.setValue(value);
//                            showToast("服务器出现异常");
//                        }
//                    });
//                }
//            });
            holdeView.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //购物车删除,参数为productId，list集合
                    List<Integer> infoList = new ArrayList<>();
                    infoList.add(itemBean.shopCartId);
                    ubProductService.postShopcartDelete(infoList, new CommonResultListener<String>(UbShopCartActivity.this) {
                        @Override
                        public void successHandle(String result) throws JSONException {
                            dataList.remove(position);
                            if (selectedList != null) {
                                for (ShopCartItemListBean bean : selectedList) {
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

                        @Override
                        public void failHandle(String code, String result) {
                            itemBean.amount = value;
                            holdeView.nsv_number_edit.setValue(value);
                            showToast(result);
                        }

                        public void errorHandle(Response response, Exception e) {
                            itemBean.amount = value;
                            holdeView.nsv_number_edit.setValue(value);
                            showToast("服务器出现异常");
                        }
                    });

                }
            });

            holdeView.iv_select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //购物车选择 or批量选择
                    boolean contains = selectList.contains(itemBean);
                    if (!contains) {
                        selectList.add(itemBean);
                        holdeView.iv_select.setImageResource(R.mipmap.edit_select);
                    } else {
                        selectedList.remove(itemBean);
                        holdeView.iv_select.setImageResource(R.mipmap.edit_unselect);
                    }
                    selectedChangeHandle();
                }
            });
            if (isEditStatus) {
                holdeView.rl_shopcart_edit.setVisibility(View.GONE);
                holdeView.rl_shopcart_first.setVisibility(View.VISIBLE);
            } else {
                holdeView.rl_shopcart_edit.setVisibility(View.VISIBLE);
                holdeView.rl_shopcart_first.setVisibility(View.GONE);
            }
            return convertView;
        }

        class HolderView {
            ImageView iv_select;
            ImageView iv_product_image;
            TextView tv_price;
            TextView tv_name;
            TextView tv_prop;
            //   NumericStepperView nsv_number;
            NumericStepperView nsv_number_edit;
            TextView tv_num;
            TextView delete;
            RelativeLayout rl_shopcart_edit;
            RelativeLayout rl_shopcart_first;
        }
    }

}
