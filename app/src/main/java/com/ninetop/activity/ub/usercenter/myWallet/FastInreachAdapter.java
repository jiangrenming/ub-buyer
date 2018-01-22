//package com.ninetop.activity.ub.usercenter.myWallet;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.ninetop.activity.ub.bean.product.BalanceIntoBean;
//import com.ninetop.base.BaseActivity;
//import com.ninetop.common.util.Tools;
//
//import java.util.List;
//
//import youbao.shopping.R;
//
///**
// * Created by huangjinding on 2017/6/26.
// */
//
//public class FastReChargeAdapter extends BaseAdapter {
//    BaseActivity activity;
//    List<BalanceIntoBean> dataList;
//    private int selectPosition;
//    public FastReChargeAdapter(BaseActivity activity,List<BalanceIntoBean> dataList){
//        this.activity=activity;
//        this.dataList=dataList;
//    }
//
//    @Override
//    public int getCount() {
//        return dataList.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return dataList.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//       final HolderView holderView;
//        if(convertView==null){
//            holderView=new HolderView();
//            convertView= LayoutInflater.from(activity).inflate(R.layout.ub_item_fast_inreach,null);
//            holderView.tv_name=(TextView)convertView.findViewById(R.id.tv_balance_count);
//            holderView.tv_u_count=(TextView)convertView.findViewById(R.id.tv_balance_send);
//            holderView.ll_balance=(LinearLayout)convertView.findViewById(R.id.ll_balance);
//
//            convertView.setTag(holderView);
//        }else {
//            holderView=(HolderView)convertView.getTag();
//        }
//        final BalanceIntoBean bean=dataList.get(position);
//        holderView.tv_name.setText(bean.amount+"元");
//        holderView.tv_u_count.setText("送"+bean.ub_given+"U币");
//        holderView.ll_balance.setBackgroundResource(R.drawable.bg_border_gray_radius);
//        if(selectPosition==position){
//            holderView.tv_name.setTextColor(Tools.getColorByResId(activity,R.color.text_red));
//            holderView.tv_u_count.setTextColor(Tools.getColorByResId(activity,R.color.text_red));
//            holderView.ll_balance.setBackgroundResource(R.drawable.bg_border_red_radius);
//            int inreachId=bean.id;
//        }else {
//            holderView.tv_name.setTextColor(Tools.getColorByResId(activity,R.color.text_gray));
//            holderView.tv_u_count.setTextColor(Tools.getColorByResId(activity,R.color.text_gray));
//            holderView.ll_balance.setBackgroundResource(R.drawable.bg_border_gray_radius);
//        }
////
////       convertView.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////
////            }
////        });
//        return convertView;
//    }
//    public void changeState(int pos){
//        selectPosition=pos;
//        notifyDataSetChanged();
//    }
//
//    class HolderView{
//        TextView tv_name;
//        TextView tv_u_count;
//        LinearLayout ll_balance;
//    }
//}
