package com.common;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import java.util.HashMap;
import java.util.Iterator;

/**
 * @author sun 2015年10月10日 上午9:58:40
 * AsyncHttpClient与volley一样是异步网络库，但volley是封装的httpUrlConnection，
 * 它是封装的httpClient，而android平台不推荐用HttpClient了，所以这个库已经不适合android平台了。
 */
public class HttpUtils {
    private static AsyncHttpClient client = new AsyncHttpClient(); // 实例话对象
    private static String JSESSIONID; //定义一个静态的字段，保存sessionID

    static {
        client.setTimeout(15000); // 设置链接超时，如果不设置，默认为15s
        client.setMaxRetriesAndTimeout(3, 15000);
        client.setEnableRedirects(true);


    }

//	public static void initClient(Context c) {
//		PersistentCookieStore myCookieStore = new PersistentCookieStore(c);
//		client.setCookieStore(myCookieStore);
//	}

    public static AsyncHttpClient getClient() {
        return client;
    }


    /********************返回Sting结果的GET***********************/
    /**
     * 异步GET请求
     * 用一个完整url获取一个string对象
     *
     * @param res
     * @param url
     */
    public static void doGet(String url, AsyncHttpResponseHandler res) {
        client.get(url, res);
    }

    /**
     * url里面带参数
     *
     * @param url
     * @param params
     * @param res
     */
    public static void doGet(String url, RequestParams params, AsyncHttpResponseHandler res) {
        client.get(url, params, res);
    }


    /**
     * 下载数据使用，会返回byte数据
     *
     * @param url
     * @param bHandler
     */
    public static void doGet(String url, BinaryHttpResponseHandler bHandler) {
        client.get(url, bHandler);
    }


    /************************返回JSON对象结果的GET请求**************************/

    /**
     * 不带参数，获取json对象或者数组
     *
     * @param url
     * @param res
     */
    public static void doGet(String url, JsonHttpResponseHandler res) {
        client.get(url, res);
    }


    /**
     * 带参数，获取json对象或者数组
     *
     * @param url
     * @param params
     * @param res
     */
    public static void doGet(String url, RequestParams params, JsonHttpResponseHandler res) {
        client.get(url, params, res);
    }

    /**
     * 异步POST请求，返回String结果的方法
     *
     * @param res
     * @param url
     * @param requestParams
     */
    public static void doPost(String url, RequestParams requestParams, AsyncHttpResponseHandler res) {
        client.post(url, requestParams, res);
    }

    /**
     * 异步POST 请求，返回JSON对象结果的方法
     *
     * @param url
     * @param requestParams
     * @param res
     */
    public static void doPost(String url, RequestParams requestParams, JsonHttpResponseHandler res) {
        client.post(url, requestParams, res);
    }


    public static String iterateParams(HashMap<String, String> params) {
        String parameter = "";
        Iterator<String> iterator = params.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            parameter += "&" + key + "=" + params.get(key);
        }
        return parameter;
    }

}
