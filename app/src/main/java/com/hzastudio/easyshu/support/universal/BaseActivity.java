package com.hzastudio.easyshu.support.universal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Activity基类（配合Activity管理器，增加StartNewActivity函数的多个重载）
 * @author Zean Huang
 * @link https://github.com/thunderbird1997
 */
public class BaseActivity extends AppCompatActivity {

    public static void StartNewActivity(Context From,Class<?> To)
    {
        Intent intent=new Intent(From,To);
        From.startActivity(intent);
    }

    public static void StartNewActivity(Context From,Class<?> To,String... params)
    {
        Intent intent=new Intent(From,To);
        for(int i=0;i<params.length;i++)
        {
            intent.putExtra("param"+i,params[i]);
        }
        From.startActivity(intent);
    }

    public static void StartNewActivity(Context From,Class<?> To,Boolean data1,String data2)
    {
        Intent intent=new Intent(From,To);
        intent.putExtra("data1",data1);
        intent.putExtra("data2",data2);
        From.startActivity(intent);
    }

    public static void StartNewActivity(Context From,Class<?> To,Boolean data)
    {
        Intent intent=new Intent(From,To);
        intent.putExtra("data",data);
        From.startActivity(intent);
    }

    public static void StartNewActivity(Context From,Class<?> To,int data)
    {
        Intent intent=new Intent(From,To);
        intent.putExtra("data",data);
        From.startActivity(intent);
    }

    public static void StartNewActivity(Context From,Class<?> To,Bundle data)
    {
        Intent intent=new Intent(From,To);
        intent.putExtra("data",data);
        From.startActivity(intent);
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.AddActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.RemoveActivity(this);
    }
}
