package com.ly.luoyan.mycustomview.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

/**
 * Created by luoyan on 16/5/11.
 */
public abstract class BaseActivity extends Activity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initLayout();
        initView();
        initListener();
    }

    @Override
    public void onClick(View v) {
        onViewClick(v);
    }

    public abstract void initLayout();
    public abstract void initView();
    public abstract void initListener();
    public abstract void onViewClick(View v);
}
