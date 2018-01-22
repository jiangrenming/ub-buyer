package com.ninetop.activity.index;


import android.widget.ListView;

import com.ninetop.adatper.index.NewUserGiftAdapter;
import com.ninetop.base.BaseActivity;
import com.ninetop.bean.index.gift.UserGiftBean;
import com.ninetop.common.view.HeadView;
import com.ninetop.service.impl.IndexService;
import com.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import java.util.List;

import butterknife.BindView;
import youbao.shopping.R;

/**
 * Created by jill on 2016/11/14.
 */

public class NewUserActivity extends BaseActivity {
    @BindView(R.id.hv_head)
    HeadView hvHead;
    @BindView(R.id.lv_gift)
    ListView lvGift;

    private IndexService indexService;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_user;
    }

    @Override
    protected void initData() {
        indexService = new IndexService(this);

        super.initData();
        hvHead.setTitle("新人专区");

        getGiftList();
    }

    protected void getGiftList(){
        indexService.getNewUserGiftList(new CommonResultListener<List<UserGiftBean>>(this) {
            @Override
            public void successHandle(List<UserGiftBean> result) throws JSONException {
                if(result == null || result.size() == 0){
                    return;
                }

                NewUserGiftAdapter adapter = new NewUserGiftAdapter(NewUserActivity.this, result);
                lvGift.setAdapter(adapter);
            }
        });
    }
}
