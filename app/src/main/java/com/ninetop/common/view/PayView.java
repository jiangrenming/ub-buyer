package com.ninetop.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ninetop.common.util.ToastUtils;

import youbao.shopping.R;

public class PayView extends LinearLayout{
	
	private ImageView iv_1;

	private ImageView iv_2;
	
	private ImageView iv_3;
	
	private TextView tv_wallet;
	
	private int payType = 2; //默认微信支付
	
	private float walletBalance = 0;
	
	private float payMoney = 0;

	public PayView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(getLayoutId(), this);
		
		iv_1 = (ImageView) view.findViewById(R.id.iv_1);
		iv_2 = (ImageView) view.findViewById(R.id.iv_2);
		iv_3 = (ImageView) view.findViewById(R.id.iv_3);
		tv_wallet = (TextView) view.findViewById(R.id.tv_wallet);
		
		View pay_ali_pay_layout = view.findViewById(R.id.pay_ali_pay_layout);
		View pay_we_chat_layout = view.findViewById(R.id.pay_we_chat_layout);
		View pay_wallet_layout = view.findViewById(R.id.pay_wallet_layout);
		pay_ali_pay_layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				payType = 1;
			}
		});
		
		pay_we_chat_layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				payType = 2;
			}
		});
		
		pay_wallet_layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(walletBalance < payMoney){
					ToastUtils.showCenter("钱包余额不足");
					return;
				}
				
				payType = 3;
			}
		});
	}
	
	protected int getLayoutId(){
		return R.layout.view_pay;
	}
	
	protected String formatWalletText(String balance){
		return "钱包支付(余额:" + balance + ")";
	}
	
	public void setPayMoney(float pay){
		payMoney = pay;
	}

	public int getPayType() {
		return payType;
	}

}
