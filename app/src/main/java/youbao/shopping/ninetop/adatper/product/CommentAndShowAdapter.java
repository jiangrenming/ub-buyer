package youbao.shopping.ninetop.adatper.product;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import youbao.shopping.ninetop.base.DefaultBaseAdapter;
import youbao.shopping.ninetop.bean.user.order.EvaluateGoodsBean;
import youbao.shopping.ninetop.common.util.ImageSaveUtil;
import youbao.shopping.ninetop.common.util.PictureUtils;
import youbao.shopping.ninetop.common.util.Tools;
import youbao.shopping.ninetop.common.view.EvaluateView;
import youbao.shopping.ninetop.common.view.listener.EvaluateAddClickListener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import youbao.shopping.R;

/**
 * @date: 2016/12/27
 * @author: Shelton
 * @version: 1.1.3
 * @Description:
 */
public class CommentAndShowAdapter extends DefaultBaseAdapter {
    private final List<EvaluateGoodsBean> goodsList;
    private Activity context;

    private List<String> stars = new ArrayList<>();
    private List<String> comments = new ArrayList<>();

    private String starsRemarks[];
    private String commentsRemarks[];

    private List<File> files1 = new ArrayList<>();
    private List<File> files2 = new ArrayList<>();
    private List<File> files3 = new ArrayList<>();
    private List<File> files4 = new ArrayList<>();
    private List<File> files5 = new ArrayList<>();

    public EvaluateView evaluateView = null;
    //private EvaluateView evaluateRemoveView = null;

    private List<ViewHolder> viewHolderList = new ArrayList<>();
    private int clickPosition = -1;
    private HashMap params;
    private File compressFile;

    public CommentAndShowAdapter(List<EvaluateGoodsBean> goodsList, Context ctx) {
        super(goodsList, ctx);

        this.goodsList = goodsList;
        this.context = (Activity) ctx;
        if (goodsList != null) {
            starsRemarks = new String[goodsList.size()];
            commentsRemarks = new String[goodsList.size()];
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_comment_and_show, null);

            holder = new ViewHolder(convertView, position);
            viewHolderList.add(holder);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setData();
        commentsRemarks[position] = "";

        return convertView;
    }

    private void initRatingBar(ViewHolder holder, final int position) {
        final List<ImageView> viewList = new ArrayList<>();

        viewList.add(holder.iv_star1);
        viewList.add(holder.iv_star2);
        viewList.add(holder.iv_star3);
        viewList.add(holder.iv_star4);
        viewList.add(holder.iv_star5);
        // 添加默认的评分
        for (int i = 0; i < viewList.size(); i++) {
            final int index = i;
            View view = viewList.get(i);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int j = 0; j < viewList.size(); j++) {
                        ImageView oneView = viewList.get(j);
                        if (j <= index) {
                            oneView.setImageResource(R.mipmap.start_select2);
                        } else {
                            oneView.setImageResource(R.mipmap.start_unselect2);
                        }
                        starsRemarks[position] = index + 1 + "";
                        stars.add(index + 1 + "");
                    }
                }
            });
        }
    }

    public List<String> getComments() {
        return comments;
    }

    public String[] getCommentsArray() {
        return commentsRemarks;
    }

    public List<String> getStars() {
        return stars;
    }

    public String[] getStarsArray() {
        return starsRemarks;
    }

    public void upload2LocalPic() {
        if (evaluateView == null) {
            return;
        }
        File imageFile = evaluateView.getFilePath();
        informMap(imageFile);
        Bitmap bitmap = getBitmap(imageFile);
        addImage(bitmap);
    }

    public void informMap(File imageFile) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(imageFile);
        intent.setData(uri);
        context.sendBroadcast(intent);
    }

    public Bitmap getBitmap(File imageFile) {
        //Bitmap bitmap = ImageSaveUtil.ratio(imageFile.toString(), Tools.dip2px(context, 50), Tools.dip2px(context, 50));
        //Bitmap bitmap = PictureUtils.getBitmapFromFile(imageFile.toString(),Tools.dip2px(context, 50), Tools.dip2px(context, 50));
        Bitmap bitmap = PictureUtils.compressBySize(imageFile.toString(), Tools.dip2px(context, 50), Tools.dip2px(context, 50));
        //Bitmap compressImage = PictureUtils.compressImage(bitmap);
        String compressFilePath = ImageSaveUtil.saveImageToGallery(context, bitmap);

        compressFile = new File(compressFilePath);
        bitmap = Bitmap.createScaledBitmap(bitmap, 50, 50, true);
        return bitmap;
    }

    public void addImage(Bitmap bitmap) {
        evaluateView.setImageBitmap(bitmap);
        evaluateView.setIvAddImageClickable(false);
        evaluateView.setRemoveImageVisible(true);
        evaluateView.setIvRemoveImageClickable(true);
        //fileList.clear();
        switch (clickPosition) {
            case 0:
                files1.clear();
                //files1 = fileList;
                files1.add(compressFile);
                break;
            case 1:
                files2.clear();
                files2.add(compressFile);
                break;
            case 2:
                files3.clear();
                files3.add(compressFile);
                break;
            case 3:
                files4.clear();
                files4.add(compressFile);
                break;
            case 4:
                files5.clear();
                files5.add(compressFile);
                break;
        }

        System.out.println("Adapter:file1:::" + files1);
        System.out.println("Adapter:files2:::" + files2);
        params = new HashMap();
        params.put(1, files1);
        params.put(2, files2);
        params.put(3, files3);
        params.put(4, files4);
        params.put(5, files5);

        System.out.println("Adapter:params" + params);
    }

    public HashMap<Integer, List> getMap() {

        return params;
    }

    class ViewHolder {
        private final int position;
        public ImageView iv_product_picture;
        public ImageView iv_star1;
        public ImageView iv_star2;
        public ImageView iv_star3;
        public ImageView iv_star4;
        public ImageView iv_star5;
        public EditText et_comment;
        public EvaluateView evaluate_view;
        public EvaluateView evaluate_view2;
        public EvaluateView evaluate_view3;
        public EvaluateView evaluate_view4;
        public EvaluateView evaluate_view5;

        public ViewHolder(View convertView, int position) {
            this.position = position;
            this.iv_product_picture = (ImageView) convertView.findViewById(R.id.iv_product_picture);
            this.iv_star1 = (ImageView) convertView.findViewById(R.id.iv_star1);
            this.iv_star2 = (ImageView) convertView.findViewById(R.id.iv_star2);
            this.iv_star3 = (ImageView) convertView.findViewById(R.id.iv_star3);
            this.iv_star4 = (ImageView) convertView.findViewById(R.id.iv_star4);
            this.iv_star5 = (ImageView) convertView.findViewById(R.id.iv_star5);
            this.et_comment = (EditText) convertView.findViewById(R.id.et_comment);
            evaluate_view = (EvaluateView) convertView.findViewById(R.id.evaluate_view);
            evaluate_view2 = (EvaluateView) convertView.findViewById(R.id.evaluate_view2);
            evaluate_view3 = (EvaluateView) convertView.findViewById(R.id.evaluate_view3);
            evaluate_view4 = (EvaluateView) convertView.findViewById(R.id.evaluate_view4);
            evaluate_view5 = (EvaluateView) convertView.findViewById(R.id.evaluate_view5);
        }

        public void setData() {
            et_comment.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    commentsRemarks[position] = ("null".equals(et_comment.getText().toString().trim()) ? "" : et_comment.getText().toString().trim());
                    comments.add("null".equals(et_comment.getText().toString().trim()) ? "" : et_comment.getText().toString().trim());
                }
            });

            Tools.ImageLoaderShow(context, goodsList.get(position).icon, iv_product_picture);

            initRatingBar(this, position);
            final EvaluateView[] evaluateViewArray = new EvaluateView[]{evaluate_view, evaluate_view2, evaluate_view3, evaluate_view4, evaluate_view5};

            for (int i = 0; i < evaluateViewArray.length; i++) {
                EvaluateView view = evaluateViewArray[i];
                final int k = i;

                view.setAddImageListener(new EvaluateAddClickListener() {
                    @Override
                    public void onClick(EvaluateView view) {
                        evaluateView = view;
                        clickPosition = k;
                        System.out.println("clickPostion:::" + clickPosition);
                    }
                });
            }
        }
    }


}





