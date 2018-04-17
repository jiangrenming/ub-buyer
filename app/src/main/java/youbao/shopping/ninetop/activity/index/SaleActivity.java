package youbao.shopping.ninetop.activity.index;

import android.widget.ListView;

import youbao.shopping.ninetop.adatper.index.SaleDetailAdapter;
import youbao.shopping.ninetop.base.PullRefreshBaseActivity;
import youbao.shopping.ninetop.bean.index.SalePagingBean;
import youbao.shopping.ninetop.common.view.HeadView;
import youbao.shopping.ninetop.service.impl.IndexService;
import youbao.shopping.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import butterknife.BindView;
import youbao.shopping.R;

/**
 * Created by jill on 2016/11/15.
 */

public class SaleActivity extends PullRefreshBaseActivity {

    @BindView(R.id.hv_head)
    HeadView hvHead;

    private IndexService indexService;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_seckilling;
    }

    @Override
    protected void initView() {
        super.initView();

        ListView list = (ListView) refreshLayout.getPullableView();
        listAdapter = new SaleDetailAdapter(SaleActivity.this, dataList);
        list.setAdapter(listAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        indexService = new IndexService(this);
        hvHead.setTitle("限时秒杀");

        getServerData();
    }

    @Override
    protected void getServerData() {
        indexService.getSaleDetailList(currentPage + "", new CommonResultListener<SalePagingBean>(this) {
            @Override
            public void successHandle(SalePagingBean result) throws JSONException {
                if(result == null || result.dataList == null)
                    return;

                dataList.addAll(result.dataList);
                listAdapter.notifyDataSetChanged();
            }
        });
    }
}
