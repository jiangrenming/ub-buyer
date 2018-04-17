package youbao.shopping.ninetop.activity.ub.usercenter.mycollection;

import youbao.shopping.ninetop.UB.CollectionProductListAdapter;
import youbao.shopping.ninetop.UB.UbUserService;
import youbao.shopping.ninetop.activity.ub.bean.product.ProductFavorBean;
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
public class ProductFragment extends BaseFragment implements StatusChangeListener {
    @BindView(R.id.lv_list)
    ScrollViewWithListView lvList;
    protected List<ProductFavorBean> dataList = new ArrayList<>();
    private UbUserService ubUserService;
    protected CollectionProductListAdapter listAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.ub_fragment_list;
    }

    @Override
    protected void initView() {
        ubUserService = new UbUserService(this);
        ubUserService.getProductfavorList(new CommonResultListener<List<ProductFavorBean>>(this) {
            @Override
            public void successHandle(List<ProductFavorBean> result) throws JSONException {
                dataList.clear();
                dataList.addAll(result);
                listAdapter.notifyDataSetChanged();
            }
        });
        initViewPage();
    }

    protected void initViewPage() {
        listAdapter = new CollectionProductListAdapter(ubUserService, this.getContext(), dataList);
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
        List<ProductFavorBean> list = listAdapter.getSelectList();
        List<Object> id_list = new ArrayList<>();

        if(list==null||list.size()==0){
            showToast("选择为空");
            return;
        }
            for (ProductFavorBean bean : list) {
                id_list.add(bean.favor_id);
            }

            Object[] listNew = id_list.toArray(new Object[id_list.size()]);
            ubUserService.postProductCollectionListCasel(listNew, new CommonResultListener(this) {
                @Override
                public void successHandle(Object result) throws JSONException {
                    initView();
                    showToast("取消成功！");
                }
            });
        }

    }
