package com.aabao.adnroid;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.aabao.adnroid.aaplan.bean.NameBean;
import com.aabao.adnroid.base.BaseActivity;
import com.aabao.adnroid.db.AADataBaseManager;
import com.aabao.adnroid.db.BillDataBaseHelper;
import com.aabao.adnroid.adapter.AccountAdapter;
import com.aabao.adnroid.widget.AccountDecoration;
import com.aabao.adnroid.widget.TopBar;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Verse Part on 2017/2/9.
 * email: versepartwang@163.com
 */
public class AccountDetailActivity extends BaseActivity {

    private static final String TAG = "AccountDetailActivity";
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.topbar)
    TopBar mTopBar;
    List<NameBean> mList = new ArrayList<NameBean>();
    private boolean mExistBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail);
        ButterKnife.bind(this);
        initDate();
        init();
    }

    private void init() {
        mTopBar.mTitleText.setText(R.string.detail);
        mTopBar.mRightImageButton.setVisibility(View.INVISIBLE);
        mTopBar.mLeftImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new AccountAdapter(getApplicationContext(), mList));
        mRecyclerView.addItemDecoration(new AccountDecoration(this, LinearLayoutManager.VERTICAL));
    }

    @OnClick({R.id.settle_account_money})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.settle_account_money:
                Intent intent = new Intent(this, CaculateMoneyActivity.class);
                intent.putExtra("list", (Serializable)mList);
                startActivity(intent);
                break;
        }
    }

    private void initDate() {
        AADataBaseManager dBManager = new AADataBaseManager(getApplicationContext());
        Cursor cursor = dBManager.query(BillDataBaseHelper.USER_TABLE, "_id desc");
        if (cursor == null){
            return;
        }
        mList.clear();
        while (cursor.moveToNext()){
            int indexLaseAccount = cursor.getColumnIndexOrThrow(BillDataBaseHelper.LASE_ACCOUNT);
            String laseAccount = cursor.getString(indexLaseAccount);
            if (laseAccount != null){
                if (laseAccount.equals(BillDataBaseHelper.ACCOUNT_MARK)){
                    int indexDate = cursor.getColumnIndexOrThrow(BillDataBaseHelper.TIME_DATE);
                    String date = cursor.getString(indexDate);
                    Log.i(TAG, " 最后结账算清的标记位  时间 : "+ date);
                    return;
                }
            }
            mExistBean = false;
            int indexName = cursor.getColumnIndexOrThrow(BillDataBaseHelper.NAME);
            int indexMoney = cursor.getColumnIndexOrThrow(BillDataBaseHelper.MONEY);
            String name = cursor.getString(indexName);
            String strMoney = cursor.getString(indexMoney);
            float money = Float.parseFloat(strMoney);
            NameBean forBean;
            for (int i = 0; i < mList.size(); i++){
                forBean = mList.get(i);
                if (name.equals(forBean.getmName())){
                    float totalMoney = forBean.getmTotalMoney() + money;
                    forBean.setmTotalMoney(totalMoney);
                    mExistBean = true;
                }
            }
            if (!mExistBean){
                int indexDate = cursor.getColumnIndexOrThrow(BillDataBaseHelper.TIME_DATE);
                String date = cursor.getString(indexDate);
                NameBean bean = new NameBean(name);
                bean.setmTotalMoney(money);
                bean.setmDate(date);
                mList.add(bean);
            }
        }
        if (cursor != null){
            cursor.close();
        }
    }
}
