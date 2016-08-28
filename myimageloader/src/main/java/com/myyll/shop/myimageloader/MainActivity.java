package com.myyll.shop.myimageloader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.myyll.shop.myimageloader.adapter.MyAdapter;
import com.myyll.shop.myimageloader.constant.Constants;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MainActivity extends AppCompatActivity {
    private ListView lv;
    private MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //创建默认的ImageLoader配置参数
//        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
//                .createDefault(this);

        //Initialize ImageLoader with configuration.
//        ImageLoader.getInstance().init(configuration);
        initView();
        new MyAsycTask(this,lv).execute(Constants.url);
    }

    private void initView() {
        lv = (ListView) findViewById(R.id.lv);
    }
}
