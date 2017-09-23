package com.wuzp.newspace.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.wuzp.newspace.base.BaseFragment;

import java.util.List;

/**
 * Created by wuzp on 2017/9/23.
 * viewpager 中的adapter
 */
public class MainPageAdapter extends FragmentStatePagerAdapter {

    private List<BaseFragment> mData;
    private String[] mTitles;

    public MainPageAdapter(FragmentManager manager, List<BaseFragment> data,String[] titles){
        super(manager);
        this.mData = data;
        this.mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
