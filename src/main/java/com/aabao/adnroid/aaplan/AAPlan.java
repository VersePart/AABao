package com.aabao.adnroid.aaplan;

import com.aabao.adnroid.aaplan.bean.NameBean;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



/**
 * Created by Verse Part on 2017/1/22.
 * email: versepartwang@163.com
 */

public class AAPlan {

    int mAverageMin;
    int mAverageMax;
    int mAverageTo = -1;
    int mAverageFrom;

    List<NameBean> mList = new ArrayList<NameBean>();

    List <Float> mListCount = new ArrayList<Float>();
    float[] mCount = new float[5];
    private static final float CON = (float)2.5;

    public void showAA(){
        if (mList != null)
            mList.clear();
        if (mListCount != null)
            mListCount.clear();
        for (int a = 0; a < mCount.length; a++){
            NameBean bean = new NameBean(a+"", CON + (float)a);
            mList.add(bean);
        }
        mList.add(new NameBean("ddad", 110f));
        compareNumber(mList);

        for (int c = 0; c < mList.size(); c++){
            NameBean bean = mList.get(c);
            System.out.println("222222  bean =" +bean.toString());
        }
    }

    public void compareNumber (List<NameBean> list){
        float totalMoney = 0f;
        float averageMoney = 0f;
        NameBean nameBean = null;
        for (int a = 0; a < list.size(); a++){
            nameBean = list.get(a);
            totalMoney += nameBean.getmMoney();
        }
        DecimalFormat df = new DecimalFormat("#.##");
        averageMoney = totalMoney / list.size();
        String averageString = df.format(averageMoney);
        averageMoney = Float.parseFloat(averageString);
        Collections.sort(list, new Comparator<NameBean>(){

            @Override
            public int compare(NameBean o1, NameBean o2) {
                if (o1.getmMoney() > o2.getmMoney())
                    return 1;
                if (o1.getmMoney() < o2.getmMoney())
                    return -1;
                return 0;
            }
        });

        //从小到大的排序
        averagePosition(list, averageMoney);

        //计算每人该给谁多少钱
        toBMoneyA(list, averageMoney);
    }

    /**
     * 平均值区间是[mAverageMin, mAverageMax]
     * @param list
     * @param averageMoney
     */
    private void averagePosition(List<NameBean> list ,float averageMoney) {

        for (int a = 0; a < list.size(); a++){
            NameBean nameBean = list.get(a);
            float money = nameBean.getmMoney();
            if (money < averageMoney)
                mAverageMin = a;
            if (money == averageMoney)
                mAverageTo = a;
            if (money == averageMoney && mAverageTo != 0)
                mAverageFrom = a;
            if (money > averageMoney)
                mAverageMax = a;
        }
    }

    //    int mAverageMin;
//    int mAverageMax;
//    int mAverageTo = -1;
//    int mAverageFrom;
    private void toBMoneyA(List<NameBean> list, float averageMoney) {

        // mAverageMax = mAverageMin + 1
//        if (mAverageTo == -1){
        int a = 0;
        int b = list.size() - 1;
        float saveData = 0f;
        boolean to = false;
        boolean get = false;
        NameBean toXBean;
        NameBean getXBean;
        while (a < (mAverageMin + 1)){
            System.out.println("     a = "+a +"   b = "+b + "   lise.size = "+list.size());
            float toX = 0f;
            float getX = 0f;
            float compareMoney = 0f;
            toXBean = list.get(a);
            toXBean.setToOrGet(true);
            getXBean = list.get(b);
            getXBean.setToOrGet(false);

            if (to == true && get == true){
                toX = toXBean.getmMoney() - averageMoney;
                getX = getXBean.getmMoney() - averageMoney;
//                    compareMoney = getX + toX;
            } else if (to == true && get == false){
                toX = saveData;
                getX = toXBean.getmMoney() - averageMoney;
//                    compareMoney = getX + toX;
            } else if (to == false && get == true){
                toX = toXBean.getmMoney() - averageMoney;
                getX = saveData;
//                    compareMoney = getX + toX;
            } else if (to == false && get == false){
                toX = toXBean.getmMoney() - averageMoney;
                getX = getXBean.getmMoney() - averageMoney;
//                    compareMoney = getX + toX;
            }
            compareMoney = getX + toX;

            if (compareMoney > 0){
                toXBean.setmAANames(getXBean.getmName());
                toXBean.setmAAMoneys(Math.abs(toX));

                getXBean.setmAANames(toXBean.getmName());
                getXBean.setmAAMoneys(toX);
                saveData = compareMoney;
                get = true;
                to = false;
                a++;
                System.out.println("  compareMoney > 0  ");
                continue;
            } else if (compareMoney < 0){
                toXBean.setmAANames(getXBean.getmName());
                toXBean.setmAAMoneys(getX);

                getXBean.setmAANames(toXBean.getmName());
                getXBean.setmAAMoneys(getX);
                saveData = compareMoney;
                get = false;
                to = true;
                b--;
                System.out.println(" 22222222 compareMoney < 0  ");
                continue;
            } else {
                toXBean.setmAANames(getXBean.getmName());
                toXBean.setmAAMoneys(getX);

                getXBean.setmAANames(toXBean.getmName());
                getXBean.setmAAMoneys(getX);
                saveData = compareMoney;
                get = true;
                to = true;
                a++;
                b--;
                System.out.println("  else  ");
                continue;
            }
        }
//        } else {
//
//        }
    }
}