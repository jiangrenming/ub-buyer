package youbao.shopping.ninetop.UB;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import youbao.shopping.R;

/**
 * Created by huangjinding on 2017/5/16.
 */

public class QuestionListAdapter extends BaseAdapter {
    Context context;
    List<OnLineAskBean> dataList;

    public QuestionListAdapter(Context context, List<OnLineAskBean> dataList) {
        this.context = context;
        this.dataList = dataList;
    }


    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressWarnings({"ViewHolder", "InflateParams"})
    @Override
    public View getView(final int index, View convertView, ViewGroup parent) {
        HolderView holderView;
        if (convertView == null) {
            holderView = new HolderView();
            convertView = LayoutInflater.from(context).inflate(R.layout.ub_item_online_quetion, null);
            holderView.questionItem = (RelativeLayout) convertView.findViewById(R.id.rl_question);
            holderView.id = (TextView) convertView.findViewById(R.id.tv_question_id);
            holderView.question = (TextView) convertView.findViewById(R.id.tv_question_name);
            holderView.answer = (TextView) convertView.findViewById(R.id.tv_question);
            convertView.setTag(holderView);
        } else {
            holderView = (HolderView) convertView.getTag();
        }
        String mIndex = String.valueOf(index);
        final OnLineAskBean seller1 = dataList.get(index);
//        String id = String.valueOf(seller1.getId());
//        holderView.id.setText(id + "" + context.getResources().getString(R.string.division));
        holderView.id.setText(index + "" + context.getResources().getString(R.string.division));
        holderView.question.setText(seller1.getQuestion());
        holderView.answer.setText(seller1.getAnswer());
        // Tools.ImageLoaderShow(context, seller1.getIcon1(), holderView.ll_consume_list);
//        holderView.questionItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context,OnlineKeFuActivity.class);
//                intent.putExtra(IntentExtraKeyConst.SELLER_ID, seller1.getId());
//                context.startActivity(intent);
//            }
//        });
        return convertView;
    }

    class HolderView {
        RelativeLayout questionItem;
        TextView id;
        TextView question;
        TextView answer;
    }
}
