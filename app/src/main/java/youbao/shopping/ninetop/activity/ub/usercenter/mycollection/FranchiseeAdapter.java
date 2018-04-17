package youbao.shopping.ninetop.activity.ub.usercenter.mycollection;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import youbao.shopping.ninetop.UB.product.FranchiseeListBean;
import youbao.shopping.ninetop.activity.MainActivity;
import youbao.shopping.ninetop.common.IntentExtraKeyConst;
import youbao.shopping.ninetop.common.util.MySharedPreference;

import java.util.ArrayList;
import java.util.List;

import youbao.shopping.R;

/**
 * Created by huangjinding on 2017/7/3.
 */

public class FranchiseeAdapter extends BaseAdapter {

    boolean isEditStatus = false;
    Context context;
    private List<FranchiseeListBean> selectList;
    List<FranchiseeListBean> dataList;

    public FranchiseeAdapter(Context context, List<FranchiseeListBean> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    public List<FranchiseeListBean> getSelectList() {
        return selectList;
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    public void setIsEditStatus(boolean isEditStatus) {
        this.isEditStatus = isEditStatus;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        HolderView holderView;
        if (convertView == null) {
            holderView = new HolderView();
            convertView = LayoutInflater.from(context).inflate(R.layout.ub_franchisee_item_collection, null);
            holderView.iv_select = (ImageView) convertView.findViewById(R.id.iv_select);
            holderView.tvname = (TextView) convertView.findViewById(R.id.tv_franchiseeName);
            holderView.ll_franchisee = (LinearLayout) convertView.findViewById(R.id.ll_franchisee);

            holderView.tvmobile = (TextView) convertView.findViewById(R.id.tv_franchiseeMobile);
            holderView.tvaddress = (TextView) convertView.findViewById(R.id.tv_franchiseeAddress);
            convertView.setTag(holderView);
        } else {
            holderView = (HolderView) convertView.getTag();
        }
        final FranchiseeListBean franchisee = dataList.get(position);
        holderView.tvname.setText(franchisee.franchiseeName);
        holderView.tvmobile.setText(franchisee.mobile);
        holderView.tvaddress.setText(franchisee.address);

        if (isEditStatus) {
            holderView.iv_select.setVisibility(View.VISIBLE);
            holderView.ll_franchisee.setEnabled(false);
        } else {
            holderView.iv_select.setVisibility(View.GONE);
        }
        holderView.ll_franchisee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(IntentExtraKeyConst.FRANCHID, franchisee.franchiseeId);
                MySharedPreference.save(IntentExtraKeyConst.FRANCHID, franchisee.franchiseeId + "", context);
                bundle.putString(IntentExtraKeyConst.FRANCHNAME, franchisee.franchiseeName);
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        holderView.iv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = (ImageView) v;
                if (isSelected((ImageView) v)) {
                    for (FranchiseeListBean bean : dataList) {
                        addSelectItem(dataList.get(position));
                    }
                    imageView.setImageResource(R.mipmap.edit_select);
                } else {
                    for (FranchiseeListBean bean : dataList) {
                        removeSelectItem(dataList.get(position));
                    }
                    imageView.setImageResource(R.mipmap.edit_unselect);
                }

            }
        });
        return convertView;
    }

    private boolean isSelected(ImageView imageView) {
        if (imageView.getDrawable().getCurrent().getConstantState().
                equals(context.getResources().getDrawable(R.mipmap.edit_unselect).getConstantState())) {
            return true;
        }
        return false;
    }

    protected void addSelectItem(FranchiseeListBean bean) {
        if (selectList == null) {
            selectList = new ArrayList<>();
        }
        if (!selectList.contains(bean)) {
            selectList.add(bean);
        }
    }


    protected void removeSelectItem(FranchiseeListBean bean) {
        if (selectList == null) {
            selectList = new ArrayList<>();
        }
        if (selectList.contains(bean)) {
            selectList.remove(bean);
        }
    }

    class HolderView {
        TextView tvname;
        ImageView iv_select;
        LinearLayout ll_franchisee;
        public TextView tvmobile;
        TextView tvaddress;
    }
}
