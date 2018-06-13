package com.viewinject;

import android.app.Activity;
import android.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.viewinject.annotation.BindContentView;
import com.viewinject.annotation.BindOnClick;
import com.viewinject.annotation.BindView;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ViewInject {
    public static final HashSet<Class<?>> IGNORED = new HashSet<>();

    static {
        IGNORED.add(Object.class);
        IGNORED.add(Activity.class);
        IGNORED.add(android.app.Fragment.class);
        try {
            IGNORED.add(Class.forName("android.support.v4.app.Fragment"));
            IGNORED.add(Class.forName("android.support.v4.app.FragmentActivity"));
        } catch (Throwable ignored) {
        }
    }

    private static ViewInject INSTANCE;

    public static ViewInject getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ViewInject();
        }
        return INSTANCE;
    }

    public void inject(Activity target) {
        Class<?> cls = target.getClass();
        BindContentView contentView = findContentView(cls);
        try {
            //获取注解中的值
            if (contentView != null) {
                int layoutId = contentView.value();
                //获取方法
                if (layoutId > 0) {
                    Method setContentView = cls.getMethod("setContentView", int.class);
                    setContentView.invoke(target, layoutId);
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        View view = target.getWindow().getDecorView();
        inject(view, target);
    }

    public void inject(View rootview, Fragment target) {
        Class<?> cls = target.getClass();
    }

    public void inject(View rootview, Object target) {
        Class<?> cls = target.getClass();
        try {
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                BindView bindView = field.getAnnotation(BindView.class);
                if (bindView != null) {
                    int resId = bindView.value();
                    if (resId > 0) {
                        View view = rootview.findViewById(resId);
                        field.setAccessible(true);
                        field.set(target, view);
                        field.setAccessible(false);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        injectEevent(rootview, target);
    }

    /**
     * 绑定view
     *
     * @param target
     */
    public void injectEevent(View rootview, Object target) {
        Class<?> cls = target.getClass();
        Method[] methods = cls.getDeclaredMethods();
        for (Method method : methods) {
            BindOnClick onClick = method.getAnnotation(BindOnClick.class);
            if (onClick != null) {
                try {
                    int[] resIds = onClick.value();//获取目标控件的id
                    Class<?> methodType = onClick.type();//获取点击事件的类型
                    String methodName = onClick.method();//点击事件的方法名
                    for (int resId : resIds) {
                        View view = rootview.findViewById(resId);
                        DynamicHandler dynamicHandler = new DynamicHandler(view);
                        Method setEventLintener = view.getClass().getMethod(methodName, methodType);
                        setEventLintener.setAccessible(true);
                        dynamicHandler.addMethod(method.getName(),method);
                        Object lintener = Proxy.newProxyInstance(
                                methodType.getClassLoader(),
                                new Class<?>[]{methodType},dynamicHandler);
                        setEventLintener.invoke(view, lintener);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取contentView注解
     * 如果找不到注解，从他父类中去找；
     * 如果没有父类，或者父类不是对象，返回null
     *
     * @param cls
     * @return
     */
    private BindContentView findContentView(Class<?> cls) {
        if (cls == null || IGNORED.contains(cls)) {
            return null;
        } else {
            BindContentView contentView = cls.getAnnotation(BindContentView.class);
            if (contentView == null) {
                return findContentView(cls.getSuperclass());
            }
            return contentView;
        }
    }

    public static class DynamicHandler implements InvocationHandler {
        // 存放代理对象，比如Fragment或view holder
        private WeakReference<Object> handlerRef;
        // 存放代理方法
        private final HashMap<String, Method> methodMap = new HashMap<String, Method>(1);

        private static long lastClickTime = 0;

        public DynamicHandler(Object handler) {
            this.handlerRef = new WeakReference<Object>(handler);
        }

        public void addMethod(String name, Method method) {
            methodMap.put(name, method);
        }

        public Object getHandler() {
            return handlerRef.get();
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object handler = handlerRef.get();
            if (handler != null) {

                String eventMethod = method.getName();
                if ("toString".equals(eventMethod)) {
                    return DynamicHandler.class.getSimpleName();
                }

                method = methodMap.get(eventMethod);
                if (method == null && methodMap.size() == 1) {
                    for (Map.Entry<String, Method> entry : methodMap.entrySet()) {
                        if (TextUtils.isEmpty(entry.getKey())) {
                            method = entry.getValue();
                        }
                        break;
                    }
                }

                if (method != null) {

//                    if (AVOID_QUICK_EVENT_SET.contains(eventMethod)) {
//                        long timeSpan = System.currentTimeMillis() - lastClickTime;
//                        if (timeSpan < QUICK_EVENT_TIME_SPAN) {
//                            Log.w("aaaaa","onClick cancelled: " + timeSpan);
//                            return null;
//                        }
//                        lastClickTime = System.currentTimeMillis();
//                    }

                    try {
                        return method.invoke(handler, args);
                    } catch (Throwable ex) {
                        throw new RuntimeException("invoke method error:" +
                                handler.getClass().getName() + "#" + method.getName(), ex);
                    }
                } else {
                    Log.e("aaaaa","method not impl: " + eventMethod + "(" + handler.getClass().getSimpleName() + ")");
                }
            }
            return null;
        }
    }
}
