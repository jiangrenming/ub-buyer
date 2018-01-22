package com.ninetop.adatper;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
  
public class HorizontalScrollViewAdapter  extends BaseAdapter
{  
    
    private Activity activity;  
    private LayoutInflater mInflater;  
    private List<String> mDataS;
  
    public HorizontalScrollViewAdapter(Activity activity,  List<String> mDataS)
    {  
        this.activity = activity;  
        mInflater = LayoutInflater.from(activity);  
        this.mDataS = mDataS;
    }  
  
    public int getCount()  
    {  
        return mDataS.size();
    }  
  
    public Object getItem(int position)  
    {  
        return mDataS.get(position);
    }  
  
    public long getItemId(int position)  
    {  
        return position;  
    }  
  
    public View getView(int position, View convertView, ViewGroup parent)  
    {  
        ViewHolder viewHolder = null;  
        if (convertView == null)  
        {  
            viewHolder = new ViewHolder();  
//            convertView = mInflater.inflate(R.layout.item_theme_recycler, parent, false);
//            viewHolder.mImg = (ImageView) convertView
//                    .findViewById(R.id.iv_productlogo);
//            viewHolder.tv_name = (TextView) convertView
//                    .findViewById(R.id.tv_name);
//            viewHolder.tv_price= (TextView) convertView
//                    .findViewById(R.id.tv_price);
//            viewHolder.tv_oldprice1 = (TextView) convertView.findViewById(R.id.tv_oldprice_1);
//            viewHolder.tv_oldprice = (TextView) convertView
//                    .findViewById(R.id.tv_oldprice);
            convertView.setTag(viewHolder);  
        } else  
        {  
            viewHolder = (ViewHolder) convertView.getTag();  
        }
        
//        LinearLayout lay_product = (LinearLayout) convertView.findViewById(R.id.lay_product);
//        LayoutParams lp2 = lay_product.getLayoutParams();
//        lp2.width = (int) ((Tools.getScreenWidth(activity) - Tools.dip2px(activity, 40)) / 3.0f);
//        lay_product.setLayoutParams(lp2);
        
        if(mDataS.size() > position){
        	LayoutParams imgLp = viewHolder.mImg.getLayoutParams();
//        	imglp.width = lp2.width;
            imgLp.height = imgLp.width;
        	viewHolder.mImg.setLayoutParams(imgLp);
        	
//	        Tools.ImageLoaderShow(activity,mDataS.get(position).getImage(),viewHolder.mImg);
//	        viewHolder.tv_name.setText(mDataS.get(position).getProductName());
//	        viewHolder.tv_price.setText("¥ "+mDataS.get(position).getProductPrice());
//	        viewHolder.tv_oldprice1.setText("专柜价："+mDataS.get(position).getOrignalPrice());
//	        viewHolder.tv_oldprice.setText("专柜价："+mDataS.get(position).getOrignalPrice());
        }
		
		
        return convertView;  
    }  
  
    private class ViewHolder  
    {  
        ImageView mImg;  
        TextView tv_name;  
        TextView tv_price; 
        TextView tv_oldprice1; 
        TextView tv_oldprice; 
    }  
  
}  
