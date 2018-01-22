package com.ninetop.fragment.index;

import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.ninetop.activity.index.SaleActivity;
import com.ninetop.adatper.index.IndexCategoryAdapter;
import com.ninetop.adatper.index.IndexRecommendAdapter;
import com.ninetop.adatper.index.IndexSaleAdapter;
import com.ninetop.base.BaseActivity;
import com.ninetop.base.Viewable;
import com.ninetop.bean.index.BannerBean;
import com.ninetop.bean.index.NewsBean;
import com.ninetop.bean.index.category.CategoryBean;
import com.ninetop.bean.index.recommend.RecommendBean;
import com.ninetop.bean.product.ProductBean;
import com.ninetop.bean.product.ProductSaleBean;
import com.ninetop.common.action.BannerActionEnum;
import com.ninetop.common.view.LinearLayoutListView;
import com.ninetop.common.view.MyHorizontalScrollView;
import com.ninetop.fragment.BaseFragment;
import com.ninetop.service.impl.IndexService;
import com.ninetop.service.listener.CommonResultListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import youbao.shopping.R;

import static youbao.shopping.R.id.lv_recommend_list;

/**
 * Created by jill on 2016/11/25.
 */

public class RecommendFragment extends BaseFragment {
    @BindView(R.id.cb_banner)
    ConvenientBanner cbBanner;
    @BindView(R.id.ts_news)
    TextSwitcher tsNews;
    @BindView(lv_recommend_list)
    LinearLayoutListView lvRecommendList;
    @BindView(R.id.lv_category_list)
    LinearLayoutListView lvCategoryList;
    @BindView(R.id.hsv_seckill)
    MyHorizontalScrollView hsvSale;

    private List<ProductBean> recommendList = null;
    private List<ProductSaleBean> saleList = null;

    private IndexService indexService = null;

    private boolean bannerHasLoad = false;
    String[] newsTitleArray = null;
    private int newsCurrentIndex = 0;

    private Handler newsPlayHandler = new Handler();
    private NewsPlayRunnable newsPlayRunnable = new NewsPlayRunnable();

    private IndexSaleAdapter seckillingAdapter;

    private IndexRecommendAdapter recommendAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recommend;
    }

    public void onResume() {
        super.onResume();

        startPlayBanner();
        startPlayNews();
    }

    public void onPause() {
        super.onPause();

        stopPlayBanner();
        stopPlayNews();
    }

    @Override
    protected void initView() {
        super.initView();

        indexService = new IndexService((Viewable) getContext());

        initNews();
        initBanner();
        initRecommendAndSale();
        initCategory();

//        svRecommend.smoothScrollTo(0, 20);
    }

    @OnClick({R.id.ll_seckilling})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_seckilling:
                ((BaseActivity) getContext()).startActivity(SaleActivity.class);
                break;
        }
    }

    private void initBanner() {
        indexService.getBannerList(new CommonResultListener<List<BannerBean>>((Viewable) getContext()) {
            @Override
            public void successHandle(final List<BannerBean> result) {
                List<Integer> localImages = new ArrayList<Integer>();
                localImages.add(R.mipmap.demo_banner);
                List<String> netWorkImages = new ArrayList<String>();
                for (BannerBean banner : result) {
                    netWorkImages.add(banner.getPicUrl());
                }
                cbBanner.setData(cbBanner, netWorkImages, localImages, new Integer[]{R.mipmap.rotation_default, R.mipmap.rotation_select});
                cbBanner.setCanLoop(true);

                cbBanner.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        BannerBean bean = result.get(position);
                        BannerActionEnum action = BannerActionEnum.getByKey(bean.getType());
                        if (action == null || action.bannerAction == null)
                            return;

                        action.bannerAction.action(getContext(), bean);
                    }
                });

                bannerHasLoad = true;
            }
        });
    }

    private void initNews() {
        tsNews.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                final TextView tv = new TextView(getContext());
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                return tv;
            }
        });

        indexService.getNewsList(new CommonResultListener<List<NewsBean>>((Viewable) getContext()) {
            @Override
            public void successHandle(List<NewsBean> result) {
                if (result == null || result.size() == 0) {
                    newsTitleArray = new String[]{};
                    return;
                }

                newsTitleArray = new String[result.size()];
                for (int i = 0; i < result.size(); i++) {
                    newsTitleArray[i] = result.get(i).toString();
                }

                stopPlayNews();
                startPlayNews();
            }
        });
    }

    private void initRecommendAndSale() {
        indexService.getRecommendList(new CommonResultListener<RecommendBean>((Viewable) getContext()) {
            @Override
            public void successHandle(RecommendBean result) {
                if (result == null)
                    return;

                recommendList = result.getRecommendList();
                saleList = result.getSecKillList();

                initRecommend();
                initSale();
            }
        });
    }

    private void initSale() {
        if (hsvSale == null)
            return;

        if (saleList != null && saleList.size() > 0) {
            seckillingAdapter = new IndexSaleAdapter((BaseActivity) getContext(), saleList);
            hsvSale.initDatas(seckillingAdapter);
            hsvSale.setVisibility(View.VISIBLE);
        } else {
            hsvSale.setVisibility(View.GONE);
        }
    }

    private void initRecommend() {
        if (lvRecommendList == null)
            return;

        if (recommendList != null && recommendList.size() > 0) {
            recommendAdapter = new IndexRecommendAdapter((BaseActivity) getContext(), recommendList);
            lvRecommendList.setAdapter(recommendAdapter);
        }
    }

    private void initCategory() {
        indexService.getCategoryList(new CommonResultListener<List<CategoryBean>>((Viewable) getContext()) {
            @Override
            public void successHandle(List<CategoryBean> result) {
                if (result == null) {
                    return;
                }

                IndexCategoryAdapter categoryAdapter = new IndexCategoryAdapter((BaseActivity) getContext(), result);
                categoryAdapter.setShowMore(true);
                lvCategoryList.setAdapter(categoryAdapter);
            }
        });
    }

    private void startPlayBanner() {
        if (!bannerHasLoad)
            return;

        cbBanner.startTurning(5000);
    }

    private void stopPlayBanner() {
        if (!bannerHasLoad)
            return;

        cbBanner.stopTurning();
    }

    private void startPlayNews() {
        if (newsTitleArray == null || newsTitleArray.length == 0)
            return;

        newsPlayHandler.postDelayed(newsPlayRunnable, 3000);
    }

    private void stopPlayNews() {
        newsPlayHandler.removeCallbacks(newsPlayRunnable);
    }

    class NewsPlayRunnable implements Runnable {
        @Override
        public void run() {
            if (newsTitleArray == null || newsTitleArray.length == 0)
                return;

            tsNews.setText(newsTitleArray[newsCurrentIndex % newsTitleArray.length]);
            newsPlayHandler.postDelayed(this, 3000);
            newsCurrentIndex++;
        }
    }
}
