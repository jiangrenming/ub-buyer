package com.ninetop.common.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import youbao.shopping.R;

public class HeadView extends RelativeLayout{
	
	private OnClickListener backClickListener = null;
	
	private OnClickListener searchClickListener = null;
	
	private String headTitle;
	
	private int backImageVisible = View.VISIBLE;

	private int searchImageVisible = View.GONE;
	
	private int backVisible = View.VISIBLE;

	private int searchVisible = View.VISIBLE;
	
	private View ll_back;
	
	private TextView tv_title;
	
	private View iv_search;
	
	private Context context;

	public HeadView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		this.context = context;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.view_head, this);
		
		ll_back = view.findViewById(R.id.ll_back);
		tv_title = (TextView) view.findViewById(R.id.tv_title);
		iv_search = view.findViewById(R.id.iv_search);

		ll_back.setOnClickListener(new BackClickListener());
		
		initUI();
	}

	public void setBackClickListener(OnClickListener clickListener) {
		this.backClickListener = clickListener;

		ll_back.setOnClickListener(backClickListener);
	}

	public void setSearchClickListener(OnClickListener clickListener) {
		this.searchClickListener = clickListener;
		
		if(searchClickListener == null)
			return;

		iv_search.setOnClickListener(searchClickListener);
	}

	public void setTitle(String title) {
		this.headTitle = title;
		initUI();
	}

	public void setBackVisible(int backVisible) {
		this.backVisible = backVisible;
		initUI();
	}

	public void setBackImageVisible(int backImageVisible) {
		this.backImageVisible = backImageVisible;
		initUI();
	}

	public void setSearchImageVisible(int searchImageVisible) {
		this.searchImageVisible = searchImageVisible;
		initUI();
	}


	public void setSearchVisible(int searchVisible) {
		this.searchVisible = searchVisible;
		initUI2();
	}

	private void initUI(){
		if(ll_back == null)
			return;
		
		ll_back.setVisibility(backImageVisible);
		iv_search.setVisibility(searchImageVisible);
		tv_title.setText(headTitle);
	}

	private void initUI2(){
		if(ll_back == null)
			return;

		ll_back.setVisibility(backImageVisible);
		iv_search.setVisibility(searchVisible);
		tv_title.setText(headTitle);
	}
	
	class BackClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			((Activity)context).finish();
		}
		
	}

}
