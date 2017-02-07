package com.aabao.adnroid.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.aabao.adnroid.MonthActivity;
import com.aabao.adnroid.NoteActivity;
import com.aabao.adnroid.R;
import com.aabao.adnroid.TodayActivity;
import com.aabao.adnroid.WeekActivity;
import com.aabao.adnroid.YearActivity;
import com.aabao.adnroid.base.BaseFragment;

/**
 * Created by Verse Part on 2017/1/9.
 * email: versepartwang@163.com
 */

public class MainFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener {


    private static final String TAG = "MainFragment";
    private TextView mTextTodayNote;
    private ListView mListView;
    private final int[] LISTCOUNT = {R.string.today_case, R.string.this_week, R.string.this_month, R.string.this_year};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mTextTodayNote = (TextView)view.findViewById(R.id.text_today_note);
        mTextTodayNote.setOnClickListener(this);
        mListView = (ListView)view.findViewById(R.id.list_view);
        mListView.setAdapter(new MainListAdapter(getActivity()));
        mListView.setOnItemClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_today_note:
                Intent intent = new Intent(getActivity(), NoteActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0){
            mainStartActivity(TodayActivity.class);
        } else if (position == 1){
            mainStartActivity(WeekActivity.class);
        } else if (position == 2){
            mainStartActivity(MonthActivity.class);
        } else if (position == 3){
            mainStartActivity(YearActivity.class);
        }
    }

    private void mainStartActivity(Class<?> clazz){
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
    }

    private class MainListAdapter extends BaseAdapter{

        LayoutInflater mInflater;

        MainListAdapter(Context context){
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return LISTCOUNT.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null){
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.main_list_item, null);
                holder.mImageItem = (ImageView)convertView.findViewById(R.id.image_item);
                holder.mTextToday =(TextView)convertView.findViewById(R.id.text_today);
                holder.mTextState =(TextView)convertView.findViewById(R.id.text_state);
                holder.mImageRightTab = (ImageView)convertView.findViewById(R.id.image_right_tab);
                holder.mTextOnlyOne =(TextView)convertView.findViewById(R.id.text_only_one);
                holder.mTextTotal =(TextView)convertView.findViewById(R.id.text_total);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.mTextToday.setText(LISTCOUNT[position]);
            Log.i(TAG, "  getView       position = "+position+"        text = "+holder.mTextOnlyOne.getText());
            return convertView;
        }
    }

    private class ViewHolder{
        ImageView mImageItem;
        TextView mTextToday;
        TextView mTextState;
        ImageView mImageRightTab;
        TextView mTextOnlyOne;
        TextView mTextTotal;
    }
}
