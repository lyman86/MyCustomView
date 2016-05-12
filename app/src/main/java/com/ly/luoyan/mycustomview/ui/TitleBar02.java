package com.ly.luoyan.mycustomview.ui;

import android.view.View;
import android.widget.Toast;

import com.ly.luoyan.mycustomview.R;
import com.ly.luoyan.mycustomview.view.CustomSelectItem;

/**
 * Created by luoyan on 16/5/11.
 */
public class TitleBar02 extends BaseActivity implements CustomSelectItem.OnBarViewClickListener{
    private CustomSelectItem customSelectItem;
    @Override
    public void initLayout() {
        setContentView(R.layout.title_bar_02);
    }

    @Override
    public void initView() {
        customSelectItem = (CustomSelectItem) findViewById(R.id.cus);
    }

    @Override
    public void initListener() {
        customSelectItem.setOnBarViewClickListener(this);
    }

    @Override
    public void onViewClick(View v) {

    }

    @Override
    public void onBarViewClick(View v, int whitch) {
        switch (whitch){
            case CustomSelectItem.LEFT_VIEW:
                Toast.makeText(this,"left",Toast.LENGTH_SHORT).show();
                break;
            case CustomSelectItem.RIGHT_VIEW:
                Toast.makeText(this,"right",Toast.LENGTH_SHORT).show();
                break;
            case CustomSelectItem.CENTER_VIEW:
                Toast.makeText(this,"center",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void rightChange(View v){
        customSelectItem.setRightSideText("商品");
    }
    public void leftChange(View v){
        customSelectItem.setRightSideText("返回");
    }

}
