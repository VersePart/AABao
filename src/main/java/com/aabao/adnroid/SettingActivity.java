package com.aabao.adnroid;

import android.os.Bundle;
import android.widget.TextView;

import com.aabao.adnroid.base.BaseActivity;

import butterknife.ButterKnife;

/**
 * Created by Verse Part on 2017/1/18.
 * email: versepartwang@163.com
 */
public class SettingActivity extends BaseActivity {

    private static final String TAG = "SettingActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
    }
}
