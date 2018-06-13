package com.viewinject;

import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * 事件的代理
 * Created by zhangxuehui on 2018/6/13.
 */
public class EventProxyHandler implements InvocationHandler {
    private Object handler;
    private final static HashSet<String> AVOID_QUICK_EVENT_SET = new HashSet<String>(2);

    static {
        AVOID_QUICK_EVENT_SET.add("onClick");
        AVOID_QUICK_EVENT_SET.add("onItemClick");
    }

    //    拦截的方法名列表
    private Map<String, Method> methodMap = new HashMap<>();
    //    记录上次点击时间
    private long lastClickTime;
    private static final long QUICK_EVENT_TIME_SPAN = 500;

    public EventProxyHandler(Object handler) {
        this.handler = handler;
    }

    /**
     * 向拦截列表里添加拦截的方法
     */
    public void add(Method method) {
        methodMap.put(method.getName(), method);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object handler = this.handler;
        if (handler != null) {
            //tostring事件，不做处理
            String eventMethod = method.getName();
            if ("toString".equals(eventMethod)) {
                return EventProxyHandler.class.getSimpleName();
            }
            method = methodMap.get(eventMethod);
            //如果通过事件名称，无法获取事件方法，遍历缓存，获取方法
            if (method == null && methodMap.size() == 1) {
                for (Map.Entry<String, Method> entry : methodMap.entrySet()) {
                    if (TextUtils.isEmpty(entry.getKey())) {
                        method = entry.getValue();
                    }
                    break;
                }
            }
            if (method != null) {
                //禁止快速二次点击
                if (AVOID_QUICK_EVENT_SET.contains(eventMethod)) {
                    long timeSpan = System.currentTimeMillis() - lastClickTime;
                    if (timeSpan < QUICK_EVENT_TIME_SPAN) {
                        return null;
                    }
                    lastClickTime = System.currentTimeMillis();
                }

                try {
                    return method.invoke(handler, args);
                } catch (Throwable ex) {
                    throw new RuntimeException("invoke method error:" +
                            handler.getClass().getName() + "#" + method.getName(), ex);
                }
            } else {
                Log.w("EventProxyHandler", "method not impl: " + eventMethod + "(" + handler.getClass().getSimpleName() + ")");
            }
        }
        return null;
    }
}
