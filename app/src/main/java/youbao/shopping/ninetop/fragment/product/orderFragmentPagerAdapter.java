package youbao.shopping.ninetop.fragment.product;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import youbao.shopping.ninetop.fragment.OrderFragmentFactory;

/**
 * @date: 2017/1/17
 * @author: Shelton
 * @version: 1.1.3
 * @Description: orderFragmentPagerAdapter
 */
public class orderFragmentPagerAdapter extends FragmentPagerAdapter {

    public orderFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        OrderFragmentFactory.clear();
    }

    @Override
    public Fragment getItem(int position) {
        return OrderFragmentFactory.create(position);
    }

    @Override
    public int getCount() {
        return 5;
    }
}
