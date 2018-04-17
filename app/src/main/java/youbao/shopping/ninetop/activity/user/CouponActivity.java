package youbao.shopping.ninetop.activity.user;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import youbao.shopping.ninetop.adatper.MyCouponPageAdapter;
import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.bean.user.CouponBean;
import youbao.shopping.ninetop.common.view.HeadView;
import youbao.shopping.ninetop.page.BasePager;
import youbao.shopping.ninetop.page.DisableCouPomPage;
import youbao.shopping.ninetop.page.NotUsedCouponPage;
import youbao.shopping.ninetop.service.impl.UserCenterService;
import youbao.shopping.ninetop.service.listener.CommonResultListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import youbao.shopping.R;

/*
                   _ooOoo_
                  o8888888o
                  88" . "88
                  (| -_- |)
                  O\  =  /O
               ____/`---'\____
             .'  \\|     |//  `.
            /  \\|||  :  |||//  \
           /  _||||| -:- |||||-  \
           |   | \\\  -  /// |   |
           | \_|  ''\---/''  |   |
           \  .-\__  `-`  ___/-. /
         ___`. .'  /--.--\  `. . __
      ."" '<  `.___\_<|>_/___.'  >'"".
     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
     \  \ `-.   \_ __\ /__ _/   .-` /  /
======`-.____`-.___\_____/___.-`____.-'======
                   `=---='
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
         佛祖保佑       永无BUG
 * Created by Administrator on 2016/11/12.
 */
public class CouponActivity extends BaseActivity {

    @BindView(R.id.tv_no_use)
    TextView tvNoUse;
    @BindView(R.id.tv_disable)
    TextView tvDisable;
    @BindView(R.id.line1)
    View line1;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.ll_is_used)
    LinearLayout ll_is_used;
    @BindView(R.id.hv_head)
    HeadView hv_head;
    private ArrayList<BasePager> activities;
    private int lastPosition=0;
    private int itemLength;
    private int disable_left;
    private int tvNoUse_left;
    private final UserCenterService userCenterService;
    private ArrayList<CouponBean> enable;
    private ArrayList<CouponBean> disenable;

    public CouponActivity(){
        userCenterService = new UserCenterService(this);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_coupon;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        initTitle();
        measure();
    }

    private void initTitle() {
        hv_head.setTitle("消费账单");
        hv_head.setBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        hv_head.setSearchImageVisible(View.GONE);
    }

    @Override
    protected void initData() {
        super.initData();
        if (activities==null){
            activities = new ArrayList<>();
        }
        if (enable==null){
            enable= new ArrayList<>();
        }
        if (disenable==null){
            disenable= new ArrayList<>();
        }
        activities.clear();
        enable.clear();
        disenable.clear();
        getData();
        //测量
    }

    private void measure() {
        ViewTreeObserver viewTreeObserver = ll_is_used.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //未使用左边距
                tvNoUse_left = tvNoUse.getLeft();
                int width = tvNoUse.getWidth();
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) line1.getLayoutParams();
                layoutParams.width=width;
                layoutParams.leftMargin=tvNoUse_left;
                //失效左边距
                disable_left = tvDisable.getLeft();
                //得到父控件长度
                ViewGroup parent = (ViewGroup) tvNoUse.getParent();
                itemLength = parent.getWidth();
            }
        });
    }

    public void getData() {
        userCenterService.getCouponList(new CommonResultListener<List<CouponBean>>(this) {
            @Override
            public void successHandle(List<CouponBean> result) {
               //得到bean的集合
                for (int i = 0; i < result.size(); i++) {
                    CouponBean couponBean = result.get(i);
                    if (couponBean.useStatus==0){
                        //未使用
                        enable.add(couponBean);
                    }else if(couponBean.useStatus==1||couponBean.useStatus==2){
                        //未使用和已失效
                        disenable.add(couponBean);
                    }
                }
                setAdapter();
            }
        });
    }

    private void setAdapter() {
        activities.add(new NotUsedCouponPage(this,enable));
        activities.add(new DisableCouPomPage(this,disenable));
        viewPager.setAdapter(new MyCouponPageAdapter(activities,this));
        viewPager.setOnPageChangeListener(new MyPageLinsner());
        onClick(tvNoUse);
    }

    private class MyPageLinsner implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }
        @Override
        public void onPageSelected(int position) {
            if (position==0){
                init_no_used();
                line1.startAnimation(getAnimator(lastPosition,0));
                lastPosition=0;
            }else if (position==1){
                initDisable();
                line1.startAnimation(getAnimator(lastPosition,itemLength));
                lastPosition=itemLength;
            }
        }
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


    @OnClick({R.id.tv_no_use, R.id.tv_disable})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_no_use:
                init_no_used();
                line1.startAnimation(getAnimator(lastPosition,0));
                lastPosition=0;
                viewPager.setCurrentItem(0);
                break;
            case R.id.tv_disable:
                initDisable();
                line1.startAnimation(getAnimator(lastPosition,itemLength));
                lastPosition=itemLength;
                viewPager.setCurrentItem(1);
                break;
        }
    }

    private void initDisable() {
        tvDisable.setTextColor(getResources().getColor(R.color.category_select));
        tvNoUse.setTextColor(getResources().getColor(R.color.text_black));
    }

    private void init_no_used() {
        tvNoUse.setTextColor(getResources().getColor(R.color.category_select));
        tvDisable.setTextColor(getResources().getColor(R.color.text_black));
    }


    private TranslateAnimation getAnimator(int start,int end){
        TranslateAnimation animation=new TranslateAnimation
                (Animation.ABSOLUTE, start,
                        Animation.ABSOLUTE, end,
                        Animation.ABSOLUTE, 0,
                        Animation.ABSOLUTE, 0);
        animation.setDuration(300);
        animation.setInterpolator(new AccelerateInterpolator());
        animation.setFillAfter(true);
        return animation;

    }
}
