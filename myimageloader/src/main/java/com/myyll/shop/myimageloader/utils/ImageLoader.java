package com.myyll.shop.myimageloader.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;

import com.myyll.shop.myimageloader.R;
import com.myyll.shop.myimageloader.adapter.MyAdapter;
import com.myyll.shop.myimageloader.utils.http.HttpUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by luoyan on 16/8/27.
 */
public class ImageLoader {
    private ImageView imageView;
    private String url;
    private ListView mListView;
    private Set<ImageAsycTask>tasks;
    //创建缓存
    private LruCache<String,Bitmap>mCaches;
    public ImageLoader(ListView listView){
        this.mListView = listView;
        tasks = new HashSet<>();
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory/4;
        mCaches = new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //在每次存入缓存的时候调用
                return value.getByteCount();
            }
        };
    }
    public void showImgeByAsycTask(ImageView img,String url){
        Bitmap bitmap = getBitmapFromCache(url);
        if (bitmap==null){
            img.setImageResource(R.mipmap.default_cover_picture_m);
        }else{
            img.setImageBitmap(bitmap);
        }
    }

    public void cancleAllTask() {
        if (tasks!=null){
            for (ImageAsycTask task:tasks){
                task.cancel(false);
            }
        }
    }

    class ImageAsycTask extends AsyncTask<String,Void,Bitmap>{
        private String url;
        public ImageAsycTask(String url) {
            this.url = url;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            Bitmap bitmap = getBitmapFromUrl(url);
            if (bitmap!=null){
                addBitmapToCache(url,bitmap);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView = (ImageView) mListView.findViewWithTag(url);
            if (bitmap!=null&&imageView!=null){
                imageView.setImageBitmap(bitmap);
            }
            tasks.remove(this);
        }
    }

    /**
     *  将bitmap加入到缓存
     * @param url
     * @param bitmap
     */
    public void addBitmapToCache(String url,Bitmap bitmap){
        if (getBitmapFromCache(url)==null){
            mCaches.put(url,bitmap);
        }
    }

    /**
     * 从缓存中取出bitmap
     * @param url
     * @return
     */
    public Bitmap getBitmapFromCache(String url){
        return mCaches.get(url);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (imageView.getTag().equals(url)){
                imageView.setImageBitmap((Bitmap) msg.obj);
            }

        }
    };
    public void showImageByThread(ImageView img, final String url){
        imageView = img;
        this.url = url;
        new Thread(){
            @Override
            public void run() {
                super.run();
                Bitmap bitmap = getBitmapFromCache(url);
                Message message = Message.obtain();
                if (bitmap==null){
                    bitmap = getBitmapFromUrl(url);
                    addBitmapToCache(url,bitmap);
                }
                message.obj = bitmap;
                handler.sendMessage(message);
            }
        }.start();
    }

    public Bitmap getBitmapFromUrl(String urlString){
        Bitmap bitmap = null;
        InputStream inputStream = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection  = (HttpURLConnection) url.openConnection();
            inputStream = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
            connection.disconnect();
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void loadImage(int start,int end){
        for (int i = start;i<end;i++){
            final String url = MyAdapter.URLS[i];

            Bitmap bitmap = getBitmapFromCache(url);
            if (bitmap==null){
                ImageAsycTask task = new ImageAsycTask(url);
                task.execute(url);
                tasks.add(task);
            }else{
                ImageView imageView = (ImageView) mListView.findViewWithTag(url);
                imageView.setImageBitmap(bitmap);
            }
        }
    }

//    public  Bitmap decodeSampledBitmapFromServe(InputStream inputStream, int resId,
//                                                         int reqWidth, int reqHeight) {
//        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
//        final BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.de
//        BitmapFactory.decodeResource(res, resId, options);
//        // 调用上面定义的方法计算inSampleSize值
//        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
//        // 使用获取到的inSampleSize值再次解析图片
//        options.inJustDecodeBounds = false;
//        return BitmapFactory.decodeResource(res, resId, options);
//    }

    public int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }
}
