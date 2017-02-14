package com.aabao.adnroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aabao.adnroid.R;
import com.aabao.adnroid.aaplan.bean.NameBean;

import java.util.List;

import static com.aabao.adnroid.util.ToolUtils.getResourceString;

/**
 * Created by Verse Part on 2017/2/12.
 * email: versepartwang@163.com
 */
public class CaculateAdapter extends RecyclerView.Adapter<CaculateAdapter.MyViewHolder> {

    private static final String TAG = "CaculateAdapter";
    private Context mContext;
    private final LayoutInflater mInflater;
    private List<NameBean> mList;
    private int mType;
    private static final int TYPEZERO = 0;
    private static final int TYPEFIRST = 1;
    private static final int TYPESECOND = 2;
    private static final int TYPETHIRDLY = 3;
    private static final int TYPEFOURTH = 4;
    private static final int GIVE = R.string.give;
    private static final int YUAN = R.string.yuan;
    private static final int CONG = R.string.cong;
    private static final int GIVE_IT = R.string.give_it;

    public CaculateAdapter(Context context, List<NameBean> list) {
        this.mContext = context;
        this.mList = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = null;
        Log.i(TAG, " onCreateViewHolder   mType = "+mType);
        switch (mType){
            case TYPEZERO:
                holder = new MyViewHolder(mInflater.inflate(R.layout.caculate_item_1, parent, false));
                break;
            case TYPEFIRST:
                holder = new MyViewHolder(mInflater.inflate(R.layout.caculate_item_1, parent, false));
                break;
            case TYPESECOND:
                holder = new MyViewHolder(mInflater.inflate(R.layout.caculate_item_2, parent, false));
                break;
            case TYPETHIRDLY:
                holder = new MyViewHolder(mInflater.inflate(R.layout.caculate_item_3, parent, false));
                break;
            case TYPEFOURTH:
                holder = new MyViewHolder(mInflater.inflate(R.layout.caculate_item_4, parent, false));
                break;
            default:
                new IllegalAccessException(" CaculateAdapter type 类型错误");
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NameBean nameBean;
        boolean flag;
        Log.i(TAG, " onBindViewHolder       mType = "+mType);
        switch (mType){
            case TYPEZERO:
                nameBean = mList.get(position);
                holder.name.setText(nameBean.getmName());
                holder.text_0_money_1.setText("谁也不欠谁的");
                break;
            case TYPEFIRST:
                nameBean = mList.get(position);
                flag = nameBean.isToOrGet();
                holder.name.setText(nameBean.getmName());
                if (flag){
                    holder.text_1_money_1.setText(getResourceString(mContext, GIVE) + nameBean.getmAANames().get(0)
                            + nameBean.getmAAMoneys().get(0) + getResourceString(mContext, YUAN));
                } else {
                    holder.text_1_money_1.setText(getResourceString(mContext, CONG) + nameBean.getmAANames().get(0)
                            + getResourceString(mContext, GIVE_IT) + nameBean.getmAAMoneys().get(0) + getResourceString(mContext, YUAN));
                }
                break;
            case TYPESECOND:
                nameBean = mList.get(position);
                flag = nameBean.isToOrGet();
                holder.name.setText(nameBean.getmName());
                if (flag){
                    holder.text_2_money_1.setText(getResourceString(mContext, GIVE) + nameBean.getmAANames().get(0)
                            + nameBean.getmAAMoneys().get(0) + getResourceString(mContext, YUAN));
                    holder.text_2_money_2.setText(getResourceString(mContext, GIVE) + nameBean.getmAANames().get(1)
                            + nameBean.getmAAMoneys().get(1) + getResourceString(mContext, YUAN));
                } else {
                    holder.text_2_money_1.setText(getResourceString(mContext, CONG) + nameBean.getmAANames().get(0)
                            + getResourceString(mContext, GIVE_IT) + nameBean.getmAAMoneys().get(0) + getResourceString(mContext, YUAN));
                    holder.text_2_money_2.setText(getResourceString(mContext, CONG) + nameBean.getmAANames().get(1)
                            + getResourceString(mContext, GIVE_IT) + nameBean.getmAAMoneys().get(1) + getResourceString(mContext, YUAN));
                }
                break;
            case TYPETHIRDLY:
                nameBean = mList.get(position);
                flag = nameBean.isToOrGet();
                holder.name.setText(nameBean.getmName());
                if (flag){
                    holder.text_3_money_1.setText(getResourceString(mContext, GIVE) + nameBean.getmAANames().get(0)
                            + nameBean.getmAAMoneys().get(0) + getResourceString(mContext, YUAN));
                    holder.text_3_money_2.setText(getResourceString(mContext, GIVE) + nameBean.getmAANames().get(2)
                            + nameBean.getmAAMoneys().get(1) + getResourceString(mContext, YUAN));
                    holder.text_3_money_3.setText(getResourceString(mContext, GIVE) + nameBean.getmAANames().get(3)
                            + nameBean.getmAAMoneys().get(2) + getResourceString(mContext, YUAN));
                } else {
                    holder.text_3_money_1.setText(getResourceString(mContext, CONG) + nameBean.getmAANames().get(0)
                            + getResourceString(mContext, GIVE_IT) + nameBean.getmAAMoneys().get(0) + getResourceString(mContext, YUAN));
                    holder.text_3_money_2.setText(getResourceString(mContext, CONG) + nameBean.getmAANames().get(1)
                            + getResourceString(mContext, GIVE_IT) + nameBean.getmAAMoneys().get(1) + getResourceString(mContext, YUAN));
                    holder.text_3_money_3.setText(getResourceString(mContext, CONG) + nameBean.getmAANames().get(2)
                            + getResourceString(mContext, GIVE_IT) + nameBean.getmAAMoneys().get(1) + getResourceString(mContext, YUAN));
                }
                break;
            case TYPEFOURTH:
                nameBean = mList.get(position);
                flag = nameBean.isToOrGet();
                holder.name.setText(nameBean.getmName());
                if (flag){
                    holder.text_4_money_1.setText(getResourceString(mContext, GIVE) + nameBean.getmAANames().get(0)
                            + nameBean.getmAAMoneys().get(0) + getResourceString(mContext, YUAN));
                    holder.text_4_money_2.setText(getResourceString(mContext, GIVE) + nameBean.getmAANames().get(1)
                            + nameBean.getmAAMoneys().get(1) + getResourceString(mContext, YUAN));
                    holder.text_4_money_3.setText(getResourceString(mContext, GIVE) + nameBean.getmAANames().get(2)
                            + nameBean.getmAAMoneys().get(2) + getResourceString(mContext, YUAN));
                    holder.text_4_money_4.setText(getResourceString(mContext, GIVE) + nameBean.getmAANames().get(3)
                            + nameBean.getmAAMoneys().get(3) + getResourceString(mContext, YUAN));
                } else {
                    holder.text_4_money_1.setText(getResourceString(mContext, CONG) + nameBean.getmAANames().get(0)
                            + getResourceString(mContext, GIVE_IT) + nameBean.getmAAMoneys().get(0) + getResourceString(mContext, YUAN));
                    holder.text_4_money_2.setText(getResourceString(mContext, CONG) + nameBean.getmAANames().get(1)
                            + getResourceString(mContext, GIVE_IT) + nameBean.getmAAMoneys().get(1) + getResourceString(mContext, YUAN));
                    holder.text_4_money_3.setText(getResourceString(mContext, CONG) + nameBean.getmAANames().get(2)
                            + getResourceString(mContext, GIVE_IT) + nameBean.getmAAMoneys().get(2) + getResourceString(mContext, YUAN));
                    holder.text_4_money_4.setText(getResourceString(mContext, CONG) + nameBean.getmAANames().get(3)
                            + getResourceString(mContext, GIVE_IT) + nameBean.getmAAMoneys().get(3) + getResourceString(mContext, YUAN));
                }
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (mList.size() > 0){
            return mList.size();
        }
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        NameBean name = mList.get(position);
        mType = name.getmAANames().size();
        Log.i(TAG, " getItemViewType  mType = "+mType);
        return mType;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView text_0_money_1;
        private TextView text_1_money_1;
        private TextView text_2_money_1;
        private TextView text_2_money_2;
        private TextView text_3_money_1;
        private TextView text_3_money_2;
        private TextView text_3_money_3;
        private TextView text_4_money_1;
        private TextView text_4_money_2;
        private TextView text_4_money_3;
        private TextView text_4_money_4;
        private TextView name;

        public MyViewHolder(View view) {
            super(view);
            Log.i(TAG, " MyViewHolder         mType = "+mType);
            name = (TextView)view.findViewById(R.id.name);
            switch (mType){
                case TYPEZERO:
                    text_0_money_1 = (TextView)view.findViewById(R.id.about_money_1);
                    break;
                case TYPEFIRST:
                    text_1_money_1 = (TextView)view.findViewById(R.id.about_money_1);
                    break;
                case TYPESECOND:
                    text_2_money_1 = (TextView)view.findViewById(R.id.about_money_1);
                    text_2_money_2 = (TextView)view.findViewById(R.id.about_money_2);
                    break;
                case TYPETHIRDLY:
                    text_3_money_1 = (TextView)view.findViewById(R.id.about_money_1);
                    text_3_money_2 = (TextView)view.findViewById(R.id.about_money_2);
                    text_3_money_3 = (TextView)view.findViewById(R.id.about_money_3);
                    break;
                case TYPEFOURTH:
                    text_4_money_1 = (TextView)view.findViewById(R.id.about_money_1);
                    text_4_money_2 = (TextView)view.findViewById(R.id.about_money_2);
                    text_4_money_3 = (TextView)view.findViewById(R.id.about_money_3);
                    text_4_money_4 = (TextView)view.findViewById(R.id.about_money_4);
                    break;
                default:
                    break;
            }
        }
    }
}
