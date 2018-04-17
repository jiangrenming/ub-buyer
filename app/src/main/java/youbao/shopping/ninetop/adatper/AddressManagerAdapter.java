package youbao.shopping.ninetop.adatper;
/*
                   _ooOoo_
                  o8888888o
                  88" . "88
                  (| -_- |)
                  O\  =  /O
               ____/`---'\____
             .'  \\|     |//  `.
            /  \\|||  :  |||//  \
           /  _||||| -:- |||||-  \
           |   | \\\  -  /// |   |
           | \_|  ''\---/''  |   |
           \  .-\__  `-`  ___/-. /
         ___`. .'  /--.--\  `. . __
      ."" '<  `.___\_<|>_/___.'  >'"".
     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
     \  \ `-.   \_ __\ /__ _/   .-` /  /
======`-.____`-.___\_____/___.-`____.-'======
                   `=---='
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
         佛祖保佑       永无BUG
 * Created by Administrator on 2016/11/15.
 */


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import youbao.shopping.ninetop.base.DefaultBaseAdapter;
import youbao.shopping.ninetop.bean.user.AddressBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import youbao.shopping.R;

public class AddressManagerAdapter extends DefaultBaseAdapter {

    private String YES="yes";
    private String NO="no";
    private OnAdapterItemDeleteOrEdit onAdapterItemDeleteOrEdit;

    public AddressManagerAdapter(List<AddressBean> datas, Context ctx) {
        super(datas, ctx);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            convertView= View.inflate(ctx, R.layout.item_address_manage_listview, null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        AddressBean addressBean = (AddressBean) datas.get(position);
        if (addressBean==null){
            return null;
        }

        viewHolder.tvAddressName.setText(((addressBean.receiver+"").length())>4?(addressBean.receiver.substring(0,3)+"..."):addressBean.receiver+"");
        viewHolder.tvPhoneNumber.setText(addressBean.tel);
        viewHolder.tvAddress.setText(addressBean.province+"\t"+addressBean.city+"\t"+addressBean.county+"\t"+addressBean.detail);
        if (YES.equals(addressBean.defaultX)){
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
