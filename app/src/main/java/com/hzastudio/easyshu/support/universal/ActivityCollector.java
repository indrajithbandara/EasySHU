package com.hzastudio.easyshu.support.universal;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Programmed by Zean Huang
 * Github: https://github.com/thunderbird1997
 */

public class ActivityCollector {

    private static List<Activity> MyActivities=new ArrayList<>();

    public static void AddActivity(Activity activity)
    {
        MyActivities.add(activity);
    }

    public static void RemoveActivity(Activity activity)
    {
        MyActivities.remove(activity);
    }

    public static void FinishAllActivities()
    {
        for(Activity activity:MyActivities){
            if(!activity.isFinishing()){
                activity.finish();
                //MyActivities.remove(activity);
            }
        }
    }
}
