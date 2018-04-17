package youbao.shopping.ninetop.fragment.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import youbao.shopping.R;
import youbao.shopping.ninetop.adatper.index.IndexCategoryAdapter;
import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.base.Viewable;
import youbao.shopping.ninetop.bean.index.category.CategoryBean;
import youbao.shopping.ninetop.fragment.BaseFragment;
import youbao.shopping.ninetop.service.impl.IndexService;
import youbao.shopping.ninetop.service.listener.CommonResultListener;

import java.util.List;

import butterknife.BindView;

/**
 * Created by jill on 2016/11/25.
 */

public class CategoryProductFragment extends BaseFragment {

    @BindView(R.id.lv_category_list)
    ListView lvCategoryList;

    private CategoryBean categoryBean;

    private IndexService indexService = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);
        if(categoryBean == null || categoryBean.getCatCode() == null)
            return view;

        indexService = new IndexService((Viewable) getContext());

        indexService.getProductByCategory(categoryBean.getCatCode(), new CommonResultListener<List<CategoryBean>>((Viewable) getContext()) {
            @Override
            public void successHandle(List<CategoryBean> result) {
                if(result == null || result.size() == 0){
                    return;
                }
                IndexCategoryAdapter categoryAdapter = new IndexCategoryAdapter((BaseActivity) getContext(), result);
                lvCategoryList.setAdapter(categoryAdapter);
            }
        });


        return view;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_category_list;
    }

    public void setCategory(@Nullable CategoryBean category) {
        categoryBean = category;
    }

    @Override
    public void onStart() {
        super.onStart();

    }


}
