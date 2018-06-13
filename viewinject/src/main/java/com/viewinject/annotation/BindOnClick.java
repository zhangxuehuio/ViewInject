package com.viewinject.annotation;

import android.support.annotation.IdRes;
import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BindOnClick {
    /**
     * 绑定事件的viewid
     *
     * @return
     */
    @IdRes int[] value();

    /**
     * 设置点击事件的类型
     *
     * @return
     */
    Class<?> type() default View.OnClickListener.class;

    /**
     * 点击事件的回到
     * @return
     */
    String method() default "setOnClickListener";
//   tv.setOnClickListener(new View.OnClickListener()
//
//    {
//        @Override
//        public void onClick (View v){
//
//    }
//    });
}
