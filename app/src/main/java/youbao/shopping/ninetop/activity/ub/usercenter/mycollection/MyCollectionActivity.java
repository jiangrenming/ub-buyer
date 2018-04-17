package youbao.shopping.ninetop.activity.ub.usercenter.mycollection;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import youbao.shopping.ninetop.UB.UbSellerService;
import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.common.util.Tools;
import youbao.shopping.ninetop.common.view.listener.StatusChangeListener;
import youbao.shopping.ninetop.fragment.FragmentContext;

import butterknife.BindView;
import butterknife.OnClick;
import youbao.shopping.R;

/**
 * Created by huangjinding on 2017/5/9.
 */
public class MyCollectionActivity extends BaseActivity implements FragmentContext {
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.v_line_info)
    View vLineInfo;
    @BindView(R.id.tv_Seller_detail)
    TextView tvSellerDetail;
    @BindView(R.id.v_line_seller_detail)
    View vLineSellerDetail;
    @BindView(R.id.tv_shoper_detail)
    TextView tvShopDetail;
    @BindView(R.id.v_line_detail)
    View vLineDetail;
    @BindView(R.id.vp_mycollection_detail)
    ViewPager vpMyCollectionDetail;
    @BindView(R.id.btn_cansel)
    Button btnCancel;

    boolean isSelect =false;
    private View[] lineArray = null;
    private TextView[] textViewArray = null;
    private UbSellerService ubSellerService;
    private SellerFragmentPagerAdapter pagerAdapter;
    private String id;

    @Override
    protected int getLayoutId() {
        return R.layout.ub_my_collection;
    }

    @Override
    protected void initView() {
        super.initView();
        ubSellerService = new UbSellerService(this);
        initViewPager();
    }

    protected void initViewPager() {
        pagerAdapter = createPagerAdapter();

        vpMyCollectionDetail.setAdapter(pagerAdapter);
        vpMyCollectionDetail.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                   changeDisplay(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        changeDisplay(0);
        vpMyCollectionDetail.setCurrentItem(0);
    }
    protected SellerFragmentPagerAdapter createPagerAdapter(){
        return new SellerFragmentPagerAdapter(getSupportFragmentManager());
    }


    @OnClick({R.id.tv_edit,R.id.iv_back,R.id.btn_cansel, R.id.ll_product_info, R.id.ll_seller_detail, R.id.ll_shoper_detail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_edit:
                changeEditStatusHandler();
                break;
            case R.id.btn_cansel:
                changeCancelHandler();
                break;
            case R.id.ll_product_info:
                changeDisplay(0);
                vpMyCollectionDetail.setCurrentItem(0);
                initStatus();
                break;
            case R.id.ll_seller_detail:
                changeDisplay(1);
                vpMyCollectionDetail.setCurrentItem(1);
                initStatus();
                break;
            case R.id.ll_shoper_detail:
                changeDisplay(2);
                vpMyCollectionDetail.setCurrentItem(2);
                initStatus();
                break;
        }
    }

    private void changeEditStatusHandler(){
        if(!isSelect){
            btnCancel.setVisibility(View.GONE);
            tvEdit.setText("编辑");
            isSelect=true;
        }else{
            btnCancel.setVisibility(View.VISIBLE);
            tvEdit.setText("选择");
            isSelect=false;
        }
        boolean edit = !isSelect;
        Fragment fragment = pagerAdapter.getItem(vpMyCollectionDetail.getCurrentItem());
        if(fragment instanceof StatusChangeListener){
            ((StatusChangeListener)fragment).changeHandle(edit);
        }
    }

    private void changeCancelHandler(){
        Fragment fragment = pagerAdapter.getItem(vpMyCollectionDetail.getCurrentItem());

        if(fragment instanceof StatusChangeListener){
            ((StatusChangeListener)fragment).removeHandle();
        }
    }
    public void changeDisplay(int index){
        if(textViewArray==null){
            lineArray=new View[]{vLineInfo,vLineSellerDetail,vLineDetail};
            textViewArray=new TextView[]{tvInfo,tvSellerDetail,tvShopDetail};
        }
        for(int i=0;i<textViewArray.length;i++){
            if(i==index){
                lineArray[i].setVisibility(View.VISIBLE);
                textViewArray[i].setTextSize(16);
                textViewArray[i].setTextColor(Tools.getColorValueByResId(this, R.color.text_red));
                isSelect=true;
                continue;
            }
            lineArray[i].setVisibility(View.INVISIBLE);
            textViewArray[i].setTextSize(16);
            textViewArray[i].setTextColor(Tools.getColorValueByResId(this, R.color.text_gray));
        }
    }

    @Override
    public void setCurrentItem(int position){
        vpMyCollectionDetail.setCurrentItem(position);
        isSelect = false;
        initStatus();
    }

    private void initStatus(){
        btnCancel.setVisibility(View.GONE);
        tvEdit.setText("编辑");
        Fragment fragment = pagerAdapter.getItem(vpMyCollectionDetail.getCurrentItem());
        if(fragment instanceof StatusChangeListener){
            ((StatusChangeListener)fragment).changeHandle(false);
        }
    }
}
