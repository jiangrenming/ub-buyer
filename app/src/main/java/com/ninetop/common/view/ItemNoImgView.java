package com.ninetop.common.view;
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
 * Created by Administrator on 2016/11/11.
 */


import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import youbao.shopping.R;

public class ItemNoImgView extends LinearLayout {

    private View view;
    private TextView textView;
    private ImageView iv_go;
    private int gone=View.GONE;
    private int visible=View.VISIBLE;

    public ItemNoImgView(Context context) {
        this(context,null);
    }
    public ItemNoImgView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public ItemNoImgView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    public void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ItemMyView);
        String attr1 = ta.getString(R.styleable.ItemMyView_text);
        int attr2 = ta.getInteger(R.styleable.ItemMyView_iv_visibility,0);

        int color = ta.getColor(R.styleable.ItemMyView_text_color, getResources().getColor(R.color.text_black));
        ta.recycle();
        view = View.inflate(getContext(), R.layout.item_horicenter_no_img, null);
        textView = (TextView) view.findViewById(R.id.tv_config);
        iv_go = (ImageView) view.findViewById(R.id.iv_go);
        addView(view);
        if (!TextUtils.isEmpty(attr1)){
            setTextView(attr1);
        }
        setIvGOVisibility(attr2);
        setTextColor(color);
    }
    public void setTextSize(float dimension){
        textView.setTextSize(dimension);
    }
    public void setTextColor(int color){
        textView.setTextColor(color);
    }
    public void setTextView(CharSequence charS){
        textView.setText(charS);
    }
    public void setIvGOVisibility(int visibility){
        if (visible==visibility){
            iv_go.setVisibility(VISIBLE);
        }else if (gone==visibility){
            iv_go.setVisibility(GONE);
        }
    }
}
