package com.aabao.adnroid;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.aabao.adnroid.aaplan.AAPlan;
import com.aabao.adnroid.aaplan.bean.NameBean;
import com.aabao.adnroid.adapter.AccountAdapter;
import com.aabao.adnroid.adapter.CaculateAdapter;
import com.aabao.adnroid.base.BaseActivity;
import com.aabao.adnroid.db.AADataBaseManager;
import com.aabao.adnroid.db.BillDataBaseHelper;
import com.aabao.adnroid.widget.AccountDecoration;
import com.aabao.adnroid.widget.TopBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Verse Part on 2017/2/12.
 * email: versepartwang@163.com
 */
public class CaculateMoneyActivity extends BaseActivity {
    private static final String TAG = "CaculateMoneyActivity";
    @Bind(R.id.topbar)
    TopBar mTopbar;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private List<NameBean> mList;
    private List<NameBean> mCompleteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caculate_money);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        Intent intent = getIntent();
        mList = (List<NameBean>)intent.getSerializableExtra("list");
        Log.i(TAG, "   mList.size = "+mList.size());
        AAPlan plan = new AAPlan();
        mCompleteList = plan.compareAccount(mList);
        for (NameBean bean : mCompleteList){
            Log.i(TAG, " init   bean = "+bean.toString());
        }
        mTopbar.mTitleText.setText(R.string.caculate_complete);
        mTopbar.mRightImageButton.setVisibility(View.INVISIBLE);
        mTopbar.mLeftImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new CaculateAdapter(getApplicationContext(), mCompleteList));
        mRecyclerView.addItemDecoration(new AccountDecoration(this, LinearLayoutManager.VERTICAL));
    }
}
