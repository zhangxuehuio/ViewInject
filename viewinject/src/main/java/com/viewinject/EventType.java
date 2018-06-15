package com.viewinject;

import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by zhangxuehui on 2018/6/13.
 */

public enum EventType {

    CLICK_LONG("setOnLongClickListener", View.OnLongClickListener.class),
    CLICK("setOnClickListener", View.OnClickListener.class),
    CLICK_ITEM("setOnItemClickListener", AdapterView.OnItemClickListener.class),
    TOUCH("setOnTouchListener", View.OnTouchListener.class),
    TEXT_WATCHER("addTextChangedListener", TextWatcher.class),
    LAYOUT_CHANGE("addOnLayoutChangeListener", View.OnLayoutChangeListener.class),;

    private String name;
    private Class<?> type;

    EventType(String name, Class<?> type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }
}
