package com.aabao.adnroid.fragment.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Verse Part on 2017/1/12.
 * email: versepartwang@163.com
 */

public class MainListView extends ListView {
    public MainListView(Context context) {
        super(context);
    }

    public MainListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MainListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
