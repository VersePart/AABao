package com.aabao.adnroid.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aabao.adnroid.R;

/**
 * Created by Verse Part on 2017/1/8.
 * email: versepartwang@163.com
 */

public class TopBar extends RelativeLayout {

    private static final String TAG = "TopBar";
    public ImageView mLeftImageButton;
    public TextView mTitleText;
    public ImageView mRightImageButton;
    public static final int LEFTID = 1;
    public static final int TITLEID = 2;
    public static final int RIGHTID = 3;
    private float alpha = 0.8f;

    public TopBar(Context context) {
        this(context, null);
    }

    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        setBackgroundResource(R.color.topbar_background);

        mLeftImageButton = new ImageView(context);
        mLeftImageButton.setId(LEFTID);
        mLeftImageButton.setImageResource(R.drawable.profile_ic_home_normal);
        RelativeLayout.LayoutParams leftParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT | RelativeLayout.CENTER_VERTICAL);
        leftParams.leftMargin = (int)getResources().getDimension(R.dimen.topbar_margin);
        addView(mLeftImageButton, leftParams);

        mTitleText = new TextView(context);
        mTitleText.setId(TITLEID);
        mTitleText.setTextColor(getResources().getColor(R.color.topbar_title));
        mTitleText.setAlpha(alpha);
        mTitleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.topbar_title_text));
        mTitleText.setText("dddddd");
        RelativeLayout.LayoutParams titleParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        titleParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        addView(mTitleText, titleParams);

        mRightImageButton = new ImageView(context);
        mRightImageButton.setId(RIGHTID);
        mRightImageButton.setImageResource(R.drawable.profile_ic_talk_normal);
        RelativeLayout.LayoutParams rightParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rightParams.addRule(RelativeLayout.CENTER_VERTICAL);

        rightParams.rightMargin = (int)getResources().getDimension(R.dimen.topbar_margin);
        addView(mRightImageButton, rightParams);

    }
}
