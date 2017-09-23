package com.hzastudio.easyshu.support.universal;

import android.app.Application;
import android.content.Context;

/**
 * 获取全局Context所用Application，在AndroidManifest.xml中配置使用
 * @author Zean Huang
 * @link https://github.com/thunderbird1997
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
