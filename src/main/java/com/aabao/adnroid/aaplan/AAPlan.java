package com.aabao.adnroid.aaplan;

import android.util.Log;

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

    private static final String TAG = "AAPlan";
    float mAverageMin;
    float mAverageMax = -1f;
    int mAverageTo = -1;
    int mAverageFrom;

    public AAPlan(){

    }

    public List<NameBean> compareAccount (List<NameBean> list){
        float totalMoney = 0f;
        float averageMoney = 0f;
        NameBean nameBean = null;
        for (int a = 0; a < list.size(); a++){
            nameBean = list.get(a);
            totalMoney += nameBean.getmTotalMoney();
        }
        DecimalFormat df = new DecimalFormat("#.##");
        averageMoney = totalMoney / list.size();
        String averageString = df.format(averageMoney);
        averageMoney = Float.parseFloat(averageString);
        Collections.sort(list, new Comparator<NameBean>(){

            @Override
            public int compare(NameBean o1, NameBean o2) {
                if (o1.getmTotalMoney() > o2.getmTotalMoney())
                    return 1;
                if (o1.getmTotalMoney() < o2.getmTotalMoney())
                    return -1;
                return 0;
            }
        });

        //从小到大的排序
        averagePosition(list, averageMoney);

        //计算每人该给谁多少钱
        List<NameBean> okList = toBMoneyA(list, averageMoney);
        return okList;
    }

    /**
     * 平均值区间是[mAverageMin, mAverageMax]
     * @param list
     * @param averageMoney
     */
    private void averagePosition(List<NameBean> list ,float averageMoney) {

        for (int a = 0; a < list.size(); a++){
            NameBean nameBean = list.get(a);
            float money = nameBean.getmTotalMoney();
            if (money < averageMoney)
                mAverageMin = (float)a + 0.5f;
            if (money == averageMoney)
                mAverageTo = a;
            if (money == averageMoney && mAverageTo != 0)
                mAverageFrom = a;
            if (money > averageMoney && mAverageMax == -1f)
                mAverageMax = (float)a - 0.5f;
        }
    }

    //    int mAverageMin;
//    int mAverageMax;
//    int mAverageTo = -1;
//    int mAverageFrom;
    private List<NameBean> toBMoneyA(List<NameBean> list, float averageMoney) {
        int a = 0;
        int b = list.size() - 1;
        float saveData = 0f;
        boolean to = false;
        boolean get = false;
        NameBean toXBean;
        NameBean getXBean;
        DecimalFormat df = new DecimalFormat(".00");

        while (a < mAverageMin && b > mAverageMax){
            Log.i(TAG, "     a = "+a +"   b = "+b + "   lise.size = "+list.size());
            float toX = 0f;
            float getX = 0f;
            float compareMoney = 0f;
            toXBean = list.get(a);
            toXBean.setToOrGet(true);
            getXBean = list.get(b);
            getXBean.setToOrGet(false);

            Log.i(TAG, "    saveData = "+saveData + "  averageMoney = "+averageMoney + " mAverageMin = "+mAverageMin
            + " mAverageMax = "+mAverageMax);
            if (to == true && get == true){
                toX = toXBean.getmTotalMoney() - averageMoney;
                getX = getXBean.getmTotalMoney() - averageMoney;
//                    compareMoney = getX + toX;
            } else if (to == true && get == false){
                toX = saveData;
                getX = getXBean.getmTotalMoney() - averageMoney;
            } else if (to == false && get == true){
                toX = toXBean.getmTotalMoney() - averageMoney;
                getX = saveData;
            } else if (to == false && get == false){
                toX = toXBean.getmTotalMoney() - averageMoney;
                getX = getXBean.getmTotalMoney() - averageMoney;
            }
            compareMoney = getX + toX;
            String strCompareMoney = df.format(compareMoney);
            String strGetX = df.format(getX);
            String strToX = df.format(toX);
            compareMoney = Float.parseFloat(strCompareMoney);
            getX = Float.parseFloat(strGetX);
            toX = Float.parseFloat(strToX);
            Log.i(TAG, "  toX = "+toX+"  getX = "+getX);
            if (compareMoney > 0){
                toXBean.setmAANames(getXBean.getmName());
                toXBean.setmAAMoneys(Math.abs(toX));

                getXBean.setmAANames(toXBean.getmName());
                getXBean.setmAAMoneys(Math.abs(toX));
                saveData = compareMoney;
                get = true;
                to = false;
                a++;
                System.out.println("  compareMoney > 0  ");
//                continue;
            } else if (compareMoney < 0){
                toXBean.setmAANames(getXBean.getmName());
                toXBean.setmAAMoneys(Math.abs(getX));

                getXBean.setmAANames(toXBean.getmName());
                getXBean.setmAAMoneys(Math.abs(getX));
                saveData = compareMoney;
                get = false;
                to = true;
                b--;
                System.out.println(" 22222222 compareMoney < 0  ");
//                continue;
            } else {
                toXBean.setmAANames(getXBean.getmName());
                toXBean.setmAAMoneys(Math.abs(getX));

                getXBean.setmAANames(toXBean.getmName());
                getXBean.setmAAMoneys(Math.abs(getX));
                saveData = compareMoney;
                get = true;
                to = true;
                a++;
                b--;
                System.out.println("  else  ");
//                continue;
            }
            continue;
        }
        return list;
    }
}