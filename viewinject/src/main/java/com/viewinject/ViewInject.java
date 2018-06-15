package com.viewinject;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viewinject.annotation.BindContentView;
import com.viewinject.annotation.BindEvent;
import com.viewinject.annotation.BindView;
import com.viewinject.utils.LogUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;

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
    private static final Object lock = new Object();

    public static ViewInject getInstance() {
        if (INSTANCE == null) {
            synchronized (lock) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewInject();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 绑定contentVIew
     *
     * @param activity
     */
    public void inject(Activity activity) {
        Class<?> activityClass = activity.getClass();
        if (activityClass == null || IGNORED.contains(activityClass)) {
            return;
        }
        BindContentView contentView = findContentView(activityClass);
        try {
            //获取注解中的值
            if (contentView != null) {
                int layoutId = contentView.value();
                //获取方法
                if (layoutId > 0) {
                    Method setContentView = activityClass.getMethod("setContentView", int.class);
                    setContentView.invoke(activity, layoutId);
                }
            }
        } catch (Throwable ignored) {
            LogUtils.e(activity, ignored.getLocalizedMessage() + "");
        }
        injectObject(activity, activityClass, new ViewHelper(activity));
    }

    public void inject(Object target, View view) {
        injectObject(target, target.getClass(), new ViewHelper(view));
    }

    /**
     * fragment中绑定contentVIew
     *
     * @param fragment
     * @param inflater
     * @param container
     */
    public View inject(Object fragment, LayoutInflater inflater, ViewGroup container) {
        Class<?> fragmentClass = fragment.getClass();
        BindContentView contentView = findContentView(fragmentClass);
        View view = null;
        try {
            //获取注解中的值
            if (contentView != null) {
                int layoutId = contentView.value();
                //获取方法
                if (layoutId > 0) {
                    view = inflater.inflate(layoutId, container, false);
                }
            }
        } catch (Throwable ignored) {
            LogUtils.e(fragment, ignored.getLocalizedMessage() + "");
        }
        injectObject(fragment, fragmentClass, new ViewHelper(view));
        return view;
    }

    /**
     * 绑定view
     *
     * @param target
     */
    public static void injectObject(Object target, Class<?> targetCls, ViewHelper helper) {
        //从父类轮询注解数据

        if (targetCls == null || IGNORED.contains(targetCls)) {
            return;
        }
        LogUtils.e(target, targetCls.getSuperclass().getName() + "");
        injectObject(target, targetCls.getSuperclass(), helper);
        try {
            Field[] fields = targetCls.getDeclaredFields();
            if (fields != null || fields.length > 0) {
                for (Field field : fields) {
                    Class<?> fieldType = field.getType();
                    if (
                            /* 不注入静态字段 */
                            Modifier.isStatic(field.getModifiers()) ||
                            /* 不注入final字段 */
                                    Modifier.isFinal(field.getModifiers()) ||
                            /* 不注入基本类型字段 */
                                    fieldType.isPrimitive() ||
                            /* 不注入数组类型字段 */
                                    fieldType.isArray()) {
                        continue;
                    }
                    BindView bindView = field.getAnnotation(BindView.class);
                    if (bindView != null) {
                        int resId = bindView.value();
                        if (resId > 0) {
                            View view = helper.findViewById(resId);
                            if (view != null) {
                                field.setAccessible(true);
                                field.set(target, view);
                                field.setAccessible(false);
                            } else {
                                throw new RuntimeException("Invalid @BindView for "
                                        + targetCls.getSimpleName() + "." + field.getName());
                            }
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Method[] methods = targetCls.getDeclaredMethods();
        if (methods != null || methods.length > 0) {
            for (Method method : methods) {
                BindEvent onClick = method.getAnnotation(BindEvent.class);
                if (onClick != null) {
                    try {
                        int[] resIds = onClick.value();//获取目标控件的id
                        for (int resId : resIds) {
                            if (resId > 0) {
                                View view = helper.findViewById(resId);
                                EventManager.addEventMethod(view, onClick, target, method);
                            } else {
                                throw new Exception("BindEevent Error：resId is wrong! ");
                            }
                        }
                        method.setAccessible(false);
                    } catch (Throwable ex) {
                        LogUtils.e("ta", ex.getLocalizedMessage() + "");
                    }
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


}
