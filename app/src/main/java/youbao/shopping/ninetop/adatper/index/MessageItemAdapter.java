package youbao.shopping.ninetop.adatper.index;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import youbao.shopping.R;
import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.bean.index.message.MessageDetailBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jill on 2016/11/15.
 */

public class MessageItemAdapter extends BaseAdapter {

    List<MessageDetailBean> dataList;
    BaseActivity activity;


    public MessageItemAdapter(BaseActivity activity, List<MessageDetailBean> dataList) {
        this.dataList = dataList;
        this.activity = activity;
    }

    public void setData(List<MessageDetailBean> dataList) {
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
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final HolderView holderView;


        if (convertView == null) {
            holderView = new HolderView();
            convertView = holderView.targetView;
            convertView.setTag(holderView);
        } else {
            holderView = (HolderView) convertView.getTag();
        }

        MessageDetailBean bean = dataList.get(position);
        holderView.tvTitle.setText(bean.title);
        holderView.tvTime.setText(bean.time);
        holderView.tvContent.setText(bean.content);

        return convertView;
    }

    class HolderView {

        View targetView;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_content)
        TextView tvContent;

        HolderView() {
            targetView = LayoutInflater.from(activity).inflate(R.layout.item_message_detail, null);

            ButterKnife.bind(this, targetView);
        }

    }
}
