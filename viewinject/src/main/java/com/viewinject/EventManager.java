package com.viewinject;

import android.util.Log;
import android.view.View;

import com.viewinject.annotation.BindOnClick;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ConcurrentMap;

/**
 * 事件的代理
 * Created by zhangxuehui on 2018/6/13.
 */
public class EventManager {
    //    public static final ConcurrentMap<ViewInfo,Class<?>> listenerCache = new ConcurrentMap<ViewInfo, Class<?>>();
    public static final HashMap<ViewInfo, Class<?>> listenerCache = new HashMap<ViewInfo, Class<?>>();


    public static void addEventMethod(View view, BindOnClick onClick, Object target, Method method) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Class<?> methodType = onClick.type();//获取点击事件的类型
        String setMethodName = onClick.setMethodName();//点击事件的方法名
        EventProxyHandler proxyHandler = new EventProxyHandler(target);
        proxyHandler.add(method);
        Object lintener = Proxy.newProxyInstance(
                methodType.getClassLoader(),
                new Class[]{methodType},
                proxyHandler);
        Log.e("addEventMethod", lintener.getClass().getSimpleName());
        Method setOnClickListener = view.getClass().getMethod(setMethodName, methodType);
        setOnClickListener.setAccessible(true);
        setOnClickListener.invoke(view, lintener);
    }
}
