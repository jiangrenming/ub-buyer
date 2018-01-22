package com.ninetop.activity.ub.usercenter.mycollection;

import com.ninetop.UB.UbUserService;
import com.ninetop.UB.product.FranchiseeListBean;
import com.ninetop.UB.product.UbProductService;
import com.ninetop.common.view.ScrollViewWithListView;
import com.ninetop.common.view.listener.StatusChangeListener;
import com.ninetop.fragment.BaseFragment;
import com.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import youbao.shopping.R;

/**
 * Created by huangjinding on 2017/5/9.
 */
public class FranchiseeFragment extends BaseFragment implements StatusChangeListener {

    @BindView(R.id.lv_list)
    ScrollViewWithListView lvList;
    private UbUserService ubUserService;
    protected List<FranchiseeListBean> dataList = new ArrayList<>();
    private UbProductService ubProductService;
    protected FranchiseeAdapter listAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.ub_fragment_list;
    }

    @Override
    protected void initView() {
        ubProductService = new UbProductService(this);
        ubUserService = new UbUserService(this);
        ubProductService.getFraniseeList(new CommonResultListener<List<FranchiseeListBean>>(this) {
            @Override
            public void successHandle(List<FranchiseeListBean> result) throws JSONException {
                dataList.clear();
                dataList.addAll(result);
                listAdapter.notifyDataSetChanged();
            }
        });
        initViewPage();
    }

    protected void initViewPage() {
        listAdapter = new FranchiseeAdapter(this.getContext(), dataList);
        lvList.setAdapter(listAdapter);
    }

    @Override
    public void changeHandle(boolean edit) {
        if (dataList == null || dataList.size() == 0)
            return;
        listAdapter.setIsEditStatus(edit);
        listAdapter.notifyDataSetInvalidated();
    }

    @Override
    public void removeHandle() {
        List<FranchiseeListBean> list = listAdapter.getSelectList();
        List<Object> id_list = new ArrayList<>();
        if (list == null || list.size() == 0) {
            showToast("选择为空");
            return;
        }
        for (FranchiseeListBean bean : list) {
            id_list.add(bean.favorId);
        }

        Object[] listNew = id_list.toArray(new Object[id_list.size()]);
        ubUserService.postFranchiseeCollectionListCansel(listNew, new CommonResultListener(this) {
            @Override
            public void successHandle(Object result) throws JSONException {
                initView();
                showToast("取消成功！");
            }
        });
    }

}

