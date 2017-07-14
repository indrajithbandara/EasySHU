package com.hzastudio.easyshu.support.universal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Programmed by Zean Huang
 * Github: https://github.com/thunderbird1997
 */

public class BaseActivity extends AppCompatActivity {
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
