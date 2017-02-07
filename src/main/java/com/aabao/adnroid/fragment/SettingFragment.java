package com.aabao.adnroid.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aabao.adnroid.AboutActivity;
import com.aabao.adnroid.LoginActivity;
import com.aabao.adnroid.R;
import com.aabao.adnroid.SettingActivity;
import com.aabao.adnroid.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Verse Part on 2017/1/9.
 * email: versepartwang@163.com
 */

public class SettingFragment extends BaseFragment {


    private static final String TAG = "SettingFragment";
    @Bind(R.id.layout_login_in)
    RelativeLayout mSettingsLoginIn;
    @Bind(R.id.layout_about)
    RelativeLayout mSettingsEdit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.layout_login_in, R.id.layout_about})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.layout_login_in:
                Log.i(TAG, "  OnClick   layout_login_in");
                fragmentStartActivity(LoginActivity.class);
                break;
            case R.id.layout_about:
                Log.i(TAG, "  OnClick   layout_about");
                fragmentStartActivity(AboutActivity.class);
                break;
        }
    }

    private void fragmentStartActivity(Class<?> clazz){
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
    }
}
