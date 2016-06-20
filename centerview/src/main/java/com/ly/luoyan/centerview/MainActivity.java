package com.ly.luoyan.centerview;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.ly.luoyan.centerview.fragment.home.FragmentHomePager;
import com.ly.luoyan.centerview.fragment.me.FragmentMe;

public class MainActivity extends FragmentActivity implements View.OnClickListener{
    private LinearLayout layoutHome;
    private LinearLayout layoutMe;
    private FragmentHomePager fragmentHomePager;
    private FragmentMe fragmentMe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        inidDefaultFragment();
        initListener();
    }

    private void inidDefaultFragment() {
        FragmentManager manager = getSupportFragmentManager();
        fragmentHomePager = new FragmentHomePager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.content,fragmentHomePager);
        transaction.commit();
    }

    private void initListener() {
        layoutHome.setOnClickListener(this);
        layoutMe.setOnClickListener(this);
    }

    private void initView() {
        layoutHome = (LinearLayout) findViewById(R.id.layout_home);
        layoutMe = (LinearLayout) findViewById(R.id.layout_me);
    }

    @Override
    public void onClick(View v) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        hideFragment(transaction);
        switch (v.getId()){
            case R.id.layout_home:
                if (fragmentHomePager==null){
                    fragmentHomePager = new FragmentHomePager();
                    transaction.add(R.id.content,fragmentHomePager);
                }
                transaction.show(fragmentHomePager);
                break;
            case R.id.layout_me:
                if (fragmentMe==null){
                    fragmentMe = new FragmentMe();
                    transaction.add(R.id.content,fragmentMe);
                }
                transaction.show(fragmentMe);
                break;
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (fragmentHomePager!=null){
            transaction.hide(fragmentHomePager);
        }
        if (fragmentMe!=null){
            transaction.hide(fragmentMe);
        }
    }

    public void click(View v){
        startActivity(new Intent(this,OtherActivity.class));
    }
}
