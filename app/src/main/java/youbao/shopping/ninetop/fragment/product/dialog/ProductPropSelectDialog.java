package youbao.shopping.ninetop.fragment.product.dialog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import youbao.shopping.ninetop.activity.order.shopcart.ShopcartActivity;
import youbao.shopping.ninetop.activity.product.ProductDetailActivity;
import youbao.shopping.ninetop.bean.product.ProductDetailBean;
import youbao.shopping.ninetop.bean.product.ProductSkuBean;
import youbao.shopping.ninetop.bean.product.ProductSkuValueBean;
import youbao.shopping.ninetop.common.AssembleHelper;
import youbao.shopping.ninetop.common.IntentExtraKeyConst;
import youbao.shopping.ninetop.common.constant.SkuPropStatusEnum;
import youbao.shopping.ninetop.common.util.DialogUtil;
import youbao.shopping.ninetop.common.util.Tools;
import youbao.shopping.ninetop.common.util.ValidateUtil;
import youbao.shopping.ninetop.common.view.FlowLayout;
import youbao.shopping.ninetop.common.view.NumericStepperView;
import youbao.shopping.ninetop.fragment.product.ProductInfoFragment;
import youbao.shopping.ninetop.service.impl.ProductService;
import youbao.shopping.ninetop.service.listener.CommonResultListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import youbao.shopping.R;


/**
 * Created by jill on 2016/12/2.
 */

public class ProductPropSelectDialog {

    private ProductDetailActivity activity;
    private ProductInfoFragment productInfoFragment;
    private ProductDetailBean productDetailBean;

    private ProductService productService;

    private AlertDialog dialog;
    private View mDialogView;

    private LinearLayout llPropList;
    TextView tv_price;
    TextView tv_stock;
    TextView tv_prop_selected;
    ImageView iv_product;
    TextView tv_cart_num;

    NumericStepperView nsv_number;
    Map<String, FlowLayout> propMap = new HashMap<>();

    Map<FlowLayout, List<TextView>> propValueMap = new HashMap<>();
    private List<ProductSkuBean> skuBeanList;
    private ProductSkuBean selectedSkuBean;

    private Map<String, String> selectSkuMap = new HashMap<>();
    private String productImage = null;

    public ProductPropSelectDialog(ProductDetailActivity activity, ProductInfoFragment productInfoFragment,
                                   ProductDetailBean productDetailBean, String productImage, ProductService productService) {
        this.activity = activity;
        this.productDetailBean = productDetailBean;
        this.productService = productService;
        this.productInfoFragment = productInfoFragment;
        this.productImage = productImage;
    }

    public void showDialog() {
        mDialogView = View.inflate(activity.getApplicationContext(),
                R.layout.activity_product_prop_selector, null);

        dialog = DialogUtil.createDialogBottom(activity, mDialogView);

        llPropList = (LinearLayout) mDialogView.findViewById(R.id.ll_prop_list);
        View tv_buy_now = mDialogView.findViewById(R.id.tv_buy_now);
        View tv_shopcart_add = mDialogView.findViewById(R.id.tv_shopcart_add);
        View rv_show_shop_cart = mDialogView.findViewById(R.id.rv_show_shop_cart);
        tv_price = (TextView) mDialogView.findViewById(R.id.tv_price);
        tv_stock = (TextView) mDialogView.findViewById(R.id.tv_stock);
        tv_prop_selected = (TextView) mDialogView.findViewById(R.id.tv_prop_selected);
        nsv_number = (NumericStepperView) mDialogView.findViewById(R.id.nsv_number);
        iv_product = (ImageView) mDialogView.findViewById(R.id.iv_product);
        tv_cart_num = (TextView) mDialogView.findViewById(R.id.tv_cart_num);

        if(productImage != null && productImage.length() > 0){
            Tools.ImageLoaderShow(activity, productImage, iv_product);
        }

        tv_shopcart_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productInfoFragment.addShopCart();
            }
        });

        rv_show_shop_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,String> map = new HashMap<>();
                map.put(IntentExtraKeyConst.SHOW_BACK, "1");
                activity.startActivity(ShopcartActivity.class, map);
            }
        });

        tv_buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productInfoFragment.buyNow();
            }
        });

        View iv_prop_close = mDialogView.findViewById(R.id.iv_prop_close);
        iv_prop_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        initData();
    }
    private void initSelectedSkuUI() {
        List<ProductSkuValueBean> skuValueList = productDetailBean.skuList;
        if(selectedSkuBean != null){
            skuValueList = selectedSkuBean.valueList;
        }

        selectSkuMap.clear();

        for(ProductSkuValueBean valueBean : skuValueList){
            selectSkuMap.put(valueBean.name, valueBean.value);
        }

        changeSkuHandle();
    }
    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener){
        if(dialog == null)
            return;
        dialog.setOnDismissListener(onDismissListener);
    }

    private void initData() {
        productService.getProductSkuList(productDetailBean.code, new CommonResultListener<List<ProductSkuBean>>(activity) {
            @Override
            public void successHandle(List<ProductSkuBean> result) {
                skuBeanList = result;
                Map<String, List<String>> map = assemblePropMap();

                llPropList.removeAllViews();
                propMap.clear();
                propValueMap.clear();
                for (String key : map.keySet()) {
                    addPropLayout(key, map.get(key));
                }

                initSelectedSkuUI();
            }
        });

        View iv_prop_close = mDialogView.findViewById(R.id.iv_prop_close);
        iv_prop_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private Map<String, List<String>> assemblePropMap() {
        Map<String, List<String>> map = new LinkedHashMap<String, List<String>>();
        for (ProductSkuBean bean : skuBeanList) {
            for (ProductSkuValueBean valueBean : bean.valueList) {
                String name = valueBean.name;
                String value = valueBean.value;
                List<String> list = null;
                if (map.containsKey(name)) {
                    list = map.get(name);
                } else {
                    list = new ArrayList<String>();
                    map.put(name, list);
                }

                if(!list.contains(value))
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



    private void changeSkuHandle(){
        selectedSkuBean = foundTargetSku(selectSkuMap);

        if(selectedSkuBean == null)
            return;

        changeSelectSkuUI();
    }

    private void changeSelectSkuUI(){
        List<ProductSkuValueBean> skuValueList = selectedSkuBean.valueList;
        for (ProductSkuValueBean valueBean : skuValueList) {
            FlowLayout flowLayout = propMap.get(valueBean.name);
            if (flowLayout == null)
                continue;

            String selectValue = valueBean.value;
            List<TextView> valueList = propValueMap.get(flowLayout);
            for (TextView textView : valueList) {
                setPropTagStyle(textView, SkuPropStatusEnum.UNSELECTED);

                if (textView.getText().toString().equals(selectValue)) {
                    setPropTagStyle(textView, SkuPropStatusEnum.SELECTED);
                }
            }
        }

        setDisabledUI();
        tv_price.setText(selectedSkuBean.price);
        tv_stock.setText("还剩：" + selectedSkuBean.stock + "件");
        String selectText = AssembleHelper.assembleSku(selectedSkuBean.valueList);
        int num = Integer.parseInt(selectedSkuBean.stock);

        nsv_number.setMaxValue(num);
        tv_prop_selected.setText("已选：" + selectText);
    }

    public ProductSkuBean getSelectedSkuBean(){
        return selectedSkuBean;
    }


    public int getSelectedCount(){
        return nsv_number.getValue();
    }

    private ProductSkuBean foundTargetSku(Map<String, String> skuValueMap){
        ProductSkuBean selSkuBean = null;
        for(ProductSkuBean skuBean : skuBeanList){
            List<ProductSkuValueBean> list = skuBean.valueList;
            boolean match = true;
            for(ProductSkuValueBean valueBean : list){
                if(!skuValueMap.get(valueBean.name).equals(valueBean.value)){
                    match = false;
                }
            }

            if(match){
                selSkuBean = skuBean;
                break;
            }

        }

        return selSkuBean;
    }

    public void setShopCartCount(int count){
        if(count <= 0){
            tv_cart_num.setVisibility(View.GONE);
        }else {
            tv_cart_num.setText(count + "");
            tv_cart_num.setVisibility(View.VISIBLE);
        }
    }

    public void setPropTagStyle(TextView textView, SkuPropStatusEnum status){
        textView.setEnabled(status.isEnabled());
        textView.setBackgroundResource(status.getBgDrawableResId());
        textView.setTextColor(Tools.getColorByResId(activity, status.getTextColorId()));
    }

    private int getStock(ProductSkuValueBean targetBean){
        List<ProductSkuValueBean> valueList = selectedSkuBean.valueList;
        if(valueList != null && valueList.size() == 0){
            return 0;
        }

        Map<String, String> skuMap = new HashMap<>();
        for(ProductSkuValueBean valueBean : valueList){
            skuMap.put(valueBean.name, valueBean.value);
        }
        skuMap.put(targetBean.name, targetBean.value);

        ProductSkuBean skuBean = foundTargetSku(skuMap);

        return skuBean == null ? 0 : Integer.parseInt(skuBean.stock);
    }

    private void setDisabledUI(){
        for (String name : propMap.keySet()){
            FlowLayout flowLayout = propMap.get(name);
            List<TextView> textList = propValueMap.get(flowLayout);
            for(TextView textView : textList){
                String value = textView.getText().toString();
                ProductSkuValueBean valueBean = new ProductSkuValueBean();
                valueBean.name = name;
                valueBean.value = value;
                int stock = getStock(valueBean);
                if(stock <= 0){
                    setPropTagStyle(textView, SkuPropStatusEnum.DISABLED);
                }
            }
        }
    }

}
