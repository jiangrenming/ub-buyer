package youbao.shopping.ninetop.activity.ub.order;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hykj.dialoglib.MyDialog;
import com.hykj.dialoglib.MyDialogOnClick;
import com.hykj.myviewlib.gridview.GridPasswordView;

import youbao.shopping.ninetop.UB.cartshop.ChildListView;
import youbao.shopping.ninetop.UB.order.MyOrderListBean;
import youbao.shopping.ninetop.UB.order.MyOrderDetailBean;
import youbao.shopping.ninetop.UB.product.UbProductService;
import youbao.shopping.ninetop.activity.order.ExpressQueryActivity;
import youbao.shopping.ninetop.activity.product.MyOrderActivity;
import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.base.DefaultBaseAdapter;
import youbao.shopping.ninetop.base.Viewable;
import youbao.shopping.ninetop.common.IntentExtraKeyConst;
import youbao.shopping.ninetop.common.constant.TextConstant;
import youbao.shopping.ninetop.common.util.ToastUtils;
import youbao.shopping.ninetop.common.util.Tools;
import youbao.shopping.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import youbao.shopping.R;

import static youbao.shopping.ninetop.config.AppConfig.BASE_IMAGE_URL;
import static youbao.shopping.ninetop.config.AppConfig.FRANCHISEEID;

/**
 * Created by huangjinding on 2017/6/21.
 */

public class MyOrderAdapterTwo extends DefaultBaseAdapter {
    private List<MyOrderListBean> dataList;
    private List<MyOrderDetailBean> detailList;
    private UbProductService ubProductService;
    private String status;
    private int franchiseeId;
    private int skuId;
    private int amount;

    private BaseActivity activity;

    public MyOrderAdapterTwo(List<MyOrderListBean> dataList, BaseActivity ctx, String status) {
        super(dataList, ctx);
        this.dataList = dataList;
        this.activity = ctx;
        this.status = status;
        ubProductService = new UbProductService(ctx);

    }


    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HolderView holderView;
        if (convertView == null) {
            holderView = new HolderView();
            convertView = LayoutInflater.from(activity).inflate(R.layout.ub_item_myorder, parent,false);
            holderView.tv_franchiseeName = (TextView) convertView.findViewById(R.id.tv_franchiseename);
            holderView.tv_style = (TextView) convertView.findViewById(R.id.tv_order_style);
            holderView.tv_address = (TextView) convertView.findViewById(R.id.tv_get_address);
            holderView.tv_price_total = (TextView) convertView.findViewById(R.id.tv_total_price);
            holderView.tv_left = (TextView) convertView.findViewById(R.id.tv_left);
            holderView.tv_right = (TextView) convertView.findViewById(R.id.tv_right);
            holderView.listView = (ChildListView) convertView.findViewById(R.id.listview);
            holderView.llPay = (LinearLayout) convertView.findViewById(R.id.ll_pay1);
            holderView.shopers_price = (TextView)convertView.findViewById(R.id.shopers_price);
            convertView.setTag(holderView);
        } else {
            holderView = (HolderView) convertView.getTag();
        }
        final MyOrderListBean bean = dataList.get(position);
        final int orderId = bean.orderId;
        detailList = bean.attrList;
        franchiseeId = bean.franchiseeId;
        holderView.tv_address.setText("取货地址 ：" + bean.detailAdress);
        String mTotalPrice =Math.round(bean.totalPrice)+"";
        holderView.tv_price_total.setText("总价   "+ mTotalPrice);
        holderView.shopers_price.setText(mTotalPrice);
        for (int i = 0; i < detailList.size(); i++) {
            MyOrderDetailBean detailBean = detailList.get(i);
            skuId = detailBean.productSkuId;
            amount = detailBean.amount;
        }
        final ItemOrderAdapter adapter = new ItemOrderAdapter(detailList, activity);
        holderView.listView.setAdapter(adapter);
        holderView.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(activity, UbMyOrderDetailActivity.class);
                intent.putExtra(IntentExtraKeyConst.ORDER_ID, bean.orderId + "");
                intent.putExtra(IntentExtraKeyConst.ORDER_TYPE, bean.status);
//                activity.startActivity(intent);
                activity.startActivityForResult(intent, 1);

            }
        });


        holderView.tv_franchiseeName.setText(bean.franchiseeName);
        if (status.equals("P")) {
            holderView.tv_style.setText("待兑换");
            holderView.tv_left.setText("取消订单");
            holderView.tv_right.setText("去兑换");
            holderView.tv_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showCancelOrderDialog(orderId);
                }
            });
            holderView.tv_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goChange(orderId);
                }
            });
        } else if (status.equals("A")) {
            holderView.tv_style.setText("待发货");
            holderView.tv_left.setVisibility(View.GONE);
            holderView.tv_right.setText("取消订单");
            holderView.tv_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showCancelOrderDialog(orderId);
                }
            });
        } else if (status.equals("S")) {
            holderView.tv_style.setText("待收货");
            holderView.tv_left.setText("查看物流");
            holderView.tv_right.setText("确认收货");
            holderView.tv_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkMES(orderId);
                }
            });
            holderView.tv_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirmGet(orderId);
                }
            });
        } else if (status.equals("G")) {
            holderView.tv_style.setText("待自取");
            holderView.llPay.setVisibility(View.GONE);
            // holderView.tv_price_total.setVisibility(View.GONE);
            holderView.tv_address.setVisibility(View.VISIBLE);
            setGetDetail();
        } else {
            holderView.tv_style.setText("交易成功");
            holderView.tv_left.setVisibility(View.GONE);
            holderView.tv_right.setText("删除订单");
            holderView.tv_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteOrder(orderId);
                }
            });
        }
        return convertView;
    }

    class HolderView {
        TextView tv_franchiseeName;
        TextView tv_style;
        ChildListView listView;
        TextView tv_price_total;
        TextView tv_left;
        TextView tv_right;
        LinearLayout llPay;
        TextView tv_address,shopers_price;
    }


    public void clearData() {
        dataList.clear();
        notifyDataSetChanged();
    }


    public void addData(List<MyOrderListBean> datas) {
        if (datas != null && datas.size() > 0) {
            dataList.addAll(datas);
            notifyDataSetChanged();
        }
    }

    //去兑换
    private void goChange(int orderId) {
        if (skuId == 0 || franchiseeId == 0) {
            // ctx.showToast("请选择商品规格");
            return;
        }
        final List<Map> productList = new ArrayList<>();
        final Map<String, Integer> map = new HashMap<>();
        map.put("franchiseeId", FRANCHISEEID);
        map.put("skuId", skuId);
        map.put("amount", amount);
        productList.add(map);
        Gson gson = new Gson();
        final String jsonBeanString = gson.toJson(productList);

//        ubProductService.postEMSOrder(0, 0, 0, "", productList, new CommonResultListener<JSONObject>((Viewable) ctx) {
//            @Override
//            public void successHandle(JSONObject result) throws JSONException {
//                String dataString = result.getString("data");
//                Intent intent = new Intent(ctx, UbConfirmOrderActivity.class);
//                intent.putExtra(IntentExtraKeyConst.PRODUCT_LIST, jsonBeanString);
//                intent.putExtra(IntentExtraKeyConst.JSON_DATA, dataString);
//                intent.putExtra(IntentExtraKeyConst.ORDER_FROM, "buy");
//                intent.putExtra(IntentExtraKeyConst.ORDER_SKUID, skuId + "");
//                intent.putExtra(IntentExtraKeyConst.ORDER_AMOUNT, amount + "");
//                ctx.startActivity(intent);
//
//            }
//        });
        ubProductService.getOrderPay(orderId, new CommonResultListener<JSONObject>((Viewable) ctx) {
            @Override
            public void successHandle(JSONObject result) throws JSONException {
                String dataString = result.getString("data");
                Intent intent = new Intent(ctx, UbConfirmPayActivity.class);
                intent.putExtra(IntentExtraKeyConst.JSON_ORDER, dataString);
                ctx.startActivity(intent);
            }
        });
    }

    private void setGetDetail() {

    }

    //查看物流
    private void checkMES(int orderId) {
        Intent mIntent = new Intent(ctx, ExpressQueryActivity.class);
        ctx.startActivity(mIntent);
    }

    private Dialog dialog;
    private void showReceiptDialog(final int code) {
        dialog = new Dialog(activity);
        dialog.show();
        View view = View.inflate(activity.getApplicationContext(), R.layout.activity_pay_enter_password, null);
        final GridPasswordView psdView = (GridPasswordView) view.findViewById(R.id.gps_view);
        View closeView = view.findViewById(R.id.iv_close);
        View button = view.findViewById(R.id.btn_submit);

        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setContentView(view);
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth());
        window.setAttributes(lp);
        window.setBackgroundDrawableResource(R.color.transparent);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = psdView.getPassWord();
                if (password.length() < 6) {
                    activity.showToast("请输入6位支付密码");
                    return;
                }
                confirmationOfReceipt(code,password);
                dialog.dismiss();
                Log.i("收货结果=======",code+"");
            }
        });
        closeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void confirmationOfReceipt(int code,String password){
        ubProductService.postConfirmReceive(code,password,new CommonResultListener(activity) {
            @Override
            public void successHandle(Object result) throws JSONException {
                requestAgain();
                ((Viewable) ctx).showToast("确认收货成功");

            }
        });
    }

    //确认收货
    private void confirmGet(int orderId) {
        showReceiptDialog(orderId);

//        requestAgain();
//        ubProductService.postConfirmReceive(orderId,password, new CommonResultListener((Viewable) ctx) {
//            @Override
//            public void successHandle(Object result) throws JSONException {
//
//                requestAgain();
//                ((Viewable) ctx).showToast("OK");
//            }
//        });
    }

    //取消订单
    private void cancelOrder(int orderId) {
        ubProductService.getCanselOrder(orderId, new CommonResultListener((Viewable) ctx) {
            @Override
            public void successHandle(Object result) throws JSONException {
                requestAgain();
                ((Viewable) ctx).showToast("取消订单成功");
            }
        });
    }

    //删除订单
    private void deleteOrder(int orderId) {
        ubProductService.getDeleteOrder(orderId, new CommonResultListener((Viewable) ctx) {
            @Override
            public void successHandle(Object result) throws JSONException {
                requestAgain();
                ((Viewable) ctx).showToast("删除订单成功");
            }
        });
    }

    private void showCancelOrderDialog(final int orderId) {
        new MyDialog(activity, MyDialog.DIALOG_TWOOPTION, "温馨提示", "您确定要取消订单吗?", new MyDialogOnClick() {
            @Override
            public void sureOnClick(View v) {
                cancelOrder(orderId);
            }

            @Override
            public void cancelOnClick(View v) {
            }
        }).show();
    }




    //再次请求网络
    private void requestAgain() {
        ubProductService.getOrderList(status, new CommonResultListener<List<MyOrderListBean>>((Viewable) ctx) {
            @Override
            public void successHandle(List<MyOrderListBean> result) throws JSONException {
                if (result == null) {
                    return;
                }
                dataList.clear();
                dataList.addAll(result);
                MyOrderAdapterTwo.this.notifyDataSetChanged();
            }
        });
    }

    public class ItemOrderAdapter extends BaseAdapter {
        private Activity activity;
        private List<MyOrderDetailBean> itemList;

        public ItemOrderAdapter(List<MyOrderDetailBean> itemList, Activity activity) {
            this.activity = activity;
            this.itemList = itemList;
        }

        @Override
        public int getCount() {
            return itemList.size();
        }

        @Override
        public Object getItem(int position) {
            return itemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ItemHolder itemHolder;
            final MyOrderDetailBean detailBean = itemList.get(position);
            if (convertView == null) {
                itemHolder = new ItemHolder();
                convertView = LayoutInflater.from(activity).inflate(R.layout.ub_item_myorder_product, parent,false);
                itemHolder.iv_image = (ImageView) convertView.findViewById(R.id.iv_product_image);
                itemHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_product_name);
                itemHolder.tv_num = (TextView) convertView.findViewById(R.id.tv_product_num);
                itemHolder.tv_prop = (TextView) convertView.findViewById(R.id.tv_product_prop);
                itemHolder.tv_price = (TextView) convertView.findViewById(R.id.tv_product_price);
                convertView.setTag(itemHolder);
            } else {
                itemHolder = (ItemHolder) convertView.getTag();
            }
            itemHolder.tv_name.setText(detailBean.productName);
            Tools.ImageLoaderShow(ctx, BASE_IMAGE_URL + detailBean.productIcon, itemHolder.iv_image);
            itemHolder.tv_price.setText(Math.round(detailBean.unitPrice) + "");
            itemHolder.tv_num.setText(TextConstant.MULTIPLY + detailBean.amount + "");
            itemHolder.tv_prop.setText(detailBean.productSkuName);
            return convertView;
        }

        class ItemHolder {
            ImageView iv_image;
            TextView tv_name;
            TextView tv_prop;
            TextView tv_num;
            TextView tv_price;
        }
    }

}