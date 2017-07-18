package com.hzastudio.easyshu.task;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.hzastudio.easyshu.support.json_lib.json_StandardReturn;
import com.hzastudio.easyshu.support.program_const.URL;
import com.hzastudio.easyshu.support.tool.HttpFramework;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Task_CheckIfUserExist extends AsyncTask<String,Void,Boolean> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(String... params) {
        HttpFramework handler= HttpFramework.getInstance();
        RequestBody body=new FormBody.Builder()
                             .add("action","user")
                             .add("function","CheckIfUserExist")
                             .add("data[usr]",params[0])
                             .build();
        try {
            Response response = handler.httpPost(URL.SERVER_INTERFACE_URL,body).execute();
            String result=response.body().string();
            Gson gson=new Gson();
            json_StandardReturn res=gson.fromJson(result,json_StandardReturn.class);
            return res.getStatus().equals("1");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
