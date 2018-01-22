//package com.ninetop.activity.ub.question;
//
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.ninetop.UB.question.QuestionBean;
//
//import java.util.List;
//
//import youbao.shopping.R;
//
///**
// * Created by huangjinding on 2017/6/22.
// */
//
//public class QuestionAdapter extends BaseAdapter {
//    List<QuestionBean> dataList;
//    QuestionActivity activity;
//    public QuestionAdapter(QuestionActivity activity,List<QuestionBean> dataList) {
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
//        HolderView holderView;
//        if(convertView==null){
//            holderView=new HolderView();
//            convertView= LayoutInflater.from(activity).inflate(R.layout.ub_item_question,null);
//            holderView.tv_question=(TextView)convertView.findViewById(R.id.tv_question);
//            holderView.ll_question=(RelativeLayout)convertView.findViewById(R.id.rl_question);
//            convertView.setTag(holderView);
//        }else {
//            holderView = (HolderView) convertView.getTag();
//        }
//        final QuestionBean bean=dataList.get(position);
//        holderView.tv_question.setText(bean.safe_question);
//        holderView.ll_question.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(activity,QuestionActivity.class);
//            }
//        });
//        return convertView;
//    }
//    class HolderView{
//        RelativeLayout ll_question;
//        TextView tv_question;
//    }
//}
//
