package youbao.shopping.ninetop.adatper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import youbao.shopping.R;
import youbao.shopping.ninetop.bean.user.RewardBean;


/**
 * Created by Administrator on 2018/3/21/021.
 */

public class RewardAdapter extends BaseAdapter implements ListAdapter {

    private RewardBean rewardBean;
    private Context context;

    public RewardAdapter(Context context, RewardBean rewardBean) {
        super();
        this.rewardBean = rewardBean;
        this.context = context;
    }

    @Override
    public int getCount() {
        return rewardBean.getData().size();
    }

    @Override
    public Object getItem(int position) {
        return rewardBean.getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold hold = null;
        if (convertView == null) {
            hold = new ViewHold();
            convertView = View.inflate(context,R.layout.item_reward, null);
//            accounts":"15012790742","registerTime":"2018-03-28 16:16:22","getMoney":6.8,"getPoint":10
            hold.reward_registertime = (TextView) convertView.findViewById(R.id.reward_registertime);//获得奖励时间
            hold.reward_getIntegral = (TextView)convertView.findViewById(R.id.reward_getIntegral);//获得积分
            hold.reward_getMoney = (TextView) convertView.findViewById(R.id.reward_getMoney);//对方获得奖励
            hold.reward_accounts = (TextView) convertView.findViewById(R.id.reward_accounts);//被邀请人帐号
            convertView.setTag(hold);

            hold.reward_registertime.setText(rewardBean.getData().get(position).getRegisterTime());
            hold.reward_getIntegral.setText(String.valueOf(rewardBean.getData().get(position).getGetPoint()));
            hold.reward_getMoney.setText(String.valueOf(rewardBean.getData().get(position).getGetMoney())+"元");
            hold.reward_accounts.setText(rewardBean.getData().get(position).getAccounts());
        }else {
            hold = (ViewHold) convertView.getTag();
        }
        return convertView;
    }

    class ViewHold {
        TextView reward_registertime,reward_getIntegral,reward_getMoney,reward_accounts;
    }
}
