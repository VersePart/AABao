package com.aabao.adnroid.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by Verse Part on 2017/1/9.
 * email: versepartwang@163.com
 */

public class BaseFragment extends Fragment {

    public Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
//        initData();

    }
}
