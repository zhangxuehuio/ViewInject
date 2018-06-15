package com.viewinject;

import android.view.View;

import com.viewinject.annotation.BindEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 事件的代理
 * Created by zhangxuehui on 2018/6/13.
 */
public class EventManager {

    public static void addEventMethod(View view, BindEvent event, Object target, Method method) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        EventType eventType = event.eventType();
        Class<?> methodType = eventType.getType();//获取点击事件的类型
        String setMethodName = eventType.getName();//点击事件的方法名
        EventProxyHandler proxyHandler = new EventProxyHandler(target);
        proxyHandler.add(method);
        Object lintener = Proxy.newProxyInstance(
                methodType.getClassLoader(),
                new Class[]{methodType},
                proxyHandler);
        Method setOnClickListener = view.getClass().getMethod(setMethodName, methodType);
        setOnClickListener.setAccessible(true);
        setOnClickListener.invoke(view, lintener);
    }
}
