package com.aabao.adnroid;

import android.os.Bundle;
import android.widget.TextView;

import com.aabao.adnroid.base.BaseActivity;

/**
 * Created by Verse Part on 2017/1/18.
 * email: versepartwang@163.com
 */
public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(getApplicationContext());
        textView.setText("这是一个AA软件");
        setContentView(textView);
    }
}
