package youbao.shopping.ninetop.adatper.index;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import youbao.shopping.R;
import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.bean.index.gift.UserGiftBean;
import youbao.shopping.ninetop.service.impl.IndexService;
import youbao.shopping.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jill on 2016/11/15.
 */

public class NewUserGiftAdapter extends BaseAdapter {

    List<UserGiftBean> dataList;
    BaseActivity activity;

    private IndexService indexService;

    public NewUserGiftAdapter(BaseActivity activity, List<UserGiftBean> dataList) {
        this.dataList = dataList;
        this.activity = activity;

        indexService = new IndexService(activity);
    }

    public void setData(List<UserGiftBean> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
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
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holderView;

        final UserGiftBean bean = dataList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.item_new_user_gift, null);
            holderView = new ViewHolder(convertView);
            convertView.setTag(holderView);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    indexService.receiveUserGift(bean.id, new CommonResultListener<String>(activity) {
                        @Override
                        public void successHandle(String result) throws JSONException {
                            activity.showToast("领取成功");
                        }
                    });
                }
            });
        } else {
            holderView = (ViewHolder) convertView.getTag();
        }

        holderView.tvPrice.setText(bean.amount);
        holderView.tvName.setText(bean.name);

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_name)
        TextView tvName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
