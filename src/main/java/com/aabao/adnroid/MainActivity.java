package com.aabao.adnroid;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.aabao.adnroid.base.BaseFragment;
import com.aabao.adnroid.fragment.ExpandFragment;
import com.aabao.adnroid.fragment.MPFragment;
import com.aabao.adnroid.fragment.MainFragment;
import com.aabao.adnroid.fragment.SettingFragment;
import com.aabao.adnroid.widget.TopBar;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private static final String TAG = "MainActivity";
    private TopBar mTopbar;
    private List<BaseFragment> mBaseFragmentList;
    private FrameLayout mFrameLayout;
    private RadioGroup mRadioGroup;
    private int mPosition;
    private GoogleApiClient client;
    private Fragment mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initFragment();
    }

    private void initView() {

        mBaseFragmentList = new ArrayList<BaseFragment>();
        if (mBaseFragmentList != null) {
            mBaseFragmentList.clear();
        }
        mBaseFragmentList.add(new MainFragment());
        mBaseFragmentList.add(new MPFragment());
        mBaseFragmentList.add(new ExpandFragment());
        mBaseFragmentList.add(new SettingFragment());

        mTopbar = (TopBar) findViewById(R.id.topbar);
        mTopbar.mLeftImageButton.setVisibility(View.INVISIBLE);
        mTopbar.mRightImageButton.setVisibility(View.INVISIBLE);
        mTopbar.mTitleText.setText(R.string.my_main);
        mFrameLayout = (FrameLayout) findViewById(R.id.frame_layout);
        mRadioGroup = (RadioGroup) findViewById(R.id.radio_group);
        mRadioGroup.check(R.id.button_1);
        mRadioGroup.setOnCheckedChangeListener(new ChangeListener());
    }

    private void initFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.frame_layout, mBaseFragmentList.get(0)).commit();
        mContext = mBaseFragmentList.get(0);//初始化
        TopBarListener(mPosition);
    }

    private class ChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {

                case R.id.button_1:
                    mPosition = 0;
                    mTopbar.mTitleText.setText(R.string.my_main);
                    break;
                case R.id.button_2:
                    mTopbar.mTitleText.setText(R.string.my_mp);
                    mPosition = 1;
                    break;
                case R.id.button_3:
                    mTopbar.mTitleText.setText(R.string.my_expand);
                    mPosition = 2;
                    break;
                case R.id.button_4:
                    mTopbar.mTitleText.setText(R.string.settings);
                    mPosition = 3;
                    break;
                default:
                    mTopbar.mTitleText.setText(R.string.my_main);
                    mPosition = 0;
                    break;
            }

            TopBarListener(mPosition);
            BaseFragment baseFragment = mBaseFragmentList.get(mPosition);
            switchFragment(mContext, baseFragment);
        }
    }

    private void switchFragment(Fragment from, Fragment to) {
        Log.i(TAG, "switchFragment   from = "+(from == null) +"   from != to "+(from != to ? "yes" : "no"));
        if(from != to){
            mContext = to;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //to添加了
            if(to.isAdded()){
                if (from != null){
                    ft.hide(from);
                }
                if (to != null){
                    ft.show(to).commit();
                }
            } else {
                if (from != null){
                    ft.hide(from);
                }
                if (to != null){
                    ft.add(R.id.frame_layout, to).commit();
                }
            }
        }
    }

    private void TopBarListener(int mPosition) {

    }
}
