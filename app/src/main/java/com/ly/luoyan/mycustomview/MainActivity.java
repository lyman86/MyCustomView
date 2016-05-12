package com.ly.luoyan.mycustomview;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import com.ly.luoyan.mycustomview.ui.BaseActivity;
import com.ly.luoyan.mycustomview.ui.TitleBar01;
import com.ly.luoyan.mycustomview.ui.TitleBar02;
import com.ly.luoyan.mycustomview.ui.TitleBar03;

public class MainActivity extends BaseActivity {
    private Button btnClick01;
    private Button btnClick02;
    private Button btnClick03;
    @Override
    public void initLayout() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initView() {
        btnClick01 = (Button) findViewById(R.id.click01);
        btnClick02 = (Button) findViewById(R.id.click02);
        btnClick03 = (Button) findViewById(R.id.click03);
    }

    @Override
    public void initListener() {
        btnClick01.setOnClickListener(this);
        btnClick02.setOnClickListener(this);
        btnClick03.setOnClickListener(this);
    }

    @Override
    public void onViewClick(View v) {
        switch (v.getId()){
            case R.id.click01:
                startActivity(new Intent(this, TitleBar01.class));
                break;
            case R.id.click02:
                startActivity(new Intent(this, TitleBar02.class));
                break;
            case R.id.click03:
                startActivity(new Intent(this, TitleBar03.class));
                break;
        }
    }
}
