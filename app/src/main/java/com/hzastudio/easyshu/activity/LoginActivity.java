package com.hzastudio.easyshu.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hzastudio.easyshu.R;
import com.hzastudio.easyshu.module.UserConfig;
import com.hzastudio.easyshu.support.universal.BaseActivity;
import com.hzastudio.easyshu.support.universal.MainApplication;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 登录界面
 * @author Zean Huang
 * @link https://github.com/thunderbird1997
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener
{

    private TextView         textUserName;
    private TextView         textPassword;
    private Button           submitButton;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*UI元素定义*/
        textUserName = (TextView)findViewById(R.id.Login_Username);
        textPassword = (TextView)findViewById(R.id.Login_Password);
        submitButton = (Button)  findViewById(R.id.Login_Submit  );
        progressDialog=new ProgressDialog(LoginActivity.this);

        TextWatcher ChangeWatcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!textUserName.getText().toString().equals("") && !textPassword.getText().toString().equals("")){
                    submitButton.setEnabled(true);
                }else{
                    submitButton.setEnabled(false);
                }
            }
        };
        textUserName.addTextChangedListener(ChangeWatcher);
        textPassword.addTextChangedListener(ChangeWatcher);
        submitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId())
        {
            case R.id.Login_Submit:
                progressDialog.setMessage("验证中，请稍后");
                progressDialog.setCancelable(false);
                progressDialog.show();
                UserConfig.CheckServer()
                        .flatMap(new Function<Boolean, ObservableSource<Boolean>>() {
                            @Override
                            public ObservableSource<Boolean> apply(@NonNull Boolean aBoolean) throws Exception {
                                return UserConfig.CreateUser(textUserName.getText().toString(),
                                        textPassword.getText().toString());
                            }
                        })
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Boolean>() {
                                       @Override
                                       public void accept(@NonNull Boolean aBoolean) throws Exception {
                                           Log.d("LoginActivity","成功！");
                                           progressDialog.dismiss();
                                           SharedPreferences app= MainApplication.getContext().getSharedPreferences("application",MODE_PRIVATE);
                                           app.edit().putBoolean("firstLaunch",false).apply();
                                           StartNewActivity(LoginActivity.this,MainActivity.class,false);
                                           finish();
                                       }
                                   },
                                new Consumer<Throwable>() {
                                    @Override
                                    public void accept(@NonNull Throwable throwable) throws Exception {
                                        Log.e("LoginActivity","失败！原因："+throwable.getMessage());
                                        progressDialog.dismiss();
                                        textPassword.setText("");
                                        Snackbar.make(v,
                                                "账号或密码错误，请重试！",
                                                Snackbar.LENGTH_SHORT)
                                                .show();
                                        //删除保存的账号和密码
                                        SharedPreferences sp = MainApplication.getContext().getSharedPreferences("user",MODE_PRIVATE);
                                        sp.edit().remove("username").remove("password").apply();
                                    }
                                });
                break;
            default:
                break;
        }
    }
}
