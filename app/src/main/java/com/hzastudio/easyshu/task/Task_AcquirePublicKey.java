package com.hzastudio.easyshu.task;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.hzastudio.easyshu.support.json_lib.json_StandardReturn;
import com.hzastudio.easyshu.support.program_const.URL;
import com.hzastudio.easyshu.support.tool.HttpFramework;
import com.hzastudio.easyshu.support.universal.MainApplication;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Task_AcquirePublicKey extends AsyncTask<String,Void,Boolean> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }

    @Override
    protected Boolean doInBackground(String... params) {
        HttpFramework handler= HttpFramework.getInstance();
        RequestBody body=new FormBody.Builder()
                .add("action","user")
                .add("function","AcquirePublicKey")
                .add("data[usr]",params[0])
                .build();
        try {
            Response response = handler.httpPost(URL.SERVER_INTERFACE_URL,body).execute();
            String result=response.body().string();
            Log.d("sss","Result:"+result);
            Gson gson=new Gson();
            json_StandardReturn res=gson.fromJson(result,json_StandardReturn.class);
            Log.d("sss","Status:"+res.getStatus());
            Log.d("sss","Data  :"+res.getData());
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
                SharedPreferences sp = MainApplication.getContext().getSharedPreferences("data",
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
        return null;
    }
}
