package com.ninetop.adatper.product;

import android.view.View;

import com.ninetop.base.MyApplication;

import java.util.HashMap;
import java.util.Map;

import youbao.shopping.R;

/**
 * @date: 2016/12/8
 * @author: Shelton
 * @version: 1.1.3
 * @Description:
 */
public class OrderFragmentEnum {

    public static Map<String, OrderFragmentEnum> map = new HashMap<>();
    public static final String STATUS_WAIT_PAY = "P";//待付款
    public static final String STATUS_WAIT_SEND = "A";//待发货
    public static final String STATUS_SEND = "S";//已发货
    public static final String STATUS_FINISH = "F";//已完成
    public static final String STATUS_CANCEL = "C";//取消
    public static final String STATUS_E = "E"; //已关闭
    public static final String STATUS_DELETE = "D";//删除

    public static int btnTextColorGray = MyApplication.getContext().getResources().getColor(R.color.gray_light3);
    public static int btnTextColorRed = MyApplication.getContext().getResources().getColor(R.color.text_red2);

    public String orderStatus;
    public String tvMyOrderType;
    public int btn_left, btn_right;
    public int btn_leftVisible, btn_rightVisible;
    public String btn_rightText, btn_leftText;
    public int btn_leftTextColor, btn_rightTextColor;
    public int btn_leftBg, btn_rightBg;
    public View.OnClickListener leftListener;
    public View.OnClickListener rightListener;


    static {
        map.put(STATUS_WAIT_PAY, new OrderFragmentEnum(STATUS_WAIT_PAY, "等待买家付款", R.id.btn_left, R.id.btn_right, View.VISIBLE, View.VISIBLE, "取消订单", "付款", btnTextColorGray, btnTextColorRed, R.mipmap.border_btn_gray, R.mipmap.border_btn_red, null, null));
        map.put(STATUS_WAIT_SEND, new OrderFragmentEnum(STATUS_WAIT_SEND, "等待卖家发货", R.id.btn_left, R.id.btn_right, View.GONE, View.VISIBLE, "", "催单", 0, btnTextColorGray, 0, R.mipmap.border_btn_gray, null, null));
        map.put(STATUS_SEND, new OrderFragmentEnum(STATUS_SEND, "卖家已发货", R.id.btn_left, R.id.btn_right, View.VISIBLE, View.VISIBLE, "查看物流", "确认收货", btnTextColorGray, btnTextColorRed, R.mipmap.border_btn_gray, R.mipmap.border_btn_red, null, null));
        map.put(STATUS_FINISH, new OrderFragmentEnum(STATUS_FINISH, "交易成功",R.id.btn_left, R.id.btn_right, View.VISIBLE, View.VISIBLE, "查看物流", "去评价", btnTextColorGray, btnTextColorRed, R.mipmap.border_btn_gray, R.mipmap.border_btn_red, null, null));
        map.put(STATUS_CANCEL, new OrderFragmentEnum(STATUS_CANCEL, "交易关闭",R.id.btn_left, R.id.btn_right, View.GONE, View.VISIBLE, "", "删除订单", 0, btnTextColorGray, 0, R.mipmap.border_btn_gray, null, null));
        map.put(STATUS_E, new OrderFragmentEnum(STATUS_E, "交易关闭",R.id.btn_left, R.id.btn_right, View.GONE, View.VISIBLE, "", "删除订单", 0, btnTextColorGray, 0, R.mipmap.border_btn_gray, null, null));
    }


    private OrderFragmentEnum(String orderStatus, String tvMyOrderType, int btn_left, int btn_right, int btn_leftVisible,
                              int btn_rightVisible, String btn_leftText, String btn_rightText, int btn_leftTextColor,
                              int btn_rightTextColor, int btn_leftBg, int btn_rightBg, View.OnClickListener leftListener,
                              View.OnClickListener rightListener) {
        this.orderStatus = orderStatus;
        this.tvMyOrderType = tvMyOrderType;
        this.btn_left = btn_left;
        this.btn_right = btn_right;
        this.btn_leftVisible = btn_leftVisible;
        this.btn_rightVisible = btn_rightVisible;
        this.btn_leftText = btn_leftText;
        this.btn_rightText = btn_rightText;
        this.btn_leftTextColor = btn_leftTextColor;
        this.btn_rightTextColor = btn_rightTextColor;
        this.btn_leftBg = btn_leftBg;
        this.btn_rightBg = btn_rightBg;
        this.leftListener = leftListener;
        this.rightListener = rightListener;
    }


    public static OrderFragmentEnum getByKey(String status) {
        return map.get(status);
    }

    public void registerLeftOnClickListener(View.OnClickListener onClickListener) {
        this.leftListener = onClickListener;
    }

    public void registerRightOnClickListener(View.OnClickListener onClickListener) {
        this.rightListener = onClickListener;
    }


}
