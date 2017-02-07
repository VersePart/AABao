package com.aabao.adnroid;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.aabao.adnroid.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Verse Part on 2017/1/18.
 * email: versepartwang@163.com
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";

    @Bind(R.id.reg_user)
    EditText mRegUser;
    @Bind(R.id.reg_pwd)
    EditText mRegPwd;
    @Bind(R.id.bt_username_clear)
    Button mBtUsernameClear;
    @Bind(R.id.bt_password_clear)
    Button mBtPasswordClear;
    @Bind(R.id.sure_btn)
    Button mSureBtn;
    @Bind(R.id.image_lock_pwd)
    ImageView mImageLockPwd;
    @Bind(R.id.layout_third_party)
    FrameLayout mlayoutThirdParty;
    
    private boolean isLockState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mImageLockPwd.setOnClickListener(this);
        mlayoutThirdParty.setOnClickListener(this);
        mRegUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                String text = mRegUser.getText().toString();
//                Log.i(TAG, "beforeTextChanged  s = "+s+"  start = "+start+"  count = "+count+" after = "+after+"  text = "+text);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                String text = mRegUser.getText().toString();
//                Log.i(TAG, "onTextChanged  s = "+s+"  start = "+start+"  count = "+count+" text = "+text);
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = mRegUser.getText().toString();
                if (text.isEmpty()){
                    mBtUsernameClear.setVisibility(View.INVISIBLE);
                } else {
                    mBtUsernameClear.setVisibility(View.VISIBLE);
                }
//                Log.i(TAG, "afterTextChanged  s = "+s+"  text = "+text);

            }
        });

        mRegPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                String text = mRegUser.getText().toString();
//                Log.i(TAG, "beforeTextChanged  s = "+s+"  start = "+start+"  count = "+count+" after = "+after+"  text = "+text);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                String text = mRegUser.getText().toString();
//                Log.i(TAG, "onTextChanged  s = "+s+"  start = "+start+"  count = "+count+" text = "+text);
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = mRegUser.getText().toString();
                if (text.isEmpty()){
                    mBtPasswordClear.setVisibility(View.INVISIBLE);
                } else {
                    mBtPasswordClear.setVisibility(View.VISIBLE);
                }
//                Log.i(TAG, "afterTextChanged  s = "+s+"  text = "+text);

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_lock_pwd:
                if (isLockState){
                    mRegPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mImageLockPwd.setBackgroundResource(R.drawable.log_in_privacy_on);
                    mRegPwd.setSelection(mRegPwd.length());
                    isLockState = false;
                } else {
                    mRegPwd.setInputType(129);
                    mImageLockPwd.setBackgroundResource(R.drawable.log_in_icon_privacy_off);
                    mRegPwd.setSelection(mRegPwd.length());
                    isLockState = true;
                }
            case R.id.layout_third_party:
                thirdPartyPopWin();
        }
    }

    private void thirdPartyPopWin() {

    }
}
