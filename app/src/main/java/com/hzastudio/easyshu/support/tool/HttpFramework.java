package com.hzastudio.easyshu.support.tool;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author Zean Huang
 * Github: https://github.com/thunderbird1997
 */

public class HttpFramework {

    /* *******************单例模式 ***********************/
    private static volatile HttpFramework instance;

    private HttpFramework() {
        OkHttpClientBuild();
    }

    public static HttpFramework getInstance() {
        if (instance == null) {
            synchronized (HttpFramework.class) {
                if (instance == null) {
                    instance = new HttpFramework();
                }
            }
        }
        return instance;
    }
    /* ************************************************* */

    /* OKHttp3 Client定义 */
    private static OkHttpClient client;
    /* ******************* */

    /**
     * OkHTTP Client 构造
     */
    private void OkHttpClientBuild(){
        client=new OkHttpClient.Builder()
                .connectTimeout(360, TimeUnit.SECONDS)
                .writeTimeout(360, TimeUnit.SECONDS)
                .readTimeout(360, TimeUnit.SECONDS)
                .build();
    }

    /**
     * Get同步请求
     * @param URL 网络地址
     * @return Call call对象
     */
    public Call httpGet(String URL) {
        Request request = new Request.Builder()
                .url(URL)
                .build();
        return client.newCall(request);
    }

    /**
     * POST 同步请求
     * @param URL 网络地址
     * @param requestBody 请求体
     * @return Call call对象
     */
    public Call httpPost(String URL, RequestBody requestBody){
        Request request = new Request.Builder()
                .url(URL)
                .post(requestBody)
                .build();
        return client.newCall(request);
    }

    /**
     * Get异步请求
     * @param URL 网络地址
     * @param callback 回调接口
     */
    public void httpGetCallback(String URL, Callback callback){
        Request request = new Request.Builder()
                .url(URL)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * POST 异步请求
     * @param URL 网络地址
     * @param requestBody 请求体
     * @param callback 回调接口
     */
    public void httpPostCallback(String URL, RequestBody requestBody, Callback callback){
        Request request = new Request.Builder()
                .url(URL)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

}
