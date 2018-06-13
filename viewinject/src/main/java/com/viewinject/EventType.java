package com.viewinject;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by zhangxuehui on 2018/6/13.
 */

public enum EventType {

    CLICK_LONG("setOnLongClickListener", View.OnLongClickListener.class),
    CLICK("setOnClickListener", View.OnClickListener.class),
    TOUCH("setOnTouchListener", View.OnTouchListener.class),
    TEXT_Watcher("addTextChangedListener", TextWatcher.class),
    LAYOUT_CHANGE("addOnLayoutChangeListener", View.OnLayoutChangeListener.class);

    private String methodName;
    private Class<?> methodType;

    EventType(String methodName, Class<?> methodType) {
        this.methodName = methodName;
        this.methodType = methodType;

    }
}
