package com.ninetop.activity.ub.product;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hykj.dialoglib.MyDialog;
import com.hykj.dialoglib.MyDialogOnClick;
import com.ninetop.UB.product.ProductHotBean;
import com.ninetop.UB.product.UbProductService;
import com.ninetop.base.BaseActivity;
import com.ninetop.common.IntentExtraKeyConst;
import com.ninetop.common.constant.SharedKeyConstant;
import com.ninetop.common.util.MySharedPreference;
import com.ninetop.common.util.Tools;
import com.ninetop.common.view.FlowLayout;
import com.ninetop.common.view.HeadSearchView;
import com.ninetop.common.view.listener.SearchEnterListener;
import com.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import youbao.shopping.R;


/**
 * @date: 2016/11/9
 * @author: Shelton
 * @version: 1.1.3
 * @Description:
 */
public class ProductSearchActivity extends BaseActivity{
    @BindView(R.id.hsv_search)
    HeadSearchView hsvSearch;
    @BindView(R.id.ll_search_tag)
    FlowLayout llSearchTag;
    @BindView(R.id.ll_search_history)
    LinearLayout llSearchHistory;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_search;
    }

    private UbProductService productService;

    @Override
    protected void initView() {
        hsvSearch.setBackImageVisible(View.GONE);

        hsvSearch.setHint("搜搜\"饼干\"试试看");

        productService = new UbProductService(this);
    }

    protected void initData() {
        initHotTag();
        initHistoryTag();
    }

    @Override
    protected void initListener() {
        super.initListener();

        hsvSearch.setOnEnterListener(new SearchEnterListener() {
            @Override
            public void onEnter(String text) {
                searchHandle(text);
            }
        });
    }

    private void initHotTag(){
       productService.getHotSearch(new CommonResultListener<List<ProductHotBean>>(this) {
           @Override
           public void successHandle(List<ProductHotBean> result) throws JSONException {
               llSearchTag.removeAllViews();
               if(result==null||result.size()==0){
                   return;
               }
               for(int i=0;i<result.size();i++){
                   final ProductHotBean bean=result.get(i);
                   View view=LayoutInflater.from(ProductSearchActivity.this).inflate(R.layout.item_search_tag,null);
                   final TextView text=(TextView)view.findViewById(R.id.tv_text);
                   text.setText(bean.name);
                   llSearchTag.addView(view);
                   text.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           searchHandle(bean.name);
                       }
                   });
               }
           }
       });
    }

    private void searchHandle(String key){
        String trimKey = key.trim();
        saveHistoryTag(trimKey);
        initHistoryTag();
        Map<String, String> map = new HashMap<>();
        map.put(IntentExtraKeyConst.SEARCH_KEY, trimKey);
        startActivity(UbProductSearchResultActivity.class, map);
    }

    protected void getServerData(String searchKey){
//        productService.searchByKey(searchKey, "1", "overall", "ASC",
//                new CommonResultListener<ProductPagingBean>(this) {
//                    @Override
//                    public void successHandle(ProductPagingBean result) throws JSONException {
//                        if(result == null || result.dataList == null || result.dataList.size() == 0)
//                            return;
//
//                        llNoData.setVisibility(View.VISIBLE);
//                        llSearchTip.setVisibility(View.GONE);
//                    }
//                }
//        );
    }

    private void initHistoryTag() {
        List<String> historyList = MySharedPreference.getList(SharedKeyConstant.SEARCH_HISTORY, null, this);

        llSearchHistory.removeAllViews();
        if (historyList == null || historyList.size() == 0)
            return;

        for (final String str : historyList) {
            TextView textView = new TextView(this);
            textView.setText(str);
            textView.setPadding(0, Tools.dip2px(this, 20), Tools.dip2px(this, 50), 0);
            textView.setTextColor(Tools.getColorValueByResId(this, R.color.text_black));
            textView.setTextSize(14);

            llSearchHistory.addView(textView);

            textView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    searchHandle(str);
                }
            });
        }
    }

    private void saveHistoryTag(String searchText){
        if(searchText.length() == 0)
            return;

        List<String> list = MySharedPreference.getList(SharedKeyConstant.SEARCH_HISTORY, null, this);
        if(list == null){
            list = new ArrayList<>();
            list.add(searchText);
        }else{
            for(String key : list){
                if(key.equals(searchText)){
                    list.remove(key);
                    break;
                }
            }
            list.add(0, searchText);

            while(list.size() > 5){
                list.remove(5);
            }
        }
        MySharedPreference.saveList(SharedKeyConstant.SEARCH_HISTORY, list, this);
    }

    private void clearHistory(){
        new MyDialog(this, MyDialog.DIALOG_TWOOPTION, "提示", "您确定要清空搜索历史吗？", new MyDialogOnClick() {
            public void sureOnClick(View v) {
                MySharedPreference.saveList(SharedKeyConstant.SEARCH_HISTORY, null, ProductSearchActivity.this);
                initHistoryTag();
            }
            public void cancelOnClick(View v) {}
        }).show();
    }

    @OnClick({R.id.iv_history_trash})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_history_trash:
                clearHistory();
                break;
        }
    }

}