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

public class ItemImgView extends LinearLayout {

    private View view;
    private TextView textView;
    private ImageView imageview;
    private String attr1;
    private int attr2;

    public ItemImgView(Context context) {
        this(context,null);
    }

    public ItemImgView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ItemImgView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    public void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ItemMyView);
        attr1 = ta.getString(R.styleable.ItemMyView_text);
        attr2 = ta.getResourceId(R.styleable.ItemMyView_src, 0);
        ta.recycle();

        view = View.inflate(getContext(), R.layout.item_img_view, null);
        textView = (TextView) view.findViewById(R.id.tv_config);
        imageview = (ImageView) view.findViewById(R.id.iv);
        addView(view);
        if (!TextUtils.isEmpty(attr1)){
            setTextView(attr1);
        }
        if (attr2!=0){
            setImageview(attr2);
        }
    }

    public void setTextView(CharSequence charS){
        textView.setText(charS);
    }
    public void setImageview(int src){
        imageview.setBackgroundResource(src);
    }
}
