package youbao.shopping.ninetop.activity.ub.util.newPop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import youbao.shopping.ninetop.UB.SellerCategoryBean;
import youbao.shopping.R;

import java.util.List;

/**
 * Created by huangjinding on 2017/7/5.
 */

public class SellerPopAdapter extends BaseAdapter {
    Context context;
    List<SellerCategoryBean> dataList;
    private int mSelectItem = 0;
    public static interface IOnItemSelectListener{
        public void onItemClick(int pos);
    };
    private LayoutInflater mInflater;
    public SellerPopAdapter(Context context, List<SellerCategoryBean> dataList) {
        this.context = context;
        this.dataList = dataList;
    }
    public void refreshData(List<SellerCategoryBean> objects, int selIndex){
        dataList = objects;
        if (selIndex < 0){
            selIndex = 0;
        }
        if (selIndex >= dataList.size()){
            selIndex = dataList.size() - 1;
        }

        mSelectItem = selIndex;
    }
    public SellerPopAdapter(Context context){
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
    public long getItemId(int position) {
        return dataList.get(position).id;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final HolderView holderView;
        if(convertView==null){
            holderView=new HolderView();
            convertView= LayoutInflater.from(context).inflate(R.layout.ub_spinner_item, null);
            holderView.tv_franchiseeName=(TextView)convertView.findViewById(R.id.textView);

            convertView.setTag(holderView);
        }else {
            holderView=(HolderView)convertView.getTag();
        }
        final SellerCategoryBean bean;
        bean = dataList.get(position);
        holderView.tv_franchiseeName.setText(bean.name);

        return convertView;
    }
    class HolderView{
        TextView tv_franchiseeName;
    }

}
