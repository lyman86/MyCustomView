package com.myyll.shop.myimageloader;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.myyll.shop.myimageloader.adapter.MyAdapter;
import com.myyll.shop.myimageloader.utils.http.HttpUtils;
import com.myyll.shop.myimageloader.model.NewsModel;

/**
 * Created by luoyan on 16/8/27.
 */
public class MyAsycTask extends AsyncTask<String,Void,NewsModel> {
    private Context context;
    private ListView listView;
    public MyAsycTask(Context context, ListView listView) {
        this.context = context;
        this.listView = listView;
    }

    @Override
    protected NewsModel doInBackground(String... params) {
        return HttpUtils.getNewsJsonData(params[0]);
    }

    @Override
    protected void onPostExecute(NewsModel newsModel) {
        super.onPostExecute(newsModel);
        MyAdapter adapter = new MyAdapter(context,R.layout.item,newsModel.data,listView);
        listView.setAdapter(adapter);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setAdapter(adapter);
    }
}
