package com.aabao.adnroid.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aabao.adnroid.base.BaseFragment;

/**
 * Created by Verse Part on 2017/1/9.
 * email: versepartwang@163.com
 */

public class MainFragment extends BaseFragment {


    private static final String TAG = "MainFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView view = new TextView(mContext);
        view.setText("MainFragment");
        return view;
    }
}
