package youbao.shopping.ninetop.activity.ub.product;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import youbao.shopping.ninetop.UB.product.New.SingleProductSkuBean;
import youbao.shopping.ninetop.UB.product.New.SingleSkuBean;
import youbao.shopping.ninetop.UB.product.UbProductService;
import youbao.shopping.ninetop.activity.ub.order.UbConfirmOrderActivity;
import youbao.shopping.ninetop.activity.ub.util.StatusBarUtil;
import youbao.shopping.ninetop.common.AssembleHelper;
import youbao.shopping.ninetop.common.IntentExtraKeyConst;
import youbao.shopping.ninetop.common.constant.SkuPropStatusEnum;
import youbao.shopping.ninetop.common.util.DialogUtil;
import youbao.shopping.ninetop.common.util.ToastUtils;
import youbao.shopping.ninetop.common.util.Tools;
import youbao.shopping.ninetop.common.util.ValidateUtil;
import youbao.shopping.ninetop.common.view.FlowLayout;
import youbao.shopping.ninetop.common.view.NumericStepperView;
import youbao.shopping.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import youbao.shopping.R;

import static youbao.shopping.ninetop.config.AppConfig.BASE_IMAGE_URL;
import static youbao.shopping.ninetop.config.AppConfig.FRANCHISEEID;

/**
 * Created by huangjinding on 2017/6/5.
 */

public class UbProductSelectInfoDetailDialog {
    private SingleProductSkuBean singleProductSkuBean;
    private UbProductInfoActivity activity;
    private UbProductService ubProductService;
    private int id;
    private int skuId;
    private Double SKUPPice;
    private int amount;
    private String mainIcon;
    private View mDialogView;


    private Map<String, String> selectSkuMap = new HashMap<>();
    private List<SingleProductSkuBean> skuBeanList;
    private LinearLayout llPropList;
    TextView tv_price;
    TextView tv_stock;
    TextView tv_prop_selected;
    ImageView iv_product;
    TextView tv_cart_num;

    NumericStepperView nsv_number;
    Map<String, FlowLayout> propMap = new HashMap<>();
    private AlertDialog dialog;
    Map<FlowLayout, List<TextView>> propValueMap = new HashMap<>();
    private String freeId;

    public UbProductSelectInfoDetailDialog(UbProductInfoActivity activity, UbProductService ubProductService
            , int id, String mainIcon, String franchiseeid) {
        this.activity = activity;
        this.ubProductService = ubProductService;
        this.id = id;
        this.mainIcon = mainIcon;
        this.freeId = franchiseeid;
    }

    public void showDialog() {
        mDialogView = View.inflate(activity.getApplicationContext(), R.layout.ub_dialog_product_guige, null);
        dialog = DialogUtil.createDialogBottom(activity, mDialogView);
        llPropList = (LinearLayout) mDialogView.findViewById(R.id.ll_prop_list);
        View tv_buy_now = mDialogView.findViewById(R.id.tv_buy_now);
        View tv_shopCart_add = mDialogView.findViewById(R.id.tv_shopcart_add);
        // View rv_show_shop_cart = mDialogView.findViewById(R.id.rv_show_shop_cart);
        tv_price = (TextView) mDialogView.findViewById(R.id.tv_price);
        tv_stock = (TextView) mDialogView.findViewById(R.id.tv_stock);
        tv_prop_selected = (TextView) mDialogView.findViewById(R.id.tv_prop_selected);
        nsv_number = (NumericStepperView) mDialogView.findViewById(R.id.nsv_number);
        iv_product = (ImageView) mDialogView.findViewById(R.id.iv_product);
        tv_cart_num = (TextView) mDialogView.findViewById(R.id.tv_cart_num);

        tv_shopCart_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addShopCart();

            }
        });
        tv_buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeNow();
            }
        });
        View iv_prop_close = mDialogView.findViewById(R.id.iv_prop_close);
        iv_prop_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Tools.ImageLoaderShow(activity, BASE_IMAGE_URL + mainIcon, iv_product);
        initData();

    }

    //加入购物车
    private void addShopCart() {
        if (id == 0 || skuId == 0 || SKUPPice == 0) {
            activity.showToast("请选择商品规格");
            return;
        }
        amount = nsv_number.getValue();
        ubProductService.postShopcartAdd("", id, 1, skuId, amount, SKUPPice, new CommonResultListener<String>(activity) {
            @Override
            public void successHandle(String result) throws JSONException {

            }
        });

    }

    //立即兑换
    private void changeNow() {
        if (id == 0 || skuId == 0 || SKUPPice == 0) {
            activity.showToast("请选择商品规格");
            return;
        }
        if (TextUtils.isEmpty(freeId)){
            freeId = 1+"";
        }
        amount = nsv_number.getValue();
        final List<Map> productList = new ArrayList<>();
        final Map<String, Integer> map = new HashMap<>();
        map.put("franchiseeId", Integer.valueOf(freeId));
        map.put("skuId", skuId);
        map.put("amount", amount);
        productList.add(map);
        Gson gson = new Gson();
        final String jsonBeanString = gson.toJson(productList);

        ubProductService.postEMSOrder(0, 0, 0, "", productList, new CommonResultListener<JSONObject>(activity) {
            @Override
            public void successHandle(JSONObject result) throws JSONException {
                String dataString = result.getString("data");
                Intent intent = new Intent(activity, UbConfirmOrderActivity.class);
                intent.putExtra(IntentExtraKeyConst.PRODUCT_LIST, jsonBeanString);
                intent.putExtra(IntentExtraKeyConst.JSON_DATA, dataString);
                intent.putExtra(IntentExtraKeyConst.ORDER_FROM, "buy");
                intent.putExtra(IntentExtraKeyConst.ORDER_SKUID, skuId + "");
                intent.putExtra(IntentExtraKeyConst.ORDER_AMOUNT, amount + "");
                intent.putExtra(IntentExtraKeyConst.FRANCHISEEID, freeId);
                activity.startActivity(intent);
                activity.finish();
            }
        });
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        if (dialog == null)
            return;
        dialog.setOnDismissListener(onDismissListener);
    }

    private void initData() {
        String idNew = id + "";
        ubProductService.getProductSpecifications(idNew, new CommonResultListener<List<SingleProductSkuBean>>(activity) {
            @Override
            public void successHandle(List<SingleProductSkuBean> result) throws JSONException {
                skuBeanList = result;
                for (int i = 0; i < result.size(); i++) {
                    singleProductSkuBean = result.get(i);
                    skuId = singleProductSkuBean.skuId;
                    SKUPPice = singleProductSkuBean.salePrice;

                }

                Map<String, List<String>> map = assemblePropMap();
                llPropList.removeAllViews();
                propMap.clear();
                propValueMap.clear();
                for (String key : map.keySet()) {
                    addPropLayout(key, map.get(key));
                }
                for (SingleProductSkuBean bean : skuBeanList) {
                    for (SingleSkuBean sBean : bean.attrList) {
                        selectSkuMap.put(sBean.attrName, sBean.attrValue);
                    }
                }
                initSelectedSkuUI();
                for (int i = 0; i < skuBeanList.size(); i++) {
                    SingleProductSkuBean bean = skuBeanList.get(i);
                    String mUbAccount = bean.salePrice + "";
                    String mUbNum = mUbAccount.substring(0, mUbAccount.indexOf("."));
                    tv_price.setText(mUbNum);
                    tv_stock.setText("还剩：" + bean.stock + "件");
                    int num = Integer.parseInt(bean.stock);
                    nsv_number.setMaxValue(num);
//
                }
            }
        });
    }

    private Map<String, List<String>> assemblePropMap() {
        Map<String, List<String>> map = new LinkedHashMap<String, List<String>>();
        for (SingleProductSkuBean bean : skuBeanList) {
            for (SingleSkuBean valueBean : bean.attrList) {
                String name = valueBean.attrName;
                String value = valueBean.attrValue;
                List<String> list = null;
                if (map.containsKey(name)) {
                    list = map.get(name);
                } else {
                    list = new ArrayList<String>();
                    map.put(name, list);
                }

                if (!list.contains(value))
                    list.add(value);
            }
        }
        return map;
    }

    private void addPropLayout(String name, List<String> valueList) {
        if (valueList == null || valueList.size() == 0)
            return;

        TextView textView = new TextView(activity);
        LinearLayout.LayoutParams textLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(textLp);
        textView.setText("选择" + name);
        textView.setTextColor(Tools.getColorValueByResId(activity, R.color.text_black));
        textView.setPadding(0, Tools.dip2px(activity, 20), 0, Tools.dip2px(activity, 10));
        textView.setTextSize(14);

        llPropList.addView(textView);

        FlowLayout flowLayout = new FlowLayout(activity);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        flowLayout.setLayoutParams(lp);
        flowLayout.setHorizontalSpacing(Tools.dip2px(activity, 10));
        flowLayout.setVerticalSpacing(Tools.dip2px(activity, 5));

        llPropList.addView(flowLayout);

        String text = valueList.get(0);

        int padding = 12;
        if (ValidateUtil.isChinese(text)) {
            padding = 20;
        }

        flowLayout.removeAllViews();
        addPropTag(flowLayout, name, valueList, Tools.dip2px(activity, padding));

        propMap.put(name, flowLayout);

    }

    private void addPropTag(final FlowLayout flowLayout, final String name, List<String> list, int paddingLeftAndRight) {
        List<TextView> valueList = new ArrayList<>();
        propValueMap.put(flowLayout, valueList);
        for (final String tag : list) {
            View view = LayoutInflater.from(activity).inflate(R.layout.item_product_prop_tag, null);
            final TextView text = (TextView) view.findViewById(R.id.tv_text);
            text.setHeight(Tools.dip2px(activity, 35));
            text.setText(tag);
            text.setPadding(paddingLeftAndRight, 0, paddingLeftAndRight, 0);
            flowLayout.addView(view);

            text.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    selectSkuMap.put(name, tag);

                    changeSkuHandle();
                }
            });

            valueList.add(text);
        }
    }

    private void initSelectedSkuUI() {
        List<SingleProductSkuBean> skuValueList = skuBeanList;


        selectSkuMap.clear();
        for (SingleProductSkuBean valueBean : skuBeanList) {
            for (SingleSkuBean mBean : valueBean.attrList) {
                selectSkuMap.put(mBean.attrName, mBean.attrValue);
            }
        }
        changeSkuHandle();
    }

    private void changeSkuHandle() {
        singleProductSkuBean = foundTargetSku(selectSkuMap);

        if (singleProductSkuBean == null)
            return;

        changeSelectSkuUI();
    }

    private void changeSelectSkuUI() {
        List<SingleSkuBean> skuValueList = singleProductSkuBean.attrList;
        for (SingleSkuBean valueBean : skuValueList) {
            FlowLayout flowLayout = propMap.get(valueBean.attrName);
            if (flowLayout == null)
                continue;

            String selectValue = valueBean.attrValue;
            List<TextView> valueList = propValueMap.get(flowLayout);
            for (TextView textView : valueList) {
                setPropTagStyle(textView, SkuPropStatusEnum.UNSELECTED);

                if (textView.getText().toString().equals(selectValue)) {
                    setPropTagStyle(textView, SkuPropStatusEnum.SELECTED);
                }
            }
        }

        setDisabledUI();
        tv_price.setText(singleProductSkuBean.salePrice + "");
        tv_stock.setText("还剩：" + singleProductSkuBean.stock + "件");
        String selectText = AssembleHelper.assembleSkuUb(singleProductSkuBean.attrList);
        int num = Integer.parseInt(singleProductSkuBean.stock);
        nsv_number.setMaxValue(num);
        tv_prop_selected.setText("已选：" + selectText);
        skuId = singleProductSkuBean.skuId;
    }

    public SingleProductSkuBean getSelectedSkuBean() {
        return singleProductSkuBean;
    }

    public int getSelectedCount() {
        return nsv_number.getValue();
    }

    private SingleProductSkuBean foundTargetSku(Map<String, String> skuValueMap) {
        SingleProductSkuBean selSkuBean = null;
        for (SingleProductSkuBean skuBean : skuBeanList) {
            List<SingleSkuBean> list = skuBean.attrList;
            boolean match = true;
            for (SingleSkuBean valueBean : list) {
                if (!skuValueMap.get(valueBean.attrName).equals(valueBean.attrValue)) {
                    match = false;
                }
            }

            if (match) {
                selSkuBean = skuBean;
                break;
            }

        }

        return selSkuBean;
    }

    public void setPropTagStyle(TextView textView, SkuPropStatusEnum status) {
        textView.setEnabled(status.isEnabled());
        textView.setBackgroundResource(status.getBgDrawableResId());
        textView.setTextColor(Tools.getColorByResId(activity, status.getTextColorId()));
    }

    public void setShopcartCount(int count) {
        if (count <= 0) {
            tv_cart_num.setVisibility(View.GONE);
        } else {
            tv_cart_num.setText(count + "");
            tv_cart_num.setVisibility(View.VISIBLE);
        }
    }

    //获取库存
    private int getStock(SingleSkuBean targetBean) {
        List<SingleSkuBean> valueList = singleProductSkuBean.attrList;
        if (valueList != null && valueList.size() == 0) {
            return 0;
        }
        Map<String, String> skuMap = new HashMap<>();
        for (SingleSkuBean valueBean : valueList) {
            skuMap.put(valueBean.attrName, valueBean.attrValue);
        }
        skuMap.put(targetBean.attrName, targetBean.attrValue);

        SingleProductSkuBean skuBean = foundTargetSku(skuMap);

        return skuBean == null ? 0 : Integer.parseInt(skuBean.stock);
    }

    // 库存不够禁止点击
    private void setDisabledUI() {
        for (String name : propMap.keySet()) {
            FlowLayout flowLayout = propMap.get(name);
            List<TextView> textList = propValueMap.get(flowLayout);
            for (TextView textView : textList) {
                String value = textView.getText().toString();
                SingleSkuBean valueBean = new SingleSkuBean();
                valueBean.attrName = name;
                valueBean.attrValue = value;
                int stock = getStock(valueBean);
                if (stock <= 0) {
                    setPropTagStyle(textView, SkuPropStatusEnum.DISABLED);
                    ToastUtils.showCenter(activity, "库存不够");
                }
            }
        }
    }


}