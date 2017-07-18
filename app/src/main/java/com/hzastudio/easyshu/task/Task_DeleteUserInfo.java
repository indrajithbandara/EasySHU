package com.hzastudio.easyshu.task;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.hzastudio.easyshu.support.json_lib.json_StandardReturn;
import com.hzastudio.easyshu.support.program_const.URL;
import com.hzastudio.easyshu.support.tool.HttpFramework;
import com.hzastudio.easyshu.support.universal.MainApplication;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Task_DeleteUserInfo extends AsyncTask<String,Void,Boolean> {

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
                .add("function","DeleteUserInfo")
                .add("data[usr]",params[0])
                .add("data[pswconf]",params[1])
                .build();
        try {
            Response response = handler.httpPost(URL.SERVER_INTERFACE_URL,body).execute();
            String result=response.body().string();
            Log.d("sss","Result:"+result);
            Gson gson=new Gson();
            json_StandardReturn res=gson.fromJson(result,json_StandardReturn.class);
            Log.d("sss","Status:"+res.getStatus());
            Log.d("sss","Data  :"+res.getData());
            return res.getStatus().equals("1");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
