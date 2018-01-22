package com.ninetop.common.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import youbao.shopping.R;
import com.ninetop.common.util.Tools;

import java.util.List;

public class TabBarView<T> extends LinearLayout{
	private LinearLayout tabbarLayout;
	
	private Context context;
	
	private List<T> valueList;
	private T lastSelectItem = null;
	
	private SelectChangedListener<T> changeListener;
	
	public TabBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		this.context = context;
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(getLayoutResId(), this);
		
		tabbarLayout = (LinearLayout) view.findViewById(R.id.tab_bar_layout);
	}
	
	@SuppressLint("InflateParams")
	public void setDataList(List<T> dataList){
		this.valueList = dataList;
		
		tabbarLayout.removeAllViews();
		lastSelectItem = null;
		if(dataList.size() > 0){
			if(lastSelectItem == null)
				lastSelectItem = valueList.get(0);
			
//			int width = Tools.getScreenWidth(context) / (valueList.size());
			
			for(int i=0;i<valueList.size();i++){
				final T item = valueList.get(i);
				View view = LayoutInflater.from(context).inflate(R.layout.view_tabbar_item, null);
				final RelativeLayout rl_tab_item = (RelativeLayout)view.findViewById(R.id.rl_tab_item);
				final TextView text = (TextView)view.findViewById(R.id.tv_text);

				String textValue = item.toString();
				text.setText(textValue);
				tabbarLayout.addView(view);

				float textLength = Tools.getTextViewLength(text, textValue);
				ViewGroup.LayoutParams lp = rl_tab_item.getLayoutParams();
				lp.width = (int)(textLength + Tools.dip2px(context, 15 * 2));
				rl_tab_item.setLayoutParams(lp);

				view.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						setSelectedItemStyle(item);
						lastSelectItem = item;
						if(changeListener != null){
							changeListener.changeHandle(item);
						}
					}
				});
			}

			setSelectedItemStyle(lastSelectItem);
		}
	}
	
	private void setSelectedItemStyle(T item){
		if(valueList == null || valueList.size() == 0)
			return;
		int index = valueList.indexOf(item);
		if(index == -1)
			return;
		
		for(int i=0;i<tabbarLayout.getChildCount();i++){
			ViewGroup oneGroup = (ViewGroup)tabbarLayout.getChildAt(i);
			TextView oneText = (TextView) oneGroup.getChildAt(0);
			View oneLine = oneGroup.getChildAt(1);
			int visible = View.GONE;
			int textColor = R.color.text_black;
			if(i == index){
				textColor = R.color.text_red;
				visible = View.VISIBLE;
			}
			oneText.setTextColor(Tools.getColorByResId(context, textColor));
			oneLine.setVisibility(visible);
		}
	}
	
	protected int getLayoutResId(){
		return R.layout.view_tabbar;
	}
	
	public T getSelectedItem(){
		return lastSelectItem;
	}
	
	public void setSelectedItem(T selectedItem){
		if(selectedItem == null)
			return;
		lastSelectItem = selectedItem;
		setSelectedItemStyle(lastSelectItem);
	}
	
	public void setChangeListener(SelectChangedListener<T> changeListener){
		this.changeListener = changeListener;
	}
	
}
