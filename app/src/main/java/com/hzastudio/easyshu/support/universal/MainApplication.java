package com.hzastudio.easyshu.support.universal;

import android.app.Application;
import android.content.Context;

/**
 * Programmed by Zean Huang
 * Github: https://github.com/thunderbird1997
 */

public class MainApplication extends Application {

        private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext=getApplicationContext();
    }

    public static Context getContext(){
            return mContext;
        }

    }
