package com.hzastudio.easyshu.activity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.hzastudio.easyshu.R;
import com.hzastudio.easyshu.support.universal.BaseActivity;
import com.hzastudio.easyshu.support.universal.MainApplication;

public class LaunchActivity extends BaseActivity {

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        sp = MainApplication.getContext().getSharedPreferences("user",MODE_PRIVATE);
        if(sp.getString("username", null) == null || sp.getString("password", null) == null) {
            sp.edit().remove("username").remove("password").apply();//删除用户可能因为程序错误留下的数据
            StartNewActivity(LaunchActivity.this,LoginActivity.class);
        }
        else
        {
            SharedPreferences app = MainApplication.getContext().getSharedPreferences("application", MODE_PRIVATE);
            //异常情况：欸？第一次启动本地竟然有数据！？（卸载残留吧这是）
            if(app.getBoolean("firstLaunch",true))
            {
                app.edit().putBoolean("firstLaunch",false).apply();
                new AlertDialog.Builder(LaunchActivity.this)
                        .setMessage("检测到本地已有账户:" +
                                sp.getString("username", null) +
                                "\n是否为您的账户？")
                        .setCancelable(false)
                        .setPositiveButton("是的，继续使用", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                StartNewActivity(LaunchActivity.this, MainActivity.class);
                            }
                        })
                        .setNegativeButton("不是，我要修改", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sp.edit().remove("username").remove("password").apply();//删除用户可能因为程序错误留下的数据
                                StartNewActivity(LaunchActivity.this, LoginActivity.class);
                            }
                        })
                        .show();
            }
            else
            {
                StartNewActivity(LaunchActivity.this, MainActivity.class);
            }
        }
    }
}
