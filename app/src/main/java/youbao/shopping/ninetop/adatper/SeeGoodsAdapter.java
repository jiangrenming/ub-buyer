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
 * Created by Administrator on 2016/11/14.
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import youbao.shopping.ninetop.base.DefaultBaseAdapter;
import youbao.shopping.ninetop.bean.user.LogisticBean;
import youbao.shopping.ninetop.common.util.Tools;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import youbao.shopping.R;

public class SeeGoodsAdapter extends DefaultBaseAdapter {

    private String trackState;

    public SeeGoodsAdapter(List<LogisticBean.TrackListBean> data, Context ctx, String trackState) {
        super(data, ctx);
        this.trackState = trackState;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView = View.inflate(ctx, R.layout.item_logistic, null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        LogisticBean.TrackListBean trackListBean = (LogisticBean.TrackListBean) datas.get(position);
        Integer state = Integer.valueOf(trackState);
        String state1;
        if (state==2){
            state1 ="在途中";
        }else if (state==3){
            state1 ="已签收";
        } else if (state==4){
            state1 ="问题件";
        }else {
            state1 ="";
        }
        if (position==0){
            viewHolder.ivShapeRoundRed.setVisibility(View.VISIBLE);
            viewHolder.ivShapeRoundGray.setVisibility(View.GONE);
            viewHolder.tvMessage.setTextColor(Tools.getColorByResId(ctx,R.color.category_select));
            viewHolder.tvTime.setTextColor(Tools.getColorByResId(ctx,R.color.category_select));
            viewHolder.tvMessage.setText(state1 +","+trackListBean.acceptstation);
            viewHolder.tvTime.setText(trackListBean.accepttime);
        }else {
            viewHolder.ivShapeRoundRed.setVisibility(View.GONE);
            viewHolder.ivShapeRoundGray.setVisibility(View.VISIBLE);
            viewHolder.tvMessage.setTextColor(Tools.getColorByResId(ctx,R.color.text_black));
            viewHolder.tvTime.setTextColor(Tools.getColorByResId(ctx,R.color.text_gray));
            viewHolder.tvMessage.setText(state1 +","+trackListBean.acceptstation);
            viewHolder.tvTime.setText(trackListBean.accepttime);
        }
        return convertView;
    }

    public class ViewHolder {
        @BindView(R.id.iv_shape_round_red)
        ImageView ivShapeRoundRed;
        @BindView(R.id.iv_shape_round_gray)
        ImageView ivShapeRoundGray;
        @BindView(R.id.tv_message)
        TextView tvMessage;
        @BindView(R.id.tv_time)
        TextView tvTime;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
