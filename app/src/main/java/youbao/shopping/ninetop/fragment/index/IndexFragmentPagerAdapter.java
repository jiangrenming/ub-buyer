package youbao.shopping.ninetop.fragment.index;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import youbao.shopping.ninetop.bean.index.category.CategoryBean;

import java.util.List;

/**
 * Created by jill on 2016/11/25.
 */

public class IndexFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<CategoryBean> dataList = null;

    public IndexFragmentPagerAdapter(FragmentManager fm, List<CategoryBean> dataList) {
        super(fm);

        IndexFragmentManager.clear();

        this.dataList = dataList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return dataList.get(position).toString();
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return IndexFragmentManager.getInstance(dataList.get(position));
    }
}
