package com.aabao.adnroid;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.aabao.adnroid.base.BaseActivity;
import com.aabao.adnroid.datapicker.JudgeDate;
import com.aabao.adnroid.datapicker.ScreenInfo;
import com.aabao.adnroid.datapicker.WheelMain;
import com.aabao.adnroid.widget.TopBar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Verse Part on 2017/1/29.
 * email: versepartwang@163.com
 */
public class NoteActivity extends BaseActivity {

    private final String TAG = "NoteActivity";
    private int[] mClassify = new int[]{ R.string.property, R.string.food_drink, R.string.go_go_lang};
    private int[] mClassifyItemO = new int[]{ R.string.rent, R.string.electric_charge, R.string.net_lint, R.string.daily_use};
    private int[] mClassifyItemT = new int[]{ R.string.one_day_three, R.string.fruit_so_on};
    private int[] mClassifyItemthree = new int[]{ R.string.sing_song, R.string.go_eat, R.string.other};
    @Bind(R.id.pay_text)
    FrameLayout mPayText;
    @Bind(R.id.income_text)
    FrameLayout mIncomeText;
    @Bind(R.id.topbar)
    TopBar mTopBar;
    @Bind(R.id.num_money)
    EditText mNumMoney;
    @Bind(R.id.date_time)
    TextView mDateTime;
    @Bind(R.id.note_layout)
    LinearLayout mNoteLayout;
    @Bind(R.id.farther_format)
    TextView mFartherFormat;
    @Bind(R.id.child_format)
    TextView mChildFormat;
    WheelMain wheelMain;
    private String txttime;
    private PopupWindow mPopupWindow;
    private String verTime;
    private ListView mListView;
    private int mPosition;
    private int mChildPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mTopBar.mTitleText.setText(R.string.note_spend);
        mTopBar.mLeftImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mPayText.setBackgroundResource(R.color.fragment_context_pass);
        mIncomeText.setBackgroundResource(R.color.topbar_background);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        txttime = formatter.format(curDate);
        mDateTime.setText(txttime);
        mNumMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String temp = s.toString();
                Log.i(TAG, " afterTextChanged s = "+s);
                if (temp.equals("0")){
                    s.delete(0, 1);
                }
                int posDot = temp.indexOf(".");
                if (posDot <= 0) return;
                if (temp.length() - posDot - 1 > 2)
                {
                    s.delete(posDot + 3, posDot + 4);
                }
            }
        });
    }

    @OnClick({R.id.pay_text, R.id.income_text, R.id.date_layout , R.id.farther_format, R.id.child_format, R.id.storage_text})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.pay_text:
                mPayText.setBackgroundResource(R.color.fragment_context_pass);
                mIncomeText.setBackgroundResource(R.color.topbar_background);
                break;
            case R.id.income_text:
                mIncomeText.setBackgroundResource(R.color.fragment_context_pass);
                mPayText.setBackgroundResource(R.color.topbar_background);
                break;
            case R.id.date_layout:
                setDate(view);
                break;
            case R.id.farther_format:
                fartherInitPop();
                break;
            case R.id.child_format:
                childInitPop();
                break;
            case R.id.storage_text:
                //保存数据
                String money = mNumMoney.getText().toString();
                if (money.equals("")){
                    Toast.makeText(getApplicationContext(), getString(R.string.import_money), Toast.LENGTH_SHORT).show();
                    return;
                }
                String fartherClass = getResources().getString(mClassify[mPosition]);
                String childClass;
                if (mPosition == 0){
                    childClass = getResources().getString(mClassifyItemO[mChildPosition]);
                } else if (mPosition == 1) {
                    childClass = getResources().getString(mClassifyItemT[mChildPosition]);
                } else {
                    childClass = getResources().getString(mClassifyItemthree[mChildPosition]);
                }
                String timeDate = mDateTime.getText().toString();
                Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void childInitPop() {
        View contentView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.note_far_popup, null);
        ListView mChildListView = (ListView)contentView.findViewById(R.id.list_view);
        mChildListView.setAdapter(new MyListAdapter(mPosition));
        final PopupWindow mFarPop = new PopupWindow(contentView, 200, ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        mFarPop.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(getResources().getColor(
                R.color.white));
        mFarPop.setBackgroundDrawable(dw);
        mFarPop.setInputMethodMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mFarPop.setOutsideTouchable(true);
        mFarPop.setFocusable(true);
        mFarPop.showAtLocation(mNoteLayout, Gravity.CENTER, 0, 0);
        mChildListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mPosition == 0){
                    mChildFormat.setText(getResources().getString(mClassifyItemO[position]));
                } else if (mPosition == 1){
                    mChildFormat.setText(getResources().getString(mClassifyItemT[position]));
                } else {
                    mChildFormat.setText(getResources().getString(mClassifyItemthree[position]));
                }
                mChildPosition = position;
                mFarPop.dismiss();
            }
        });
    }

    private void fartherInitPop() {
        View contentView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.note_far_popup, null);
        mListView = (ListView)contentView.findViewById(R.id.list_view);
        mListView.setAdapter(new MyListAdapter());
        final PopupWindow mFarPop = new PopupWindow(contentView, 200, ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        mFarPop.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(getResources().getColor(
                R.color.white));
        mFarPop.setBackgroundDrawable(dw);
        mFarPop.setInputMethodMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mFarPop.setOutsideTouchable(true);
        mFarPop.setFocusable(true);
        mFarPop.showAtLocation(mNoteLayout, Gravity.CENTER, 0, 0);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPosition = position;
                int farIten = mClassify[position];
                mFartherFormat.setText(getResources().getString(farIten));
                if (position == 0){
                    mChildFormat.setText(getResources().getString(mClassifyItemO[0]));
                } else if (position == 1){
                    mChildFormat.setText(getResources().getString(mClassifyItemT[0]));
                } else {
                    mChildFormat.setText(getResources().getString(mClassifyItemthree[0]));
                }
                mFarPop.dismiss();
            }
        });
    }

    private void setDate(View view) {
        LayoutInflater inflater = LayoutInflater.from(NoteActivity.this);
        final View timepickerview = inflater.inflate(R.layout.timepicker, null);
        ScreenInfo screenInfo = new ScreenInfo(NoteActivity.this);
        wheelMain = new WheelMain(timepickerview);
        wheelMain.screenheight = screenInfo.getHeight();
        String time = txttime;
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (JudgeDate.isDate(time, "yyyy-MM-dd")) {
            try {
                calendar.setTime(dateFormat.parse(time));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        wheelMain.initDateTimePicker(year, month, day);

        showPopupWindow(view, timepickerview);
    }

    /**
     * 初始化popupWindow窗口
     */
    private void showPopupWindow(View view, View popView) {

        LinearLayout popLayout = (LinearLayout) LayoutInflater.from(this)
                .inflate(R.layout.pop_window, null);

        LinearLayout ll_pop = (LinearLayout) popLayout
                .findViewById(R.id.ll_pop);

        TextView tv_cancel = (TextView) popLayout.findViewById(R.id.tv_cancel);
        TextView tv_ok = (TextView) popLayout.findViewById(R.id.tv_ok);

        ll_pop.addView(popView);

        mPopupWindow = new PopupWindow(popLayout,
                WindowManager.LayoutParams.MATCH_PARENT, 420);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        mPopupWindow.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(getResources().getColor(
                R.color.white));
        mPopupWindow.setBackgroundDrawable(dw);
        mPopupWindow.setInputMethodMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        // 设置popWindow的显示和消失动画
        mPopupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        mPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);

        tv_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                verTime = wheelMain.getTime();
                mDateTime.setText(verTime);
                mPopupWindow.dismiss();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });

    }

    private class MyListAdapter extends BaseAdapter {

        private LayoutInflater inflater;
        private int curPosition;

        private MyListAdapter(){
            inflater = LayoutInflater.from(NoteActivity.this);
            this.curPosition = 4;
        }

        private MyListAdapter(int position){
            inflater = LayoutInflater.from(NoteActivity.this);
            this.curPosition = position;
        }

        @Override
        public int getCount() {
            if (curPosition == 0){
                return mClassifyItemO.length;
            } else if (curPosition == 1){
                return curPosition == 1 ? mClassifyItemT.length : mClassifyItemthree.length;
            }
            return mClassify.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, android.view.View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null){
                convertView = inflater.inflate(R.layout.pop_item, null);
                holder = new ViewHolder();
                holder.mTextView = (TextView)convertView.findViewById(R.id.text_view);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (curPosition == 0){
                holder.mTextView.setText(mClassifyItemO[position]);
            } else if (curPosition == 1){
                holder.mTextView.setText(mClassifyItemT[position]);
            } else if (curPosition == 2){
                holder.mTextView.setText(mClassifyItemthree[position]);
            } else{
                holder.mTextView.setText(mClassify[position]);
            }
            return convertView;
        }
    }

    private static class ViewHolder{
        TextView mTextView;
    }
}
