package com.hzastudio.easyshu.task;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.hzastudio.easyshu.support.json_lib.json_StandardReturn;
import com.hzastudio.easyshu.support.program_const.URL;
import com.hzastudio.easyshu.support.tool.HttpFramework;
import com.hzastudio.easyshu.support.universal.MainApplication;

import java.security.PublicKey;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 与服务器通信函数（用户配置）（阻塞）
 * @author Zean Huang
 * @link https://github.com/thunderbird1997
 */
public class UserTasks {

    public static String Task_CheckUser(String Username)
    {
        HttpFramework handler= HttpFramework.getInstance();
        RequestBody body=new FormBody.Builder()
                .add("function","CheckIfUserExist")
                .add("data[usr]",Username)
                .build();
        try {
            Response response = handler.httpPost(URL.SERVER_INTERFACE_URL,body).execute();
            String result=response.body().string();
            Log.d("Task_CheckUser","Result:"+result);
            Gson gson=new Gson();
            json_StandardReturn res=gson.fromJson(result,json_StandardReturn.class);
            Log.d("Task_CheckUser","Status:"+res.getStatus());
            Log.d("Task_CheckUser","Data  :"+res.getData());
            return res.getData();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean Task_GetKey(String Username)
    {
        HttpFramework handler= HttpFramework.getInstance();
        RequestBody body=new FormBody.Builder()
                .add("function","AcquirePublicKey")
                .add("data[usr]",Username)
                .build();
        try {
            Response response = handler.httpPost(URL.SERVER_INTERFACE_URL,body).execute();
            String result=response.body().string();
            Log.d("Task_GetKey","Result:"+result);
            Gson gson=new Gson();
            json_StandardReturn res=gson.fromJson(result,json_StandardReturn.class);
            Log.d("Task_GetKey","Status:"+res.getStatus());
            Log.d("Task_GetKey","Data  :"+res.getData());
            if(res.getStatus().equals("1"))
            {
                /*
                 * PEM格式中公钥字符串是已经经过base64编码的
                 * 因此服务器返回数据解码时要进行2次base64解码
                 * 才能得到正确的RSA公钥
                 */

                /*服务器返回数据预处理及base64解码*/
                String publicKey=new String(Base64.decode(res.getData(),Base64.DEFAULT));
                publicKey=publicKey.replace("-----BEGIN PUBLIC KEY-----\n","");
                publicKey=publicKey.replace("-----END PUBLIC KEY-----","");
                Log.d("sss","PublicKey:"+publicKey);
                //TODO:用AES128加密公钥防止泄露
                SharedPreferences sp = MainApplication.getContext().getSharedPreferences("user",
                        Context.MODE_PRIVATE);
                sp.edit().putString("publicKey",publicKey).apply();
                return true;
            }
            else
            {
                return false;
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public static String Task_DeleteUser(String Username,String Password)
    {
        HttpFramework handler= HttpFramework.getInstance();
        RequestBody body=new FormBody.Builder()
                .add("function","DeleteUserInfo")
                .add("data[usr]",Username)
                .add("data[pswconf]",Password)
                .build();
        try {
            Response response = handler.httpPost(URL.SERVER_INTERFACE_URL,body).execute();
            String result=response.body().string();
            Log.d("Task_DeleteUser","Result:"+result);
            Gson gson=new Gson();
            json_StandardReturn res=gson.fromJson(result,json_StandardReturn.class);
            Log.d("Task_DeleteUser","Status:"+res.getStatus());
            Log.d("Task_DeleteUser","Data  :"+res.getData());
            return res.getData();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static String Task_CreateUser(String Username, String Password, String PasswordConfig)
    {
        HttpFramework handler= HttpFramework.getInstance();
        RequestBody body=new FormBody.Builder()
                .add("function","CreateNewUser")
                .add("data[usr]",Username)
                .add("data[psw]",Password)
                .add("data[pswconf]",PasswordConfig)
                .build();
        try {
            Response response = handler.httpPost(URL.SERVER_INTERFACE_URL,body).execute();
            String result=response.body().string();
            Log.d("Task_CreateUser","Result:"+result);
            Gson gson=new Gson();
            json_StandardReturn res=gson.fromJson(result,json_StandardReturn.class);
            Log.d("Task_CreateUser","Status:"+res.getStatus());
            Log.d("Task_CreateUser","Data  :"+res.getData());
            return res.getData();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static String Task_ChangeUserPassword(String Username, String OldPassword,
                                                 String OldPasswordConfig, String NewPassword,
                                                 String NewPassConfig)
    {
        HttpFramework handler= HttpFramework.getInstance();
        RequestBody body=new FormBody.Builder()
                .add("function","ChangePassword")
                .add("data[usr]",Username)
                .add("data[newpsw]",NewPassword)
                .add("data[newpswconf]",NewPassConfig)
                .add("data[oldpsw]",OldPassword)
                .add("data[oldpswconf]",OldPasswordConfig)
                .build();
        try {
            Response response = handler.httpPost(URL.SERVER_INTERFACE_URL,body).execute();
            String result=response.body().string();
            Log.d("Task_ChangeUserPassword","Result:"+result);
            Gson gson=new Gson();
            json_StandardReturn res=gson.fromJson(result,json_StandardReturn.class);
            Log.d("Task_ChangeUserPassword","Status:"+res.getStatus());
            Log.d("Task_ChangeUserPassword","Data  :"+res.getData());
            return res.getData();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
