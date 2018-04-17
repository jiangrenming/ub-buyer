package youbao.shopping.ninetop.activity.ub.usercenter.mycollection;

import youbao.shopping.ninetop.UB.CollectionListAdapter;
import youbao.shopping.ninetop.UB.UbSellerCollectBean;
import youbao.shopping.ninetop.UB.UbSellerService;
import youbao.shopping.ninetop.UB.UbUserService;
import youbao.shopping.ninetop.common.view.ScrollViewWithListView;
import youbao.shopping.ninetop.common.view.listener.StatusChangeListener;
import youbao.shopping.ninetop.fragment.BaseFragment;
import youbao.shopping.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import youbao.shopping.R;

/**
 * Created by huangjinding on 2017/5/9.
 */
public class SellerFragment extends BaseFragment implements StatusChangeListener {
    @BindView(R.id.lv_list)
    ScrollViewWithListView lvList;
    protected List<UbSellerCollectBean> dataList = new ArrayList<>();
    private UbSellerService ubSellerService;
    protected CollectionListAdapter listAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.ub_fragment_list;

    }

    @Override
    protected void initView() {
        ubSellerService = new UbSellerService(this);
        ubSellerService.getSellerCollectionList(new CommonResultListener<List<UbSellerCollectBean>>(this) {
            @Override
            public void successHandle(List<UbSellerCollectBean> result) throws JSONException {
                dataList.clear();
                dataList.addAll(result);
                listAdapter.notifyDataSetChanged();
            }
        });
        initViewPage();
    }

    protected void initViewPage() {
        listAdapter = new CollectionListAdapter(this.getContext(), dataList);
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
        List<UbSellerCollectBean> list = listAdapter.getSelectList();
        UbUserService ubUserService=new UbUserService(this);
        List<Object> id_list = new ArrayList<>();
        if(list==null||list.size()==0){
            showToast("选择为空");
            return;
        }
            for (UbSellerCollectBean bean : list) {
                id_list.add(bean.favorshop_id);
            }

            Object[] listNew = id_list.toArray(new Object[id_list.size()]);
            ubUserService.postSellerCollectionListCansel(listNew, new CommonResultListener(this) {
                @Override
                public void successHandle(Object result) throws JSONException {
                    initView();
                    showToast("取消成功！");
                }
            });
        }


    }

