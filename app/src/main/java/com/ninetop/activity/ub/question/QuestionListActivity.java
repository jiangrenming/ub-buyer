package com.ninetop.activity.ub.question;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ninetop.UB.UbUserCenterService;
import com.ninetop.UB.question.QuestionBean;
import com.ninetop.base.BaseActivity;
import com.ninetop.common.IntentExtraKeyConst;
import com.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import youbao.shopping.R;

/**
 * Created by huangjinding on 2017/6/22.
 */

public class QuestionListActivity extends BaseActivity {

    @BindView(R.id.listview)
    ListView listview;

     private UbUserCenterService ubUserCenterService;
    private List<QuestionBean> dataList;
    private QuestionAdapter adapter;
    private String index;
    @Override
    protected int getLayoutId() {
        return R.layout.ub_safe_question_list;

    }
    @Override
    protected void onResume(){
        super.onResume();
        getData();
    }

    protected void  getData() {
        ubUserCenterService=new UbUserCenterService(this);
        dataList=new ArrayList<>();
        index=getIntentValue(IntentExtraKeyConst.CAN_SELECT);
        adapter=new QuestionAdapter(QuestionListActivity.this,dataList);
        listview.setAdapter(adapter);
        getDetail();
    }
     private void getDetail(){
     ubUserCenterService.getQuestionList(new CommonResultListener<List<QuestionBean>>(this) {
         @Override
         public void successHandle(List<QuestionBean> result) throws JSONException {
             dataList.addAll(result);
             adapter.notifyDataSetChanged();
             addItemSelectedHandleIfNeed();
         }
     });
     }
    private void addItemSelectedHandleIfNeed(){
        final String canSelect=getIntentValue(IntentExtraKeyConst.CAN_SELECT);
        if("1".equals(canSelect) || "2".equals(canSelect) || "3".equals(canSelect)){
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    QuestionBean bean=dataList.get(position);
                    Intent intent=new Intent();
                    Gson gson=new Gson();
                    intent.putExtra(IntentExtraKeyConst.JSON_QUESTION,gson.toJson(bean));
                    intent.putExtra(IntentExtraKeyConst.CAN_SELECT, canSelect);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            });
        }

    }


    class QuestionAdapter extends BaseAdapter {
        List<QuestionBean> dataList;
        QuestionListActivity activity;
        public QuestionAdapter(QuestionListActivity activity,List<QuestionBean> dataList) {
            this.activity=activity;
            this.dataList=dataList;
        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return dataList.get(position);
        }
        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            HolderView holderView;
            if(convertView==null){
                holderView=new HolderView();
                convertView= LayoutInflater.from(activity).inflate(R.layout.ub_item_question,null);
                holderView.tv_question=(TextView)convertView.findViewById(R.id.tv_question);
                holderView.ll_question=(RelativeLayout)convertView.findViewById(R.id.rl_question);
                convertView.setTag(holderView);
            }else {
                holderView = (HolderView) convertView.getTag();
            }
            final QuestionBean bean=dataList.get(position);
            holderView.tv_question.setText(bean.safe_question);
//            holderView.ll_question.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent=new Intent(activity,QuestionActivity.class);
//                    intent.putExtra(IntentExtraKeyConst.QUESTION_NO, index);
//                    intent.putExtra(IntentExtraKeyConst.QUESTION_INFO, bean.safe_question);
//                    startActivity(intent);

//                    activity.finish();
//                }
//            });
            return convertView;
        }
        class HolderView{
            RelativeLayout ll_question;
            TextView tv_question;
        }
    }



}
