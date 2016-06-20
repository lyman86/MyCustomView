package com.ly.luoyan.centerview.fragment.home;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ly.luoyan.centerview.R;
import com.ly.luoyan.centerview.common.MyPagerAdapter;
import com.ly.luoyan.centerview.fragment.home.discover.FragmentHomePagerDiscover;
import com.ly.luoyan.centerview.fragment.home.follow.FragmentHomePagerFollow;
import com.ly.luoyan.centerview.fragment.home.hot.FragmentHomePagerHot;
import com.ly.luoyan.centerview.view.ViewPagerIndecator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by luoyan on 16/6/5.
 */
public class FragmentHomePager extends Fragment {

    private ViewPagerIndecator viewPagerIndecator;
    private ViewPager viewPager;
    private List<String> mTitles = Arrays.asList("热门","关注","发现");
    private List<Fragment>fragments = new ArrayList<>();
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_pager,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.rootView = view;
        initView();
        initData();
    }

    private void initData() {
        fragments.add(new FragmentHomePagerHot());
        fragments.add(new FragmentHomePagerFollow());
        fragments.add(new FragmentHomePagerDiscover());
        viewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager(),fragments));
    }

    private void initView() {
        viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);
        viewPagerIndecator = (ViewPagerIndecator) rootView.findViewById(R.id.indecator);
        viewPagerIndecator.setIndecatorColor(Color.YELLOW);
        viewPagerIndecator.setTabItemTitles(mTitles,0);
        viewPagerIndecator.setViewPageChange(viewPager);
    }

}
