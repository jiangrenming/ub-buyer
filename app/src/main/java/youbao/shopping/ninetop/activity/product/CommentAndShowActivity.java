package youbao.shopping.ninetop.activity.product;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import youbao.shopping.ninetop.adatper.product.CommentAndShowAdapter;
import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.bean.user.order.EvaluateGoodsBean;
import youbao.shopping.ninetop.common.MyActivityManager;
import youbao.shopping.ninetop.service.impl.OrderService;
import youbao.shopping.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import youbao.shopping.R;

import static youbao.shopping.ninetop.common.IntentExtraKeyConst.ORDER_CODE;
import static youbao.shopping.R.id.tv_common_title_detail;


public class CommentAndShowActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.lv_comment_and_show)
    ListView lvCommentAndShow;
    @BindView(R.id.iv_icon_back)
    ImageView ivIconBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_common_icon)
    ImageView ivCommonIcon;
    @BindView(tv_common_title_detail)
    TextView tvCommonTitleDetail;

    private OrderService service;

    private String mImgPath;

    //private List<File> files1 = new ArrayList<>();
    //private List<File> files2 = new ArrayList<>();
    //private List<File> files3 = new ArrayList<>();
    //private List<File> files4 = new ArrayList<>();
    //private List<File> files5 = new ArrayList<>();

    private String orderCode;
    private List<String> goodsCode = new ArrayList<>();
    private List<String> skuDesc = new ArrayList<>();

    private List<EvaluateGoodsBean> goodsList;

    private CommentAndShowAdapter adapter;

    public static final int PHOTO_WITH_CAMERA = 233;
    public static final int PHOTO_WITH_PHOTO = 222;

    private List files1;
    private List files2;
    private List files3;
    private List files4;
    private List files5;

    @Override
    protected int getLayoutId() {
        return R.layout.comment_and_show;
    }

    @Override
    protected void initView() {
        super.initView();
        ButterKnife.bind(this);
        tvTitle.setText("评价晒单");
        ivCommonIcon.setVisibility(View.GONE);
        tvCommonTitleDetail.setVisibility(View.VISIBLE);
        tvCommonTitleDetail.setText("提交");
        service = new OrderService(this);
    }

    @Override
    protected void initListener() {
        super.initListener();
        ivIconBack.setOnClickListener(this);
        tvCommonTitleDetail.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        orderCode = getIntent().getStringExtra(ORDER_CODE);
        goodsList = (List<EvaluateGoodsBean>) getIntent().getSerializableExtra("evaluateGoodsList");
        if (goodsList != null) {
            for (int i = 0; i < goodsList.size(); i++) {
                goodsCode.add(goodsList.get(i).goodsCode);
                skuDesc.add(goodsList.get(i).skuDesc);
            }
        }

        adapter = new CommentAndShowAdapter(goodsList, CommentAndShowActivity.this);
        lvCommentAndShow.setAdapter(adapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PHOTO_WITH_CAMERA:
                if (resultCode == RESULT_OK) {
                    if (data == null) {

                        adapter.upload2LocalPic();
                    }
                    break;
                }
            case PHOTO_WITH_PHOTO:
                if (data != null) {
                    Uri selectedImage = data.getData();
                    if (selectedImage == null) {
                        return;
                    }
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);

                    if (cursor != null) {
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);
                        mImgPath = picturePath;
                        cursor.close();
                    } else {
                        mImgPath = selectedImage.getPath();
                    }
                    adapter.informMap(new File(mImgPath));
                    Bitmap bitmap = adapter.getBitmap(new File(mImgPath));
                    adapter.addImage(bitmap);
                    break;
                }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_icon_back:
                finish();
                break;
            case R.id.tv_common_title_detail:
                try {

                    HashMap<Integer, List> map = adapter.getMap();
                    if (map != null) {
                        files1 = map.get(1);
                        files2 = map.get(2);
                        files3 = map.get(3);
                        files4 = map.get(4);
                        files5 = map.get(5);
                        System.out.println("file1:::" + files1);
                        System.out.println("files2:::" + files2);
                    }

                    for (int i = 0; i < goodsList.size(); i++) {
                        String[] starsRemarks = adapter.getStarsArray();
                        String star = starsRemarks[i];
                        String s = star == null ? String.valueOf(0) : star;

                        if (Integer.parseInt(s) == 0) {
                            showToast("请评分后再提交!");
                            return;
                        }

                        String[] commentsRemarks = adapter.getCommentsArray();
                        String comment = commentsRemarks[i];
                        if (comment.length() == 0 && "".equals(comment)) {
                            showToast("请评论后再提交");
                            return;
                        }
                    }
                    List<String> stars = adapter.getStars();
                    List<String> comments = adapter.getComments();
                    service.commentAndShow(orderCode, goodsCode, skuDesc, stars, comments, files1, files2, files3, files4, files5, new CommonResultListener<String>(this) {
                        @Override
                        public void successHandle(String result) throws JSONException {
                            MyActivityManager.getInstance().finishAllActivity();
                            showToast("评价提交成功。。。");
                            startActivity(MyOrderActivity.class);
                            finish();
                        }
                    });
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }
}