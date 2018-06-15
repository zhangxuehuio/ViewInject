package com.viewinject.annotation;

import android.support.annotation.IdRes;
import android.view.View;

import com.viewinject.EventType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.viewinject.EventType.CLICK;

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
    EventType eventType() default CLICK;

    /**
     * 回调的方法名称
     *
     * @return
     */
    String method() default "onClick";
}
