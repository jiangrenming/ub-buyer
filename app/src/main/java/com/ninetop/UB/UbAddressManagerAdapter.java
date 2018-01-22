package com.ninetop.UB;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ninetop.base.DefaultBaseAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import youbao.shopping.R;

/**
 * Created by huangjinding on 2017/5/13.
 */
public class UbAddressManagerAdapter extends DefaultBaseAdapter {
    private String NO="0";
    private OnAdapterItemDeleteOrEdit onAdapterItemDeleteOrEdit;

    public UbAddressManagerAdapter(List<UbAddressBean> datas, Context ctx) {
        super(datas, ctx);
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView= View.inflate(ctx, R.layout.item_address_manage_listview, null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        UbAddressBean addressBean = (UbAddressBean) datas.get(position);
        if (addressBean==null){
            return null;
        }
        viewHolder.tvAddressName.setText(((addressBean.name+"").length())>4?(addressBean.name.substring(0,3)+"..."):addressBean.name+"");
        viewHolder.tvPhoneNumber.setText(addressBean.mobile);
        viewHolder.tvAddress.setText(addressBean.addr_province+"\t"+addressBean.addr_city+"\t"+addressBean.addr_county+"\t"+addressBean.addr_detail);
        String YES = "1";
        if (YES.equals(addressBean.is_default)){
            viewHolder.ivDefaultText.setVisibility(View.VISIBLE);
        }else {
            viewHolder.ivDefaultText.setVisibility(View.INVISIBLE);
        }
        viewHolder.ivAddressEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //编辑
                if (onAdapterItemDeleteOrEdit!=null){
                    onAdapterItemDeleteOrEdit.editor(position);
                }
            }
        });
        viewHolder.ivAddressTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除回调
                if (onAdapterItemDeleteOrEdit!=null){
                    onAdapterItemDeleteOrEdit.delete(position);
                }
            }
        });
        return convertView;
    }


    public class ViewHolder {
        @BindView(R.id.tv_address_name)
        TextView tvAddressName;
        @BindView(R.id.iv_default_text)
        ImageView ivDefaultText;
        @BindView(R.id.tv_phone_number)
        TextView tvPhoneNumber;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.iv_address_edit)
        ImageView ivAddressEdit;
        @BindView(R.id.iv_address_trash)
        ImageView ivAddressTrash;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface OnAdapterItemDeleteOrEdit{
        void delete(int position);
        void editor(int position);
    }

    public void setOnAdapterItemDeleteOrEdit(OnAdapterItemDeleteOrEdit onAdapterItemDeleteOrEdit){
        this.onAdapterItemDeleteOrEdit=onAdapterItemDeleteOrEdit;
    }
}
