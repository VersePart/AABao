package com.aabao.adnroid.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aabao.adnroid.AccountDetailActivity;
import com.aabao.adnroid.R;
import com.aabao.adnroid.aaplan.AAPlan;
import com.aabao.adnroid.aaplan.bean.NameBean;
import com.aabao.adnroid.base.BaseFragment;
import com.aabao.adnroid.db.AADataBaseManager;
import com.aabao.adnroid.db.BillDataBaseHelper;
import com.aabao.adnroid.widget.TopBar;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.github.mikephil.charting.components.Legend.LegendPosition.LEFT_OF_CHART_INSIDE;

/**
 * Created by Verse Part on 2017/1/9.
 * email: versepartwang@163.com
 */

public class MPFragment extends BaseFragment implements SeekBar.OnSeekBarChangeListener,
        OnChartValueSelectedListener, View.OnClickListener {


    private static final String TAG = "MPFragment";
    protected String[] mMonths = new String[] {
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec"
    };

    protected String[] mParties = new String[] {
            "Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
            "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P",
            "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
            "Party Y", "Party Z"
    };

    private PieChart mChart;
    private SeekBar mSeekBarX, mSeekBarY;
    private TextView tvX, tvY;
    private AADataBaseManager mDBManager;
    List<NameBean> mNameBeanList = new ArrayList<NameBean>();
    private boolean mExistBean;
    private TextView mClean;
    private TextView mInset;
    private TextView mDetail;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mp, null);
        init(view);
        initDate();
        return view;
    }

    private void initDate() {
        AADataBaseManager dBManager = new AADataBaseManager(getActivity());
        Cursor cursor = dBManager.query(BillDataBaseHelper.USER_TABLE, "_id desc");
        if (cursor == null){
            return;
        }
        mNameBeanList.clear();
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
            initAABean(cursor);
        }
        cursor.close();
        setData();
    }


    private void init(View view) {
        tvX = (TextView) view.findViewById(R.id.tvXMax);
        tvY = (TextView) view.findViewById(R.id.tvYMax);

        mSeekBarX = (SeekBar) view.findViewById(R.id.seekBar1);
        mSeekBarY = (SeekBar) view.findViewById(R.id.seekBar2);
        mClean = (TextView)view.findViewById(R.id.clean);
        mInset = (TextView)view.findViewById(R.id.inset);
        mDetail = (TextView)view.findViewById(R.id.detail);
        mClean.setOnClickListener(this);
        mInset.setOnClickListener(this);
        mDetail.setOnClickListener(this);
        mSeekBarY.setProgress(10);

        mSeekBarX.setOnSeekBarChangeListener(this);
        mSeekBarY.setOnSeekBarChangeListener(this);

        mChart = (PieChart) view.findViewById(R.id.chart1);
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);

//        tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

//        mChart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));
        /**
         * 设置圆心的文字
         */
        mChart.setCenterText("AAPlan");
//        mChart.setCenterText(generateCenterSpannableText());

        mChart.setExtraOffsets(20.f, 0.f, 20.f, 0.f);

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        mChart.setOnChartValueSelectedListener(this);

//        setData(5, 100);

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.actionToggleValues: {
                for (IDataSet<?> set : mChart.getData().getDataSets())
                    set.setDrawValues(!set.isDrawValuesEnabled());

                mChart.invalidate();
                break;
            }
            case R.id.actionToggleHole: {
                if (mChart.isDrawHoleEnabled())
                    mChart.setDrawHoleEnabled(false);
                else
                    mChart.setDrawHoleEnabled(true);
                mChart.invalidate();
                break;
            }
            case R.id.actionDrawCenter: {
                if (mChart.isDrawCenterTextEnabled())
                    mChart.setDrawCenterText(false);
                else
                    mChart.setDrawCenterText(true);
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleXVals: {

                mChart.setDrawEntryLabels(!mChart.isDrawEntryLabelsEnabled());
                mChart.invalidate();
                break;
            }
            case R.id.actionSave: {
                // mChart.saveToGallery("title"+System.currentTimeMillis());
                mChart.saveToPath("title" + System.currentTimeMillis(), "");
                break;
            }
            case R.id.actionTogglePercent:
                mChart.setUsePercentValues(!mChart.isUsePercentValuesEnabled());
                mChart.invalidate();
                break;
            case R.id.animateX: {
                mChart.animateX(1400);
                break;
            }
            case R.id.animateY: {
                mChart.animateY(1400);
                break;
            }
            case R.id.animateXY: {
                mChart.animateXY(1400, 1400);
                break;
            }
            case R.id.actionToggleSpin: {
                mChart.spin(1000, mChart.getRotationAngle(), mChart.getRotationAngle() + 360, Easing.EasingOption
                        .EaseInCubic);
                break;
            }
        }
        return true;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        tvX.setText("" + (mSeekBarX.getProgress()));
        tvY.setText("" + (mSeekBarY.getProgress()));

        setData(mSeekBarX.getProgress(), mSeekBarY.getProgress());
    }

    private void setData() {
        int count = mNameBeanList.size();
        float totalMoney = 0f;

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        for (int i = 0; i < count ; i++){
            totalMoney += mNameBeanList.get(i).getmTotalMoney();
            Log.i(TAG, "   bean = "+mNameBeanList.get(i).toString() +"  totalMoney = "+totalMoney);
        }

        for (int i = 0; i < count ; i++) {
//            entries.add(new PieEntry((float) ((Math.random() * mult) + mult / 5), mParties[i % mParties.length]));
//            NameBean bean = mNameBeanList.get(i);
//
            entries.add(new PieEntry(mNameBeanList.get(i).getmTotalMoney()/totalMoney, mNameBeanList.get(i).getmName()));
            Log.i(TAG, " setData      "+(mNameBeanList.get(i).getmTotalMoney()/totalMoney));
        }




        PieDataSet dataSet = new PieDataSet(entries, "Election Results");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
//        data.setValueTypeface(mTfLight);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

    private void setData(int count, float range) {

        float mult = range;

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count ; i++) {
            entries.add(new PieEntry((float) ((Math.random() * mult) + mult / 5), mParties[i % mParties.length]));
            Log.i(TAG, " setData      "+(float) ((Math.random() * mult) + mult / 5) + "   "+mParties[i % mParties.length]);
        }

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
//        data.setValueTypeface(mTfLight);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.account:
//                if (mDBManager == null){
//                mDBManager = new AADataBaseManager(getActivity());
//            }
//                Cursor cursor = mDBManager.query(BillDataBaseHelper.USER_TABLE, "_id desc");
//                if (cursor == null){
//                    Toast.makeText(getActivity(), R.string.has_no_account, Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                mNameBeanList.clear();
//                Log.i(TAG, "    size = "+cursor.getCount());
//                while (cursor.moveToNext()){
//                    int indexLaseAccount = cursor.getColumnIndexOrThrow(BillDataBaseHelper.LASE_ACCOUNT);
//                    String laseAccount = cursor.getString(indexLaseAccount);
//                    if (laseAccount != null){
//                        if (laseAccount.equals(BillDataBaseHelper.ACCOUNT_MARK)){
//                            int indexDate = cursor.getColumnIndexOrThrow(BillDataBaseHelper.TIME_DATE);
//                            String date = cursor.getString(indexDate);
//                            Log.i(TAG, " 最后结账算清的标记位  时间 : "+ date);
//                            return;
//                        }
//                    }
//                    initAABean(cursor);
//                }
//                cursor.close();
//                AAPlan aaPlan = new AAPlan();
//                List<NameBean> finishAAAlan = aaPlan.compareAccount(mNameBeanList);
//                for (NameBean bean : finishAAAlan){
//                    Log.i(TAG, " bean = "+ bean.toString());
//                }
//                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//                Date curDate = new Date(System.currentTimeMillis());
//                String date = formatter.format(curDate);
//                boolean addStatus = mDBManager.add(null, null, null, null, date, null, "yes");
//                Log.i(TAG, " 结算插入数据库状态 "+ (addStatus ? "插入成功" : "插入失败"));
//                break;
            case R.id.clean:
                if (mDBManager == null){
                    mDBManager = new AADataBaseManager(getActivity());
                }
                Cursor cursor1 = mDBManager.query(BillDataBaseHelper.USER_TABLE, "_id desc");
                Log.i(TAG, "  cursor1.size = "+cursor1.getCount());
                if (cursor1.moveToNext()){
                    String _id = cursor1.getString(0);
                    mDBManager.delete(BillDataBaseHelper.USER_TABLE, _id);
                }
                cursor1.close();
                break;
            case R.id.inset:
                if (mDBManager == null){
                    mDBManager = new AADataBaseManager(getActivity());
                }
                boolean status = mDBManager.add(null, null, null, null, "2017", null, "yes");
                Log.i(TAG, " 结算插入数据库状态 "+ (status ? "插入成功" : "插入失败"));
                break;
            case R.id.detail:
                Intent intent = new Intent(getActivity(), AccountDetailActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void initAABean(Cursor cursor) {
        mExistBean = false;
        int indexName = cursor.getColumnIndexOrThrow(BillDataBaseHelper.NAME);
        int indexMoney = cursor.getColumnIndexOrThrow(BillDataBaseHelper.MONEY);
//        int indexFartherClass = cursor.getColumnIndexOrThrow(BillDataBaseHelper.FARTHER_CLASS);
//        int indexChildClass = cursor.getColumnIndexOrThrow(BillDataBaseHelper.CHILD_CLASS);
//        int indexDate = cursor.getColumnIndexOrThrow(BillDataBaseHelper.TIME_DATE);
//        int indexRemark = cursor.getColumnIndexOrThrow(BillDataBaseHelper.REMARK);

        String name = cursor.getString(indexName);
        String strMoney = cursor.getString(indexMoney);
        Log.i(TAG, "  initAABean  strMoney = "+strMoney);
        float money = Float.parseFloat(strMoney);
//        String fartherClass = cursor.getString(indexFartherClass);
//        String childClass = cursor.getString(indexChildClass);
//        String date = cursor.getString(indexDate);
//        String remark = cursor.getString(indexRemark);

        NameBean forBean;
        for (int i = 0; i < mNameBeanList.size(); i++){
            forBean = mNameBeanList.get(i);
            if (name.equals(forBean.getmName())){
                float totalMoney = forBean.getmTotalMoney() + money;
                forBean.setmTotalMoney(totalMoney);
                mExistBean = true;
            }
        }
        if (!mExistBean){
            NameBean bean = new NameBean(name);
            bean.setmTotalMoney(money);
            mNameBeanList.add(bean);
        }
    }
}
