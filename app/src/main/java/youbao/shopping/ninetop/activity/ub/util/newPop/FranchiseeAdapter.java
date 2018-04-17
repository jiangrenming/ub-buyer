package youbao.shopping.ninetop.activity.ub.util.newPop;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import youbao.shopping.ninetop.UB.product.New.SpinnerListBean;

import java.util.List;

import youbao.shopping.R;

/**
 * Created by huangjinding on 2017/6/30.
 */

public class FranchiseeAdapter extends BaseAdapter {
    Context context;
    List<SpinnerListBean> dataList;
    private int mSelectItem = 0;
    public static interface IOnItemSelectListener{
        public void onItemClick(int pos);
    };
    private LayoutInflater mInflater;
    public FranchiseeAdapter(Context context, List<SpinnerListBean> dataList) {
        this.context = context;
        this.dataList = dataList;
    }
    public void refreshData(List<SpinnerListBean> objects, int selIndex){
        dataList = objects;
        if (selIndex < 0){
            selIndex = 0;
        }
        if (selIndex >= dataList.size()){
            selIndex = dataList.size() - 1;
        }

        mSelectItem = selIndex;
    }
    public  FranchiseeAdapter(Context context){
        init(context);
    }
    private void init(Context context) {
        context = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    public long getItemId(int position){
//        return dataList.get(position).id;
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final HolderView holderView;
        if(convertView==null){
            holderView=new HolderView();
            convertView= LayoutInflater.from(context).inflate(R.layout.ub_spinner_item, null);
            holderView.tv_franchiseeName=(TextView)convertView.findViewById(R.id.textView);

            convertView.setTag(holderView);

            SpinnerListBean bean = dataList.get(position);
            holderView.tv_franchiseeName.setText(bean.name);
        }else {
            holderView=(HolderView)convertView.getTag();
        }

//          convertView.setOnClickListener(new View.OnClickListener() {
//              @Override
//              public void onClick(View v) {
////                  String item = (String) getItem(position);
////                  holderView.tv_franchiseeName.setText(item);
//
//           getItemId(position);
//              }
//          });
        return convertView;
    }
    class HolderView{
        TextView tv_franchiseeName;
    }

}
