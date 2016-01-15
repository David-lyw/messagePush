package com.common;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Gson 解析的util工具类
 * Created by sun on 2015/10/10.
 */
public class GsonUtils {
    /**
     * 实现单例
     */
    private static Gson gson = null;

    static {
        if (gson == null) {
            gson = new Gson();
        }
    }

    /**
     * 隐藏默认的构造方法
     */
    private GsonUtils() {

    }

    /**
     * 将对象转换成json格式
     *
     * @param ts
     * @return
     */
    public static String objectToJson(Object ts) {
        String jsonStr = null;
        if (gson != null) {
            jsonStr = gson.toJson(ts);
        }
        return jsonStr;
    }

    /**
     * ----lyw
     * 返回字符串类型的ArrayList
     * 数据格式：{\"messageList\": [\"123123\", \"45654654\"]}
     *
     * @param s
     * @param
     * @return
     */

    public static ArrayList<String> jsonToStringList(String s) {
        List<String> ls = new ArrayList<String>();
        JSONObject demojson;
        JSONArray ss;
        try {
            demojson = new JSONObject(s);
            ss = demojson.getJSONArray("messageList");
            for (int i = 0; i < ss.length(); i++) {
                String str = ss.getString(i);
                ls.add(str);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return (ArrayList) ls;
    }

    /**
     * 返回cla 类型的list数组
     *
     * @param s
     * @param cla
     * @return
     */
    public static <T extends Object> T jsonToBeanList(String s, Class<?> cla) {
        List<Object> ls = new ArrayList<Object>();
        JSONArray ss;
        try {
            ss = new JSONArray(s);
            for (int i = 0; i < ss.length(); i++) {
                String str = ss.getString(i);
                Object a = jsonToBean(str, cla);
                ls.add(a);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return (T) ls;
    }

    /**
     * JSONArray 转成List<T> 的方法
     *
     * @param ss
     * @param cla
     * @param <T>
     * @return
     */

    public static <T extends Object> List<T> jsonToBeanList(JSONArray ss, Class<?> cla) {
        List<T> ls = new ArrayList<T>();
        try {
            for (int i = 0; i < ss.length(); i++) {
                String str = ss.getString(i);
                T a = jsonToBean(str, cla);
                ls.add(a);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ls;
    }


    /**
     * 返回cla 类型的list数组
     *
     * @param s
     * @param cla
     * @return
     */
    public static <T extends Object> T jsonToBeanList(String s, String cla) {

        List<Object> ls = new ArrayList<Object>();
        JSONArray ss;
        try {
            ss = new JSONArray(s);
            for (int i = 0; i < ss.length(); i++) {
                String str = ss.getString(i);
                Object a = jsonToBean(str, cla);
                ls.add(a);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return (T) ls;
    }

    /**
     * 将jsonStr转换成cl对象
     *
     * @param jsonStr
     * @return
     */
    public static <T extends Object> T jsonToBean(String jsonStr, Class<?> cl) {
        Object obj = null;
        if (gson != null) {
            if (!TextUtils.isEmpty(jsonStr))
                obj = gson.fromJson(jsonStr, cl);
        }
        return (T) obj;
    }


    /**
     * 根据
     *
     * @param jsonStr
     * @param classType
     * @param <T>
     * @return
     */
    public static <T extends Object> T jsonToBean(String jsonStr, String classType) {

        Class c = null;
        try {
            c = Class.forName(classType);
        } catch (ClassNotFoundException e) {
            c = Object.class;
            e.printStackTrace();
        }
        return (T) jsonToBean(jsonStr, c);
    }


    public static <T extends Object> T json2b(String jsonStr, String classType) {


        if (jsonStr.trim().startsWith("[")) {
            return jsonToBeanList(jsonStr, classType);

        } else {
            return jsonToBean(jsonStr, classType);
        }


    }

    /**
     * 根据 给的 jsonStr 自动的生成对象或者数组.
     *
     * @param jsonStr
     * @param classType
     * @param <T>
     * @return
     */
    public static <T extends Object> T json2b(String jsonStr, Class classType) {


        if (jsonStr.trim().startsWith("[")) {
            return (T) jsonToBeanList(jsonStr, classType);

        } else {
            return (T) jsonToBean(jsonStr, classType);
        }


    }


    /**
     * 将json格式转换成map对象
     *
     * @param jsonStr
     * @return
     */
    public static Map<?, ?> jsonToMap(String jsonStr) {
        Map<?, ?> objMap = null;
        if (gson != null) {
            java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<Map<?, ?>>() {
            }.getType();
            objMap = gson.fromJson(jsonStr, type);
        }
        return objMap;
    }


}
