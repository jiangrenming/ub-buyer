package youbao.shopping.ninetop.activity.user;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hykj.myviewlib.pulltorefresh.pullableview.PullToRefreshLayout;
import youbao.shopping.ninetop.adatper.ReturnMoneyAdapter;
import youbao.shopping.ninetop.base.PullRefreshBaseActivity;
import youbao.shopping.ninetop.bean.user.ChangingOrRefundBean;
import youbao.shopping.ninetop.common.IntentExtraKeyConst;
import youbao.shopping.ninetop.common.view.HeadView;
import youbao.shopping.ninetop.service.impl.OrderService;
import youbao.shopping.ninetop.service.impl.UserCenterService;
import youbao.shopping.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import youbao.shopping.R;

public class ReturnGoodsActivity extends PullRefreshBaseActivity<ChangingOrRefundBean.DataListBean> {
    private final UserCenterService userCenterService;
    private final OrderService orderService;
    @BindView(R.id.hv_head)
    HeadView hvHead;
    @BindView(R.id.prl_refresh)
    PullToRefreshLayout prlRefresh;
    private List<ChangingOrRefundBean.DataListBean> orderBeanList;
    private ListView list;

    public ReturnGoodsActivity() {
        orderService = new OrderService(this);
        userCenterService = new UserCenterService(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_seckilling;
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentPage=1;
        dataList.clear();
        getServerData();
    }

    private void setAdapter() {
        if (listAdapter == null) {
            listAdapter = new ReturnMoneyAdapter(dataList, this);
        }
        list.setAdapter(listAdapter);
        list.setOnItemClickListener(new MyItemOnlick());
        cancelOrder((ReturnMoneyAdapter) listAdapter);
    }

    private void cancelOrder(ReturnMoneyAdapter returnMoneyAdapter) {
        returnMoneyAdapter.setOnCanCelOnClickLis(new ReturnMoneyAdapter.OnCanCelOnClickLis() {
            @Override
            public void cancel(int position) {
                cancelReturnMoney(position);
            }
        });
    }
    private void cancelReturnMoney(final int position) {
        ChangingOrRefundBean.DataListBean dataListBean = dataList.get(position);
        userCenterService.cancelReturnMoney(dataListBean.complaintId + "", new CommonResultListener<String>(this) {
            @Override
            public void successHandle(String result) {
                for (int i = 0; i < dataList.size(); i++) {
                    if (i==position){
                       dataList.get(position).complaintstatus="C";
                    }
                }
               listAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void initView() {
        super.initView();
        initTitle();
    }

    @Override
    protected void getServerData() {
        if (orderBeanList == null) {
            orderBeanList = new ArrayList<>();
        }
        orderBeanList.clear();
        orderService.getChangeOrRefundLis(currentPage + "", new CommonResultListener<ChangingOrRefundBean>(this) {
            @Override
            public void successHandle(ChangingOrRefundBean result) throws JSONException {
                List<ChangingOrRefundBean.DataListBean> dataList = result.dataList;
                if (dataList.size() > 0) {
                    for (int i = 0; i < dataList.size(); i++) {
                        ChangingOrRefundBean.DataListBean dataListBean = dataList.get(i);
                        String complaintstatus = dataListBean.complaintstatus;
                        if (complaintstatus.equals("P")
                                || complaintstatus.equals("B")
                                || complaintstatus.equals("M")
                                || complaintstatus.equals("F")
                                || complaintstatus.equals("R")
                                || complaintstatus.equals("C")
                                || complaintstatus.equals("W")) {
                            orderBeanList.add(dataListBean);
                        }
                    }
                }
                ReturnGoodsActivity.this.dataList.addAll(orderBeanList);
                setAdapter();
            }
        });
    }

    private void initTitle() {
        hvHead.setBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        hvHead.setTitle("退换货");
        hvHead.setSearchImageVisible(View.GONE);
        list = (ListView) prlRefresh.getPullableView();
    }

    private class MyItemOnlick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ChangingOrRefundBean.DataListBean dataListBean = dataList.get(position);
            String complaintId = dataListBean.complaintId + "";
            String state = dataListBean.complaintstatus;
            HashMap<String, String> map = new HashMap<>();
            map.put(IntentExtraKeyConst.COMPLAINTID, complaintId);
            startActivity(state, map);
        }
    }

    private void startActivity(String state, HashMap<String, String> map) {
        if ("F".equals(state)) {
            //退款完成
            startActivity(RefundDetailsActivity.class, map);
        } else if ("R".equals(state) || "C".equals(state)) {
            //交易关闭
            startActivity(RefundDetailsActivity.class, map);
        } else if ("M".equals(state)) {
            //退款中
            startActivity(RefundBeing.class, map);
        } else if ("P".equals(state) || "W".equals(state)) {
            //退款申请中
            startActivity(RefundBeingProcessed.class, map);
        } else if ("B".equals(state)) {
            //退货中
            startActivity(ScaleReturnActivity.class, map);
        }
    }
}
