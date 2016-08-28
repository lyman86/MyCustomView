package com.myyll.shop.myimageloader.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import com.myyll.shop.myimageloader.R;
import com.myyll.shop.myimageloader.model.NewsModel;
import com.myyll.shop.myimageloader.utils.ImageLoader;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by luoyan on 16/8/27.
 */
public class MyAdapter extends CommonAdapter<NewsModel.DataModel> implements AbsListView.OnScrollListener{

    private Context context;
    private ImageLoader imageLoader;
    private int mStart,mEnd;
    public static String[] URLS;
    private boolean first = true;
    //显示图片的配置
    DisplayImageOptions options = new DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build();

    ImageSize mImageSize = new ImageSize(100, 100);
    public MyAdapter(Context context, int layoutId, List<NewsModel.DataModel> datas, ListView listView) {
        super(context, layoutId, datas);
        imageLoader = new ImageLoader(listView);
        URLS = new String[datas.size()];
        for (int i =0 ;i<datas.size();i++){
            URLS[i] = datas.get(i).picSmall;
        }
        listView.setOnScrollListener(this);
    }

//    @Override
//    protected void convert(ViewHolder holder, NewsModel.DataModel dataModel, int position) {
//        holder.setText(R.id.tv_title,dataModel.name).setText(R.id.tv_content,dataModel.description);
//        final  ImageView img = holder.getView(R.id.iv);
//        img.setTag(dataModel.picSmall);
////        com.nostra13.universalimageloader.core.ImageLoader.getInstance().loadImage(dataModel.picBig,mImageSize,options,new SimpleImageLoadingListener(){
////            @Override
////            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
////                super.onLoadingComplete(imageUri, view, loadedImage);
////                img.setImageBitmap(loadedImage);
////            }
////        });
////        imageLoader.showImageByThread(img,dataModel.picSmall);
//    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState==SCROLL_STATE_IDLE){
            //停止时加载可见项
            imageLoader.loadImage(mStart,mEnd);
        }else{
            imageLoader.cancleAllTask();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mStart = firstVisibleItem;
        mEnd = firstVisibleItem + visibleItemCount;
        if (first &&visibleItemCount>0){
            imageLoader.loadImage(mStart,mEnd);
            first = false;
        }
    }

    @Override
    protected void convert(com.zhy.adapter.abslistview.ViewHolder viewHolder, NewsModel.DataModel item, int position) {
        viewHolder.setText(R.id.tv_title,item.name).setText(R.id.tv_content,item.description);
        final  ImageView img = viewHolder.getView(R.id.iv);
        img.setTag(item.picSmall);
//        com.nostra13.universalimageloader.core.ImageLoader.getInstance().loadImage(dataModel.picBig,mImageSize,options,new SimpleImageLoadingListener(){
//            @Override
//            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                super.onLoadingComplete(imageUri, view, loadedImage);
//                img.setImageBitmap(loadedImage);
//            }
//        });
//        imageLoader.showImageByThread(img,dataModel.picSmall);
        imageLoader.showImgeByAsycTask(img,item.picSmall);
    }
}
