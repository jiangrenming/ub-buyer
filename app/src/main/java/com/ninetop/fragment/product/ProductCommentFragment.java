package com.ninetop.fragment.product;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ninetop.adatper.product.ProductCommentAdapter;
import com.ninetop.base.BaseActivity;
import com.ninetop.bean.product.ProductCommentBean;
import com.ninetop.bean.product.ProductCommentPagingBean;
import com.ninetop.common.IntentExtraKeyConst;
import com.ninetop.common.util.Tools;
import com.ninetop.fragment.PullRefreshFragment;
import com.ninetop.service.impl.ProductService;
import com.ninetop.service.listener.CommonResultListener;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import youbao.shopping.R;


/**
 * Created by jill on 2016/11/25.
 */

public class ProductCommentFragment extends PullRefreshFragment {

    @BindView(R.id.tv_comment_count)
    TextView tvCommentCount;
    @BindView(R.id.rb_score_star)
    RatingBar rbScoreStar;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.tv_review_all)
    TextView tvReviewAll;
    @BindView(R.id.v_line_all)
    View vLineAll;
    @BindView(R.id.tv_review_has_img)
    TextView tvReviewHasImg;
    @BindView(R.id.v_line_has_img)
    View vLineHasImg;
    @BindView(R.id.ll_no_data)
    LinearLayout llNoData;
    @BindView(R.id.ll_comment)
    LinearLayout llComment;

    private ProductService productService;

    private String productCode = "";

    private int selectTabIndex = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_product_comment;
    }

    @OnClick({R.id.ll_review_all, R.id.ll_review_has_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_review_all:
                commentTabChanged(0);
                break;
            case R.id.ll_review_has_img:
                commentTabChanged(1);
                break;
        }
    }

    @Override
    protected void initView() {
        super.initView();

        productCode = ((BaseActivity)getContext()).getIntentValue(IntentExtraKeyConst.PRODUCT_CODE);
        productService = new ProductService(this);
        getComment(true);

        ListView list = (ListView) refreshLayout.getPullableView();
        list.setDividerHeight(0);
        listAdapter = new ProductCommentAdapter(dataList, (BaseActivity) getContext());
        list.setAdapter(listAdapter);
    }

    private void getComment(final boolean init){
        productService.getProductComment(productCode, currentPage + "", new CommonResultListener<ProductCommentPagingBean>(this) {
            @Override
            public void successHandle(ProductCommentPagingBean result) {
                if(init) {
                    if(result == null || "0".equals(result.count)){
                        llComment.setVisibility(View.GONE);
                        llNoData.setVisibility(View.VISIBLE);
                    }else{
                        llComment.setVisibility(View.VISIBLE);
                        llNoData.setVisibility(View.GONE);
                    }

                    tvCommentCount.setText("小伙伴的评价(" + result.count + ")");
                    float score = Float.parseFloat(result.score);
                    rbScoreStar.setRating(score);
                    tvScore.setText(score + "分");

                    tvReviewAll.setText("全部(" + result.count + ")");
                    tvReviewHasImg.setText("有图(" + result.picCount + ")");

                }

                List<ProductCommentBean> beanList = result.dataList;
                if(beanList != null && beanList.size() > 0) {
                    dataList.addAll(beanList);
                    listAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void getCommentWithPicture(){
        productService.getProductCommentWithPicture(productCode, currentPage + "", new CommonResultListener<ProductCommentPagingBean>(this) {
            @Override
            public void successHandle(ProductCommentPagingBean result) {
                List<ProductCommentBean> beanList = result.dataList;
                if(beanList != null && beanList.size() > 0) {
                    dataList.addAll(beanList);
                    listAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void commentTabChanged(int index){
        if(selectTabIndex == index)
            return;

        if(index == 1) {
            tvReviewAll.setTextColor(Tools.getColorValueByResId(getContext(), R.color.text_black));
            vLineAll.setVisibility(View.INVISIBLE);
            tvReviewHasImg.setTextColor(Tools.getColorValueByResId(getContext(), R.color.text_red));
            vLineHasImg.setVisibility(View.VISIBLE);
        }else{
            tvReviewAll.setTextColor(Tools.getColorValueByResId(getContext(), R.color.text_red));
            vLineAll.setVisibility(View.VISIBLE);
            tvReviewHasImg.setTextColor(Tools.getColorValueByResId(getContext(), R.color.text_black));
            vLineHasImg.setVisibility(View.INVISIBLE);

        }
        selectTabIndex = index;
        currentPage = 1;
        dataList.clear();
        listAdapter.notifyDataSetChanged();

        getServerData();
    }

    @Override
    protected void getServerData() {
        if(selectTabIndex == 0){
            getComment(false);
        }else{
            getCommentWithPicture();
        }
    }
}
