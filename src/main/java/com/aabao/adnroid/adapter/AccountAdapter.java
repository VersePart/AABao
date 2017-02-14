package com.aabao.adnroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aabao.adnroid.R;
import com.aabao.adnroid.aaplan.bean.NameBean;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Verse Part on 2017/2/11.
 * email: versepartwang@163.com
 */

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.MyViewHolder>  {

    private Context mContext;
    private List<NameBean> mList;
    private final LayoutInflater mFlater;

    public AccountAdapter(Context context, List<NameBean> list){
        mFlater = LayoutInflater.from(context);
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(mFlater.inflate(R.layout.recyler_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NameBean bean = mList.get(position);
        DecimalFormat df = new DecimalFormat(".00");
        String strMoney = df.format(bean.getmTotalMoney());
        holder.nameText.setText(bean.getmName());
        holder.caseNumber.setText(strMoney + mContext.getResources().getString(R.string.yuan));
        holder.lastDateText.setText(bean.getmDate());
    }

    @Override
    public int getItemCount() {
        if (mList.size() > 0){
            return mList.size();
        }
        return 1;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView nameText;
        private TextView caseNumber;
        private TextView lastDateText;

        public MyViewHolder(View view) {
            super(view);
            nameText = (TextView)view.findViewById(R.id.name_text);
            caseNumber = (TextView)view.findViewById(R.id.case_number);
            lastDateText = (TextView)view.findViewById(R.id.last_date_text);
        }
    }
}
