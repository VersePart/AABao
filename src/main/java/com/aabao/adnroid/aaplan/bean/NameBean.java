package com.aabao.adnroid.aaplan.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Verse Part on 2017/1/22.
 * email: versepartwang@163.com
 */

public class NameBean {

    private String mName;
    private float mMoney;
    private float mToX;
    private float mGetX;
    private List<String> mAANames = new ArrayList<String>();
    private List<Float> mAAMoneys = new ArrayList<Float>();
    //真为给别人钱，假为得到钱
    private boolean toOrGet;

    public NameBean(String name, float money){
        this.mName = name;
        this.mMoney = money;
    }

    public float getmMoney() {
        return mMoney;
    }

    public void setmMoney(float mMoney) {
        this.mMoney = mMoney;
    }


    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public float getmToX() {
        return mToX;
    }

    public void setmToX(float mToX) {
        this.mToX = mToX;
    }

    public float getmGetX() {
        return mGetX;
    }

    public void setmGetX(float mGetX) {
        this.mGetX = mGetX;
    }

    public List<String> getmAANames() {
        return mAANames;
    }

    public void setmAANames(String mNames) {
        this.mAANames.add(mNames);
    }

    public boolean isToOrGet() {
        return toOrGet;
    }

    public void setToOrGet(boolean toOrGet) {
        this.toOrGet = toOrGet;
    }

    public List<Float> getmAAMoneys() {
        return mAAMoneys;
    }

    public void setmAAMoneys(float mAAMoneys) {
        this.mAAMoneys.add(mAAMoneys);
    }

    @Override
    public String toString() {
        return "NameBean{" +
                "mName='" + mName + '\'' +
                ", mMoney=" + mMoney +
                ", mToX=" + mToX +
                ", mGetX=" + mGetX +
                ", mAANames=" + mAANames +
                ", mAAMoneys=" + mAAMoneys +
                ", toOrGet=" + toOrGet +
                '}';
    }
}
