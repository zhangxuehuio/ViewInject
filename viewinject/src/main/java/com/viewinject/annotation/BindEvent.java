package com.viewinject.annotation;

import android.support.annotation.IdRes;
import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zhangxuehui on 2018/6/13.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BindEvent {
    /**
     * 需要绑定的组件的id合集
     *
     * @return
     */
    @IdRes int[] value();

    /**
     * 设置监听事件的方法名称
     *
     * @return
     */
    String setMethod() default "setOnClickListener";

    /**
     * 设置事件的类型
     *
     * @return
     */
    Class<?> type() default View.OnClickListener.class;
    /**
     * 回调的方法名称
     *
     * @return
     */
    String method() default "onClick";
}
