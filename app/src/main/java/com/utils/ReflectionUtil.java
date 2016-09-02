package com.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;

/**
 * Created by David on 16/9/2.
 */
public class ReflectionUtil {
    public ReflectionUtil() {
    }

    /**
     * 生成对象。
     * @param clazz :指明是哪个类。
     * @return
     */
    public static Object generateObject(Class<?> clazz) {
        try {
            Constructor e = clazz.getConstructor(new Class[0]);
            return e.newInstance(new Object[0]);
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param clazz
     * @param paramType
     * @param args
     * @return
     */
    public static Object generateObject(Class<?> clazz, Class<?> paramType, Object... args) {
        try {
            Constructor e = clazz.getConstructor(new Class[]{paramType});
            return e.newInstance(args);
        } catch (Exception var4) {
            var4.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param clazz
     * @param paramType1
     * @param paramType2
     * @param args
     * @return
     */
    public static Object generateObject(Class<?> clazz, Class<?> paramType1, Class<?> paramType2, Object... args) {
        try {
            Constructor e = clazz.getConstructor(new Class[]{paramType1, paramType2});
            return e.newInstance(args);
        } catch (Exception var5) {
            var5.printStackTrace();
            return null;
        }
    }

    public static String getGetMethodName(String cName) {
        String start = cName.substring(0, 1).toUpperCase(Locale.getDefault());
        String end = cName.substring(1);
        return "get" + start + end;
    }

    public static Object invokeGetMethod(Object o, String methodName) {
        String result = "";

        try {
            Method e = o.getClass().getMethod(methodName, new Class[0]);
            return e.invoke(o, new Object[0]);
        } catch (Exception var4) {
            var4.printStackTrace();
            return result;
        }
    }

    public static Object getStaticFieldValue(Class<?> clazz, String fieldName) {
        String result = "";

        try {
            Field e = clazz.getField(fieldName);
            return e.get(clazz);
        } catch (Exception var4) {
            var4.printStackTrace();
            return result;
        }
    }

    public static Object getFieldValue(Object obj, String fieldName) {
        String result = "";

        try {
            Field e = obj.getClass().getDeclaredField(fieldName);
            e.setAccessible(true);
            return e.get(obj);
        } catch (Exception var4) {
            var4.printStackTrace();
            return result;
        }
    }

}
